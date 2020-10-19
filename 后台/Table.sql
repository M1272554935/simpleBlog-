-- ----------------------------
--  Table structure for `blog_article`
-- ----------------------------
DROP TABLE IF EXISTS `blog_article`;
CREATE TABLE `blog_article` (
  `aid` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(200) DEFAULT '',
  `titlePic` VARCHAR(2000) DEFAULT '' COMMENT '标题图片',
  `info` VARCHAR(1000) DEFAULT '' COMMENT '简介',
  `content` TEXT COMMENT '内容文字',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `author` VARCHAR(20) DEFAULT '',
  `authorId` INT(10) DEFAULT '0',
  `state` INT(10) UNSIGNED DEFAULT '0' COMMENT '0为草稿，1为发布',
  `labelId` INT(10) DEFAULT '0' COMMENT '标签的id',
  `hits` INT(10) UNSIGNED DEFAULT '0',
  `commentsNum`INT(40) DEFAULT '0',
  PRIMARY KEY (`aid`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for 文章-标签中间表
-- ----------------------------
DROP TABLE IF EXISTS `article_label`;
CREATE TABLE `article_label` (
  `id` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `aid` INT(10) NOT NULL DEFAULT 0,
  `lid` INT(10) NOT NULL DEFAULT 0,
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
-- ----------------------------
--  Table structure for `blog_label`
-- ----------------------------
DROP TABLE IF EXISTS `blog_label`;
CREATE TABLE `blog_label` (
  `lid` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `lname` VARCHAR(100) NOT NULL DEFAULT '',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`lid`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `blog_user`
-- ----------------------------
DROP TABLE IF EXISTS `blog_user`;
CREATE TABLE `blog_user` (
  `uid` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(32) DEFAULT NULL,
  `password` VARCHAR(64) DEFAULT NULL,
  `email` VARCHAR(200) DEFAULT NULL,
  `role` VARCHAR(20) DEFAULT NULL,
  `screenName` VARCHAR(32) DEFAULT NULL COMMENT '昵称',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `name` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `blog_picture`
-- ----------------------------
DROP TABLE IF EXISTS `blog_picture`;
CREATE TABLE `blog_picture` (
  `pid` BIGINT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `uid` INT(11) DEFAULT '0',
  `pname` VARCHAR(100) NOT NULL DEFAULT '',
  `address` TEXT ,
  `createTime`  DATETIME NOT NULL COMMENT '创建时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`pid`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `blog_comment`
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment` (
  `cid` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `aid` INT(10) UNSIGNED DEFAULT '0',
  `content` TEXT,
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `author` VARCHAR(200) DEFAULT '',
  `authorId` INT(10) UNSIGNED DEFAULT '0',
  PRIMARY KEY (`cid`),
  KEY `cid` (`cid`),
) ENGINE=INNODB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



