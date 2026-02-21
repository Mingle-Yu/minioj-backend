package com.github.mingleyu.minioj.judge.strategy;

import com.github.mingleyu.minioj.model.dto.question.JudgeCase;
import com.github.mingleyu.minioj.judge.codesandbox.model.JudgeInfo;
import com.github.mingleyu.minioj.model.entity.Question;
import com.github.mingleyu.minioj.model.entity.Submit;
import lombok.Data;

import java.util.List;

/**
 * 在策略中传递的上下文参数
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private Submit submit;
}
