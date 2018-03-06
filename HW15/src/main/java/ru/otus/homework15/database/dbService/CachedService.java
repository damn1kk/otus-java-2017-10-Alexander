package ru.otus.homework15.database.dbService;

public interface CachedService {
    public int getCacheHit();
    public int getCacheMiss();
}
