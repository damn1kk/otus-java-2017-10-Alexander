package ru.otus.homework08.ParserToJSON;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.homework08.objectsForTesting.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleToJSONParserTest {
    private Gson gson;

    @Before
    public void initGson(){
        gson = new Gson();
    }

    @Test
    public void parseObjectToJSONStringTest_withSimpleObject(){
        SimpleObject simpleObject = new SimpleObject("ABC", 23, 2323L);
        ToJSONParser parser = new SimpleToJSONParser();

        String jsonString = parser.parseObjectToJSONString(simpleObject);

        SimpleObject deserializedObject = gson.fromJson(jsonString, SimpleObject.class);

        Assert.assertEquals(simpleObject, deserializedObject);

    }

    @Test
    public void parseObjectToJSONStringTest_withSimpleObject_withNullField(){
        SimpleObject simpleObject = new SimpleObject(null, 23, 2323L);
        ToJSONParser parser = new SimpleToJSONParser();

        String jsonString = parser.parseObjectToJSONString(simpleObject);

        SimpleObject deserializedObject = gson.fromJson(jsonString, SimpleObject.class);

        Assert.assertEquals(simpleObject, deserializedObject);

    }

    @Test
    public void parseObjectToJSONStringTest_withObjectWithInnerObject(){
        ObjectWithInnerObject objectWithInnerObject = new ObjectWithInnerObject(
                new SimpleObject("xz", 42, 4242L)
        );

        ToJSONParser parser = new SimpleToJSONParser();
        String jsonString = parser.parseObjectToJSONString(objectWithInnerObject);
        ObjectWithInnerObject deserializedObject = gson.fromJson(jsonString, ObjectWithInnerObject.class);

        Assert.assertEquals(objectWithInnerObject, deserializedObject);
    }

    @Test
    public void parseObjectToJSONStringTest_withObjectWithInnerObject_withNullField(){
        ObjectWithInnerObject objectWithInnerObject = new ObjectWithInnerObject(null);

        ToJSONParser parser = new SimpleToJSONParser();
        String jsonString = parser.parseObjectToJSONString(objectWithInnerObject);
        ObjectWithInnerObject deserializedObject = gson.fromJson(jsonString, ObjectWithInnerObject.class);

        Assert.assertEquals(objectWithInnerObject, deserializedObject);
    }


    @Test
    public void parseObjectToJSONStringTest_withObjectWithArray(){
        SimpleObject simpleObject = new SimpleObject("ABC", 23, 2323L);
        SimpleObject secondObject = new SimpleObject("DEF", 42, 5454L);
        SimpleObject thirdObject = new SimpleObject("GHI", 64, 3849L);

        ObjectWithArray objectWithArray = new ObjectWithArray(
                42, new int[]{42, 23, 56},
                "xz", new String[]{"string1", "string2"},
                simpleObject,
                new SimpleObject[]{simpleObject, secondObject, thirdObject}
        );

        ToJSONParser parser = new SimpleToJSONParser();
        String jsonString = parser.parseObjectToJSONString(objectWithArray);
        ObjectWithArray deserializedObject = gson.fromJson(jsonString, ObjectWithArray.class);

        Assert.assertEquals(objectWithArray, deserializedObject);
    }

    @Test
    public void parseObjectToJSONStringTest_withObjectWithArray_withNullFields(){
        ObjectWithArray objectWithArray = new ObjectWithArray(
                42, null,null,null,null,null);

        ToJSONParser parser = new SimpleToJSONParser();
        String jsonString = parser.parseObjectToJSONString(objectWithArray);
        ObjectWithArray deserializedObject = gson.fromJson(jsonString, ObjectWithArray.class);

        Assert.assertEquals(objectWithArray, deserializedObject);
    }

    @Test
    public void parseObjectToJSONStringTest_WithObjectWithCollection(){
        List<String> myList = new ArrayList<>();
        myList.add("xz");
        myList.add("da");
        myList.add("net");

        ObjectWithCollection<String> objWithList = new ObjectWithCollection(myList);

        ToJSONParser parser = new SimpleToJSONParser();
        String jsonString = parser.parseObjectToJSONString(objWithList);
        ObjectWithCollection deserializedObject = gson.fromJson(jsonString, ObjectWithCollection.class);

        Assert.assertEquals(objWithList, deserializedObject);
    }

    @Test
    public void parseObjectToJSONStringTest_WithObjectWithCollectionOfObjects(){
        List<SimpleObject> myList = new ArrayList<>();
        myList.add(new SimpleObject("xz", 42, 4343L));
        myList.add(new SimpleObject("da", 23, 65656L));
        myList.add(new SimpleObject("someString", -23, -3434L));

        ObjectWithCollection<SimpleObject> objWithList = new ObjectWithCollection(myList);

        ToJSONParser parser = new SimpleToJSONParser();
        String jsonString = parser.parseObjectToJSONString(objWithList);

        Type genericTypeSimpleObject = new TypeToken<ObjectWithCollection<SimpleObject>>(){}.getType();
        ObjectWithCollection<SimpleObject> deserializedObject = gson.fromJson(jsonString, genericTypeSimpleObject);

        Assert.assertEquals(objWithList, deserializedObject);
    }

    @Test
    public void parseObjectToJSONStringTest_WithObjectWithCollection_WithNullField(){
        ObjectWithCollection objWithList = new ObjectWithCollection(null);

        ToJSONParser parser = new SimpleToJSONParser();
        String jsonString = parser.parseObjectToJSONString(objWithList);
        ObjectWithCollection deserializedObject = gson.fromJson(jsonString, ObjectWithCollection.class);

        Assert.assertEquals(objWithList, deserializedObject);
    }

    @Test
    public void parseObjectToJSONStringTest_WithObjectWithMap(){
        Map<String, Integer> myMap = new HashMap<>();
        myMap.put("xz", 42);
        myMap.put("simpleString", 23);
        myMap.put("secondString", 45);

        ObjectWithMap<String, Integer> objWithMap = new ObjectWithMap<>(myMap);

        ToJSONParser parser = new SimpleToJSONParser();
        String jsonString = parser.parseObjectToJSONString(objWithMap);

        Type genericTypeStringInteger = new TypeToken<ObjectWithMap<String, Integer>>(){}.getType();
        ObjectWithMap<String, Integer> deserializedObject = gson.fromJson(jsonString, genericTypeStringInteger);

        Assert.assertEquals(objWithMap, deserializedObject);
    }

    @Test
    public void parseObjectToJSONStringTest_WithObjectWithMapOfObjects(){
        Map<Integer, SimpleObject> myMap = new HashMap<>();
        myMap.put(2, new SimpleObject("xz", 42, 4343L));
        myMap.put(3, new SimpleObject("da", 23, 65656L));
        myMap.put(5, new SimpleObject("someString", -23, -3434L));

        ObjectWithMap<Integer, SimpleObject> objWithMap = new ObjectWithMap<>(myMap);

        ToJSONParser parser = new SimpleToJSONParser();
        String jsonString = parser.parseObjectToJSONString(objWithMap);

        Type genericTypeIntegerSimpleObject = new TypeToken<ObjectWithMap<Integer, SimpleObject>>(){}.getType();
        ObjectWithMap<Integer, SimpleObject> deserializedObject = gson.fromJson(jsonString, genericTypeIntegerSimpleObject);

        Assert.assertEquals(objWithMap, deserializedObject);
    }

    @Test
    public void parseObjectToJSONStringTest_WithObjectWithMap_WithNullField(){
        ObjectWithMap objWithMap = new ObjectWithMap(null);

        ToJSONParser parser = new SimpleToJSONParser();
        String jsonString = parser.parseObjectToJSONString(objWithMap);
        ObjectWithMap deserializedObject = gson.fromJson(jsonString, ObjectWithMap.class);

        Assert.assertEquals(objWithMap, deserializedObject);
    }
}
