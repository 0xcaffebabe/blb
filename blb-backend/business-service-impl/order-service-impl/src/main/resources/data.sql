INSERT INTO tb_order
(
    order_id,
    shop_id,
    consumer_id,
    consumer_name,
    consumer_phone,
    consumer_address,
    order_amount,
    order_status,
    pay_status,
    order_note,
    removed,
    create_time,
    update_time
)
VALUES (1,1,1,'蔡徐坤','17359561234','泉州师范学院软件学院 男B305',7,1,2,'芋圆波波奶茶不要芋圆不要奶茶',0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
    (2,1,1,'蔡徐坤','17359561234','泉州师范学院软件学院 男B305',15,0,2,'黄焖鸡米饭多一点',0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );

INSERT INTO tb_order_detail
(
    detail_id,
    order_id,
    product_id,
    product_name,
    product_img,
    product_quantity,
    product_price,
    product_spec,
    spec_name,
    removed,
    create_time,
    update_time
)
VALUES (1,1,1,'黄焖鸡米饭','黄焖鸡米饭img',1,3.5,1,'大份',0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
        (2,1,1,'黄焖鸡米饭','黄焖鸡米饭img',1,3.5,2,'小份',0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
        (3,2,2,'黄焖猪脚米饭','黄焖猪脚米饭img',2,3.5,3,'小份',0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
        (4,2,3,'黄焖鸭米饭','黄焖鸭米饭img',2,3.5,4,'小份',0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );
