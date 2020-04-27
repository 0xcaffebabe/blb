CREATE TABLE tb_pay(
    pay_id BIGINT NOT NULL   COMMENT '支付ID' ,
    third_part_id VARCHAR(128)    COMMENT '第三方支付系统ID' ,
    pay_type VARCHAR(32) NOT NULL   COMMENT '支付类型' ,
    order_id BIGINT NOT NULL   COMMENT '所属订单' ,
    consumer_id BIGINT NOT NULL   COMMENT '所属订餐者' ,
    pay_amount DECIMAL(32,8) NOT NULL   COMMENT '实际支付金额' ,
    pay_status TINYINT NOT NULL   COMMENT '支付状态，0为未支付，1为已支付，2为已取消' ,
    pay_title VARCHAR(128) COMMENT '订单标题',
    removed tinyint(1)    COMMENT '逻辑删除N为正常，Y为删除' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (pay_id)
) ENGINE = InnoDB,COMMENT = 'tb_pay ';;