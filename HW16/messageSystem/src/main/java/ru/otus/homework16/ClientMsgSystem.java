package ru.otus.homework16;

import ru.otus.homework16.addressee.Addressee;
import ru.otus.homework16.addressee.TypeOfAddressee;
import ru.otus.homework16.msg.Msg;
import ru.otus.homework16.msg.RegisterMsg;
import ru.otus.homework16.workers.ClientSocketMsgWorker;
import ru.otus.homework16.workers.SocketMsgWorker;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ClientMsgSystem implements Addressee{
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    protected static final String SERVER_NAME = "Server";

    protected String msId;
    protected TypeOfAddressee typeOfAddressee;
    protected boolean isRegistered;
    protected SocketMsgWorker msgWorker;

    public ClientMsgSystem(TypeOfAddressee typeOfAddressee){
        this.typeOfAddressee = typeOfAddressee;
    }

    public ClientMsgSystem(String msId, TypeOfAddressee typeOfAddressee){
        this.msId = msId;
        this.typeOfAddressee = typeOfAddressee;
    }

    public void connectToServerBySocket(String host, int port){
        try {
            msgWorker = new ClientSocketMsgWorker(host, port);
            msgWorker.init();
            logger.info("Msg service started");

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(this::handleMsg);

            if(msId != null) {
                msgWorker.send(new RegisterMsg(msId, SERVER_NAME, typeOfAddressee));
            }else{
                msgWorker.send(new RegisterMsg(getClass().getName(), SERVER_NAME, typeOfAddressee));
            }
        }catch(IOException e){
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    protected abstract void handleMsg();

    protected void sendMessage(Msg message) throws Exception{
        if(isRegistered) {
            msgWorker.send(message);
        }else{
            throw new Exception("This service unregistered into messageSystem");
        }
    }

    @Override
    public String getMsId() {
        return msId;
    }

    @Override
    public void setMsId(String msId){
        this.msId = msId;
    }

    @Override
    public TypeOfAddressee getTypeOfAddressee(){
        return typeOfAddressee;
    }

    public boolean isRegistered() {
        return isRegistered;
    }
}
