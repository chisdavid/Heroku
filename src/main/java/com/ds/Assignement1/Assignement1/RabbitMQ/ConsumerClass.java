package com.ds.Assignement1.Assignement1.RabbitMQ;

import com.ds.Assignement1.Assignement1.Dto.AddSensorData;
import com.ds.Assignement1.Assignement1.Model.Sensor;
import com.ds.Assignement1.Assignement1.Model.SensorData;
import com.ds.Assignement1.Assignement1.Repository.SensorRepository;
import com.ds.Assignement1.Assignement1.Service.Implementation.RoleServiceImplementation;
import com.ds.Assignement1.Assignement1.Service.Implementation.SensorServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
public class ConsumerClass {

    @Autowired
    private SensorServiceImplementation sensorServiceImplementation;

    @Autowired
    private SensorRepository sensorRepository;

//    @Bean
//    public void Consumer() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {
//        String URI = "amqps://hstxfzri:VjGRBiIpdgPB-ZAWVhAEci-boT7QUlUT@roedeer.rmq.cloudamqp.com/hstxfzri";
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setUri(URI);
//
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//        String queue = "queue";     //queue name
//        boolean durable = false;    //durable - RabbitMQ will never lose the queue if a crash occurs
//        boolean exclusive = false;  //exclusive - if queue only will be used by one connection
//        boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes
//
//        channel.queueDeclare(queue, durable, exclusive, autoDelete, null);
//        factory.setConnectionTimeout(300000);
//
//        ObjectMapper mapper = new ObjectMapper();
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//
//            String message = new String(delivery.getBody(), "UTF-8");
//            Map<String, Object> map = mapper.readValue(message, Map.class);
//            AddSensorData addSensorData = AddSensorData.builder()
//                    .sensorId(Long.valueOf(map.get("sensorId").toString()))
//                    .value(Double.valueOf(map.get("value").toString()))
//                    .date(LocalDateTime.now())
//                    .build();
//            System.out.println(addSensorData);
//
//            sensorServiceImplementation.addData(addSensorData);
//
//        };
//        channel.basicConsume(queue, true, deliverCallback, consumerTag -> {});
//    }
}
