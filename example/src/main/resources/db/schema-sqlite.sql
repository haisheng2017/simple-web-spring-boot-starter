CREATE TABLE IF not exists `stream_info`
(
    `id`    INTEGER  NOT NULL ,
    `stream_id`  VARCHAR(32) NOT NULL DEFAULT '' ,
    `stream_url`  VARCHAR(255) NOT NULL DEFAULT '' ,
    `create_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    PRIMARY KEY(`id`)
);