package ru.otus.homework15.utils;

import java.util.List;

public class ListUtils {
    public static <T> String listToHtmlString(List<T> list){
        StringBuilder sb = new StringBuilder();
        for(T element : list){
            sb.append("<p>").append(element).append("</p>");
        }
        return sb.toString();
    }
}
