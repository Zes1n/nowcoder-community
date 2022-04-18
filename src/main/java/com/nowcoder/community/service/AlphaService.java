package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * TODO
 *
 * @author wuzexin
 * @created 2022/4/14 10:02
 */

@Service
// scope默认方式为singleton单例，每次访问bean都是同一个实例
// 用prototype，则每次访问都创建一个新的实例
//@Scope("prototype")
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;

    public AlphaService() {
        System.out.println("实例化AlphaService");
    }

    // 这个注解表示，这个方法会在构造器之后调用
    @PostConstruct
    public void init() {
        System.out.println("初始化AlphaService");
    }

    // 销毁对象之前会调用这个方法
    @PreDestroy
    public void destroy() {
        System.out.println("销毁AlphaService");
    }

    public String find() {
        return alphaDao.select();
    }
}
