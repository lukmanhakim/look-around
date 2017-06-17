package com.lukmanh.android.lookaround.service;

import com.lukmanh.android.lookaround.domain.Event;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by opaw on 6/17/17.
 */

public class HttpService {

    private String HOST = "http://192.168.1.49/look-around";
    private String BASE_URI =  HOST + "/api/";
    private RestTemplate restTemplate = new RestTemplate();

    public HttpService(){
        SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory();
        s.setReadTimeout(30 * 1000);
        s.setConnectTimeout(30 * 1000);
        restTemplate.setRequestFactory(s);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    public ResponseEntity listEvent (){
        String url = BASE_URI + "list.php";
        return restTemplate.getForEntity(url, Event[].class);
    }

}
