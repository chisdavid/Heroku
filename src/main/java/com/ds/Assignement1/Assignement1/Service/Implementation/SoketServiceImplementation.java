package com.ds.Assignement1.Assignement1.Service.Implementation;

import com.ds.Assignement1.Assignement1.Service.SocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static com.ds.Assignement1.Assignement1.Utils.Constants.CLIENT_ENDPOINT;

@Service
public class SoketServiceImplementation implements SocketService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void SendMessage(Long ClientId, String message) {
        simpMessagingTemplate.convertAndSend(CLIENT_ENDPOINT + ClientId, message);
    }
}
