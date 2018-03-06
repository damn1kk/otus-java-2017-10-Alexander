package ru.otus.homework15.messageSystem;

import ru.otus.homework15.messageSystem.messages.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageSystem {
    private final static Logger logger = Logger.getLogger(MessageSystem.class.getName());

    private static final int DEFAULT_STEP_TIME = 10;

    private final List<Thread> workers;
    private final Map<Address, ConcurrentLinkedQueue<Message>> messageMap;
    private final Map<Address, Addressee> addresseeMap;

    public MessageSystem(){
        workers = new ArrayList<>();
        messageMap = new HashMap<>();
        addresseeMap = new HashMap<>();
    }

    public void addAddressee(Addressee addressee){
        addresseeMap.put(addressee.getAddress(), addressee);
        messageMap.put(addressee.getAddress(), new ConcurrentLinkedQueue<>());
    }

    public void sendMessage(Message message){
        messageMap.get(message.getTo()).add(message);
    }

    public void start(){
        for (Map.Entry<Address, Addressee> entry : addresseeMap.entrySet()){
            String threadName = "MS-worker-" + entry.getKey().getId();

            Thread thread = new Thread(() -> {
               while(true){
                    ConcurrentLinkedQueue<Message> queue = messageMap.get(entry.getKey());
                    while(!queue.isEmpty()){
                        Message message = queue.poll();
                        message.exec(entry.getValue());
                    }
                    try{
                        Thread.sleep(MessageSystem.DEFAULT_STEP_TIME);
                    }catch(InterruptedException e){
                        logger.log(Level.INFO, "Thread interrupted. Finishing: " + threadName);
                        return;
                    }
                    if(Thread.currentThread().isInterrupted()){
                        logger.log(Level.INFO, "Finishing: " + threadName);
                        return;
                    }
               }
            });

            thread.setName(threadName);
            thread.start();
            workers.add(thread);
        }
    }

    public void dispose(){
        workers.forEach(Thread::interrupt);
    }
}
