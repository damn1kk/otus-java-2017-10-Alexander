package ru.otus.homework15.messageSystem.messages.toCacheService;

import ru.otus.homework15.database.cach.CacheHitMissListener;
import ru.otus.homework15.database.dbService.CachedService;
import ru.otus.homework15.messageSystem.Address;

public class MsgAddCacheHitMissListener extends MsgToCache {
    private CacheHitMissListener listener;
    public MsgAddCacheHitMissListener(Address from, Address to, CacheHitMissListener listener){
        super(from, to);
        this.listener = listener;
    }

    @Override
    public <T> void exec(CachedService cachedService) {
        cachedService.addCacheHitMissListener(listener);
    }
}
