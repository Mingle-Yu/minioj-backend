package com.github.mingleyu.minioj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.github.mingleyu.minioj.model.dto.question.JudgeCase;
import com.github.mingleyu.minioj.model.dto.question.JudgeConfig;
import com.github.mingleyu.minioj.judge.codesandbox.model.JudgeInfo;
import com.github.mingleyu.minioj.model.entity.Question;
import com.github.mingleyu.minioj.model.enums.JudgeResultEnum;

import java.util.List;
import java.util.Optional;

public class JavaLanguageJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        Long memory = Optional.ofNullable(judgeInfo.getMemory()).orElse(0L);
        Long time = Optional.ofNullable(judgeInfo.getTime()).orElse(0L);
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();

        JudgeResultEnum judgeResultEnum = JudgeResultEnum.ACCEPTED;

        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMessage(judgeResultEnum.getValue());
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);

        // 判断沙箱执行结果的输出数量是否和预期的输出数量一致
        if (outputList.size() != inputList.size()) {
            judgeResultEnum = JudgeResultEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeResultEnum.getValue());
            return judgeInfoResponse;
        }
        // 判断沙箱执行结果的每一项是否与预期结果的相应项是否一致
        for (int i = 0; i < judgeCaseList.size(); i++) {
            String output = judgeCaseList.get(i).getOutput();
            if (!output.equals(outputList.get(i))) {
                judgeResultEnum = JudgeResultEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeResultEnum.getValue());
                return judgeInfoResponse;
            }
        }
        // 判断代码执行信息是否满足判题配置
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long memoryLimit = judgeConfig.getMemoryLimit();
        Long timeLimit = judgeConfig.getTimeLimit();

        if (memory > memoryLimit) {
            judgeResultEnum = JudgeResultEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeResultEnum.getValue());
            return judgeInfoResponse;
        }
        long JAVA_PROGRAM_TIME_COST = 10000L;
        if ((time - JAVA_PROGRAM_TIME_COST) > timeLimit) {
            judgeResultEnum = JudgeResultEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeResultEnum.getValue());
            return judgeInfoResponse;
        }

        return judgeInfoResponse;
    }
}
