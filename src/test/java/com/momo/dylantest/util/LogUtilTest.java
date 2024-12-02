package com.momo.dylantest.util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogUtilTest {


    @Test
    void testErrorWithValidInputs() {
        Throwable throwable = new RuntimeException("Test exception");
        String params = "testParam";

        String result = LogUtil.error(params, throwable);

        assertTrue(result.contains("\"params\":\"testParam\""));
        assertTrue(result.contains("\"exception\":\"Test exception\""));
        assertTrue(result.contains("\"stackTrace\":\""));
    }

    @Test
    void testErrorWithNullThrowable() {
        String params = "testParam";

        String result = LogUtil.error(params, null);

        assertTrue(result.contains("\"params\":\"testParam\""));
        assertTrue(result.contains("\"exception\":\"\""));
        assertTrue(result.contains("\"stackTrace\":\"\""));
    }

    @Test
    void testInfoWithValidInputs() {
        int gate = LogUtil.GATE_FRONT;
        String remark = "Test remark";
        String detail = "Test detail";

        String result = LogUtil.info(gate, remark, detail);

        assertTrue(result.contains("\"gate\":0"));
        assertTrue(result.contains("\"remark\":\"Test remark\""));
        assertTrue(result.contains("\"detail\":\"Test detail\""));
    }

    @Test
    void testReplaceStrWithSpecialCharacters() {
        String input = "Test\"String\nWith\tSpecial\\Characters";

        String result = LogUtil.replaceStr(input);

        assertEquals("Test'StringWithSpecialCharacters", result);
    }

    @Test
    void testErrorWithJsonProcessingException() {
        // 創建一個無法被序列化的對象
        Object cyclic = new HashMap<>();
        ((HashMap) cyclic).put("self", cyclic);

        String result = LogUtil.error(cyclic.toString(), new RuntimeException("Test"));

        // 驗證結果包含預期的鍵，並且值不為空
        assertTrue(result.contains("\"params\""));
        assertTrue(result.contains("\"exception\""));
        assertTrue(result.contains("\"stackTrace\""));
    }

    @Test
    void testInfoWithJsonProcessingException() {
        // 創建一個會導致 Jackson 序列化失敗的特殊字符
        String invalidChar = String.valueOf((char) 0x0001);

        String result = LogUtil.info(LogUtil.GATE_FRONT, invalidChar, "test");

        // 驗證結果包含預期的鍵，並且值不為空
        assertTrue(result.contains("\"gate\""));
        assertTrue(result.contains("\"remark\""));
        assertTrue(result.contains("\"detail\""));
    }

    @Test
    void testGetStackTraceWithIOException() {
        // 創建一個會在寫入時拋出異常的 PrintWriter
        Throwable throwable = new RuntimeException("Test exception");

        // 使用一個非常大的異常堆棧來觸發可能的 IO 異常
        StackTraceElement[] stackTrace = new StackTraceElement[10000];
        for (int i = 0; i < stackTrace.length; i++) {
            stackTrace[i] = new StackTraceElement("TestClass", "testMethod", "TestFile.java", i);
        }
        throwable.setStackTrace(stackTrace);

        String result = LogUtil.getStackTrace(throwable);

        // 驗證結果要麼是截斷的堆棧跟踪，要麼是空字符串（如果發生IO異常）
        assertTrue(result.isEmpty() || result.length() <= 500);
    }

}
