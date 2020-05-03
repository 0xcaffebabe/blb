CREATE TABLE tb_seller
(
    seller_id   BIGINT COMMENT '商家ID',
    username    VARCHAR(32) COMMENT '',
    phone       VARCHAR(32) COMMENT '',
    email       VARCHAR(32) COMMENT '',
    password    VARCHAR(128) COMMENT '',
    enable   tinyint(1) COMMENT '账户是否可用',
    removed   tinyint(1) COMMENT '逻辑删除N为正常，Y为删除',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (seller_id)
) ENGINE = InnoDB,COMMENT = 'tb_seller 卖家主表';;

CREATE TABLE tb_seller_info
(
    seller_id     BIGINT COMMENT '',
    concat_number VARCHAR(32) COMMENT '联系电话',
    real_name     VARCHAR(32) COMMENT '商家真实姓名',
    id_number     VARCHAR(32) COMMENT '商家身份证号',
    removed     tinyint(1) COMMENT '逻辑删除N为正常，Y为删除',
    create_time   DATETIME COMMENT '创建时间',
    update_time   DATETIME COMMENT '更新时间',
    PRIMARY KEY (seller_id)
) ENGINE = InnoDB,COMMENT = 'tb_seller_info 商家信息表';;