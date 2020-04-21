CREATE TABLE tb_consumer
(
    user_id     BIGINT       NOT NULL COMMENT '用户ID',
    username    VARCHAR(64)  NOT NULL COMMENT '用户名',
    password    VARCHAR(128) NOT NULL COMMENT '密码',
    email       VARCHAR(64)  NOT NULL COMMENT '电子邮箱',
    phone       VARCHAR(32)  NOT NULL COMMENT '手机号码',
    enable   tinyint(1)   NOT NULL COMMENT '账号是否可用 Y为可用 N为不可用',
    removed     tinyint(1) COMMENT '标记记录是否被逻辑删除 0 为正常 1为被删除',
    create_time DATETIME COMMENT '',
    update_time DATETIME COMMENT '',
    PRIMARY KEY (user_id)
) ENGINE = InnoDB,COMMENT = 'tb_consumer 订餐者主表';

CREATE TABLE tb_consumer_info
(
    user_id     BIGINT NOT NULL COMMENT '',
    avatar      VARCHAR(1024) COMMENT '用户头像图片地址',
    real_name   VARCHAR(32) COMMENT '用户的真实姓名',
    gender      tinyint(1) COMMENT '用户性别Y为男，N为女',
    removed        tinyint(1) COMMENT '逻辑删除 N为正常 Y为被删除',
    create_time DATETIME COMMENT '',
    update_time DATETIME COMMENT '',
    PRIMARY KEY (user_id)
) ENGINE = InnoDB,COMMENT = 'tb_consumer_info ';;

CREATE TABLE tb_delivery_info
(
    delivery_info_id BIGINT       NOT NULL COMMENT '收货信息ID',
    building         VARCHAR(128) NOT NULL COMMENT '收货地址具体小区 写字楼 学校等等',
    detail           VARCHAR(128) NOT NULL COMMENT '详细地址 如门牌号',
    removed        tinyint(1) COMMENT '逻辑删除 N为正常 Y为被删除',
    create_time      DATETIME COMMENT '创建时间',
    update_time      DATETIME COMMENT '更新时间',
    PRIMARY KEY (delivery_info_id)
) ENGINE = InnoDB,COMMENT = 'tb_delivery_info ';;

CREATE TABLE tb_consumer_delivery
(
    delivery_info_id BIGINT NOT NULL COMMENT '',
    user_id          BIGINT NOT NULL COMMENT '',
    is_default       tinyint(1) COMMENT '是否是默认收货信息',
    removed        tinyint(1) COMMENT '逻辑删除N为正常，Y为删除',
    create_time      DATETIME COMMENT '创建时间',
    update_time      DATETIME COMMENT '更新时间',
    PRIMARY KEY (delivery_info_id)
) ENGINE = InnoDB,COMMENT = 'tb_consumer_delivery ';;
