package com.github.mingleyu.minioj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 提交代码运行结果
 */
public enum JudgeResultEnum {

    ACCEPTED("accepted", "通过"),
    WRONG_ANSWER("wrong answer", "答案错误"),
    COMPILE_ERROR("compile error", "编译"),
    MEMORY_LIMIT_EXCEEDED("memory limit exceeded", "内存超限"),
    TIME_LIMIT_EXCEEDED("time limit exceeded", "时间超限"),
    FORMAT_ERROR("format error", "格式错误"),
    OUTPUT_LIMIT_ERROR("output limit error", "输出溢出"),
    WAITING("waiting", "等待中"),
    DANGEROUS_OPERATION("dangerous operation", "危险操作"),
    RUNTIME_ERROR("runtime error", "答案错误"),
    SYSTEM_ERROR("system error", "系统错误");


    private final String value;
    private final String text;

    JudgeResultEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 获取枚举的 value 列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static JudgeResultEnum getTextByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        for (JudgeResultEnum anEnum : JudgeResultEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 根据 value 获取 text
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     * 根据 text 获取 value
     *
     * @return
     */
    public String getText() {
        return text;
    }
}
