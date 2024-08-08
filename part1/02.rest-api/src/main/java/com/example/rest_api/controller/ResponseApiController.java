package com.example.rest_api.controller;

import com.example.rest_api.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
//@RestController //-> 응답값 무조건 json 하겠다는 선언
@Controller //html 응답 가능
@RequestMapping("/api/v1")
public class ResponseApiController {


    @GetMapping("") //== @RequestMapping(path="",method = RequestMethod.GET) -> method 없으면 모든 메스드 받기에 꼭 적어주기
    @ResponseBody //@Controller일때 값을 json으로 받으려면 @ResponseBody 적어줘야함 , 아
     public UserRequest user(){
        var user = new UserRequest();
        user.setName("홍길동");
        user.setUserAge(13);
        user.setEmail("asd@naver.com");

        log.info("user: {}",user);
        return user;
    }

    //http status 같이 출력하고 싶을떄
    @GetMapping("/1")
    public ResponseEntity user2(){
        var user = new UserRequest();
        user.setName("홍길동");
        user.setUserAge(13);
        user.setEmail("asd@naver.com");

        log.info("user: {}",user);

        var response = ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("x-custom","hi")
                .body(user);
        //ResponseEntity -> status 코드 조작을 하려거나 다른 값들을 넣어 주고 싶을때(원하는 형태의 에러 출력 가능)
        //특별한 코드를 넘겨주고 싶을떄, 에러가 발생했을 때
        return response;
    }
}
