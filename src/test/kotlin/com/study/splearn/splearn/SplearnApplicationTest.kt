package com.study.splearn.splearn

import org.mockito.Mockito
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import kotlin.test.Test

class SplearnApplicationTest {

    @Test
    fun `main에서 SpringApplication이 실행되는지 확인`() {
        // 정적 메서드 mocking
        Mockito.mockStatic(SpringApplication::class.java).use { mock ->
            mock.`when`<Any> {
                SpringApplication.run(SplearnApplication::class.java, *arrayOf<String>())
            }.thenReturn(Mockito.mock(ConfigurableApplicationContext::class.java))

            // 메인 실행
            main(arrayOf())

            // 호출 여부 검증
            mock.verify {
                SpringApplication.run(SplearnApplication::class.java, *arrayOf<String>())
            }
        }
    }
}//
