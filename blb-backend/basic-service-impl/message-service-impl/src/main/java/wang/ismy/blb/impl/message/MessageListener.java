package wang.ismy.blb.impl.message;

import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import wang.ismy.blb.impl.message.service.MessageService;
import wang.ismy.blb.impl.message.service.MessageServiceImpl;

import java.io.IOException;

/**
 * @author MY
 * @date 2020/4/14 9:48
 */
@Component
@Slf4j
@AllArgsConstructor
public class MessageListener {

    private final MessageService messageService;

    /**
     * 当其他服务需要发送消息通知
     * 需要往message-exchange投递消息
     * 数据是Message
     * @param message
     * @param channel
     * @param payload
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "message-queue"),
            exchange = @Exchange(name = "message-exchange", type = "fanout")
    ))
    @RabbitHandler
    public void onMessage(Message message, Channel channel, @Payload wang.ismy.blb.api.message.Message payload) throws IOException {
        log.info("消费消息队列消息:{}", payload);
        if (StringUtils.isEmpty(payload.getType())) {
            log.warn("消息类型为空,{}", payload);
            confirm(message, channel);
            return;
        }
        if (StringUtils.isEmpty(payload.getContent())) {
            log.warn("消息内容为空,{}", payload);
            confirm(message, channel);
            return;
        }
        if (StringUtils.isEmpty(payload.getReceiverId())) {
            log.warn("消息接受者为空,{}", payload);
            confirm(message, channel);
            return;
        }
        messageService.sendTextMail(payload.getReceiverId(), "饱了吧邮件通知", payload.getContent());
        confirm(message,channel);
        log.info("确认消费消息完成:{}", payload);
    }

    private void confirm(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
