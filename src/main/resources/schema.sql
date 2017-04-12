-- drop all table
DROP TABLE IF EXISTS store_value_policy;
DROP TABLE IF EXISTS store_desc;
DROP TABLE IF EXISTS store_photo;
DROP TABLE IF EXISTS store_value;
DROP TABLE IF EXISTS store;


-- store Table Create SQL
CREATE TABLE store
(
    `store_id`          BIGINT          NOT NULL    AUTO_INCREMENT COMMENT '상점 ID', 
    `store_name`        VARCHAR(100)    NULL         COMMENT '상점명', 
    `phone_number`      VARCHAR(20)     NOT NULL     COMMENT '연락처', 
    `view_count`        BIGINT          NOT NULL     COMMENT '상점 조회수', 
    `like_count`        BIGINT          NOT NULL     COMMENT '상점 좋아요 수', 
    `on_event`          VARCHAR(1)      NOT NULL         COMMENT '이벤트 여부 (Y,N)', 
    `user_avg_score`    REAL         NOT NULL         COMMENT '상점 사용자 평점', 
    `store_type`        VARCHAR(1)      NOT NULL         COMMENT '상점 유형. 소형,중형,대형(S, M, L)', 
    `last_update_date`  DATETIME        NOT NULL         COMMENT '최종 수정일', 
    `created_at`        DATETIME        NOT NULL, 
    `modified_at`       DATETIME        NOT NULL, 
    PRIMARY KEY (store_id)
);
ALTER TABLE store ADD CONSTRAINT store_type_check CHECK (store_type IN ('S','M','L'));
ALTER TABLE store ADD CONSTRAINT on_event_check CHECK (on_event IN ('Y','N'));

-- store_desc Table Create SQL
CREATE TABLE store_desc
(
    `store_desc_id`  BIGINT      NOT NULL    AUTO_INCREMENT COMMENT '상점 ID', 
    `store_id`       BIGINT      NOT NULL, 
    `desciption`     LONGTEXT    NOT NULL         COMMENT '상점 설명', 
    `created_at`     DATETIME    NOT NULL, 
    `modified_at`    DATETIME    NOT NULL, 
    PRIMARY KEY (store_desc_id),
    CONSTRAINT `store_desc_fk_1` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`) ON DELETE CASCADE
);



-- store_photo Table Create SQL
CREATE TABLE store_photo
(
    `store_photo_id`  BIGINT          NOT NULL    AUTO_INCREMENT COMMENT '상점 사진 ID', 
    `store_id`        BIGINT          NOT NULL         COMMENT '상점 ID', 
    `photo_url`       VARCHAR(100)    NOT NULL         COMMENT '상점 사진 URL', 
    `source`          VARCHAR(10)     NOT NULL         COMMENT '상점 사진 출처 (self, provide)',
    `upload_date`     DATETIME        NOT NULL,
    `created_at`      DATETIME        NOT NULL, 
    `modified_at`     DATETIME        NOT NULL, 
    PRIMARY KEY (store_photo_id),
    CONSTRAINT `store_photo_fk_1` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`) ON DELETE CASCADE
);
CREATE INDEX store_photo_idx_1 ON store_photo(`upload_date`);
ALTER TABLE store_photo ADD CONSTRAINT source_check CHECK (source IN ('self','provide'));

-- store_value Table Create SQL
CREATE TABLE store_value
(
    `store_value_id`     BIGINT      NOT NULL    AUTO_INCREMENT COMMENT '상점 가치 ID', 
    `store_id`           BIGINT      NOT NULL         COMMENT '상점 ID', 
    `value`              REAL         NOT NULL         COMMENT '상점 가치 점수', 
    `created_at`         DATETIME    NOT NULL, 
    `modified_at`        DATETIME    NOT NULL, 
    PRIMARY KEY (store_value_id),
    CONSTRAINT `store_value_fk_1_a` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`) ON DELETE CASCADE
);
CREATE INDEX store_value_a_idx_1 ON store_value_a(`value`);
ALTER TABLE store_value_a ADD CONSTRAINT store_value_a_uniq UNIQUE (store_id);


CREATE TABLE store_value_backup
(
    `store_value_id`     BIGINT      NOT NULL    AUTO_INCREMENT COMMENT '상점 가치 ID', 
    `store_id`           BIGINT      NOT NULL         COMMENT '상점 ID', 
    `value`              INT         NOT NULL         COMMENT '상점 가치 점수', 
    `value_by_customer`  INT         NOT NULL         COMMENT '고객 관점의 가치', 
    `value_by_store`     INT         NOT NULL         COMMENT '상점 관점의 가치', 
    `created_at`         DATETIME    NOT NULL, 
    `modified_at`        DATETIME    NOT NULL, 
    PRIMARY KEY (store_value_id),
    CONSTRAINT `store_value_fk_1_b` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`) ON DELETE CASCADE
);
CREATE INDEX store_value_b_idx_1 ON store_value_b(`value`);
ALTER TABLE store_value_b ADD CONSTRAINT store_value_b_uniq UNIQUE (store_id);


CREATE TABLE RESOURCE_LOCK (
	LOCK_NAME VARCHAR(36) NOT NULL PRIMARY KEY, 
	OWNER VARCHAR(100) NOT NULL, 
	LOCKED_AT DATETIME NOT NULL, 
	UNLOCK_AT DATETIME NOT NULL, 
	VERSION NUMBER(19,0)
);


-- store_value_policy Table Create SQL
CREATE TABLE store_value_policy
(
    `section_code`         VARCHAR(100)    NOT NULL     COMMENT '측정 항목 코드', 
    `section`              VARCHAR(100)    NOT NULL     COMMENT '측정 항목', 
    `ratio`                TINYINT         NOT NULL     COMMENT '반영 비율', 
    `section_type`         VARCHAR(1)      NOT NULL     COMMENT '측정항목 분류(R:Root,S:Sub)', 
    `root_section_code`    VARCHAR(100)    NOT NULL		COMMENT '소분류 항목의 대분류 항목 코드', 
    `created_at`           DATETIME        NOT NULL, 
    `modified_at`          DATETIME        NOT NULL, 
    PRIMARY KEY (section_code)
);
ALTER TABLE store_value_policy ADD CONSTRAINT section_type_check CHECK (section_type IN ('R','S'));