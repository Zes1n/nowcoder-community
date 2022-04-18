package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @author wuzexin
 * @created 2022/4/14 09:01
 */

@Repository("alphaHibernate")
public class AlphaDaoHibernateImpl implements AlphaDao {

    @Override
    public String select() {
        return "Hibernate";
    }
}
