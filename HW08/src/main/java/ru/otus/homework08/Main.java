package ru.otus.homework08;

import com.google.gson.Gson;
import ru.otus.homework08.ParserToJSON.ToJSONParser;
import ru.otus.homework08.ParserToJSON.SimpleToJSONParser;
import ru.otus.homework08.objectsForTesting.*;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception{
        SimpleObject simpleObject = new SimpleObject(null, 23, 2323L);
        SimpleObject secondObject = new SimpleObject("DEF", 42, 5454L);
        SimpleObject thirdObject = new SimpleObject("GHI", 64, 3849L);

        ObjectWithArray objectWithArray = new ObjectWithArray(
                42, new int[]{42, 23, 56},
                "xz", new String[]{"string1", "string2"},
                simpleObject,
                new SimpleObject[]{simpleObject, secondObject, thirdObject}
                );

        ToJSONParser parser = new SimpleToJSONParser();
        Gson gson = new Gson();
        String json = parser.parseObjectToJSONString(objectWithArray);
        String json2 = gson.toJson(objectWithArray);
        System.out.println("JSON after my parser:\n " + json);
        System.out.println("JSON after gson parser:\n " + json2);

        System.out.println();

        Map<String, SimpleObject> myMap = new HashMap<>();
        myMap.put("xz", simpleObject);
        myMap.put("da", secondObject);
        ObjectWithMap objWithMap = new ObjectWithMap(myMap);
        json = parser.parseObjectToJSONString(objWithMap);
        json2 = gson.toJson(objWithMap);
        System.out.println("JSON after my parser:\n " + json);
        System.out.println("JSON after gson parser:\n " + json2);



    }
}
