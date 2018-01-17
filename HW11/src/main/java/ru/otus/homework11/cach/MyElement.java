package ru.otus.homework11.cach;


public class MyElement<K, V> {
    private K key;
    private V value;

    private long creationTime;
    private long lastAccessTime;

    public MyElement(K key, V value) {
        this.key = key;
        this.value = value;
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    private long getCurrentTime(){
        return System.currentTimeMillis();
    }

    public K getKey(){
        return key;
    }

    public V getValue(){
        return value;
    }

    public long getCreationTime(){
        return creationTime;
    }

    public long getLastAccessTime(){
        return lastAccessTime;
    }

    public void setAccessed(){
        lastAccessTime = getCurrentTime();
    }
}
