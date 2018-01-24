package ru.otus.homework12.database.cach;

public interface CacheEngine<K, V> {
    void put(MyElement<K, V> element);

    MyElement<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    void delete(K key);

    void dispose();

}
