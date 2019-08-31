package com.zypcy.framework.fast;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.google.common.collect.Maps;
import com.zypcy.framework.fast.common.response.ResponseBodyWrapFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.Map;

@EnableAsync
@SpringBootApplication
@MapperScan({"com.zypcy.framework.fast.sys.mapper" , "com.zypcy.framework.fast.bus.mapper"})
public class FastApplication {

	@Resource
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(FastApplication.class, args);
	}

	/**
	 * 启用统一结果返回功能
	 * @return
	 */
	@Bean
	public ResponseBodyWrapFactory getResponseBodyWrapFactory(){
		return new ResponseBodyWrapFactory();
	}

	/**
	 * Mybatis-Plus分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}


	/**
	 * 给Thymeleaf视图引擎设置全局静态变量
	 * 使用方式：th:src="${StaticResourcePath}+@{'layui/lib/layui/layui.all.js'}"
	 * @param viewResolver
	 */
	@Resource
	private void configureThymeleafStaticVars(ThymeleafViewResolver viewResolver) {
		if(viewResolver != null) {
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("StaticResourcePath", env.getProperty("sys.static.res.path","/"));//静态资源路径，默认使用本地，发布环境使用https://res.zypcy.cn
			viewResolver.setStaticVariables(vars);
		}
	}

}
