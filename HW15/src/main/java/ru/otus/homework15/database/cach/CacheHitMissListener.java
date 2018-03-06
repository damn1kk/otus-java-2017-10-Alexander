package ru.otus.homework15.database.cach;

public interface CacheHitMissListener {
    public void cacheHitMissChanged(int cacheHit, int cacheMiss);
}
