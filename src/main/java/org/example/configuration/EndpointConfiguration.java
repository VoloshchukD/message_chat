package org.example.configuration;

import jakarta.websocket.Endpoint;
import jakarta.websocket.server.ServerApplicationConfig;
import jakarta.websocket.server.ServerEndpointConfig;
import org.example.entity.Constants;
import org.example.socket.Socket;

import java.util.HashSet;
import java.util.Set;

public class EndpointConfiguration implements ServerApplicationConfig {

    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> set) {
        Set<ServerEndpointConfig> result = new HashSet<>();

        if (set.contains(Socket.class)) {
            result.add(ServerEndpointConfig.Builder.create(
                            Socket.class,
                            Constants.CHAT_ENDPOINT)
                    .configurator(new HttpSessionConfigurator())
                    .build());
        }
        return result;
    }

    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> set) {
        return Set.of();
    }
}
