package me.jiyoung.springbootdeveloper;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jiyoung.springbootdeveloper.domain.Article;
import me.jiyoung.springbootdeveloper.dto.AddArticleRequest;
import me.jiyoung.springbootdeveloper.dto.UpdateArticleRequest;
import me.jiyoung.springbootdeveloper.repository.BlogRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@SpringBootTest//테스트용 애플리케이션 컨텍스트
@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성
class BlogApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper; //직렬화 또는 역직렬화 할때 필요

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach//테스트 실행 전에 실행됨
    public void mockMvcSetUp(){
        this.mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
        blogRepository.deleteAll();
    }
    @DisplayName("addArticle: 블로그 글 추가에 성공한다")
    @Test
    public void addArticle() throws Exception{
        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title,content);

        //writeValueAsString을 통해 객체 json을 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        //when
        //설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);//크기가 1인지 검증
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);

    }

    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAllArticles() throws Exception{
        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        blogRepository.save(Article.builder()
                        .title(title)
                        .content(content)
                        .build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));
    }

    @DisplayName(("findArticle: 블로그 글 조회에 성공한다"))
    @Test
    public void findArticle() throws Exception{
        //given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article saveArticle = blogRepository.save(Article.builder()
                        .title(title)
                        .content(content)
                        .build());

        //when
        final  ResultActions resultActions = mockMvc.perform(get(url,saveArticle.getId())
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").value(content))
                .andExpect(jsonPath("title").value(title));

    }
    @DisplayName("DeleteArticle: 블로그 글 삭제에 성공한다")
    @Test
    public void deleteArticle() throws Exception{
        //given : 블로그 글을 저장
        final String url="/api/articles/{id}";
        final String content= "content";
        final String title = "title";

        Article saveArticle = blogRepository.save(Article.builder()
                        .title(title)
                        .content(content)
                        .build());
        //when 저장한 블로그 글의 id값으로 삭제 api 호출
        mockMvc.perform(delete(url,saveArticle.getId()))
                .andExpect(status().isOk());

        //then 응답코드 200 ok 이고, 블로그 글 ㄹ스트를 전체 조회해 조회한 배열 크기가 0인지 확인
        List<Article> articles = blogRepository.findAll();
        assertThat(articles).isEmpty();
    }

    @DisplayName("UpdateArticle: 블로그 글 수정에 성공한다")
    @Test
    public void updateArticle() throws Exception{
        //given 블로그 글 저장, 수정에 필요한 객체 생성
        final String url="/api/articles/{id}";
        final String content= "content";
        final String title = "title";

        Article saveArticle = blogRepository.save(Article.builder().title(title).content(content).build());

        final String newcontent= "newcontent";
        final String newtitle = "newtitle";
        UpdateArticleRequest request = new UpdateArticleRequest(newtitle,newcontent);

        //when  update api로 수정 요청을 보냄, 요청 타입은 json
        ResultActions result = mockMvc.perform(put(url,saveArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));


        //then 응답코드 200ok, 블로그 글 id 로 조회 한 후 값이 수정되었는지 확인
        result.andExpect(status().isOk());

        Article article = blogRepository.findById(saveArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newtitle);
        assertThat(article.getContent()).isEqualTo(newcontent);
    }
}