INSERT INTO tb_product
(product_id,
 product_category,
 product_name,
 product_desc,
 product_img,
 removed,
 create_time,
 update_time)
VALUES (1, 1, '黄焖鸡米饭', '香香甜甜的黄焖鸡米饭', '黄焖鸡米饭img', 0, CURRENT_TIME, CURRENT_TIME),
       (2, 1, '黄焖猪脚米饭', '香香甜甜的黄焖脚米饭', '黄焖猪米饭img', 0, CURRENT_TIME, CURRENT_TIME),
       (3, 1, '黄焖鸭米饭', '香香甜甜的黄焖鸭米饭', '黄焖鸭米饭img', 0, CURRENT_TIME, CURRENT_TIME);

INSERT INTO tb_product_category
(category_id,
 shop_id,
 category_name,
 category_desc,
 removed,
 create_time,
 update_time)
VALUES (1, 1, '1号店铺招牌菜', '黄焖系列', 0, CURRENT_TIME, CURRENT_TIME),
       (2, 1, '1号店铺兼职', '沙县小吃系列', 0, CURRENT_TIME, CURRENT_TIME);

INSERT INTO tb_product_spec
(spec_id,
 product_id,
 spec_name,
 package_fee,
 price,
 stock,
 removed,
 create_time,
 update_time)
VALUES (1, 1, '黄焖鸡-大份', 2, 18,100, 0, CURRENT_TIME, CURRENT_TIME),
       (2, 1, '黄焖鸡-小份', 2, 13, 100,0, CURRENT_TIME, CURRENT_TIME),
       (3, 2, '黄焖猪脚-正常规格', 2, 13,100, 0, CURRENT_TIME, CURRENT_TIME),
       (4, 3, '黄焖鸭-正常规格', 2, 13, 100,0, CURRENT_TIME, CURRENT_TIME);

INSERT INTO tb_product_evaluation
(
    eval_id,
    product_id,
    consumer_id,
    shop_id,
    ranking,
    content,
    removed,
    create_time,
    update_time
)
VALUES ( 1,1,1,1,3.5,'黄焖鸡评价1',0,CURRENT_TIME,CURRENT_TIME ),
       ( 2,1,2,1,3.5,'黄焖鸡评价2',0,CURRENT_TIME,CURRENT_TIME ),
       ( 3,1,3,1,3.5,'黄焖鸡评价3',0,CURRENT_TIME,CURRENT_TIME ),
       ( 4,1,4,1,3.5,'黄焖鸡评价4',0,CURRENT_TIME,CURRENT_TIME )