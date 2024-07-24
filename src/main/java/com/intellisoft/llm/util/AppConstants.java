package com.intellisoft.llm.util;

import org.springframework.context.annotation.Bean;

public class AppConstants {

    /* CHAT GPT */

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String API_KEY1 = "sk-proj-";
    public static final String API_KEY2 = "S6eqzcCezj659dMRPOpAT3";
    public static final String API_KEY3 = "BlbkFJcdAPOUWoiv0IJh4qTh09";
    public static final String MODEL = "gpt-4o";
    public static final Integer MAX_TOKEN = 300;
    public static final Double TEMPERATURE = 0.0;
    public static final Double TOP_P = 1.0;
    public static final String MEDIA_TYPE = "application/json; charset=UTF-8";
    public static final String URL = "https://api.openai.com/v1/chat/completions";


    /*Google Bard*/
    public static final String BARD_URL = "http://127.0.0.1:2000/askBard";

}
