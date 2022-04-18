package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.Thymeleaf;
import org.thymeleaf.context.Context;

import java.util.UUID;

/**
 * TODO
 *
 * @author wuzexin
 * @created 2022/4/18 09:41
 */
@SpringBootTest
public class MailTest {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void mailTest() {
        String to = "4066468@qq.com";
        String subject = "Mail Test";
        String content = "<h1>测试邮箱发送</h1>";
        mailClient.sendMail(to, subject, content);
    }

    /*
        利用thymeleaf生成邮件内容
     */
    @Test
    public void testHtmlMail() {
        Context context = new Context();
        context.setVariable("username", "sun");

        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("4066468@qq.com", "test", content);
    }

}
