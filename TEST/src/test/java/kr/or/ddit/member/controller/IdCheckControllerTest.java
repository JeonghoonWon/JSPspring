package kr.or.ddit.member.controller;

//static import
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import kr.or.ddit.TestWebAppConfiguration;

@RunWith(SpringRunner.class)
@TestWebAppConfiguration
public class IdCheckControllerTest {
	
	@Inject
	private WebApplicationContext container;
	private MockMvc mockMvc;
	
	
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(container).build();
	}

	@Test
	public void testDoPost() throws Exception {
		 mockMvc.perform(post("/member/idCheck.do")
				 	.param("id","a001")
				    .accept(MediaType.APPLICATION_JSON_UTF8)
	               )
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(log())
	            .andReturn();
	
				 	
	}

}
