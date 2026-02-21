package com.github.mingleyu.minioj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mingleyu.minioj.model.dto.submit.SubmitAddRequest;
import com.github.mingleyu.minioj.model.dto.submit.SubmitQueryRequest;
import com.github.mingleyu.minioj.model.entity.Submit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mingleyu.minioj.model.entity.User;
import com.github.mingleyu.minioj.model.vo.SubmitVO;

/**
* @author Mingle
* @description 针对表【submit(提交记录)】的数据库操作Service
* @createDate 2026-01-14 10:45:01
*/
public interface SubmitService extends IService<Submit> {
    /**
     * 题目提交
     *
     * @param submitAddRequest
     * @param loginUser
     * @return
     */
    long doSubmit(SubmitAddRequest submitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param submitQueryRequest
     * @return
     */
    QueryWrapper<Submit> getQueryWrapper(SubmitQueryRequest submitQueryRequest);

    /**
     * 获取提交记录封装
     *
     * @param submit
     * @param loginUser
     * @return
     */
    SubmitVO getSubmitVO(Submit submit, User loginUser);

    /**
     * 分页获取提交记录封装
     *
     * @param submitPage
     * @param loginUser
     * @return
     */
    Page<SubmitVO> getSubmitVOPage(Page<Submit> submitPage, User loginUser);
}
