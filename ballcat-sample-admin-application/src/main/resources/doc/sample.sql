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


-- 菜单
INSERT INTO `sys_menu` (`id`, `parent_id`, `title`, `icon`, `permission`, `path`, `target_type`, `uri`, `sort`, `keep_alive`, `hidden`, `type`, `remarks`, `deleted`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (980000, 0, '功能演示', 'gift', NULL, 'feature', 1, '', 98, 0, 0, 0, NULL, 0, 1, NULL, '2021-12-03 14:09:56', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `title`, `icon`, `permission`, `path`, `target_type`, `uri`, `sort`, `keep_alive`, `hidden`, `type`, `remarks`, `deleted`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (970100, 910000, '用户文档', 'file-text', NULL, 'document', 1, 'sample/document/DocumentPage', 1, 1, 0, 1, NULL, 0, 1, NULL, '2021-12-03 14:14:04', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `title`, `icon`, `permission`, `path`, `target_type`, `uri`, `sort`, `keep_alive`, `hidden`, `type`, `remarks`, `deleted`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (910000, 980000, '数据权限', 'hdd', NULL, 'data-scope', 1, '', 1, 0, 0, 0, NULL, 0, 1, NULL, '2021-12-03 14:10:48', NULL);

-- 菜单对应国际化
INSERT INTO `i18n_data` (`language_tag`, `code`, `message`, `remarks`, `deleted`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES ('en-US', '用户文档', 'Document', NULL, 0, 1, NULL, '2021-12-03 14:14:04', NULL);
INSERT INTO `i18n_data` (`language_tag`, `code`, `message`, `remarks`, `deleted`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES ('zh-CN', '用户文档', '用户文档', NULL, 0, 1, NULL, '2021-12-03 14:14:04', NULL);
INSERT INTO `i18n_data` (`language_tag`, `code`, `message`, `remarks`, `deleted`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES ('en-US', '数据权限', 'Data Scope', NULL, 0, 1, NULL, '2021-12-03 14:10:48', NULL);
INSERT INTO `i18n_data` (`language_tag`, `code`, `message`, `remarks`, `deleted`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES ('zh-CN', '数据权限', '数据权限', NULL, 0, 1, NULL, '2021-12-03 14:10:48', NULL);
INSERT INTO `i18n_data` (`language_tag`, `code`, `message`, `remarks`, `deleted`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES ('en-US', '功能演示', 'Feature', NULL, 0, 1, NULL, '2021-12-03 14:09:56', NULL);
INSERT INTO `i18n_data` (`language_tag`, `code`, `message`, `remarks`, `deleted`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES ('zh-CN', '功能演示', '功能演示', NULL, 0, 1, NULL, '2021-12-03 14:09:56', NULL);


