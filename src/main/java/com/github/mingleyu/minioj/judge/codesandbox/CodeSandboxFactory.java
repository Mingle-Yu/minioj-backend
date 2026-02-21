package com.github.mingleyu.minioj.judge.codesandbox;

import com.github.mingleyu.minioj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.github.mingleyu.minioj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.github.mingleyu.minioj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

import java.util.Scanner;

/**
 * 代码沙箱静态工厂
 */
public class CodeSandboxFactory {

    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();

        }
    }
}
