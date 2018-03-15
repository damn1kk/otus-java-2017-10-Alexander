package ru.otus.homework15.database.dbService;

import ru.otus.homework15.database.cach.CacheHitMissListener;

public interface CachedService {
    public int getCacheHit();
    public int getCacheMiss();

    public void addCacheHitMissListener(CacheHitMissListener listener);
    public void deleteCacheHitMissListener(CacheHitMissListener listener);
}

