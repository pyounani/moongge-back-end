package com.example.narshaback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class TextServiceImpl implements TextService {

    WebClient webClient = WebClient.create("http://localhost:8000");

    @Override
    public String getTextFiltering(String text){

        String textInput = "{\"input\": \""+ text +"\"}";

        String res = webClient.post()
                .uri("/lime/curse-filter")
                .acceptCharset(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(textInput))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        String krRes = uniToKor(res);

        System.out.println(krRes);

        return krRes;
    }

    public String uniToKor(String uni){ // 유니코드 한글로 바꾸는 함수
        StringBuffer result = new StringBuffer();

        for(int i=0; i<uni.length(); i++){
            if(uni.charAt(i) == '\\' &&  uni.charAt(i+1) == 'u'){
                Character c = (char)Integer.parseInt(uni.substring(i+2, i+6), 16);
                result.append(c);
                i+=5;
            }else{
                result.append(uni.charAt(i));
            }
        }
        return result.toString();
    }

}
