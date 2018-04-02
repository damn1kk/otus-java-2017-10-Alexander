package ru.otus.homework16.database.cach;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.function.Function;

public class Cache<K, V> implements CacheEngine<K, V>{
    private Map<K, SoftReference<MyElement<K, V>>> elements = new LinkedHashMap<>();
    private Set<CacheHitMissListener> listeners = new HashSet<>();

    private Timer timer = new Timer();

    private final int capacity;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;


    private int hit = 0;
    private int miss = 0;

    public Cache(int capacity){
        this.capacity = capacity;
        lifeTimeMs = 0;
        idleTimeMs = 0;
        isEternal = true;
    }

    public Cache(int capacity, long lifeTimeMs, long idleTimeMs, boolean isEternal){
        this.capacity = capacity;
        this.lifeTimeMs = lifeTimeMs;
        this.idleTimeMs = idleTimeMs;
        this.isEternal = isEternal;
    }

    @Override
    public void put(MyElement<K, V> element) {
        if(elements.size() == capacity){
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }
        elements.put(element.getKey(), new SoftReference<>(element));

        if(!isEternal){
            if(lifeTimeMs != 0){
                TimerTask lifeTimerTask = getTimerTask(element.getKey(),
                        lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if(idleTimeMs != 0){
                TimerTask idleTimerTask = getTimerTask(element.getKey(),
                        idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs);
            }

        }
    }

    @Override
    public MyElement<K, V> get(K key) {
        SoftReference reference = elements.get(key);
        if(reference != null){
            MyElement element = (MyElement)reference.get();
            if(element != null){
                hit++;
                notifyCacheHitMissListeners();
                element.setAccessed();
            }else{
                miss++;
                notifyCacheHitMissListeners();
            }
            return element;
        }else{
            miss++;
            notifyCacheHitMissListeners();
        }
        return null;
    }

    @Override
    public int getHitCount() {
        return hit;
    }

    @Override
    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    @Override
    public void delete(K key) {
        if(elements.get(key) != null){
            elements.remove(key);
        }
    }

    private TimerTask getTimerTask(final K key, Function<MyElement<K, V>, Long> timeFunction){
        return new TimerTask() {
            @Override
            public void run() {
                MyElement<K, V> element = elements.get(key).get();
                if(element == null){
                    elements.remove(key);
                    this.cancel();
                }
                if(timeFunction.apply(element) < System.currentTimeMillis()){
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }

    @Override
    public void addListener(CacheHitMissListener listener){
        listeners.add(listener);
    }

    private void notifyCacheHitMissListeners(){
        for(CacheHitMissListener listener : listeners){
            listener.cacheHitMissChanged(getHitCount(), getMissCount());
        }
    }
}
