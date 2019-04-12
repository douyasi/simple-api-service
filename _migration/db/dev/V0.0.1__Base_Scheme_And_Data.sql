SET NAMES utf8mb4;

-- ----------------------------
--  Table structure for `tm_pages`
-- ----------------------------
DROP TABLE IF EXISTS `tm_pages`;
CREATE TABLE `tm_pages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT 'user_id',
  `content` text COMMENT 'content',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Records of `tm_pages`
-- ----------------------------
INSERT INTO `tm_pages` VALUES ('1', '1', '# Hello world\n\nThis is a demo page.', '2017-11-09 13:54:39', '2017-11-09 13:54:39'), ('2', '1', 'Tinyme - Simple restful api demo', '2017-11-09 13:58:39', '2017-11-09 13:58:39');

-- ----------------------------
--  Table structure for `tm_tokens`
-- ----------------------------
DROP TABLE IF EXISTS `tm_tokens`;
CREATE TABLE `tm_tokens` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `uid` int(11) DEFAULT NULL COMMENT 'user_id',
  `token` varchar(100) DEFAULT NULL COMMENT 'token',
  `expired_at` int(11) DEFAULT NULL COMMENT 'at what timestamp expired',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `token` (`token`) USING BTREE,
  KEY `uid_token` (`uid`,`token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Records of `tm_tokens`
-- ----------------------------
BEGIN;
INSERT INTO `tm_tokens` VALUES ('1', '1', 'crQHrvSBNmv84fTW3i7ZaYVIihIUnGtSx8VDdaL87', '1510141596', '2017-11-08 18:46:36', '2017-11-08 18:46:36'), ('2', '1', 'z95BW8363MiykWv10w21iKnVc7IHUGAf1sEygBV3P', '1510141614', '2017-11-08 18:46:54', '2017-11-08 18:46:54'), ('3', '1', 'DPsQn2hudiMRfFkE8GCwaSWTRBCsy3qr6Vq0KjIpi', '1510211333', '2017-11-09 14:08:53', '2017-11-09 14:08:53'), ('4', '1', 'ifpGcwagCU63qACMhyx18ItMyKDvsAScfCu1DTIy9', '1510215367', '2017-11-09 15:16:07', '2017-11-09 15:16:07');
COMMIT;

-- ----------------------------
--  Table structure for `tm_users`
-- ----------------------------
DROP TABLE IF EXISTS `tm_users`;
CREATE TABLE `tm_users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `email` varchar(64) NOT NULL DEFAULT '' COMMENT 'email',
  `password` char(32) NOT NULL DEFAULT '' COMMENT 'password',
  `nickname` varchar(128) NOT NULL DEFAULT '' COMMENT 'nickname',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Records of `tm_users`
-- ----------------------------
BEGIN;
INSERT INTO `tm_users` VALUES ('1', 'foo@example.com', '8f94604c843418f04c61839df661bbfe', 'foo_user', '2017-11-07 21:08:31', '2017-11-07 21:08:31'), ('2', 'bar@example.com', '8f94604c843418f04c61839df661bbfe', 'bar_user', '2017-11-07 21:08:31', '2017-11-07 21:08:31');
COMMIT;