package ru.otus.homework15.database.dbService.hibernateService;

import ru.otus.homework15.MessageSystemContext;
import ru.otus.homework15.database.cach.CacheEngine;
import ru.otus.homework15.database.cach.CacheHitMissListener;
import ru.otus.homework15.database.cach.MyElement;
import ru.otus.homework15.database.dataSet.DataSet;
import ru.otus.homework15.database.dbService.CachedService;
import ru.otus.homework15.database.dbService.DBException;
import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.Addressee;
import ru.otus.homework15.messageSystem.messages.Message;
import ru.otus.homework15.messageSystem.messages.toCacheHitMissListener.MsgNotifyCacheHitMissListener;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class HibernateCachedService <T extends DataSet, ID extends Serializable>
        extends HibernateDBService<T,ID> implements CachedService, CacheHitMissListener {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    private CacheEngine<ID, T> cache;

    private Set<CacheHitMissListener> listeners = new HashSet<>();

    public HibernateCachedService(Class<T> type, CacheEngine<ID, T> cache, Address address, MessageSystemContext context){
        super(type, address, context);
        this.cache = cache;
        cache.addListener(this);
    }

    @Override
    public int getCacheMiss() {
        return cache.getMissCount();
    }

    @Override
    public int getCacheHit() {
        return cache.getHitCount();
    }

    @Override
    public T findById(ID id) throws DBException {
        notifyQueryListeners("Find by ID from cache: " + id);

        MyElement<ID, T> element = cache.get(id);
        if(element != null){
            T result = element.getValue();

            return result;
        }else {
            T result = super.findById(id);
            cache.put(new MyElement<>(id, result));
            return result;
        }
    }

    @Override
    public void save(T object) throws DBException {
        MyElement<ID, T> element = new MyElement<>((ID)object.getId(), object);
        super.save(object);
        cache.put(element);
    }

    @Override
    public void update(T object) throws DBException {
        MyElement<ID, T> element = new MyElement<>((ID)object.getId(), object);
        super.update(object);
        cache.put(element);
    }

    @Override
    public void deleteById(ID id) throws DBException {
        cache.delete(id);
        super.deleteById(id);
    }

    @Override
    public void delete(T object) throws DBException {
        cache.delete((ID)object.getId());
        super.delete(object);
    }

    @Override
    public List<T> findAll() throws DBException {
        List<T> result =  super.findAll();
        for(T user : result){
            cache.put(new MyElement<>((ID)user.getId(), user));
        }
        return result;
    }

    @Override
    public void close() throws IOException {
        cache.dispose();
        super.close();
    }

    @Override
    public void addCacheHitMissListener(CacheHitMissListener listener){
        listeners.add(listener);
    }

    @Override
    public void deleteCacheHitMissListener(CacheHitMissListener listener){
        listeners.remove(listener);
    }

    public void notifyCacheHitMissListeners(int cacheHit, int cacheMiss){
        for(CacheHitMissListener listener : listeners) {
            if (listener instanceof Addressee) {
                Address listenersAddress = ((Addressee)listener).getAddress();

                Message message = new MsgNotifyCacheHitMissListener(
                        getAddress(), listenersAddress, cacheHit, cacheMiss
                );

                getMS().sendMessage(message);
            }
        }
    }

    @Override
    public void cacheHitMissChanged(int cacheHit, int cacheMiss) {
        notifyCacheHitMissListeners(cacheHit, cacheMiss);
    }
}
