CREATE TABLE `reviewers`
(
    `id`      BIGINT       NOT NULL AUTO_INCREMENT COMMENT '리뷰 작성자의 id',
    `user_id` VARCHAR(256) NOT NULL COMMENT '사용자의 id',
    `point`   INTEGER      NOT NULL COMMENT '작성자의 리뷰 포인트 값',
    PRIMARY KEY (`id`),
    UNIQUE KEY uk_reviewers_user_id (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
    COMMENT '리뷰 작성자의 정보';

CREATE TABLE `reviews`
(
    `id`             BIGINT       NOT NULL AUTO_INCREMENT COMMENT '이벤트 리뷰 정보의 id',
    `review_id`      VARCHAR(256) NOT NULL COMMENT '리뷰 정보의 id',
    `place_id`       VARCHAR(256) NOT NULL COMMENT '리뷰를 작성한 장소의 id',
    `reviewer_id`    bigint       NOT NULL COMMENT '리뷰를 작성한 작성자의 id',
    `content_length` INTEGER      NOT NULL COMMENT '리뷰 작성 내용의 길이',
    `photo_count`    INTEGER      NOT NULL COMMENT '리뷰 내 첨부 사진의 개수',
    `is_first`       BOOLEAN      NOT NULL COMMENT '첫 번째 리뷰의 여부',
    `create_at`      DATETIME     NOT NULL COMMENT '이벤트 리뷰 작성 시간',
    PRIMARY KEY (`id`),
    UNIQUE KEY uk_reviews_review_id (`review_id`),
    FOREIGN KEY fk_reviews_reviewer_id (`reviewer_id`) REFERENCES reviewers (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
    COMMENT '리뷰의 정보';

CREATE TABLE `event_logs`
(
    `id`            BIGINT      NOT NULL AUTO_INCREMENT COMMENT '이벤트 리뷰 로그의 id',
    `action_type`   VARCHAR(64) NOT NULL COMMENT '실행된 타입 정보',
    `point_type`    VARCHAR(64) NOT NULL COMMENT '포인트 타입 정보',
    `operator_type` VARCHAR(64) NOT NULL COMMENT '점수 타입 정보',
    `reviewer_id`   BIGINT      NOT NULL COMMENT '리뷰 작성자의 id',
    `create_at`     DATETIME    NOT NULL COMMENT '컬럼의 생성 시간',
    PRIMARY KEY (`id`),
    FOREIGN KEY fk_event_log_reviewer_id (`reviewer_id`) REFERENCES reviewers (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
    COMMENT '이벤트 리뷰의 로그 정보';

