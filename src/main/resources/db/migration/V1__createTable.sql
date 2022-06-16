CREATE TABLE `review_events`
(
    `id`       bigint       NOT NULL AUTO_INCREMENT COMMENT '이벤트의 id',
    `type`     varchar(128) NOT NULL COMMENT '이벤트의 타입',
    `points`   bigint       NOT NULL COMMENT '이벤트 점수',
    `user_id`  varchar(256) NOT NULL COMMENT '사용자 id',
    `place_id` varchar(256) NOT NULL COMMENT '장소 id',
    PRIMARY KEY (`id`),
    INDEX `idx_events_user_id` (`user_id`),
    INDEX `idx_events_place_id` (`place_id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER
      SET = utf8mb4
    COMMENT
        '이벤트 정보';

CREATE TABLE `review_event_logs`
(
    `id`              bigint       NOT NULL AUTO_INCREMENT COMMENT '로그 id',
    `action_type`     varchar(128) NOT NULL COMMENT '실행된 행동',
    `create_at`       datetime     NOT NULL COMMENT '저장된 시간',
    `review_event_id` bigint       NOT NULL COMMENT '이벤트 id',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`review_event_id`) REFERENCES review_events (`id`),
    INDEX `idx_review_event_logs_action_type` (`action_type`),
    INDEX `idx_review_event_logs_event_id` (`review_event_id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER
      SET = utf8mb4
    COMMENT
        '이벤트 정보 로그';
