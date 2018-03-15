package ru.otus.homework15.messageSystem.messages.toCacheService;

import ru.otus.homework15.database.cach.CacheHitMissListener;
import ru.otus.homework15.database.dbService.CachedService;
import ru.otus.homework15.messageSystem.Address;

public class MsgDeleteCacheHitMissListener extends MsgToCache {

    CacheHitMissListener cacheHitMissListener;

    public MsgDeleteCacheHitMissListener(Address from, Address to, CacheHitMissListener cacheHitMissListener){
        super(from, to);
        this.cacheHitMissListener = cacheHitMissListener;
    }

    @Override
    public <T> void exec(CachedService cachedService) {
        cachedService.deleteCacheHitMissListener(cacheHitMissListener);
    }
}
