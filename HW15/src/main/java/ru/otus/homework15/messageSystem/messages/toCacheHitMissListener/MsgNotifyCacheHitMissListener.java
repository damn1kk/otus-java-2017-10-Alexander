package ru.otus.homework15.messageSystem.messages.toCacheHitMissListener;

import ru.otus.homework15.database.cach.CacheHitMissListener;
import ru.otus.homework15.messageSystem.Address;

public class MsgNotifyCacheHitMissListener extends MsgToCacheHitMissListener {
    private int cacheHit;
    private int cacheMiss;

    public MsgNotifyCacheHitMissListener(Address from, Address to, int cacheHit, int cacheMiss){
        super(from, to);
        this.cacheHit = cacheHit;
        this.cacheMiss = cacheMiss;
    }

    @Override
    public void exec(CacheHitMissListener listener) {
        listener.cacheHitMissChanged(cacheHit, cacheMiss);
    }
}
