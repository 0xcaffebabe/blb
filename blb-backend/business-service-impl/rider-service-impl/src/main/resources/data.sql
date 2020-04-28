INSERT INTO tb_rider
(
    rider_id,
    username,
    phone,
    email,
    password,
    enable,
    removed,
    create_time,
    update_time
)
VALUES (1,'rider-cxk','17359561234','71571@qq.com','202cb962ac59075b964b07152d234b70',1,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
    (2,'rider-xxl','17359561234','71571@qq.com','202cb962ac59075b964b07152d234b70',1,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );

INSERT INTO tb_rider_info
(
    rider_id,
    avatar,
    real_name,
    id_number,
    gender,
    removed,
    create_time,
    update_time
)
VALUES (1,'cxk-avatar','蔡徐坤','350623123402880588',1,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
        (2,'xxl-avatar','徐雪莉','3506231234028805xx',0,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );

INSERT INTO tb_rider_order
(
    order_id,
    rider_id,
    removed,
    create_time,
    update_time
)
VALUES (1,1,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
        (406,1,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
        (2,2,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );

INSERT INTO tb_rider_evaluation
(
    eval_id,
    order_id,
    rider_id,
    consumer_id,
    ranking,
    content,
    removed,
    create_time,
    update_time
)
VALUES (1,406,1,1,3.5,'骑手评价1',0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
    (2,2,2,1,3.5,'骑手评价2',0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP )