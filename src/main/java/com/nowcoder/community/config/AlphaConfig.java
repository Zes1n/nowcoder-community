package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * TODO
 *
 * @author wuzexin
 * @created 2022/4/14 13:11
 */
// 表示这个类是配置类，不是普通的类
@Configuration
public class AlphaConfig {

    @Bean
    public SimpleDateFormat simpleDateFormat () {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
