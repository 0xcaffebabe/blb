package wang.ismy.blb.impl.product.message.service;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;
class MessageServiceImplTest {

    @Test
    void test(){
        JavaMailSender mockMailSender = mock(JavaMailSender.class);
        ApplicationContext mockContext = mock(ApplicationContext.class);
        Environment mockEnv = mock(Environment.class);
        when(mockContext.getEnvironment()).thenReturn(mockEnv);
        when(mockEnv.getProperty(eq("spring.mail.username"))).thenReturn("username");

        String to = "to";
        String content = "content";
        String subject = "subject";
        MessageServiceImpl messageServiceImpl = new MessageServiceImpl(mockMailSender,mockContext);
        messageServiceImpl.sendTextMail(to,subject,content);

        verify(mockMailSender).send(argThat((SimpleMailMessage s)->
            s.getFrom().equals("username") && s.getText().equals(content) &&
                        s.getSubject().equals(subject) && s.getTo()[0].equals(to)
        ));
    }
}