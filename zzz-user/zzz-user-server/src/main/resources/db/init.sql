-- 创建权限表
DROP TABLE IF EXISTS `t_rbac_perm`;
CREATE TABLE `t_rbac_perm`
(
    `id`               int(11)      NOT NULL AUTO_INCREMENT COMMENT '主键，无业务含义',
    `gmt_create`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `perm_id`          bigint(20)   NOT NULL COMMENT '权限ID（业务编号）',
    `perm_name`        varchar(50)  NOT NULL COMMENT '权限名称',
    `perm_code`        varchar(50)  NOT NULL COMMENT '权限编号',
    `perm_description` varchar(255) NULL COMMENT '权限描述',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_p_pi` (`perm_id`) USING BTREE,
    UNIQUE INDEX `uk_p_pc` (`perm_code`) USING BTREE
) COMMENT '权限表';

-- 创建角色权限关联表
DROP TABLE IF EXISTS `t_rbac_role_perm`;
CREATE TABLE `t_rbac_role_perm`
(
    `id`           int(11)    NOT NULL AUTO_INCREMENT COMMENT '主键，无业务含义',
    `gmt_create`   timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `role_perm_id` bigint(20) NOT NULL COMMENT '角色权限关联ID（关联ID）',
    `role_id`      bigint(20) NOT NULL COMMENT '角色ID',
    `perm_id`      bigint(20) NOT NULL COMMENT '权限ID',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_rp_rpi` (`role_perm_id`) USING BTREE,
    UNIQUE INDEX `uk_rp_ri_pi` (`role_id`, `perm_id`) USING BTREE
) COMMENT '角色权限关联表';

-- 创建角色表
DROP TABLE IF EXISTS `t_rbac_role`;
CREATE TABLE `t_rbac_role`
(
    `id`               int(11)      NOT NULL AUTO_INCREMENT COMMENT '主键，无业务含义',
    `gmt_create`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `role_id`          bigint(20)   NOT NULL COMMENT '角色ID（业务编号）',
    `role_name`        varchar(50)  NOT NULL COMMENT '角色名称',
    `role_code`        varchar(50)  NOT NULL COMMENT '角色编号',
    `role_description` varchar(255) NULL COMMENT '角色描述',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_r_ri` (`role_id`) USING BTREE,
    UNIQUE INDEX `uk_r_rc` (`role_code`) USING BTREE
) COMMENT '角色表';

-- 创建用户角色关联表
DROP TABLE IF EXISTS `t_rbac_user_role`;
CREATE TABLE `t_rbac_user_role`
(
    `id`           int(11)    NOT NULL AUTO_INCREMENT COMMENT '主键，无业务含义',
    `gmt_create`   timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `user_role_id` bigint(20) NOT NULL COMMENT '角色权限关联ID（关联ID）',
    `user_id`      bigint(20) NOT NULL COMMENT '用户ID',
    `role_id`      bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_br_bri` (`user_role_id`) USING BTREE,
    UNIQUE INDEX `uk_br_bi_ri` (`user_id`, `role_id`) USING BTREE
) COMMENT '用户角色关联表';
