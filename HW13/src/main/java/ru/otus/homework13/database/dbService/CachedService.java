package ru.otus.homework13.database.dbService;

public interface CachedService {
    public int getCacheHit();
    public int getCacheMiss();
}
