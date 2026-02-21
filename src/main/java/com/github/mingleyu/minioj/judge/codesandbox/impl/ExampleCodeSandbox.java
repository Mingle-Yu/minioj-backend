package com.github.mingleyu.minioj.judge.codesandbox.impl;

import com.github.mingleyu.minioj.judge.codesandbox.CodeSandbox;
import com.github.mingleyu.minioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.github.mingleyu.minioj.judge.codesandbox.model.ExecuteCodeResponse;
import com.github.mingleyu.minioj.judge.codesandbox.model.JudgeInfo;
import com.github.mingleyu.minioj.model.enums.JudgeResultEnum;
import com.github.mingleyu.minioj.model.enums.SubmitStatusEnum;

import java.util.List;

public class ExampleCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行");
        executeCodeResponse.setStatus(SubmitStatusEnum.SUCCEED.getValue());

        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeResultEnum.ACCEPTED.getText());
        judgeInfo.setMemory(1000L);
        judgeInfo.setTime(1000L);

        executeCodeResponse.setJudgeInfo(judgeInfo);

        return executeCodeResponse;
    }
}
