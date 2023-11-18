package com.ds.Assignement1.Assignement1.Service;

import org.springframework.stereotype.Component;

@Component
public interface SocketService {
    void SendMessage (Long ClientId,String message);
}
