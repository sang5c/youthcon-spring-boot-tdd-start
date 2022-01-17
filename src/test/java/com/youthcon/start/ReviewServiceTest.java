package com.youthcon.start;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
class ReviewServiceTest {

    private ReviewRepository reviewRepository = mock(ReviewRepository.class);
    private ReviewService reviewService = new ReviewService(reviewRepository);

    private long id = 1L;
    private String content = "재밌어요";
    private String phoneNumber = "010-1111-2222";

    @DisplayName("후기 조회 성공")
    @Test
    void success() {
        // given
        given(reviewRepository.findById(1L))
                .willReturn(Optional.of(new Review(id, content, phoneNumber)));

        //when
        Review review = reviewService.getById(1L);

        //then
        assertThat(review.getId()).isEqualTo(id);
        assertThat(review.getContent()).isEqualTo(content);
        assertThat(review.getPhoneNumber()).isEqualTo(phoneNumber);
    }

}
