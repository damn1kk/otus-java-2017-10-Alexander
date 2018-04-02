package ru.otus.homework16;

import java.util.Map;
import java.util.Set;

public class MapUtils {
    public static <K, V> K findFirstKeyByValue(Map<K,V> map, V value){
        Set<Map.Entry<K, V>> entrySet = map.entrySet();
        for(Map.Entry<K, V> entry : entrySet){
            if(entry.getValue().equals(value)){
                return entry.getKey();
            }
        }
        return null;
    }
}
