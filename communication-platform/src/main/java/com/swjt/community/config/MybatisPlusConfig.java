/**
 * @Type com.swjt.community.config
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/10/27  14:30
 * @VERSION Version 1.0
 **/

package com.swjt.community.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.swjt.community.mapper")
public class MybatisPlusConfig {


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){

        MybatisPlusInterceptor mybatisPlusInterceptor=new MybatisPlusInterceptor();

        /*
        分页插件，可以快速进行分页
         */
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        //防止全表更新，即如果语句是未来更新修改某一个，但是由于没有输入条件而进行了全表更新
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return mybatisPlusInterceptor;
    }

    /*
    未来避免分页插件出现问题而创建
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }

}
