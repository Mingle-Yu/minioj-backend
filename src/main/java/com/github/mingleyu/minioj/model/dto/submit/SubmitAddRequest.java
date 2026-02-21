package com.github.mingleyu.minioj.model.dto.submit;

import lombok.Data;
import java.io.Serializable;

/**
 * 创建请求
 *
 *
 */
@Data
public class SubmitAddRequest implements Serializable {
    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    private static final long serialVersionUID = 1L;
}