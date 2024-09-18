package me.jiyoung.springbootdeveloper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //테스트용 애플리케이션 컨텍스트 생성
@AutoConfigureMockMvc //MockMvc 생성 및 자동 구성
class TestControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @AfterEach
    public void cleanUp(){
        memberRepository.deleteAll();
    }

    @DisplayName("getAllMembers: 아티클 조회에 성공한다")
    @Test
    public void getAllMembers() throws Exception{
        //given : 멤버 저장
        final  String url = "/test";
        Member savedMember = memberRepository.save(new Member(1L, "홍길동"));


        //when : 멤버 리스트 를 조회하는 api 호출
        final ResultActions result = mockMvc.perform(get(url)  //perform : 요청을 전송하는 역할
                .accept(MediaType.APPLICATION_JSON));  //accept : 무슨 타입으로 받을건지 결정

        //then : 응답코드 200 ok, 반환받은 값 중 0번째 요소의 값 같은 지 확인
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(savedMember.getId()))
                .andExpect(jsonPath("$[0].name").value(savedMember.getName()));
    }


}