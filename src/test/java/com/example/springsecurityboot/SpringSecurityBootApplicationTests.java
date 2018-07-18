package com.example.springsecurityboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpringSecurityBootApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testValidLoginWithUser01() throws Exception {

        FormLoginRequestBuilder login = formLogin().user("email", "richard@gmail.com")
                                                   .password("richard");
        mockMvc.perform(login)
               .andExpect(redirectedUrl("/home"))
               .andExpect(authenticated().withUsername("richard@gmail.com"));
    }

    @Test
    public void testValidLoginWithUser02() throws Exception {

        FormLoginRequestBuilder login = formLogin().user("email", "eva@gmail.com")
                                                   .password("eva");
        mockMvc.perform(login)
               .andExpect(redirectedUrl("/home"))
               .andExpect(authenticated().withUsername("eva@gmail.com"));
    }

    @Test
    public void testInvalidLogin() throws Exception {

        FormLoginRequestBuilder login = formLogin().user("email", "test")
                                                   .password("test");

        mockMvc.perform(login)
               .andExpect(redirectedUrl("/login?error"))
               .andExpect(unauthenticated());
    }

    @Test
    public void testAccessAdminProtectedPageWithoutAuthentication() throws Exception {

        mockMvc.perform(get("/admin/index"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    public void testAccessUserProtectedPageWithoutAuthentication() throws Exception {

        mockMvc.perform(get("/user/index"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    @WithMockUser(authorities = { "USER" })
    public void testAccessUserProtectedPageWithAuthentication() throws Exception {

        mockMvc.perform(get("/user/index"))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = { "ADMIN" })
    public void testAccessAdminProtectedPageWithAuthentication() throws Exception {

        mockMvc.perform(get("/admin/index"))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = { "ADMIN" })
    public void testAccessUserProtectedPageWithWrongPermission() throws Exception {

        mockMvc.perform(get("/user/index"))
               .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = { "USER" })
    public void testAccessAdminProtectedPageWithWrongPermission() throws Exception {

        mockMvc.perform(get("/admin/index"))
               .andExpect(status().isForbidden());
    }

}
