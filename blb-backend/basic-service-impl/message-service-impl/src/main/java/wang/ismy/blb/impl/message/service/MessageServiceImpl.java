package wang.ismy.blb.impl.message.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 邮件服务配置信息都存在配置中心了
 * @author MY
 * @date 2020/4/14 8:49
 */
@Service
@AllArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService{

    private final JavaMailSender javaMailSender;
    private final ApplicationContext applicationContext;

    @Override
    public void sendTextMail(String to, String subject, String content){
        log.info("发送一封邮件给:{}",to);
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        String from = applicationContext.getEnvironment().getProperty("spring.mail.username");
        if (StringUtils.isEmpty(from)){
            log.error("发送邮件时无法获取到from");
            return;
        }
        message.setFrom(from);
        javaMailSender.send(message);
    }
}
