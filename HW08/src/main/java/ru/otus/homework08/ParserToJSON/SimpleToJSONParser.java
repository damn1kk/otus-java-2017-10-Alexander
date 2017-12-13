package ru.otus.homework08.ParserToJSON;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.otus.homework08.ReflectionHelper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class SimpleToJSONParser implements ToJSONParser {

    @Override
    public String parseObjectToJSONString(Object object) {
        return parseObjectToJSONObject(object).toString();
    }

    private JSONObject parseObjectToJSONObject(Object object) {
        Field[] fields = ReflectionHelper.getAllFields(object);
        JSONObject jsonObject = new JSONObject();

        for (Field f : fields) {
            String fieldName = f.getName();
            Object fieldValue = null;
            try{
                fieldValue = ReflectionHelper.getFieldValue(f, object);
                fieldValue = handleObjectAccordingToType(fieldValue);
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
                System.err.println("Can not get field value");
            }
            jsonObject.put(fieldName, fieldValue);
        }
        return jsonObject;
    }

    private Object handleObjectAccordingToType(Object obj){
        if(obj == null) {
            return null;
        }

        Class clazz = obj.getClass();
        boolean isArray = clazz.isArray();
        boolean isPrimitive = obj instanceof Number || obj instanceof Boolean;
        boolean isString = obj instanceof String;
        boolean isCollection = obj instanceof Collection;
        boolean isMap = obj instanceof Map;

        if(isArray) {
            JSONArray list = new JSONArray();
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                Object objectIntoArray = Array.get(obj, i);
                list.add(handleObjectAccordingToType(objectIntoArray));
            }
            return list;
        }else if(isCollection) {
            JSONArray list = new JSONArray();
            Collection collection = (Collection) obj;
            for (Object o : collection) {
                list.add(handleObjectAccordingToType(o));
            }
            return list;
        }else if(isMap){
            JSONObject childObject = new JSONObject();
            Map map = (Map) obj;
            map.forEach((k,v) -> childObject.put(k,handleObjectAccordingToType(v)));
            return childObject;
        }else if(isPrimitive || isString){
            return obj;
        }else{
            return parseObjectToJSONObject(obj);
        }
    }
}
