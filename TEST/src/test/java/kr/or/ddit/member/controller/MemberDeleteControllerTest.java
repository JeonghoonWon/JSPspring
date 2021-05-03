package kr.or.ddit.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import kr.or.ddit.TestWebAppConfiguration;
import kr.or.ddit.vo.MemberVO;

@RunWith(SpringRunner.class)
@TestWebAppConfiguration
public class MemberDeleteControllerTest {
   
   @Inject
   private WebApplicationContext container;
   private MockMvc mockMvc;
   
   @Before
   public void setUp() throws Exception {
      mockMvc = MockMvcBuilders.webAppContextSetup(container).build();

   }


   @Test
   public void testMemberDelete() throws Exception {
      mockMvc.perform(post("/member/memberDelete.do")
            .param("password", "java")
            .sessionAttr("authMember", new MemberVO("a001", "java"))
         )
      .andExpect(status().is3xxRedirection())
      .andExpect(redirectedUrl("/"))
      .andDo(log())
      .andReturn();
   }

}