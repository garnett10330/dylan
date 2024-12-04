package com.momo.dylantest.controller.test;

import com.momo.dylantest.model.mysql.CompanyStockMysqlPo;
import com.momo.dylantest.model.request.RedisSetReq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class RedisControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @InjectMocks
    private RedisController redisController;

    public RedisControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(redisController).build();
    }

    @Test
    void testSetValue() throws Exception {
        RedisSetReq request = new RedisSetReq();
        request.setKey("testKey");
        request.setData("testData");

        ValueOperations<String, Object> mockValueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(mockValueOperations);

        String requestBody = """
            {
                "key": "testKey",
                "data": "testData"
            }
            """;

        mockMvc.perform(post("/api/test/redis/object/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").value("Value set in Redis"));

        verify(redisTemplate, times(1)).opsForValue();
        verify(mockValueOperations, times(1)).set(eq("testKey"), any(CompanyStockMysqlPo.class), eq(100L), eq(TimeUnit.SECONDS));
    }


    @Test
    void testGetObjectValue() throws Exception {
        String testKey = "testKey";
        CompanyStockMysqlPo mockValue = new CompanyStockMysqlPo();
        mockValue.setId(12345);
        mockValue.setSymbol("testSymbol");

        ValueOperations<String, Object> mockValueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(mockValueOperations);
        when(mockValueOperations.get(testKey)).thenReturn(mockValue);

        mockMvc.perform(get("/api/test/redis/object/v1")
                        .param("key", testKey))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.symbol").value("testSymbol"));

        verify(redisTemplate, times(1)).opsForValue();
        verify(mockValueOperations, times(1)).get(testKey);
    }

}
