package com.github.mingleyu.minioj.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mingleyu.minioj.common.ErrorCode;
import com.github.mingleyu.minioj.constant.CommonConstant;
import com.github.mingleyu.minioj.exception.BusinessException;
import com.github.mingleyu.minioj.judge.JudgeService;
import com.github.mingleyu.minioj.model.dto.question.QuestionQueryRequest;
import com.github.mingleyu.minioj.model.dto.submit.SubmitAddRequest;
import com.github.mingleyu.minioj.model.dto.submit.SubmitQueryRequest;
import com.github.mingleyu.minioj.model.entity.Question;
import com.github.mingleyu.minioj.model.entity.Submit;
import com.github.mingleyu.minioj.model.entity.User;
import com.github.mingleyu.minioj.model.enums.CodeLanguageEnum;
import com.github.mingleyu.minioj.model.enums.SubmitStatusEnum;
import com.github.mingleyu.minioj.model.vo.QuestionVO;
import com.github.mingleyu.minioj.model.vo.SubmitVO;
import com.github.mingleyu.minioj.model.vo.UserVO;
import com.github.mingleyu.minioj.service.QuestionService;
import com.github.mingleyu.minioj.service.SubmitService;
import com.github.mingleyu.minioj.mapper.SubmitMapper;
import com.github.mingleyu.minioj.service.UserService;
import com.github.mingleyu.minioj.utils.SqlUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author Mingle
 * @description 针对表【submit(提交记录)】的数据库操作Service实现
 * @createDate 2026-01-14 10:45:01
 */
@Service
public class SubmitServiceImpl extends ServiceImpl<SubmitMapper, Submit>
        implements SubmitService {
    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    @Resource
    @Lazy
    private JudgeService judgeService;

    /**
     * 题目提交
     *
     * @param submitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    @Override
    public long doSubmit(SubmitAddRequest submitAddRequest, User loginUser) {
        // 编程语言校验
        String language = submitAddRequest.getLanguage();
        System.out.println("language:" + language);
        CodeLanguageEnum codeLanguageEnum = CodeLanguageEnum.getEnumByValue(language);
        if (codeLanguageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        // 判断题目是否存在
        long questionId = submitAddRequest.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 设置 submit 属性
        long userId = loginUser.getId();
        Submit submit = new Submit();
        submit.setUserId(userId);
        submit.setQuestionId(questionId);
        submit.setCode(submitAddRequest.getCode());
        submit.setLanguage(language);
        submit.setStatus(SubmitStatusEnum.WAITING.getValue());
        submit.setJudgeInfo("{}");
        boolean save = this.save(submit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交失败");
        }
        // 调用判题服务
        long submitId = submit.getId();
        CompletableFuture.runAsync(() -> {
            judgeService.doJudge(submitId);
        });
        return submitId;
    }

    /**
     * 获取查询包装类
     *
     * @param submitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Submit> getQueryWrapper(SubmitQueryRequest submitQueryRequest) {
        QueryWrapper<Submit> queryWrapper = new QueryWrapper<>();
        if (submitQueryRequest == null) {
            return queryWrapper;
        }
        String language = submitQueryRequest.getLanguage();
        Integer status = submitQueryRequest.getStatus();
        Long userId = submitQueryRequest.getUserId();
        Long questionId = submitQueryRequest.getQuestionId();
        String sortField = submitQueryRequest.getSortField();
        String sortOrder = submitQueryRequest.getSortOrder();

        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(SubmitStatusEnum.getEnumByValue(status) != null, "status", status);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public SubmitVO getSubmitVO(Submit submit, User loginUser) {
        SubmitVO submitVO = SubmitVO.objToVo(submit);
        long userId = loginUser.getId();
        if (userId != submit.getUserId() && !userService.isAdmin(loginUser)) {
            submitVO.setCode(null);
        }

        return submitVO;
    }

    @Override
    public Page<SubmitVO> getSubmitVOPage(Page<Submit> submitPage, User loginUser) {
        List<Submit> submitList = submitPage.getRecords();
        Page<SubmitVO> submitVOPage = new Page<>(submitPage.getCurrent(), submitPage.getSize(), submitPage.getTotal());
        if (CollectionUtils.isEmpty(submitList)) {
            return submitVOPage;
        }

        List<SubmitVO> submitVOList = submitList.stream()
                .map(submit -> getSubmitVO(submit, loginUser))
                .collect(Collectors.toList());
        submitVOPage.setRecords(submitVOList);
        return submitVOPage;
    }
}




