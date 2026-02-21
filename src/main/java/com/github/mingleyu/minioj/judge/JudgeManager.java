package com.github.mingleyu.minioj.judge;

import com.github.mingleyu.minioj.judge.strategy.DefaultJudgeStrategy;
import com.github.mingleyu.minioj.judge.strategy.JavaLanguageJudgeStrategy;
import com.github.mingleyu.minioj.judge.strategy.JudgeContext;
import com.github.mingleyu.minioj.judge.strategy.JudgeStrategy;
import com.github.mingleyu.minioj.judge.codesandbox.model.JudgeInfo;
import com.github.mingleyu.minioj.model.entity.Submit;
import org.springframework.stereotype.Service;

@Service
public class JudgeManager {

    public JudgeInfo doJudge(JudgeContext judgeContext) {
        Submit submit = judgeContext.getSubmit();
        String language = submit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
