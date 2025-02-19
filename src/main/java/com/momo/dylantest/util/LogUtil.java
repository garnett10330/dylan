package com.momo.dylantest.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LogUtil {

    // 私有構造函數，防止實例化
    private LogUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static final int MAX_STACK_TRACE_LENGTH = 500;
    private static final String EMPTY_JSON = "{}";
    private static final String EMPTY_STRING = "";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();  // 靜態共用實例
    
    // 定義 gate 類型的常數說明
    public static final int GATE_FRONT = 0;  // 前台
    public static final int GATE_CMS = 1;    // 後台
    public static final int GATE_OTHER = 2;  // 其他

    /**
     * 系统发送意外异常时捕获到的日志，一般通过 try-catch 捕获到的
     *
     * @param params 参数
     * @param e      异常对象
     * @return String
     */
    public static String error(String params, Throwable e) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("params", replaceStr(params));
        errorDetails.put("exception", e == null ? "" : replaceStr(e.getMessage()));
        errorDetails.put("stackTrace", e == null ? "" : replaceStr(getStackTrace(e)));

        return convertToJson(errorDetails);
    }

    /**
     * 系统动作产生的日志。
     *
     * @param gate   前台 後台或是其他 使用enum
     * @param remark 簡要
     * @param detail 詳情
     * @return String
     */
    public static String info(int gate, String remark, String detail) {
        // 創建包含字段的 Map
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("gate", gate);
        jsonMap.put("remark", replaceStr(remark));
        jsonMap.put("detail", replaceStr(detail));
        
        return convertToJson(jsonMap);
    }

    /**
     * 將異常堆棧信息提取為字符串。
     * <p>
     * 該方法會將給定的 {@link Throwable} 堆棧信息轉換為字符串格式。如果堆棧信息超過 500 個字符，
     * 則只返回前 500 個字符。若輸入的異常對象為 {@code null}，則返回空字符串。
     * </p>
     *
     * @param e 異常對象 {@link Throwable}，可以為 {@code null}
     * @return 異常堆棧信息的字符串格式；若超過 500 字符則進行截斷；若輸入為 {@code null} 則返回空字符串
     */
    public static String getStackTrace(Throwable e) {
        if (e == null) {
            return ""; // 如果異常對象為 null，返回空字符串
        }

        // 使用 try-with-resources 保證資源安全關閉
        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw)) {

            // 將異常堆棧信息輸出到 PrintWriter
            e.printStackTrace(pw);

            // 提取堆棧信息為字符串
            String stackTrace = sw.toString();

            // 如果字符串長度超過 500，進行截斷
            return stackTrace.length() <= MAX_STACK_TRACE_LENGTH ? stackTrace : stackTrace.substring(0, MAX_STACK_TRACE_LENGTH);
        } catch (Exception ioException) {
            log.error("An Exception occurred: {}", ioException.getMessage(), ioException);
        }

        // 若發生意外情況，返回空字符串
        return "";
    }

    /**
     * 去掉不符合json的非法字符
     *
     * @param str
     * @return 取消非法字符的string
     */
    public static String replaceStr(String str) {
        if (!StringUtils.hasLength(str)) {
            return EMPTY_STRING;
        }
        return str.replace("\"", "'").replace("\n", "").replace("\t", "").replace("\\", "");
    }

    private static String convertToJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            log.error("JSON conversion failed", e);
            return EMPTY_JSON;
        }
    }
    
}
