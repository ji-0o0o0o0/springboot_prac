package com.example.rest_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data //롬복의 기능 , 생성자,set,get,toString 자동으로 생성
@AllArgsConstructor //전체 파라미터를 가지는 생성자 생성-> 기본생성자 사라짐
@NoArgsConstructor //기본 생성자 생성
public class BookQueryParam {

    private String category;
    private String issuedYear;
    private String issuedMonth;
    private String issuedDay;
}
