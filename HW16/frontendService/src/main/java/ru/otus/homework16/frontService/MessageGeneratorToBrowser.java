package ru.otus.homework16.frontService;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.otus.homework16.HtmlPageGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MessageGeneratorToBrowser {
    private final static String MAIN_PAGE_TEMPLATE = "main_page.html";
    private final static String SUCCESSFUL_REGISTRATION_TEMPLATE = "successful_registration.html";
    private final static String REGISTRATION_FAILED_TEMPLATE = "registration_failed.html";
    private final static String SUCCESSFUL_LOGIN_TEMPLATE = "successful_login.html";
    private final static String LOGIN_FAILED_TEMPLATE = "login_failed.html";


    public JSONObject createMessageMainPage(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfMessage", "changeBody");
        jsonObject.put("htmlBody", HtmlPageGenerator.instance().generatePage(MAIN_PAGE_TEMPLATE, null));
        return jsonObject;
    }

    public JSONObject createMessageUpdateDbServices(Set<String> dbServices){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(String id : dbServices){
            jsonArray.add(id);
        }
        jsonObject.put("typeOfMessage", "updateDbServices");
        jsonObject.put("dbServices", jsonArray);
        return jsonObject;
    }

    public JSONObject createMessageSuccessfulRegistration(String login){
        Map<String, Object> variables = new HashMap<>();
        variables.put("login", login);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfMessage", "changeBody");
        HtmlPageGenerator generator = HtmlPageGenerator.instance();
        jsonObject.put("htmlBody", generator.generatePage(SUCCESSFUL_REGISTRATION_TEMPLATE, variables));

        return jsonObject;
    }

    public JSONObject createMessageRegistrationFailed(String login, String reason){
        Map<String, Object> variables = new HashMap<>();
        variables.put("reason", reason);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfMessage", "changeBody");
        jsonObject.put("htmlBody", HtmlPageGenerator.instance().generatePage(REGISTRATION_FAILED_TEMPLATE, variables));
        return jsonObject;
    }

    public JSONObject createMessageSuccessfulLogin(String login){
        Map<String, Object> variables = new HashMap<>();
        variables.put("login", login);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfMessage", "changeBody");
        jsonObject.put("htmlBody", HtmlPageGenerator.instance().generatePage(SUCCESSFUL_LOGIN_TEMPLATE, variables));
        return jsonObject;
    }

    public JSONObject createMessageLoginFailed(String reason){
        Map<String, Object> variables = new HashMap<>();
        variables.put("reason", reason);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfMessage", "changeBody");
        jsonObject.put("htmlBody", HtmlPageGenerator.instance().generatePage(LOGIN_FAILED_TEMPLATE, variables));
        return jsonObject;
    }
}
