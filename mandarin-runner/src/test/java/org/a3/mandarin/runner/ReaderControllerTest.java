package org.a3.mandarin.runner;

import org.junit.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ReaderControllerTest extends MandarinRunnerApplicationTests{
    @Test
    public void testRegister() throws Exception {
        // reader role
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/reader/")
                .param("name", "reader3")
                .param("phoneNumber", "1123456789")
                .param("email", "1234@1234.com")
                .param("password", "passwd")
                .session(reader1Session))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(false));

        // admin role
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/reader/")
                .param("name", "reader3")
                .param("phoneNumber", "1123456789")
                .param("email", "1234@1234.com")
                .param("password", "passwd")
                .session(adminSessoin))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(false));

        // librarian role
        // email duplicated
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/reader/")
                .param("name", "reader3")
                .param("phoneNumber", "1123456789")
                .param("email", "1234@1234.com")
                .param("password", "passwd")
                .session(librarianSession))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(false));

        // librarian role
        // should pass
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/reader/")
                .param("name", "reader3")
                .param("phoneNumber", "1123456789")
                .param("email", "1234@12345.com")
                .param("password", "passwd")
                .session(librarianSession))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void testUpate() throws Exception{
        // reader self
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/reader/3")
                .param("name", "reader_")
                .param("email", "123123@12323131.com")
                .param("phoneNumber", "11234567819")
                .session(reader1Session))
                .andExpect(jsonPath("$.success").value(true));

        // reader other
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/reader/3")
                .param("name", "reader_")
                .param("email", "123123@12323131.com")
                .param("phoneNumber", "11234567819")
                .session(reader2Session))
                .andExpect(jsonPath("$.success").value(false));

        // librarian
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/reader/3")
                .param("name", "reader_")
                .param("email", "123123@12323131.com")
                .param("phoneNumber", "11234567819123123")
                .session(librarianSession))
                .andExpect(jsonPath("$.success").value(true));

        // admin
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/reader/3")
                .param("name", "reader_")
                .param("email", "123123@12323131.com")
                .param("phoneNumber", "11234567819")
                .session(adminSessoin))
                .andExpect(jsonPath("$.success").value(false));
    }
}
