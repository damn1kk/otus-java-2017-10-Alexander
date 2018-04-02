package ru.otus.homework16;

import javax.websocket.server.ServerEndpointConfig;

public class MyConfigurator extends ServerEndpointConfig.Configurator {
    private static final FrontendServiceMsgClient ENDPOINT = new FrontendServiceMsgClient();

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        if(FrontendServiceMsgClient.class.equals(endpointClass)){
            return (T) ENDPOINT;
        }else{
            throw new InstantiationException();
        }
    }
}
