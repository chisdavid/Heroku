package com.ds.Assignement1.Assignement1.Service.Implementation;

import com.ds.Assignement1.Assignement1.Dto.ProgramStartTimeAndBaseLine;
import com.ds.Assignement1.Assignement1.Dto.RPCResponse;
import com.ds.Assignement1.Assignement1.Model.Device;
import com.ds.Assignement1.Assignement1.Model.Sensor;
import com.ds.Assignement1.Assignement1.Model.SensorData;
import com.ds.Assignement1.Assignement1.Repository.DeviceRepository;
import com.ds.Assignement1.Assignement1.Repository.SensorRepository;
import com.ds.Assignement1.Assignement1.Service.RPCService;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.ds.Assignement1.Assignement1.Utils.Utils.*;

@Service
@AutoJsonRpcServiceImpl
@RequiredArgsConstructor
public class RPCServiceImplementation implements RPCService {

    public final SensorRepository sensorRepository;
    public final DeviceRepository deviceRepository;

    @Override
    public List<SensorData> getSensorDataBySensorId(Long id) {
        Sensor sensor = sensorRepository.findFirstById(id);
        if (sensor == null)
            return null;
        return sensor.getDataList();
    }

    @Override
    public List<RPCResponse> getAverage(Long id, Integer daysNumber) {
        Device device = deviceRepository.findFirstById(id);

        List<RPCResponse> response = new ArrayList<>();
        if (device == null)
            return null;

        int day = daysNumber.intValue();
        if (LocalDateTime.now().getDayOfMonth() > daysNumber) {
            LocalDateTime localDateTime = LocalDateTime.now();

            while (day > 0) {
                response.add(new RPCResponse("Day" + (daysNumber.intValue() - day) + getKey(localDateTime.toString()), getSensorDataAverageByDate(device, localDateTime)));
                localDateTime = localDateTime.minusDays(1);
                day--;
            }
        }
        return response;
    }

    @Override
    public ArrayList<ArrayList<RPCResponse>> getHourlyHistoricalEnergy(Long id, Integer daysNumber) {
        ArrayList<ArrayList<RPCResponse>> result = new ArrayList<ArrayList<RPCResponse>>();
        Device device = deviceRepository.findFirstById(id);

        if (device == null)
            return null;
        int day = daysNumber.intValue();


            LocalDateTime localDateTime = LocalDateTime.now();

            while (day > 0) {
                result.add(getSensorDataByDateHourly(device, localDateTime));
                localDateTime = localDateTime.minusDays(1);
                day--;
            }

        return result;
    }

    @Override
    public ArrayList<RPCResponse> getBaseLine(Long deviceID) {

        ArrayList<RPCResponse> responses = new ArrayList<>();
        Device device = deviceRepository.findFirstById(deviceID);
        for (int hour = 0; hour < 24; hour++) {
            int day = 7;
            LocalDateTime localDateTime = LocalDateTime.now();
            Double value = 0D;
            while (day > 0) {
                value += getBaselineByHour(device, localDateTime, hour);
                localDateTime = localDateTime.minusDays(1);
                day--;
            }
            System.out.println(value);
            responses.add(new RPCResponse(hour + ":00", value / 7.0));
        }
        return responses;
    }

    @Override
    public ProgramStartTimeAndBaseLine getProgram(Long deviceId, Integer hour) {
        Device device = deviceRepository.findFirstById(deviceId);
        List<RPCResponse> baseLine = getBaseLine(deviceId);
        List<Double> baseLineValues = baseLine.stream().map(i -> i.getValue()).collect(Collectors.toList());
        Map<Integer, Double> result = new HashMap<>();
        Integer startTime = 0;
        for (int i = 0; i < 24; i++) {
            ArrayList<Double> arrayList = new ArrayList<>();
            Double maxim = 0D;
            for (int j = i; j < i + hour; j++) {
                if (i + j > 23 || i + hour > 23 || baseLineValues.get(j) == 0) {
                    break;
                }
                Double value = 0D;
                value = baseLineValues.get(j) + device.getMaximumEnergyConsumption();
                if (value > maxim) {
                    maxim = value;
                }
            }
            result.put(i, maxim);
        }

        startTime = Collections.min(result.entrySet()
                        .stream()
                        .filter(i -> i.getValue() > 0)
                        .collect(Collectors.toMap(i -> i.getKey(), i -> i.getValue()))
                        .entrySet()
                , Comparator.comparingDouble(Map.Entry::getValue)).getKey();

        return ProgramStartTimeAndBaseLine.builder()
                .startTime(startTime)
                .baseline(updateBaseLine(baseLine,startTime,hour,Double.parseDouble(device.getMaximumEnergyConsumption()+"")))
                .build();
    }

    private List<RPCResponse> updateBaseLine(List<RPCResponse> baseline, Integer startTime, Integer hour, Double deviceValue) {
        List<RPCResponse> response = new ArrayList<>();
        for (RPCResponse i : baseline) {
            int index = baseline.indexOf(i);
            Double addedValue = i.getValue();
            if (index >= startTime.intValue() && index <= startTime.intValue() + hour) {
                addedValue +=deviceValue;
            }
            response.add(new RPCResponse(i.getName(),addedValue));
        }
        return response;
    }
}