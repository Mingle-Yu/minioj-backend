package com.github.mingleyu.minioj.judge;

import com.github.mingleyu.minioj.model.entity.Submit;

/**
 * 判题服务
 */
public interface JudgeService {

    /**
     * 判题
     *
     * @param submitId
     * @return
     */
    Submit doJudge(long submitId);
}
