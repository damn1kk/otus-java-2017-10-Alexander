package ru.otus.homework08.objectsForTesting;

import java.util.Map;

public class ObjectWithMap <K, V>{
    Map<K, V> myMap;

    public ObjectWithMap(Map<K, V> myMap) {
        this.myMap = myMap;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof ObjectWithMap))
            return false;

        ObjectWithMap that = (ObjectWithMap) obj;

        if(this.myMap == null){
            if(that.myMap != null)
                return false;
        }else {
            if (!this.myMap.equals(that.myMap))
                return false;
        }

        return true;
    }
}
