package com.github.mingleyu.minioj.judge;

import cn.hutool.json.JSONUtil;
import com.github.mingleyu.minioj.common.ErrorCode;
import com.github.mingleyu.minioj.exception.BusinessException;
import com.github.mingleyu.minioj.judge.codesandbox.CodeSandbox;
import com.github.mingleyu.minioj.judge.codesandbox.CodeSandboxFactory;
import com.github.mingleyu.minioj.judge.codesandbox.CodeSandboxProxy;
import com.github.mingleyu.minioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.github.mingleyu.minioj.judge.codesandbox.model.ExecuteCodeResponse;
import com.github.mingleyu.minioj.judge.strategy.JudgeContext;
import com.github.mingleyu.minioj.model.dto.question.JudgeCase;
import com.github.mingleyu.minioj.judge.codesandbox.model.JudgeInfo;
import com.github.mingleyu.minioj.model.entity.Question;
import com.github.mingleyu.minioj.model.entity.Submit;
import com.github.mingleyu.minioj.model.enums.JudgeResultEnum;
import com.github.mingleyu.minioj.model.enums.SubmitStatusEnum;
import com.github.mingleyu.minioj.service.QuestionService;
import com.github.mingleyu.minioj.service.SubmitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JudgeServiceImpl implements JudgeService {

    @Value("${codesandbox.type}")
    private String type;

    @Resource
    private QuestionService questionService;

    @Resource
    private SubmitService submitService;

    @Resource
    private JudgeManager judgeManager;

    @Override
    public Submit doJudge(long submitId) {
        // 1)根据 提交id 获取题目信息和提交信息
        Submit submit = submitService.getById(submitId);
        if (submit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交记录不存在");
        }
        Question question = questionService.getById(submit.getQuestionId());
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 避免多次对同一条提交记录进行判题
        if (!submit.getStatus().equals(SubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "请勿重复提交");
        }
        // 及时更新题目状态
        Submit submitUpdate = new Submit();
        submitUpdate.setId(submitId);
        submitUpdate.setStatus(SubmitStatusEnum.JUDGING.getValue());
        boolean updateResult = submitService.updateById(submitUpdate);
        if (!updateResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "提交状态更新错误");
        }

        // 2)调用代码沙箱，获取执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String code = submit.getCode();
        String language = submit.getLanguage();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .language(language)
                .inputList(inputList)
                .code(code)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);

        // 3)根据执行结果，设置题目判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setQuestion(question);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setSubmit(submit);

        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        submitUpdate = new Submit();
        submitUpdate.setId(submitId);
        submitUpdate.setStatus(SubmitStatusEnum.SUCCEED.getValue());
        submitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));

        updateResult = submitService.updateById(submitUpdate);
        if (!updateResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "提交状态更新错误");
        }

        question.setSubmitNum(question.getSubmitNum() + 1);
        if (JudgeResultEnum.ACCEPTED.getValue().equals(judgeInfo.getMessage())) {
            question.setAcceptedNum(question.getAcceptedNum() + 1);
        }
        questionService.updateById(question);

        Submit submitResult = submitService.getById(submitId);
        return submitResult;
    }
}
