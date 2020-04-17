package wang.ismy.blb.impl.product.message;

import com.rabbitmq.client.Channel;
import org.junit.jupiter.api.Test;

import org.springframework.amqp.core.MessageProperties;
import wang.ismy.blb.api.message.Message;
import wang.ismy.blb.impl.product.message.service.MessageService;

import java.io.IOException;

import static org.mockito.Mockito.*;

class MessageListenerTest {


    @Test
    void test() throws IOException {
        MessageService mock = mock(MessageService.class);
        Message message = new Message();
        message.setContent("content");
        message.setReceiverId("1");
        message.setType("email");

        Channel mockChannel = mock(Channel.class);
        MessageProperties mockProperties = mock(MessageProperties.class);
        org.springframework.amqp.core.Message mockMessage = mock(org.springframework.amqp.core.Message.class);
        when(mockMessage.getMessageProperties()).thenReturn(mockProperties);
        when(mockProperties.getDeliveryTag()).thenReturn(1L);
        MessageListener listener = new MessageListener(mock);
        listener.onMessage(mockMessage,mockChannel,message);
        verify(mockChannel).basicAck(eq(1L),eq(false));
    }
}