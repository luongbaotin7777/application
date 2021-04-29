-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `USERS`;
CREATE TABLE `USERS`
(
    `ID`           char(36) CHARACTER SET utf8 COLLATE utf8_general_ci     NOT NULL,
    `USER_NAME`    varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `PASSWORD`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `EMAIL`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `CREATED_TIME` datetime(0)                                             NULL DEFAULT NULL,
    `UPDATED_TIME` datetime(0)                                             NULL DEFAULT NULL,
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `ROLES`;
CREATE TABLE `ROLES`
(
    `ID`           char(36) CHARACTER SET utf8 COLLATE utf8_general_ci     NOT NULL,
    `ROLE_NAME`    varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `DESCRIPTION`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `CREATED_TIME` datetime(0)                                             NULL DEFAULT NULL,
    `UPDATED_TIME` datetime(0)                                             NULL DEFAULT NULL,
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for users_roles
-- ----------------------------
DROP TABLE IF EXISTS `USERS_ROLES`;
CREATE TABLE `USERS_ROLES`
(
    `ID`      char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `USER_ID` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `ROLE_ID` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`ID`) USING BTREE,
    CONSTRAINT `FK_USERS_ROLES_USERS` FOREIGN KEY (`USER_ID`) REFERENCES `USERS` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `FK_USERS_ROLES_ROLES` FOREIGN KEY (`ROLE_ID`) REFERENCES `ROLES` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;