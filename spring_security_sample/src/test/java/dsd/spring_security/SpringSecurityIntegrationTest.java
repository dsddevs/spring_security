package dsd.spring_security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SpringSecurityIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testPublicEndpoint() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = "USER")
	public void testUserEndpointWithUserRole() throws Exception {
		mockMvc.perform(get("/api/user"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testAdminEndpointWithAdminRole() throws Exception {
		mockMvc.perform(get("/api/admin"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = "USER")
	public void testAdminEndpointWithUserRoleShouldBeForbidden() throws Exception {
		mockMvc.perform(get("/api/admin"))
				.andExpect(status().isForbidden());  // Ожидаем статус 403
	}

	@Test
	public void testUserEndpointWithoutAuthenticationShouldBeUnauthorized() throws Exception {
		mockMvc.perform(get("/api/user"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void testAdminEndpointWithoutAuthenticationShouldBeUnauthorized() throws Exception {
		mockMvc.perform(get("/api/admin"))
				.andExpect(status().isUnauthorized());
	}
}