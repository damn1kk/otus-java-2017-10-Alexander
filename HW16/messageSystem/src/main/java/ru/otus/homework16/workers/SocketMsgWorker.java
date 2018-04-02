package ru.otus.homework16.workers;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.otus.homework16.msg.Msg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketMsgWorker implements MsgWorker {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private static final int WORKERS_COUNT = 2;

    private final BlockingQueue<Msg> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Msg> input = new LinkedBlockingQueue<>();

    private final ExecutorService executor;
    private final Socket socket;

    public SocketMsgWorker(Socket socket){
        this.socket = socket;
        this.executor = Executors.newFixedThreadPool(WORKERS_COUNT);
    }

    public void init(){
        executor.execute(this::sendMessage);
        executor.execute(this::receiveMessage);
    }

    @Override
    public void send(Msg msg) {
        logger.info("try to send message:" + msg);
        output.add(msg);
    }

    @Override
    public Msg poll() {
        return input.poll();
    }

    @Override
    public Msg take() throws InterruptedException {
        return input.take();
    }

    @Override
    public void close() throws IOException {
        executor.shutdown();
        socket.close();
    }

    private void sendMessage(){
        while(true){
            try(PrintWriter out = new PrintWriter(socket.getOutputStream(), true)){
                while(socket.isConnected()) {
                    Msg msg = output.take();
                    String json = new Gson().toJson(msg);
                    out.println(json);
                    out.println();
                }
            }catch(IOException | InterruptedException e){
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    private void receiveMessage() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) {
                    String json = stringBuilder.toString();
                    Msg msg = getMsgFromJSON(json);
                    input.add(msg);
                    stringBuilder = new StringBuilder();
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Problem with socket connection or socket inputStream", e);
        }catch(ParseException | ClassNotFoundException e){
            logger.log(Level.SEVERE, "Can not parse JSON to MSG", e);
        }finally{
            executor.shutdown();
            try {
                socket.close();
            }catch(IOException e){
                logger.log(Level.SEVERE, e.getMessage(), e);
            }

        }

    }

    private static Msg getMsgFromJSON(String json) throws ParseException, ClassNotFoundException{
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        String className = (String) jsonObject.get(Msg.CLASS_NAME_VARIABLE);
        Class<?> msgClass = Class.forName(className);
        return (Msg) new Gson().fromJson(json, msgClass);
    }
}
