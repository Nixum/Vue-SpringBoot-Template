package com.nixum.common.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring MVC 配置配置类
 * 如果使用了mvc配置，继承addResourceHandlers，
 * 会使用addResourceHandlers方法，默认没具体实现，需要重写
 * @author Zoctan
 * @date 2018/06/09
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 静态资源映射
     * https://blog.csdn.net/catoop/article/details/50501706
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    /**
     * 请求和响应数据转化成json
     * 阿里 FastJson 作JSON MessageConverter
     */
    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
        final FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        final FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                // 保留空的字段
                //SerializerFeature.WriteMapNullValue,
                // String null -> ""
                SerializerFeature.WriteNullStringAsEmpty,
                // Number null -> 0
                SerializerFeature.WriteNullNumberAsZero
        );
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converters.add(converter);
        // 233moutian add this to fix "Caused by: java.lang.IllegalArgumentException: 'Content-Type' cannot contain wildcard type '*'"
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);
        converter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(converter);

    }

    // 可以在这里添加拦截器, 重写addInterceptors方法,
    // registry添加自定义拦截器(实现HandlerInterceptor)和路径
}