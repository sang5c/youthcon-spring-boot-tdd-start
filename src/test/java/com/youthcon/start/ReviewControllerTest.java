package com.youthcon.start;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    private long id = 1L;
    private String content = "재밌어요";
    private String phoneNumber = "010-1111-2222";

    @DisplayName("후기 조회 성공")
    @Test
    void success() throws Exception {
        given(reviewService.getById(id))
                .willReturn(new Review(id, content, phoneNumber));

        ResultActions perform = mockMvc.perform(get("/reviews/" + id));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("content").value(content))
                .andExpect(jsonPath("phoneNumber").value(phoneNumber));
    }


    @DisplayName("후기 조회 실패")
    @Test
    void fail() throws Exception {
        given(reviewService.getById(1000L))
                .willThrow(new ReviewNotFoundException("no review id: " + 1000));

        ResultActions perform = mockMvc.perform(get("/reviews/" + 1000));

        perform.andExpect(status().isNotFound());
    }
}
