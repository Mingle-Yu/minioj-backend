package com.github.mingleyu.minioj.judge.codesandbox;

import com.github.mingleyu.minioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.github.mingleyu.minioj.judge.codesandbox.model.ExecuteCodeResponse;

public interface CodeSandbox {
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
