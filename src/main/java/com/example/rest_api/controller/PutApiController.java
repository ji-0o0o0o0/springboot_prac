package com.example.rest_api.controller;

import com.example.rest_api.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class PutApiController {
    @PutMapping("/put")
    public void put(
            @RequestBody UserRequest userRequest
            ){
        System.out.println(""); //프로그램 실행 시 시스템 아웃 실행되고 다음 메소드 호출되기에 시스템 아웃을 많이 찍을수록 서버 진행/처리 속도 저하
        log.info("Request : {} ",userRequest);
        //로그 백 자체로 버퍼를 가지고 있기 때문에 해당 버퍼에 담고 다음 메소드 진행 그리고 콘솔에 출력 (버퍼 사이즈도 지정 가능) -> 진행 속도에 영향 크게 주지 않음
    }
}
