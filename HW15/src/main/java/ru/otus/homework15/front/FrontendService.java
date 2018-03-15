package ru.otus.homework15.front;

import ru.otus.homework15.messageSystem.Addressee;

public interface FrontendService extends Addressee {
    public void sendQueryStringResultToClients(String result);
}
