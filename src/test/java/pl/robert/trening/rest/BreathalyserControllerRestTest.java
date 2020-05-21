package pl.robert.trening.rest;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BreathalyserControllerRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenCanIDriveDrink1l2p2h() throws Exception {
        // Given
        TreeMap<Integer, Integer> drinkingHistory = new TreeMap<>();
        drinkingHistory.put(1, 10);
        drinkingHistory.put(2, 10);
        // When
        int iCanDrive = callEndpoint(drinkingHistory);
        // Then
        assertEquals(21, iCanDrive, "1l2p2h");
    }

    @Test
    public void whenCanIDriveNoDrink() throws Exception {
        // Given
        TreeMap<Integer, Integer> drinkingHistory = new TreeMap<>();
        // When
        int iCanDrive = callEndpoint(drinkingHistory);
        // Then
        assertEquals(0, iCanDrive, "Wypiłem tylko cocacole");
    }

    @Test
    public void whenCanIDriveNoDrink2() throws Exception {
        // Given
        TreeMap<Integer, Integer> drinkingHistory = new TreeMap<>();
        drinkingHistory.put(1, 0);
        drinkingHistory.put(2, 0);
        // When
        Gson gson = new Gson();
        String jsonString = gson.toJson(drinkingHistory);
        // Then
        mockMvc.perform(post("/whenICanDrive")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn()
                .getResponse();
    }

    private int callEndpoint(TreeMap<Integer, Integer> drinkingHistory) throws Exception {
        Gson gson = new Gson();
        String jsonString = gson.toJson(drinkingHistory);

        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(post("/whenICanDrive")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        return Integer.parseInt(mockHttpServletResponse.getContentAsString());
    }
}