package ru.otus.homework16.database.cach;

public interface CacheHitMissListener {
    public void cacheHitMissChanged(int cacheHit, int cacheMiss);
}
