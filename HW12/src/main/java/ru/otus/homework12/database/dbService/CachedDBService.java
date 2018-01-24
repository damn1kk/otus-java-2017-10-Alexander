package ru.otus.homework12.database.dbService;

public interface CachedDBService<T, ID>{
    public int getCacheHit();
    public int getCacheMiss();
}
