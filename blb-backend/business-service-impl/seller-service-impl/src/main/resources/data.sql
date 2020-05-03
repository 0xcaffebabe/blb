INSERT INTO tb_seller
(
    seller_id,
    username,
    phone,
    email,
    password,
    enable,
    removed,
    create_time,
    update_time
)
VALUES (1,'seller-cxk','17359561234','admin@qq.com','202cb962ac59075b964b07152d234b70',1,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
(2,'seller-xxl','17359561234','admin@qq.com','202cb962ac59075b964b07152d234b70',1,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );

INSERT INTO tb_seller_info
(
    seller_id,
    concat_number,
    real_name,
    id_number,
    removed,
    create_time,
    update_time
)
VALUES (1,'173595612334','蔡徐坤','36059682749285902854',0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
(2,'173595xx2334','徐雪莉','3605968274928590285x',0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );