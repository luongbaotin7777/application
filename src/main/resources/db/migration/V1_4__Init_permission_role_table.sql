-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`
(
    `ID`               char(36) CHARACTER SET utf8 COLLATE utf8_general_ci     NOT NULL,
    `PERMISSION_NAME`  varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `DESCRIPTION`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `GROUP_PERMISSION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `CREATED_TIME`     datetime(0)                                             NULL DEFAULT NULL,
    `UPDATED_TIME`     datetime(0)                                             NULL DEFAULT NULL,
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permissions
-- ----------------------------
INSERT INTO `permissions`
VALUES ('016bda20-660e-41e2-8c25-8c8929cd46ce', 'Create Role', 'Create Role', 'groupSecurity', '2021-04-19 15:53:14',
        '2021-04-19 15:53:14');
INSERT INTO `permissions`
VALUES ('0edfd9d7-16d7-4ec1-89f9-5f92c142aca9', 'Delete Permission', 'Delete Permission', 'groupSecurity',
        '2021-04-19 15:56:11', '2021-04-19 15:56:11');
INSERT INTO `permissions`
VALUES ('15ae686e-2169-4872-865a-da21bd55257c', 'View User', 'View User', 'groupSecurity', '2021-04-19 15:52:10',
        '2021-04-19 15:52:10');
INSERT INTO `permissions`
VALUES ('194c9e7d-2fe6-43c4-864d-402f91473fb2', 'Update Role', 'Update Role', 'groupSecurity', '2021-04-19 15:53:22',
        '2021-04-19 15:53:22');
INSERT INTO `permissions`
VALUES ('1e57bf79-6c2d-4132-891f-8ee6d310efd9', 'View Permission', 'View Permission', 'groupSecurity',
        '2021-04-19 15:55:42', '2021-04-19 15:55:42');
INSERT INTO `permissions`
VALUES ('4d1ed2d2-ec84-441b-a879-197e08018d09', 'Delete User', 'Delete User', 'groupSecurity', '2021-04-19 15:52:51',
        '2021-04-19 15:52:51');
INSERT INTO `permissions`
VALUES ('57239be6-d550-4b9c-85c9-a34851c7f237', 'Update Permission', 'Update Permission', 'groupSecurity',
        '2021-04-19 15:56:02', '2021-04-19 15:56:02');
INSERT INTO `permissions`
VALUES ('957fc478-7d2f-4aac-ae98-1a7773a9c0fe', 'View Role', 'Vie Role', 'groupSecurity', '2021-04-19 15:53:06',
        '2021-04-19 15:53:06');
INSERT INTO `permissions`
VALUES ('967bde53-b598-47df-8ff0-4635ab11cb79', 'Create Permission', 'Create Permission', 'groupSecurity',
        '2021-04-19 15:55:53', '2021-04-19 15:55:53');
INSERT INTO `permissions`
VALUES ('a9d63d2c-1fbb-4404-9ad4-622fe5abb5a0', 'Delete Role', 'Delete Role', 'groupSecurity', '2021-04-19 15:55:23',
        '2021-04-19 15:55:23');
INSERT INTO `permissions`
VALUES ('cc48cce5-85fd-408e-8e17-5e22073f7958', 'Update User', 'Update User', 'groupSecurity', '2021-04-19 15:52:38',
        '2021-04-19 15:52:38');
