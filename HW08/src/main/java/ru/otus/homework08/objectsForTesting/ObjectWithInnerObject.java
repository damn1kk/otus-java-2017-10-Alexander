package ru.otus.homework08.objectsForTesting;

public class ObjectWithInnerObject {
    SimpleObject objectField;

    public ObjectWithInnerObject(SimpleObject objectField) {
        this.objectField = objectField;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof ObjectWithInnerObject))
            return false;

        ObjectWithInnerObject that = (ObjectWithInnerObject) obj;

        if(this.objectField == null){
            if(that.objectField != null)
                return false;
        }else {
            if (!this.objectField.equals(that.objectField))
                return false;
        }

        return true;
    }
}
