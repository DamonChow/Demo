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