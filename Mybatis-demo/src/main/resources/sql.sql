-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) DEFAULT NULL,
  `userAge` int(11) DEFAULT NULL,
  `userAddress` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'summer', '30', 'shanghai');
INSERT INTO `user` VALUES ('2', 'test2', '22', 'suzhou');
INSERT INTO `user` VALUES ('3', 'test1', '29', 'some place');
INSERT INTO `user` VALUES ('4', 'lu', '28', 'some place');
INSERT INTO `user` VALUES ('5', 'xiaoxun', '27', 'nanjing');

-- ----------------------------
-- Table structure for `article`
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `content` text,
  `blogid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('1', '1', 'test_title_1', 'test_content_1', '1');
INSERT INTO `article` VALUES ('2', '1', 'test_title_2', 'test_content_2', '1');
INSERT INTO `article` VALUES ('3', '1', 'test_title_3', 'test_content_3', '2');
INSERT INTO `article` VALUES ('4', '1', 'test_title_4', 'test_content_4', '2');
INSERT INTO `article` VALUES ('5', '2', 'test_title_5', 'test_content_5', '2');

-- ----------------------------
-- Table structure for `blog`
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES ('1', 'xiaoxun_blog');
INSERT INTO `blog` VALUES ('2', 'zhang_blog');