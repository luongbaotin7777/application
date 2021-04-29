-- ----------------------------
-- Table structure for roles
-- ----------------------------
INSERT INTO roles
    (ID, ROLE_NAME, DESCRIPTION, CREATED_TIME, UPDATED_TIME)
VALUES ('450e56e6-305a-11ea-8602-0a0027000005', 'Admin', 'Company Admin', null, null)
ON DUPLICATE KEY UPDATE ID           = '450e56e6-305a-11ea-8602-0a0027000005',
                        ROLE_NAME    = 'Admin',
                        DESCRIPTION  = 'Company Admin',
                        CREATED_TIME = null,
                        UPDATED_TIME=null;

INSERT INTO roles
    (ID, ROLE_NAME, DESCRIPTION, CREATED_TIME, UPDATED_TIME)
VALUES ('4521dd0d-305a-11ea-8602-0a0027000005', 'Manager', 'Manager', null, null)
ON DUPLICATE KEY UPDATE ID           = '4521dd0d-305a-11ea-8602-0a0027000005',
                        ROLE_NAME    = 'Manager',
                        DESCRIPTION  = 'Manager',
                        CREATED_TIME = null,
                        UPDATED_TIME=null;


INSERT INTO roles
    (ID, ROLE_NAME, DESCRIPTION, CREATED_TIME, UPDATED_TIME)
VALUES ('4525b61c-305a-11ea-8602-0a0027000005', 'Team Lead', 'Team Lead', null, null)
ON DUPLICATE KEY UPDATE ID           = '4525b61c-305a-11ea-8602-0a0027000005',
                        ROLE_NAME    = 'Team Lead',
                        DESCRIPTION  = 'Team Lead',
                        CREATED_TIME = null,
                        UPDATED_TIME=null;

INSERT INTO roles
    (ID, ROLE_NAME, DESCRIPTION, CREATED_TIME, UPDATED_TIME)
VALUES ('452543cf-305a-11ea-8602-0a0027000005', 'User', 'User', null, null)
ON DUPLICATE KEY UPDATE ID           = '452543cf-305a-11ea-8602-0a0027000005',
                        ROLE_NAME    = 'User',
                        DESCRIPTION  = 'User',
                        CREATED_TIME = null,
                        UPDATED_TIME=null;

# USER
INSERT INTO users
    (ID, USER_NAME, PASSWORD, EMAIL, CREATED_TIME, UPDATED_TIME)
VALUES ('3b49f056-2b7e-11ea-8602-0a0027000005', 'admin', '$2a$10$GlM8TpyPUl6rhawbg07VCuPU1m3IVvsYF3jc/5QP9IcIE6CVfjNke',
        'admin@gmail.com', null, null)
ON DUPLICATE KEY UPDATE ID           = '3b49f056-2b7e-11ea-8602-0a0027000005',
                        USER_NAME    = 'admin',
                        PASSWORD     = '$2a$10$GlM8TpyPUl6rhawbg07VCuPU1m3IVvsYF3jc/5QP9IcIE6CVfjNke',
                        EMAIL        = 'admin@gmail.com',
                        CREATED_TIME = null,
                        UPDATED_TIME=null;

INSERT INTO users
    (ID, USER_NAME, PASSWORD, EMAIL, CREATED_TIME, UPDATED_TIME)
VALUES ('3feafcf1-2b7e-11ea-8602-0a0027000005', 'manager',
        '$2a$10$GlM8TpyPUl6rhawbg07VCuPU1m3IVvsYF3jc/5QP9IcIE6CVfjNke', 'manager@gmail.com', null, null)
ON DUPLICATE KEY UPDATE ID           = '3feafcf1-2b7e-11ea-8602-0a0027000005',
                        USER_NAME    = 'manager',
                        PASSWORD     = '$2a$10$GlM8TpyPUl6rhawbg07VCuPU1m3IVvsYF3jc/5QP9IcIE6CVfjNke',
                        EMAIL        = 'manager@gmail.com',
                        CREATED_TIME = null,
                        UPDATED_TIME=null;

INSERT INTO users
    (ID, USER_NAME, PASSWORD, EMAIL, CREATED_TIME, UPDATED_TIME)
VALUES ('d694796a-6dbb-11ea-af1d-7abe4c41632c', 'teamlead',
        '$2a$10$GlM8TpyPUl6rhawbg07VCuPU1m3IVvsYF3jc/5QP9IcIE6CVfjNke', 'teamlead@gmail.com', null, null)
ON DUPLICATE KEY UPDATE ID           = 'd694796a-6dbb-11ea-af1d-7abe4c41632c',
                        USER_NAME    = 'teamlead',
                        PASSWORD     = '$2a$10$GlM8TpyPUl6rhawbg07VCuPU1m3IVvsYF3jc/5QP9IcIE6CVfjNke',
                        EMAIL        = 'teamlead@gmail.com',
                        CREATED_TIME = null,
                        UPDATED_TIME=null;

#USER ROLE
INSERT INTO users_roles
    (ID, USER_ID, ROLE_ID)
VALUES ('2b0f8a06-817d-11eb-a4ff-a85e452ce8e0', '3b49f056-2b7e-11ea-8602-0a0027000005',
        '450e56e6-305a-11ea-8602-0a0027000005')
ON DUPLICATE KEY UPDATE ID      = '2b0f8a06-817d-11eb-a4ff-a85e452ce8e0',
                        USER_ID = '3b49f056-2b7e-11ea-8602-0a0027000005',
                        ROLE_ID = '450e56e6-305a-11ea-8602-0a0027000005';

INSERT INTO users_roles
    (ID, USER_ID, ROLE_ID)
VALUES ('c3e99880-2b7e-11ea-8602-0a0027000005', '3feafcf1-2b7e-11ea-8602-0a0027000005',
        '4521dd0d-305a-11ea-8602-0a0027000005')
ON DUPLICATE KEY UPDATE ID      = 'c3e99880-2b7e-11ea-8602-0a0027000005',
                        USER_ID = '3feafcf1-2b7e-11ea-8602-0a0027000005',
                        ROLE_ID = '4521dd0d-305a-11ea-8602-0a0027000005';

INSERT INTO users_roles
    (ID, USER_ID, ROLE_ID)
VALUES ('c72ebf40-2b7e-11ea-8602-0a0027000005', 'd694796a-6dbb-11ea-af1d-7abe4c41632c',
        '4525b61c-305a-11ea-8602-0a0027000005')
ON DUPLICATE KEY UPDATE ID      = 'c72ebf40-2b7e-11ea-8602-0a0027000005',
                        USER_ID = 'd694796a-6dbb-11ea-af1d-7abe4c41632c',
                        ROLE_ID = '4525b61c-305a-11ea-8602-0a0027000005';
