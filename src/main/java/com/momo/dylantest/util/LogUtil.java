package com.momo.dylantest.util;


import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


public class LogUtil {
    public final static int GATE_FRONT = 0;
    public final static int GATE_CMS = 1;
    public final static int GATE_OTHER = 2;

    /**
     * 系统发送意外异常时捕获到的日志，一般通过 try-catch 捕获到的
     *
     * @param params 参数
     * @param e      异常对象
     * @return String
     */
    public static String error(String params, Throwable e) {
        String message = (e == null) ? "" : e.getMessage();
        String stackTrace = (e == null) ? "" : getStackTrace(e);

        StringBuffer sBuffer = new StringBuffer("\"params\":");
        sBuffer.append("\"");
        sBuffer.append(replaceStr(params));
        sBuffer.append("\",\"exception\":\"");
        sBuffer.append(replaceStr(message));
        sBuffer.append("\",\"stackTrace\":\"");
        sBuffer.append(replaceStr(stackTrace));
        sBuffer.append("\"");
        return sBuffer.toString();
    }

    /**
     * 系统动作产生的日志。
     *
     * @param gate   前台还是后台，使用枚举
     * @param remark 简要
     * @param detail 详情信息
     * @return String
     */
    public static String info(int gate, String remark, String detail) {
        StringBuffer sBuffer = new StringBuffer("\"gate\":");
        sBuffer.append(gate);
        sBuffer.append(",\"remark\":\"");
        sBuffer.append(replaceStr(remark));
        sBuffer.append("\",\"detail\":\"");
        sBuffer.append(replaceStr(detail));
        sBuffer.append("\"");

        return sBuffer.toString();
    }

    /**
     * 获取前500字符的堆栈信息
     *
     * @param e
     * @return
     */
    public static String getStackTrace(Throwable e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            // 将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        String returnStr = sw.toString();
        if (StringUtils.isEmpty(returnStr)) {
            return "";
        }
        if (returnStr.length() <= 500) {
            return returnStr;
        }
        return returnStr.substring(0, 500);
    }

    /**
     * 去掉不符合json的非法字符
     *
     * @param str
     * @return
     */
    private static String replaceStr(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        return str.replaceAll("\"", "'").replaceAll("\n", "").replaceAll("\t", "").replaceAll("\\\\", "");
    }
}
