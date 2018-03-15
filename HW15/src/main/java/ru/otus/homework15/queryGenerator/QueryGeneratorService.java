package ru.otus.homework15.queryGenerator;

import ru.otus.homework15.messageSystem.Addressee;

import java.io.Serializable;
import java.util.List;

public interface QueryGeneratorService <T, ID extends Serializable> extends Addressee{
    public boolean isStarted();
    public void start();
    public void stop();
    public void doRandomQuery();

    public void doFindAllQuery();
    public void doFindByRandomIdQuery();
    public void doSaveNewDataSetQuery();
    public void doUpdateDataSetQuery();
    public void doDeleteByRandomIdQuery();

    public void sendStringResultToFrontendService(String result);

    public void addGeneratorStatusListener(GeneratorStatusListener listener);
    public void deleteGeneratorStatusListener(GeneratorStatusListener listener);
}
