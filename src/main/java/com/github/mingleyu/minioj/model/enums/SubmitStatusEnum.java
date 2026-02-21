package com.github.mingleyu.minioj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 题目提交状态枚举
 *
 */
public enum SubmitStatusEnum {

    WAITING(0, "排队中"),
    JUDGING(1, "判题中"),
    SUCCEED(2, "成功"),
    FAILED(3, "失败");


    private final Integer value;
    private final String text;

    SubmitStatusEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 获取枚举的 value 列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static SubmitStatusEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        for (SubmitStatusEnum anEnum : SubmitStatusEnum.values()) {
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
    public Integer getValue() {
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
