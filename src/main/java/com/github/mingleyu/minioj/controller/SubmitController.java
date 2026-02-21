package com.github.mingleyu.minioj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mingleyu.minioj.common.BaseResponse;
import com.github.mingleyu.minioj.common.ErrorCode;
import com.github.mingleyu.minioj.common.ResultUtils;
import com.github.mingleyu.minioj.exception.BusinessException;
import com.github.mingleyu.minioj.model.dto.submit.SubmitAddRequest;
import com.github.mingleyu.minioj.model.dto.submit.SubmitQueryRequest;
import com.github.mingleyu.minioj.model.entity.Submit;
import com.github.mingleyu.minioj.model.entity.User;
import com.github.mingleyu.minioj.model.vo.SubmitVO;
import com.github.mingleyu.minioj.service.SubmitService;
import com.github.mingleyu.minioj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 */
@RestController
@RequestMapping("/submit")
@Slf4j
public class SubmitController {

    @Resource
    private SubmitService submitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param submitAddRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public BaseResponse<Long> doSubmit(@RequestBody SubmitAddRequest submitAddRequest,
                                       HttpServletRequest request) {
        if (submitAddRequest == null || submitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        final User loginUser = userService.getLoginUser(request);
        long submitId = submitService.doSubmit(submitAddRequest, loginUser);
        return ResultUtils.success(submitId);
    }

    /**
     * 分页获取列表（仅管理员）
     *
     * @param submitQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<SubmitVO>> listSubmitVOByPage(@RequestBody SubmitQueryRequest submitQueryRequest,
                                                           HttpServletRequest request) {
        long current = submitQueryRequest.getCurrent();
        long size = submitQueryRequest.getPageSize();
        Page<Submit> submitPage = submitService.page(new Page<>(current, size),
                submitService.getQueryWrapper(submitQueryRequest));
        final User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(submitService.getSubmitVOPage(submitPage, loginUser));
    }
}
