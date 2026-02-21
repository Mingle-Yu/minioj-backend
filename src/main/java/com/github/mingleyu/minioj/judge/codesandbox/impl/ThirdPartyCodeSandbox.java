package com.github.mingleyu.minioj.judge.codesandbox.impl;

import com.github.mingleyu.minioj.judge.codesandbox.CodeSandbox;
import com.github.mingleyu.minioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.github.mingleyu.minioj.judge.codesandbox.model.ExecuteCodeResponse;

public class ThirdPartyCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
