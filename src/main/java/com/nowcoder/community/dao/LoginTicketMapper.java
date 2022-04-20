package com.nowcoder.community.dao;

import com.nowcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * TODO
 *
 * @author wuzexin
 * @created 2022/4/19 07:36
 */
@Mapper
public interface LoginTicketMapper {

    // 注解里面会自动把逗号拼接起来
    @Insert({
            "insert into login_ticket(user_id, ticket, status, expired) ",
            "values(#{userId}, #{ticket}, #{status}, #{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id") // 主键自动生成，生成的值注入给id
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id, user_id, ticket, status, expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

//     注解的方式写if
//    @Update({
//            "<script>",
//            "update login_ticket set ",
//            "status=#{status} where ticket=#{ticket} ",
//            "<if test=\"ticket!=null\">",
//                "and 1 = 1",
//            "</if>",
//            "<script>"
//    })

    @Update({
            "update login_ticket set ",
            "status=#{status} where ticket=#{ticket}"
    })
    int updateStatus(String ticket, int status);
}
