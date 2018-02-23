package ru.otus.homework13.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class PageGenerator {
    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    private PageGenerator(){
        cfg = new Configuration(Configuration.VERSION_2_3_23);
        try {
            URL pathToTemplates = getClass().getClassLoader().getResource("./templates");
            cfg.setDirectoryForTemplateLoading(new File(pathToTemplates.toURI()));
        }catch(URISyntaxException | IOException e){
            e.printStackTrace();
        }
    }

    public static PageGenerator instance(){
        if(pageGenerator == null){
            pageGenerator = new PageGenerator();
        }
        return pageGenerator;
    }

    public String generatePage(String fileName, Map<String, Object> data){
        Writer writer = new StringWriter();
        try{
            Template template = cfg.getTemplate(fileName);
            template.process(data, writer);
        }catch(IOException | TemplateException e){
            e.printStackTrace();
        }
        return writer.toString();
    }
}
