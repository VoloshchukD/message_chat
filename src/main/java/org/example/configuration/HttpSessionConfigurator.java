package org.example.configuration;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import org.example.entity.Constants;

public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator {

    public void modifyHandshake(ServerEndpointConfig config,
                                HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        config.getUserProperties().put(Constants.HTTP_SESSION, httpSession);
    }

}