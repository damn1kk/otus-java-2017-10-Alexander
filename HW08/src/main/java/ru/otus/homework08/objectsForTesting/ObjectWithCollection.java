package ru.otus.homework08.objectsForTesting;

import java.util.Collection;

public class ObjectWithCollection <T> {
    Collection<T> collection;

    public ObjectWithCollection(Collection<T> collection) {
        this.collection = collection;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof ObjectWithCollection))
            return false;

        ObjectWithCollection that = (ObjectWithCollection) obj;

        if(this.collection == null){
            if(that.collection != null)
                return false;
        }else {
            if (!this.collection.equals(that.collection))
                return false;
        }

        return true;
    }
}
