package com.github.mingleyu.minioj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 编程语言枚举
 */
public enum CodeLanguageEnum {

    JAVA("java", "java"),
    CPP("cpp", "cpp"),
    GOLANG("go", "go");


    private final String value;
    private final String text;

    CodeLanguageEnum(String value, String text) {
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
    public static CodeLanguageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        for (CodeLanguageEnum anEnum : CodeLanguageEnum.values()) {
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
