package com.example.triple

import com.example.triple.dto.EventDto
import com.example.triple.service.ReviewEventRegisterService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TripleApplicationTests(
    @Autowired
    val reviewEventRegisterService: ReviewEventRegisterService,
) {

    @BeforeEach
    fun `사전 데이터 주입`() {

    }


    @Test
    fun `리뷰를 작성 했는지, 안했는지 판단하는 테스트`() {

    }

    @Test
    fun `리뷰가 제대로 저장이 되는지 확인하는 테스트`(eventDto: EventDto) {

    }

}
