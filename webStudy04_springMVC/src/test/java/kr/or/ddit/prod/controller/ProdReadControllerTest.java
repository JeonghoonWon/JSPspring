package kr.or.ddit.prod.controller;

// static import
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
public class ProdReadControllerTest {
	
	@Inject
	private WebApplicationContext container;
	private MockMvc mockMvc;
	
	@Before // 3개의 testcase 를 각각 호출하기전에 미리 실행 후 만들어짐.
	public void setUp() throws Exception {
		
		//builder pattern을 많이 사용. 
		// mockMvc 생성
		mockMvc = MockMvcBuilders.webAppContextSetup(container).build();
					
	}
	
	@Test
	// 껍떼기 요구 HTML
	public void testListProdVOHTML() throws Exception {		
		mockMvc.perform(get("/prod/prodList.do").param("page", "2"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("pagingVO"))
				.andExpect(view().name("prod/prodList"))
				.andDo(log())
				.andReturn();
	}

	@Test
	//Data 요구 JSON
	   public void testListProdVOJSON() throws Exception {
	      mockMvc.perform(get("/prod/prodList.do")
	                  .param("page", "2")
	                  .accept(MediaType.APPLICATION_JSON_UTF8)
	               )
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(log())
	            .andReturn();
	   }

	@Test
	public void testView() throws Exception {
		mockMvc.perform(get("/prod/prodView.do"))
		.andExpect(status().isBadRequest())
		.andDo(log())
		.andReturn();
		mockMvc.perform(get("/prod/prodView.do")
				.param("what","P101000001"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("prod"))
		.andExpect(view().name("prod/prodView"))
		.andDo(log())
		.andReturn();
		}
	}


