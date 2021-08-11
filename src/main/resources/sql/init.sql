CREATE TABLE `tb_customer` (
                               `id` bigint(20) NOT NULL COMMENT 'id',
                               `id_no` varchar(32) DEFAULT NULL COMMENT '身份证',
                               `name` varchar(32) DEFAULT NULL COMMENT '姓名',
                               `cex` varchar(32) DEFAULT NULL COMMENT '性别',
                               `id_url` varchar(32) DEFAULT NULL COMMENT '身份证图片',
                               `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `modifier` bigint(20) DEFAULT NULL COMMENT '修改人',
                               `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
                               `valid` int(11) DEFAULT NULL COMMENT '是否删除 0删除',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户 ';

CREATE TABLE `tb_order` (
                            `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `modifier` bigint(20) DEFAULT NULL COMMENT '修改人',
                            `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
                            `valid` int(11) DEFAULT NULL COMMENT '是否删除 0删除',
                            `id` bigint(20) NOT NULL COMMENT 'id',
                            `order_no` varchar(32) DEFAULT NULL COMMENT '订单号',
                            `balance` decimal(6,2) DEFAULT NULL COMMENT '余额',
                            `status` varchar(32) DEFAULT NULL COMMENT '订单状态 lodging：入住中，out离开，closure:关闭',
                            `mortgage` decimal(6,2) DEFAULT NULL COMMENT '押金',
                            `total_price` decimal(10,2) DEFAULT NULL COMMENT '总价',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单';

CREATE TABLE `tb_order_customer` (
                                     `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
                                     `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                     `modifier` bigint(20) DEFAULT NULL COMMENT '修改人',
                                     `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
                                     `valid` int(11) DEFAULT NULL COMMENT '是否删除 0删除',
                                     `id` bigint(20) NOT NULL COMMENT 'id',
                                     `room_id` bigint(20) DEFAULT NULL COMMENT '房间id',
                                     `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
                                     `customer_id` bigint(20) DEFAULT NULL COMMENT '客户id',
                                     `status` varchar(32) DEFAULT NULL COMMENT '订单状态 lodging：入住中，out离开，closure:关闭',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单客户 ';


CREATE TABLE `tb_order_room` (
                                 `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
                                 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                 `modifier` bigint(20) DEFAULT NULL COMMENT '修改人',
                                 `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
                                 `valid` int(11) DEFAULT NULL COMMENT '是否删除 0删除',
                                 `id` bigint(20) NOT NULL COMMENT 'id',
                                 `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
                                 `room_id` varchar(32) DEFAULT NULL COMMENT '房间id',
                                 `pid` varchar(32) DEFAULT NULL COMMENT '上间房子的订单房间id 仅做转房使用',
                                 `beginTime` datetime DEFAULT NULL COMMENT '入住时间',
                                 `engTime` datetime DEFAULT NULL COMMENT '离开时间',
                                 `status` varchar(32) DEFAULT NULL COMMENT '订单状态 lodging：入住中，out离开，closure:关闭',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单房间 ';

CREATE TABLE `tb_room` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                           `room_no` varchar(32) DEFAULT NULL COMMENT '房间号',
                           `price` decimal(32,2) DEFAULT NULL COMMENT '价格',
                           `status` varchar(32) DEFAULT NULL COMMENT '状态 normal:正常，ready_clean：打扫中，repair：维修中，quit：退房',
                           `order_id` varchar(32) DEFAULT NULL COMMENT '当前入住订单id 退房时清除',
                           `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
                           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                           `modifier` bigint(20) DEFAULT NULL COMMENT '修改人',
                           `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
                           `valid` int(11) DEFAULT NULL COMMENT '是否删除 0删除',
                           `type` varchar(32) DEFAULT NULL COMMENT '房间类型',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8110772360677195777 DEFAULT CHARSET=utf8mb4 COMMENT='房间 ';

CREATE TABLE `tb_user` (
                           `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
                           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                           `modifier` bigint(20) DEFAULT NULL COMMENT '修改人',
                           `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
                           `valid` int(11) DEFAULT NULL COMMENT '是否删除 0删除',
                           `id` bigint(20) NOT NULL COMMENT 'id',
                           `user_name` varchar(32) DEFAULT NULL COMMENT '用户名',
                           `login_name` varchar(32) DEFAULT NULL COMMENT '登录名',
                           `pwd` varchar(32) DEFAULT NULL COMMENT '密码',
                           `Salt` varchar(32) DEFAULT NULL COMMENT '盐',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户 ';