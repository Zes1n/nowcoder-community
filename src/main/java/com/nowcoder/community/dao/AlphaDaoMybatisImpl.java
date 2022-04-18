package com.nowcoder.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @author wuzexin
 * @created 2022/4/14 09:06
 */

@Repository("AlphaDaoMybatisImpl")
@Primary
public class AlphaDaoMybatisImpl implements AlphaDao{

    @Override
    public String select() {
        return "Mybatis";
    }
}
