package com.example.rest_api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) //PropertyNamingStrategy 는 deprecated 되어 안 쓰는게 좋음
//-> 요청이 스네이크 케이스로 들어오면 자동적으로 해당 변수에 맵핑, 응답도 스네이크 케이스로 출력
public class UserRequest {
    private String name;
    private Integer userAge; //기본값 int :0 , Integer : null
    private String email;
    private Boolean isKorean; //기본값 boolean :false , Boolean : null
}
