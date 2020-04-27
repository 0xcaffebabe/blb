package wang.ismy.blb.impl.pay.service.impl;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.blb.api.pay.pojo.PayDO;
import wang.ismy.blb.impl.pay.pojo.PayResultDTO;
import wang.ismy.blb.impl.pay.repository.PayRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class AliPayServiceTest {

    @Autowired
    private AliPayService aliPayService;

    @Autowired
    private PayRepository payRepository;

//    @Test
    void test() throws InterruptedException {
        PayDO payDO = payRepository.findByOrderId(2L);
        aliPayService.refund(payDO.getOrderId());
        String qrCode = aliPayService.generatePay(payDO);
        System.out.println(qrCode);
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true) {
                    Thread.sleep(3000);
                    PayResultDTO pay = aliPayService.getPay(payDO.getOrderId());
                    System.out.println(pay);
                }
            }
        });
        thread.start();
        thread.join();
    }
}