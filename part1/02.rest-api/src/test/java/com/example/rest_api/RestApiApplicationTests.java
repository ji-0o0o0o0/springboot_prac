package com.example.rest_api;

import com.example.rest_api.model.UserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.runtime.ObjectMethods;

@SpringBootTest
class RestApiApplicationTests {

	@Autowired//-> 스프링에서 관리하는 빈 중에 자동으로 생성되는 오브젝트 매퍼를 가져오겠다는 뜻
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() throws JsonProcessingException {
		var user = new UserRequest("홍길동",10,"tre@naver.com",true);
		System.out.println(user);//해당 dto에 toString()매서드 존재해야 출력 가능

		//직렬화를 통해 dto->json으로 변경
		var json = objectMapper.writeValueAsString(user); //변수에 매칭X, get으로 만들어진 메서드를 참조(get메서드에 @JsonIgnore붙이면 json으로 변환 안함, @JsonProperty("ww")는 Json에 어떤 값을 매칭할지지정가능)
		System.out.println(json);

		// 역직렬화
		var dto = objectMapper.readValue(json, UserRequest.class);
		//오브젝트 매퍼는 리플렉션 기반으로 동작하기 떄문에 dto가 private로 되어있더라도 인스턴스 생성 가능
		//setter 메서드 참조( setter 메서드 없다면 getter 메서드 통해서 세팅 가능)
		System.out.println(dto);

		//dto 는 기본형태 추천(@Data,@AllArgsConstructor,@NoArgsConstructor)
	}

}
