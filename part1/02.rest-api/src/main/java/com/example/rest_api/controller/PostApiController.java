package com.example.rest_api.controller;

import com.example.rest_api.model.BookRequest;
import com.example.rest_api.model.UserRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostApiController {
    //post방식의 경우는 디폴트가 객체로 받아야 함
    //http body에 값을 넣어서 전달하는데 http body에는 text, html, xml, json 등의 값이 들어갈 수 있다
    //RequestBody : post, put 방식에서 http body로 들어 오는 데이터를 해당 객체에다가 데이터 클래스에 맵핑
    @PostMapping("/post")
    public String post(
            @RequestBody BookRequest bookRequest
    ){
        System.out.println(bookRequest);
        return bookRequest.toString();
    }

    @PostMapping("/user")
    public String postTest(
            @RequestBody UserRequest userRequest
            ){
        System.out.println(userRequest);
        return userRequest.toString();
    }


}
