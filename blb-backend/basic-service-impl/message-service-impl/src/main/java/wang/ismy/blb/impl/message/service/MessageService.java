package wang.ismy.blb.impl.message.service;

/**
 * @author MY
 * @date 2020/4/14 10:36
 */
public interface MessageService {

    /**
     * 发送邮件
     * @param to
     * @param subject
     * @param content
     */
    void sendTextMail(String to,String subject,String content);
}
