INSERT INTO tb_consumer
(
    user_id,
    username,
    password,
    email,
    phone,
    enable,
    removed,
    create_time,
    update_time
)
VALUES (1,'cxk','202cb962ac59075b964b07152d234b70','cxk@jntm.com','17359561234',1,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP ),
(2,'xxl','202cb962ac59075b964b07152d234b70','xxl@jntm.com','17359561234',1,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP );

INSERT INTO tb_consumer_info
(
    user_id,
    avatar,
    real_name,
    gender,
    removed,
    create_time,
    update_time
)
VALUES (1,'cxk avatar','蔡徐坤',0,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
(2,'xxl avatar','徐雪莉',0,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );

INSERT INTO tb_delivery_info
(
    delivery_info_id,
    building,
    detail,
    removed,
    create_time,
    update_time
)
VALUES (1,'翻斗大街42栋','3602',0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
        (2,'爱情公寓A楼','3601',0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
        (3,'泉州软件学院','305',0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );

INSERT INTO tb_consumer_delivery
(
    delivery_info_id,
    user_id,
    default_delivery,
    removed,
    create_time,
    update_time
)
VALUES (1,1,0,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
        (2,1,1,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
        (3,2,1,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );