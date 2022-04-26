package com.nowcoder.community;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.MessageMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author wuzexin
 * @created 2022/4/16 08:55
 */
@SpringBootTest
public class MapperTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Autowired
    private LoginTicketMapper loginTicketMapper;
    @Autowired
    private MessageMapper messageMapper;

    @Test
    public void testSelectUser() {
        User user = userMapper.selectById(1);
        System.out.println(user);

        user = userMapper.selectByName("liubei");
        System.out.println(user);
    }

    @Test
    public void testInserUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("1234");
        user.setSalt("abc");
        user.setEmail("email@qq.com");
        user.setHeaderUrl("xxx");
        user.setCreateTime(new Date());

        int i = userMapper.insertUser(user);
        System.out.println(i);

        System.out.println(user.getId());
    }

    @Test
    public void testUpdateUser() {
        int i = userMapper.updateStatus(150, 2);
        System.out.println(i);

        int i1 = userMapper.updateHeader(150, "http://nowcoder.com/101.png");
        System.out.println(i1);

        int i2 = userMapper.updatePassword(150, "newPassword");
        System.out.println(i2);
    }

    @Test
    public void testSelectPosts() {
        int i = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(i);

        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(149, 0, 10);
        for (DiscussPost discussPost : list) {
            System.out.println(discussPost);
        }
    }

    @Test
    public void testInsertLoginTicket() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setStatus(0);
        loginTicket.setTicket("abc");
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));

        int i = loginTicketMapper.insertLoginTicket(loginTicket);
        System.out.println(i);
    }

    @Test
    public void testSelectLoginTicket() {
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);

        int i = loginTicketMapper.updateStatus("abc", 1);
        System.out.println(i);
    }

    @Test
    public void testSelectLetters() {
        List<Message> list = messageMapper.selectConversations(111, 0, 10);
        System.out.println(list.toString());

        int i = messageMapper.selectConversationCount(111);
        System.out.println(i);

        List<Message> messages = messageMapper.selectLetters("111_112", 0, 10);
        System.out.println(messages.toString());

        System.out.println(messageMapper.selectLetterCount("111_112"));

        final int i1 = messageMapper.selectLetterUnreadCount(131, "111_131");
        System.out.println(i1);
    }
}
