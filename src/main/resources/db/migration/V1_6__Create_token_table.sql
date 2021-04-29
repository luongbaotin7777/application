DROP TABLE IF EXISTS `TOKENS`;
CREATE TABLE `TOKENS`
(
    `ID`              char(36) CHARACTER SET utf8 COLLATE utf8_general_ci     NOT NULL,
    `ACCESS_TOKEN`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `EXPIRED_ACCESS`  datetime(0)                                             NULL DEFAULT NULL,
    `REFRESH_TOKEN`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `EXPIRED_REFRESH` datetime(0)                                             NULL DEFAULT NULL,
    `REVOKED_REFRESH` datetime(0)                                             NULL DEFAULT NULL,
    `CREATED_TIME`    datetime(0)                                             NULL DEFAULT NULL,
    `UPDATED_TIME`    datetime(0)                                             NULL DEFAULT NULL,
    `CREATED_BY`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `UPDATED_BY`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = Dynamic;