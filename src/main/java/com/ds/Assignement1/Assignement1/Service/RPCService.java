package com.ds.Assignement1.Assignement1.Service;

import com.ds.Assignement1.Assignement1.Dto.ProgramStartTimeAndBaseLine;
import com.ds.Assignement1.Assignement1.Dto.RPCResponse;
import com.ds.Assignement1.Assignement1.Model.SensorData;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@JsonRpcService("/rpc")
public interface RPCService {
    List<SensorData> getSensorDataBySensorId(@JsonRpcParam(value = "id") Long id);
    List<RPCResponse> getAverage(@JsonRpcParam(value="deviceId") Long id, @JsonRpcParam(value="daysNumber") Integer daysNumber);
    ArrayList<ArrayList<RPCResponse>> getHourlyHistoricalEnergy(@JsonRpcParam(value="deviceId") Long id, @JsonRpcParam(value="daysNumber") Integer daysNumber);
    ArrayList<RPCResponse> getBaseLine(@JsonRpcParam(value = "deviceId") Long deviceId);
    ProgramStartTimeAndBaseLine getProgram (@JsonRpcParam(value = "deviceId") Long deviceId, @JsonRpcParam(value = "time")Integer hour );
}