INSERT INTO tb_pay
(
    pay_id,
    third_part_id,
    pay_type,
    order_id,
    consumer_id,
    pay_amount,
    pay_status,
    pay_title,
    removed,
    create_time,
    update_time
)
VALUES (1,1,'支付宝',1,1,50,1,'cxk的点餐订单',0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
        (2,2,'支付宝',2,1,50,0,'cxk的点餐订单',0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );