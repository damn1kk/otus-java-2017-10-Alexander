package ru.otus.homework16;

import ru.otus.homework16.processRunner.ProcessRunner;
import ru.otus.homework16.processRunner.ProcessRunnerImpl;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ProcessManager {
    private static final String DBSERVICE_START_COMMAND = "java -jar ../DBService/target/DBService.jar";
    private static final String FRONTENDSERVICE_START_COMMAND = "java -jar ../frontendService/target/FrontendService.jar";

    private final Set<ProcessRunner> allProcess = new CopyOnWriteArraySet<>();

    public void startDBService() throws IOException{
        ProcessRunner pr = new ProcessRunnerImpl();
        pr.start(DBSERVICE_START_COMMAND);
        allProcess.add(pr);
    }

    public void startDBService(String processName) throws IOException{
        ProcessRunner pr = new ProcessRunnerImpl();
        pr.start(DBSERVICE_START_COMMAND + " " + processName);
        allProcess.add(pr);
    }

    public void startFrontendService() throws IOException{
        ProcessRunner pr = new ProcessRunnerImpl();
        pr.start(FRONTENDSERVICE_START_COMMAND);
        allProcess.add(pr);
    }

    public void startFrontendService(int port) throws IOException{
        ProcessRunner pr = new ProcessRunnerImpl();
        pr.start(FRONTENDSERVICE_START_COMMAND + " " + port);
        allProcess.add(pr);
    }

    public void stopAllProcess(){
        for(ProcessRunner pr : allProcess){
            pr.stop();
        }
    }

    public void stopProcess(String processName){
        //todo: think how to do it
    }

    public String getOutput(){
        String result ="";
        for(ProcessRunner p : allProcess){
            return result += p.getOutput();
        }
        return result;
    }
}
