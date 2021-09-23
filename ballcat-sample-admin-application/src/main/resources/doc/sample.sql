-- 文档表，用于演示数据权限
CREATE TABLE `sample_document` (
                                   `id` int(255) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                   `name` varchar(50) DEFAULT NULL COMMENT '文档名称',
                                   `user_id` int(11) DEFAULT NULL COMMENT '所属用户ID',
                                   `organization_id` int(11) DEFAULT NULL COMMENT '所属组织ID',
                                   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                   `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档表，用于演示数据权限';