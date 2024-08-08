package com.example.rest_api.controller;

import com.example.rest_api.model.BookQueryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController //REST API 처리하는 컨트롤러라고 지정, REST API 기능을 하는 특정한 클래스를 지정하기 위해서 쓰임
@RequestMapping("/api")//어떠한 주소를 받겠다는 것을 지정
public class RestApiController {

    @GetMapping(path="/hello")
    public  String helle(){
        var html ="<html><body><h1>hello Spring Boot</h1></body></html>";
        //1. 서버에 특정 데이터를 클라이언트는 요청
        //2. 여기에 대한 응답은 문자열
        //3. 브라우저에서는 문자열을 가지고 이쁘게 꾸며줌

        //즉, 서버 쪽에서 항상 데이터를 전달하는 것은 문자열로 전달되고
        // 그게 html, json 또는 특정 파일 형태는 해석하는 쪽에서 해석하기 나름
        // (이 문자는 전달이 될 떄 0,1 이라는 비트 단위의 데이터가 전달)
        // 서버에서 어떠한 문자의 데이터를 만들어내는지가 중요 -> 통신이란 문자를 전달하는 것이다
        return html;
    }
    //path variable
    @GetMapping(path = "/echo/{message}/age/{age}/is-man/{isMan}")
    public  String echo(
            @PathVariable(name="message") String msg,
            @PathVariable int age,
            @PathVariable boolean isMan){
        System.out.println("ehco message: "+msg);
        //int (기본값 int = 0, integer = null )
        System.out.println("ehco age: "+age);
        System.out.println("ehco isMan: "+isMan);
        //인코딩
        // 윈도우에서 한글 꺠지는 이유
        // 윈도우는 기본적으로 MS949(2byte) (유사한것 EUC-KR(2byte))
        // 웹서버는 기본적으로 UTF-8(3byte)-> 리눅스, 맥환경에서는 한글 안깨짐
        //-> 윈도우에서 인코딩 맞추기
        //-> 파일>세팅>file Encoding, console>모두 utf-8로 변경하면 됨

        //대문자로 변환=> toUpperCase
        var msgUpper = msg.toUpperCase();
        System.out.println("대문자로 변환: "+msgUpper);

        return msg;
    }

    //query parameter
    //http://localhost:8080/api/book?category=IT&issuedYear=2023&issued-month=01&issued_day=31
    @GetMapping(path = "/book")
    public void queryParam(
            @RequestParam String category,
            @RequestParam String issuedYear,
            @RequestParam(name="issued-month") String issuedMonth,//개발자가 힘들지만 원칙을 따라감
            //@RequestParam String issued_day//(추천 안함)
            @RequestParam(name = "issued_day") String issuedDay//이름 포맷 맞춰주는 것 추천
    ){
        System.out.println(category);
        System.out.println(issuedYear);
        System.out.println(issuedMonth);
        System.out.println(issuedDay);
    }

    //query parameter 객체를 통해 파싱
    //http://localhost:8080/api/book2?category=IT&issuedYear=2023&issuedMonth=01&issuedDay=31
    @GetMapping(path = "/book2")
    public void queryParamDto(
          BookQueryParam bookQueryParam
    ){
        System.out.println(bookQueryParam);
    }

    @DeleteMapping(path ={
            "/user/{userName}/delete",
            "/user/{userName}/del"}) //path로 받으면 여러 주소로 받기 가능
    public void delete(@PathVariable String userName){
        log.info("user-name: {}",userName);
    }
}
