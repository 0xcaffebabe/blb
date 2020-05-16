CREATE TABLE tb_order(
    order_id BIGINT NOT NULL   COMMENT '订单ID' ,
    shop_id BIGINT NOT NULL   COMMENT '订单所属店铺' ,
    consumer_id BIGINT NOT NULL   COMMENT '订餐者' ,
    consumer_name VARCHAR(128)    COMMENT '收货人' ,
    consumer_phone VARCHAR(32)    COMMENT '收货人手机' ,
    consumer_address VARCHAR(1024)    COMMENT '收货地址' ,
    order_amount DECIMAL(32,8) NOT NULL   COMMENT '订单总金额' ,
    order_status INT NOT NULL   COMMENT '订单状态0：未处理，1：已处理，2：配送中，3：已完结，4：已作废' ,
    pay_status INT NOT NULL   COMMENT '支付状态，0为未支付，1为已支付，2为已取消' ,
    order_note VARCHAR(512)    COMMENT '订单备注' ,
    removed tinyint(1)    COMMENT '逻辑删除N为正常，Y为删除' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (order_id)
) ENGINE = InnoDB,COMMENT = 'tb_order 订单主表';;

CREATE TABLE tb_order_detail(
    detail_id BIGINT NOT NULL   COMMENT '订单详情ID' ,
    order_id BIGINT NOT NULL   COMMENT '' ,
    product_id BIGINT NOT NULL   COMMENT '订单商品ID' ,
    product_name VARCHAR(128) NOT NULL   COMMENT '商品名称' ,
    product_img VARCHAR(1024)    COMMENT '商品图片地址' ,
    product_quantity INT NOT NULL   COMMENT '商品数量' ,
    product_price DECIMAL(32,8) NOT NULL   COMMENT '购买时的商品单价' ,
    product_spec BIGINT NOT NULL   COMMENT '商品规格' ,
    spec_name VARCHAR(128) NOT NULL COMMENT '规格名称',
    removed tinyint(1)    COMMENT '逻辑删除N为正常，Y为删除' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (detail_id)
) ENGINE = InnoDB,COMMENT = 'tb_order_detail 订单详细表';;