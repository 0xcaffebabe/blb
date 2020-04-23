CREATE TABLE tb_shop
(
    shop_id     BIGINT NOT NULL COMMENT '店铺ID',
    seller_id   BIGINT NOT NULL COMMENT '店铺所属商家ID',
    category_id BIGINT NOT NULL COMMENT '店铺所属分类',
    removed     tinyint(1) COMMENT '逻辑删除N为正常，Y为删除',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (shop_id)
) ENGINE = InnoDB,COMMENT = 'tb_shop 店铺主表';

CREATE TABLE tb_shop_info
(
    shop_id              BIGINT         NOT NULL COMMENT '',
    shop_name            VARCHAR(64)    NOT NULL COMMENT '店铺名称',
    shop_logo            VARCHAR(32) COMMENT '店铺标志',
    shop_address         VARCHAR(128)   NOT NULL COMMENT '店铺地址',
    phone                VARCHAR(32)    NOT NULL COMMENT '店铺联系电话',
    shop_desc            VARCHAR(512) COMMENT '店铺简介',
    shop_slogan          VARCHAR(128) COMMENT '店铺标语',
    delivery_fee         DECIMAL(32, 8) NOT NULL COMMENT '配送费',
    starting_price       DECIMAL(32, 8) NOT NULL COMMENT '起送价',
    location             VARCHAR(64)    NOT NULL COMMENT '店铺所在经纬度',
    business_hour        VARCHAR(128) COMMENT '营业时间',
    business_license     VARCHAR(1024)  NOT NULL COMMENT '营业执照图片地址',
    food_service_license VARCHAR(1024)  NOT NULL COMMENT '餐饮服务许可证图片地址',
    removed            tinyint(1) COMMENT '逻辑删除N为正常，Y为删除',
    create_time          DATETIME COMMENT '创建时间',
    update_time          DATETIME COMMENT '更新时间',
    PRIMARY KEY (shop_id)
) ENGINE = InnoDB,COMMENT = 'tb_shop_info 店铺信息表';;

CREATE TABLE tb_shop_category
(
    category_id    BIGINT       NOT NULL COMMENT '商铺目录ID',
    parent_id      BIGINT COMMENT '父目录ID',
    category_name  VARCHAR(128) NOT NULL COMMENT '目录名称',
    category_img   VARCHAR(1024) COMMENT '目录图片地址',
    category_level INT          NOT NULL COMMENT '目录层级(1级目录，2级目录...)',
    removed      tinyint(1) COMMENT '逻辑删除N为正常，Y为删除',
    create_time    DATETIME COMMENT '创建时间',
    update_time    DATETIME COMMENT '更新时间',
    PRIMARY KEY (category_id)
) ENGINE = InnoDB,COMMENT = 'tb_shop_category 商铺目录表';;