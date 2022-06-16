CREATE TABLE `users`
(
    `id`      bigint       NOT NULL AUTO_INCREMENT COMMENT '사용자 기본 키',
    `user_id` varchar(128) NOT NULL COMMENT '사용자 id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER
      SET = utf8mb4
    COMMENT
        '사용자 정보';

CREATE TABLE `reviews`
(
    `id`        bigint       NOT NULL AUTO_INCREMENT COMMENT '리뷰 기본 키',
    `review_id` varchar(128) NOT NULL COMMENT '리뷰 id',
    `user_id` varchar(128) NOT NULL COMMENT '연관관계 사용자 id',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES users(`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER
      SET = utf8mb4
    COMMENT
        '리뷰 정보';

CREATE TABLE `places`
(
    `id`       bigint       NOT NULL AUTO_INCREMENT COMMENT '장소 기본 키',
    `place_id` varchar(128) NOT NULL COMMENT '장소 id',
    `user_id` varchar(128) NOT NULL COMMENT '연관관계 사용자 id',
    `review_id` varchar(128) NOT NULL COMMENT '연관관계 리뷰 id',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES users(user_id),
    FOREIGN KEY (`review_id`) REFERENCES reviews(review_id)
) ENGINE = InnoDB
  DEFAULT CHARACTER
      SET = utf8mb4
    COMMENT
        '장소 정보';

CREATE TABLE `photos`
(
    `id`       bigint       NOT NULL AUTO_INCREMENT COMMENT '사진 기본 키',
    `photo_id` varchar(128) NOT NULL COMMENT '사진 id',
    `review_id` varchar(128) NOT NULL COMMENT '연관관계 리뷰 id',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`review_id`) REFERENCES reviews(review_id)
) ENGINE = InnoDB
  DEFAULT CHARACTER
      SET = utf8mb4
    COMMENT
        '사진 정보';