INSERT INTO `permissions`
VALUES ('db1b42c2-a2b5-4bca-8a7b-744946498c6e', 'Create User', 'Create User', 'groupSecurity', '2021-04-19 15:52:31',
        '2021-04-19 15:52:31');

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for role_permissions
-- ----------------------------
DROP TABLE IF EXISTS `role_permissions`;
CREATE TABLE `role_permissions`
(
    `ID`            char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `ROLE_ID`       char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `PERMISSION_ID` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `CREATED_TIME`  datetime(0)                                         NULL DEFAULT NULL,
    `UPDATED_TIME`  datetime(0)                                         NULL DEFAULT NULL,
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `FK_ROLES_PERMISSIONS_PERMISSION` (`PERMISSION_ID`) USING BTREE,
    INDEX `FK_ROLES_PERMISSIONS_ROLES` (`ROLE_ID`) USING BTREE,
    CONSTRAINT `FK_ROLES_PERMISSIONS_PERMISSION` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `permissions` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `FK_ROLES_PERMISSIONS_ROLES` FOREIGN KEY (`ROLE_ID`) REFERENCES `roles` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_permissions
-- ----------------------------
INSERT INTO `role_permissions`
VALUES ('2b474a95-4666-4cc3-bbd6-59d57da45797', '450e56e6-305a-11ea-8602-0a0027000005',
        '0edfd9d7-16d7-4ec1-89f9-5f92c142aca9', '2021-04-19 16:38:00', '2021-04-19 16:38:00');
INSERT INTO `role_permissions`
VALUES ('3b565e15-b251-4ec9-8dce-a037a46cc7f0', '450e56e6-305a-11ea-8602-0a0027000005',
        '15ae686e-2169-4872-865a-da21bd55257c', '2021-04-19 16:37:54', '2021-04-19 16:37:54');
INSERT INTO `role_permissions`
VALUES ('409cc744-53df-41f0-94b8-2dfb7284b1c1', '450e56e6-305a-11ea-8602-0a0027000005',
        'a9d63d2c-1fbb-4404-9ad4-622fe5abb5a0', '2021-04-19 16:38:00', '2021-04-19 16:38:00');
INSERT INTO `role_permissions`
VALUES ('5ebf0f94-ac89-491b-a5c7-ffaea4074efc', '450e56e6-305a-11ea-8602-0a0027000005',
        'db1b42c2-a2b5-4bca-8a7b-744946498c6e', '2021-04-19 16:37:57', '2021-04-19 16:37:57');
INSERT INTO `role_permissions`
VALUES ('69495b21-f2bd-431a-b0e5-750cf34ede36', '450e56e6-305a-11ea-8602-0a0027000005',
        'cc48cce5-85fd-408e-8e17-5e22073f7958', '2021-04-19 16:38:00', '2021-04-19 16:38:00');
INSERT INTO `role_permissions`
VALUES ('9dad2ae5-57f4-44bc-901d-8fe4c61e7316', '450e56e6-305a-11ea-8602-0a0027000005',
        '1e57bf79-6c2d-4132-891f-8ee6d310efd9', '2021-04-19 16:38:00', '2021-04-19 16:38:00');
INSERT INTO `role_permissions`
VALUES ('a30f19d1-86f4-483f-a64d-be249397633f', '450e56e6-305a-11ea-8602-0a0027000005',
        '967bde53-b598-47df-8ff0-4635ab11cb79', '2021-04-19 16:38:00', '2021-04-19 16:38:00');
INSERT INTO `role_permissions`
VALUES ('b36314c2-8137-4033-bd76-fffaed6a58e9', '450e56e6-305a-11ea-8602-0a0027000005',
        '957fc478-7d2f-4aac-ae98-1a7773a9c0fe', '2021-04-19 16:38:00', '2021-04-19 16:38:00');
INSERT INTO `role_permissions`
VALUES ('c57971d2-9085-4022-a055-b93970297b6b', '450e56e6-305a-11ea-8602-0a0027000005',
        '016bda20-660e-41e2-8c25-8c8929cd46ce', '2021-04-19 16:38:00', '2021-04-19 16:38:00');
INSERT INTO `role_permissions`
VALUES ('d2ee465f-ad8c-4169-9b8c-18f12d9c628b', '450e56e6-305a-11ea-8602-0a0027000005',
        '4d1ed2d2-ec84-441b-a879-197e08018d09', '2021-04-19 16:38:00', '2021-04-19 16:38:00');
INSERT INTO `role_permissions`
VALUES ('e9f67a3f-fa61-4cbc-8a7b-99a947ba128a', '450e56e6-305a-11ea-8602-0a0027000005',
        '194c9e7d-2fe6-43c4-864d-402f91473fb2', '2021-04-19 16:38:00', '2021-04-19 16:38:00');
INSERT INTO `role_permissions`
VALUES ('f0130af0-a1c6-4d6e-9d4d-16b441d2582d', '450e56e6-305a-11ea-8602-0a0027000005',
        '57239be6-d550-4b9c-85c9-a34851c7f237', '2021-04-19 16:38:00', '2021-04-19 16:38:00');

SET FOREIGN_KEY_CHECKS = 1;
