CREATE TABLE tb_product
(
    product_id       BIGINT      NOT NULL COMMENT '商品ID',
    product_category BIGINT      NOT NULL COMMENT '商品所属分类',
    product_name     VARCHAR(32) NOT NULL COMMENT '商品名称',
    product_desc     VARCHAR(1024) COMMENT '商品详情',
    product_img      VARCHAR(32) COMMENT '商品图片',
    removed          tinyint(1) COMMENT '逻辑删除N为正常，Y为删除',
    create_time      DATETIME COMMENT '创建时间',
    update_time      DATETIME COMMENT '更新时间',
    PRIMARY KEY (product_id)
) ENGINE = InnoDB,COMMENT = 'tb_product 商品信息主表';

CREATE TABLE tb_product_category
(
    category_id   BIGINT       NOT NULL COMMENT '商品分类ID',
    shop_id       BIGINT       NOT NULL COMMENT '商品分类所属店铺',
    category_name VARCHAR(128) NOT NULL COMMENT '目录名',
    category_desc VARCHAR(512) COMMENT '目录描述',
    removed       tinyint(1) COMMENT '逻辑删除N为正常，Y为删除',
    create_time   DATETIME COMMENT '创建时间',
    update_time   DATETIME COMMENT '更新时间',
    PRIMARY KEY (category_id)
) ENGINE = InnoDB,COMMENT = 'tb_product_category ';

CREATE TABLE tb_product_stock
(
    product_id  BIGINT NOT NULL COMMENT '商品ID',
    stock       INT    NOT NULL COMMENT '商品库存',
    removed     tinyint(1) COMMENT '逻辑删除N为正常，Y为删除',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (product_id)
) ENGINE = InnoDB,COMMENT = 'tb_product_stock 商品库存表，库存一般是写入热点，独立出表方便后期优化';;

CREATE TABLE tb_product_evaluation
(
    eval_id     BIGINT COMMENT '评价ID',
    product_id  BIGINT COMMENT '评价商品ID',
    consumer_id BIGINT COMMENT '订餐者',
    shop_id     BIGINT COMMENT '评价所属店铺',
    ranking     DECIMAL(32, 10) COMMENT '评价分数',
    content     VARCHAR(512) COMMENT '评价内容',
    removed     tinyint(1) COMMENT '逻辑删除N为正常，Y为删除',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间'
) ENGINE = InnoDB,COMMENT = 'tb_product_evaluation 商品评价表';;

CREATE TABLE tb_product_spec
(
    spec_id     VARCHAR(32) COMMENT 'spec',
    product_id  BIGINT COMMENT '规格所属商品',
    spec_name   VARCHAR(128) COMMENT '规格名称',
    package_fee DECIMAL(32, 8) COMMENT '包装费',
    price       DECIMAL(32, 8) COMMENT '价格',
    removed     tinyint(1) COMMENT '逻辑删除N为正常，Y为删除',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间'
) ENGINE = InnoDB,COMMENT = 'tb_product_spec 商品规格表';;