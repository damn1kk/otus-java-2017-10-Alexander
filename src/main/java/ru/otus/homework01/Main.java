package ru.otus.homework01;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {

    private static final String URL = "https://otus.ru";

    public static void main(String[] args)  throws Exception{

        InputStream in = new URL(URL + "/lessons/").openStream();

        try {
            String html = IOUtils.toString(in, (String) null);
            Document doc = Jsoup.parse(html);
/*

            doc.charset(StandardCharsets.UTF_8);
            doc.outputSettings().charset(StandardCharsets.UTF_8);
            doc.outputSettings().charset().forName("UTF-8");
*/

            Element allLessons = doc.select("div.lessons").first();
            Elements allLinks = allLessons.select("a.lessons__item");
            Elements allTitles = allLessons.select("div.lessons__item-title");

            System.out.println("Курсы на otus.ru на данный момент: \n");
            for(int i = 0; i < allTitles.size(); i++){
                String course = allTitles.get(i).text();
                byte[] is = course.getBytes();
                String encodingString = new String(is ,StandardCharsets.UTF_8);
                System.out.println(encodingString);

                System.out.println("\nПреподаватель курса :");
                System.out.println(getTeacher(allLinks, i));
                System.out.println("------------------------");
            }

        }catch(IOException ioex){
            System.err.println("cannt connect to otus.ru");
            ioex.printStackTrace();
        }finally {
            IOUtils.closeQuietly(in);
        }
    }

    static String getTeacher(Elements links, int i){
        try {
            String urlOfCourse = URL + links.get(i).attr("href");
            Document doc = Jsoup.connect(urlOfCourse).get();
            String teacher = doc.select("div.lesson-teachers__item-name").first().text();
            return teacher;

        }catch(IOException ex){
            System.err.println("cannt connect to otus.ru");
            ex.printStackTrace();
        }catch(NullPointerException npe){
            return "no teacher";
        }
        return "";
    }
}
