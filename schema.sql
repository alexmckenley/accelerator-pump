#
# accelerator-pump MySQL Schema
# Encoding: Unicode (UTF-8)
#

CREATE TABLE IF NOT EXISTS `projects` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `namespace` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`namespace`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;