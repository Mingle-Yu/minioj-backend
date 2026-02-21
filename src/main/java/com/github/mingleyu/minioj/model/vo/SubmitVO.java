package com.github.mingleyu.minioj.model.vo;

import cn.hutool.json.JSONUtil;
import com.github.mingleyu.minioj.judge.codesandbox.model.JudgeInfo;
import com.github.mingleyu.minioj.model.entity.Submit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 题目提交封装类
 *
 *
 */
@Data
public class SubmitVO {
    /**
     * id
     */
    private Long id;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 判题状态（0 - 排队中；1 - 判题中；2 - 成功；3 - 失败
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 提交用户信息
     */
    private UserVO userVO;

    /**
     * 对应题目信息
     */
    private QuestionVO questionVO;

    private static final long serialVersionUID = 1L;

    /**
     * 包装类转对象
     *
     * @param submitVO
     * @return
     */
    public static Submit voToObj(SubmitVO submitVO) {
        if (submitVO == null) {
            return null;
        }
        Submit submit = new Submit();
        BeanUtils.copyProperties(submitVO, submit);
        JudgeInfo submitVOJudgeInfo = submitVO.getJudgeInfo();
        if (submitVOJudgeInfo != null) {
            submit.setJudgeInfo(JSONUtil.toJsonStr(submitVOJudgeInfo));
        }
        return submit;
    }

    /**
     * 对象转包装类
     *
     * @param submit
     * @return
     */
    public static SubmitVO objToVo(Submit submit) {
        if (submit == null) {
            return null;
        }
        SubmitVO submitVO = new SubmitVO();
        BeanUtils.copyProperties(submit, submitVO);
        submitVO.setJudgeInfo(JSONUtil.toBean(submit.getJudgeInfo(), JudgeInfo.class));
        return submitVO;
    }
}