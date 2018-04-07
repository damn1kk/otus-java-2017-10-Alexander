package ru.otus.homework16.workers;

import ru.otus.homework16.msg.Msg;

import java.io.IOException;

public interface    MsgWorker {
    void send(Msg msg);
    Msg poll();
    Msg take() throws InterruptedException;
    void close() throws IOException;
}
