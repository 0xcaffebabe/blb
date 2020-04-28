CREATE TABLE tb_rider(
    rider_id BIGINT NOT NULL   COMMENT '骑手ID' ,
    username VARCHAR(64) NOT NULL   COMMENT '骑手用户名' ,
    phone VARCHAR(32) NOT NULL   COMMENT '手机号码' ,
    email VARCHAR(32) NOT NULL   COMMENT '电子邮箱' ,
    password VARCHAR(128)    COMMENT '登录密码' ,
    enable tinyint(1) NOT NULL   COMMENT '账户状态Y为正常N为不可用' ,
    removed tinyint(1)    COMMENT '逻辑删除N为正常，Y为删除' ,
    create_time DateTime    COMMENT '创建时间' ,
    update_time DateTime    COMMENT '更新时间' ,
    PRIMARY KEY (rider_id)
)ENGINE = InnoDB, COMMENT = 'tb_rider 骑手主表';;

CREATE TABLE tb_rider_info(
    rider_id BIGINT NOT NULL   COMMENT '骑手唯一ID' ,
    avatar VARCHAR(1024)    COMMENT '骑手头像' ,
    real_name VARCHAR(32)    COMMENT '骑手真实姓名' ,
    id_number VARCHAR(32)    COMMENT '骑手身份证号' ,
    gender tinyint(1)    COMMENT '骑手性别T为男，F为女' ,
    removed tinyint(1)    COMMENT '逻辑删除N为正常，Y为删除' ,
    create_time DateTime    COMMENT '创建时间' ,
    update_time DateTime    COMMENT '更新时间' ,
    PRIMARY KEY (rider_id)
) ENGINE = InnoDB,COMMENT = 'tb_rider_info 骑手信息表';;

CREATE TABLE tb_rider_evaluation(
    eval_id BIGINT NOT NULL   COMMENT '骑手评价ID' ,
    order_id BIGINT NOT NULL   COMMENT '该评价所属订单' ,
    rider_id BIGINT NOT NULL   COMMENT '骑手ID' ,
    consumer_id BIGINT NOT NULL COMMENT '评价人',
    ranking DECIMAL(32,10) NOT NULL   COMMENT '本次评价评分' ,
    content VARCHAR(128) NOT NULL   COMMENT '评价内容' ,
    removed tinyint(1)    COMMENT '逻辑删除N为正常，Y为删除' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (eval_id)
) ENGINE = InnoDB,COMMENT = 'tb_rider_evaluation 骑手评价表';;

CREATE TABLE tb_rider_order(
    order_id BIGINT NOT NULL   COMMENT '订单ID' ,
    rider_id BIGINT    COMMENT '' ,
    removed tinyint(1)    COMMENT '逻辑删除N为正常，Y为删除' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_time DATETIME    COMMENT '更新时间' ,
    PRIMARY KEY (order_id)
) ENGINE = InnoDB,COMMENT = 'tb_rider_order 骑手订单联系表';;