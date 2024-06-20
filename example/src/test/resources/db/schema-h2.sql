DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    `id`    INTEGER PRIMARY KEY NOT NULL   COMMENT '主键ID',
    `name`  VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    `age`   INTEGER NULL DEFAULT NULL COMMENT '年龄',
    `email` VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱'
);

CREATE TABLE IF not exists `stream_info`
(
    `id`    INTEGER  NOT NULL AUTO_INCREMENT ,
    `stream_id`  VARCHAR(32) NOT NULL DEFAULT '' ,
    `stream_url`  VARCHAR(255) NOT NULL DEFAULT '' ,
    `create_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    PRIMARY KEY(`id`)
);