package com.example.demo;



import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.example.demo.controller.UserRegistrationController;
import com.example.demo.dto.UserDTO;
import com.example.demo.repository.UserJpaRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserRegistrationController.class)
@ContextConfiguration(classes = UserRegistrationApplication.class)
public class UserRegistrationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserJpaRepository userJpaRepositoryMock;
	
	private MediaType contentType;
	private Optional<UserDTO> user;
	
	@Before
	public void setUp() {
		contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
							MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
		UserDTO user = this.user.get();
		user = new UserDTO();
		user.setName("Isu");
		user.setAddress("suu, mmm");
		user.setEmail("www@gmaill.com");
	}
	
	@Test
	public void shouldReturnSuccessMessage() throws Exception {
		when(this.userJpaRepositoryMock.findById(1L)).thenReturn(user);
		this.mockMvc.perform(get("/api/user/1"))
		.andExpect(status().isOk())
		.andExpect((ResultMatcher) content().contentType(contentType))
		.andExpect(jsonPath("$.name", is("Isuu")))
		.andExpect(jsonPath("$.address", is("JP Nagar; Bangalore; India")))
		.andExpect(jsonPath("$.email", is("rrree@ggma.com")))
		.andDo(print());
		
		
	}
	

}
