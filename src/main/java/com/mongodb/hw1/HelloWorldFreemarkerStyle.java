package com.mongodb.hw1;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class HelloWorldFreemarkerStyle {
    
    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setClassForTemplateLoading(
                HelloWorldFreemarkerStyle.class, "/");
        try {
            Template helloTemplate = config.getTemplate("hello.ftl");
            StringWriter writer = new StringWriter();
            Map<String, Object> helloMap = new HashMap<String, Object>();
            helloMap.put("name", "Freemarker");
            helloTemplate.process(helloMap, writer);
            System.out.println(writer);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
