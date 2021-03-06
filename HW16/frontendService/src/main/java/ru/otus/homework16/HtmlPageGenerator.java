package ru.otus.homework16;

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

public class HtmlPageGenerator {
    private final static String PATH_TO_TEMPLATES = "/templates";

    private static HtmlPageGenerator pageGenerator;
    private final Configuration cfg;

    private HtmlPageGenerator() {
        cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setClassForTemplateLoading(this.getClass(), PATH_TO_TEMPLATES);
    }

    public static HtmlPageGenerator instance(){
        if(pageGenerator == null){
            pageGenerator = new HtmlPageGenerator();
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
