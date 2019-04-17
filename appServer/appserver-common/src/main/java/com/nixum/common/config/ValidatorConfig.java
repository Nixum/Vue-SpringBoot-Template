package com.nixum.common.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * hibernate validator参数校验配置类
 * https://www.cnblogs.com/cjsblog/p/8946768.html
 * @author Zoctan
 * @date 2018/06/09
 */
@Configuration
public class ValidatorConfig {
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        final MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        // 设置 validator 模式为快速失败返回
        postProcessor.setValidator(this.validatorFailFast());
        return postProcessor;
        // 默认是普通模式，会返回所有的验证不通过信息集合
        // return new MethodValidationPostProcessor();
    }

    @Bean
    public Validator validatorFailFast() {
        // 快速失败返回，有一个验证失败立即返回错误信息
        return Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory()
                .getValidator();
    }
}