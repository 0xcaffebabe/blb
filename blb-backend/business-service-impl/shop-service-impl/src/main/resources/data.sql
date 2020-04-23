INSERT INTO tb_shop
(
    shop_id,
    seller_id,
    category_id,
    removed,
    create_time,
    update_time
)
VALUES (1,1,2,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
(2,2,2,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );

INSERT INTO tb_shop_info
(
    shop_id,
    shop_name,
    shop_logo,
    shop_address,
    phone,
    shop_desc,
    shop_slogan,
    delivery_fee,
    starting_price,
    location,
    business_hour,
    business_license,
    food_service_license,
    removed,
    create_time,
    update_time
)
VALUES (1,'黄焖鸡米饭','黄焖鸡米饭logo','媒人桥路2号','05963712345',
'纯正黄焖鸡米饭','让生活更有滋味',5,20,'117,29','8:00-20:00','license','license',0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
(2,'阿牛很芒','阿牛很芒logo','媒人桥路25号','05963712340',
'西式快餐','饿了吃不死',5,20,'117,29','8:00-20:00','license','license',0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );

INSERT INTO tb_shop_category
(
    category_id,
    parent_id,
    category_name,
    category_img,
    category_level,
    removed,
    create_time,
    update_time
)
VALUES (1,null,'快餐','快餐img',1,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
(2,1,'简餐','简餐img',2,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
(3,null,'甜品','简餐img',1,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP ),
(4,2,'汉堡可乐','汉堡可乐img',3,0,CURRENT_TIMESTAMP ,CURRENT_TIMESTAMP );