CREATE TABLE `reviewers`
(
    `id`      bigint       NOT NULL AUTO_INCREMENT COMMENT '기본 키',
    `point`   bigint       NOT NULL COMMENT '이벤트 점수',
    `user_id` varchar(256) NOT NULL COMMENT '사용자 id',
    PRIMARY KEY (`id`),
    UNIQUE KEY uk_user_id (`user_id`),
    INDEX `idx_reviewers_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER
      SET = utf8mb4
    COMMENT
        '리뷰 작성자의 정보';

CREATE TABLE `reviews`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT '기본 키',
    `review_id`      varchar(256) NOT NULL COMMENT '리뷰의 id 값',
    `place_id`       varchar(256) NOT NULL COMMENT '장소의 id 값',
    `reviewer_id`    bigint       NOT NULL COMMENT '리뷰 작성자의 id',
    `content_length` bigint       NOT NULL COMMENT '리뷰 내용물의 길이',
    `photo_count`    bigint       NOT NULL COMMENT '리뷰에 담긴 사진의 갯수',
    `is_first`       boolean      NOT NULL COMMENT '첫 리뷰 여부',
    PRIMARY KEY (`id`),
    FOREIGN KEY fk_reviews_reviewer_id(`reviewer_id`) references reviewers (`id`),
    UNIQUE KEY uk_reviews_review_id (`review_id`),
    INDEX idx_reviews_review_id (`review_id`),
    INDEX idx_reviews_place_id (`place_id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER
      SET = utf8mb4
    COMMENT
        '리뷰의 정보';

CREATE TABLE `event_logs`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT '기본 키',
    `action_type`   varchar(128) NOT NULL COMMENT '실행된 행동',
    `point_type`    varchar(128) NOT NULL COMMENT '포인트 적립된 이유',
    `operator_type` varchar(128) NOT NULL COMMENT '연산에 대한 타입',
    `create_at`     datetime     NOT NULL COMMENT '저장된 시간',
    `review_id`     bigint       NOT NULL COMMENT '리뷰 id',
    PRIMARY KEY (`id`),
    FOREIGN KEY fk_event_logs_review_id(`review_id`) REFERENCES reviews (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER
      SET = utf8mb4
    COMMENT
        '이벤트 정보 로그';
