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


        Map<String, SimpleObject> myMap = new HashMap<>();
        myMap.put("xz", simpleObject);
        myMap.put("da", secondObject);
        ObjectWithMap objWithMap = new ObjectWithMap(myMap);

        List<SimpleObject> myList = new ArrayList<>();
        myList.add(simpleObject);
        myList.add(secondObject);
        myList.add(thirdObject);
        ObjectWithCollection<SimpleObject> objWithCollection = new ObjectWithCollection<>(myList);

        ToJSONParser parser = new SimpleToJSONParser();
        Gson gson = new Gson();
        String json = parser.parseObjectToJSONString(objWithCollection);
        String json2 = gson.toJson(objWithCollection);
        System.out.println(json);
        System.out.println(json2);


    }
}
