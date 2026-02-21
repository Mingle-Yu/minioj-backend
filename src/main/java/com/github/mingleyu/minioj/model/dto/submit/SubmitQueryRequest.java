package com.github.mingleyu.minioj.model.dto.submit;

import com.github.mingleyu.minioj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询提交记录的请求
 *
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SubmitQueryRequest extends PageRequest implements Serializable {
    /**
     * 编程语言
     */
    private String language;

    /**
     * 提交状态
     */
    private Integer status;

    /**
     * 用户 Id
     */
    private Long userId;

    /**
     * 题目 Id
     */
    private Long questionId;

    private static final long serialVersionUID = 1L;
}