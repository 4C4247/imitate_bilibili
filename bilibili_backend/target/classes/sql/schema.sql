-- 创建数据库
CREATE DATABASE IF NOT EXISTS bilibili DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE bilibili;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` VARCHAR(50) NOT NULL COMMENT '账号',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `email` VARCHAR(100) NOT NULL COMMENT '邮箱',
  `nickname` VARCHAR(50) NOT NULL COMMENT '昵称',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像',
  `vip_status` TINYINT(1) DEFAULT 0 COMMENT '会员状态：0-非会员，1-会员',
  `vip_expire_time` DATETIME DEFAULT NULL COMMENT '会员过期时间',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 暂时禁用外键约束
SET FOREIGN_KEY_CHECKS = 0;

-- 删除分类表（如果存在）
DROP TABLE IF EXISTS `category`;

-- 重新启用外键约束
SET FOREIGN_KEY_CHECKS = 1;

-- 分类表（包含主分类和子分类）
CREATE TABLE `category` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
  `icon` VARCHAR(255) DEFAULT NULL COMMENT '分类图标',
  `parent_id` BIGINT(20) DEFAULT NULL COMMENT '父分类id，null表示主分类',
  `type` TINYINT(1) DEFAULT 0 COMMENT '分类类型：0-主分类，1-子分类',
  `order` INT(11) DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`),
  CONSTRAINT `fk_category_parent` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='分类表';

-- 视频表
CREATE TABLE IF NOT EXISTS `video` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户id',
  `title` VARCHAR(100) NOT NULL COMMENT '标题',
  `description` TEXT DEFAULT NULL COMMENT '描述',
  `cover` VARCHAR(255) DEFAULT NULL COMMENT '封面',
  `url` VARCHAR(255) NOT NULL COMMENT '视频地址',
  `duration` INT(11) DEFAULT 0 COMMENT '时长(秒)',
  `view_count` BIGINT(20) DEFAULT 0 COMMENT '观看次数',
  `like_count` BIGINT(20) DEFAULT 0 COMMENT '点赞次数',
  `comment_count` BIGINT(20) DEFAULT 0 COMMENT '评论次数',
  `category_id` BIGINT(20) DEFAULT NULL COMMENT '分类id',
  `tags` VARCHAR(255) DEFAULT NULL COMMENT '标签',
  `status` TINYINT(1) DEFAULT 0 COMMENT '状态：0-审核中，1-已发布，2-已下架',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_category_id` (`category_id`),
  CONSTRAINT `fk_video_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_video_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COMMENT='视频表';

-- 评论表
CREATE TABLE IF NOT EXISTS `comment` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户id',
  `video_id` BIGINT(20) NOT NULL COMMENT '视频id',
  `content` TEXT NOT NULL COMMENT '内容',
  `parent_id` BIGINT(20) DEFAULT NULL COMMENT '父评论id',
  `like_count` BIGINT(20) DEFAULT 0 COMMENT '点赞次数',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_video_id` (`video_id`),
  KEY `idx_parent_id` (`parent_id`),
  CONSTRAINT `fk_comment_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_comment_video` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_comment_parent` FOREIGN KEY (`parent_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COMMENT='评论表';

-- 收藏表
CREATE TABLE IF NOT EXISTS `collection` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户id',
  `video_id` BIGINT(20) NOT NULL COMMENT '视频id',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_video` (`user_id`, `video_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_video_id` (`video_id`),
  CONSTRAINT `fk_collection_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_collection_video` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COMMENT='收藏表';

-- 动态表
CREATE TABLE IF NOT EXISTS `activity` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户id',
  `type` TINYINT(1) NOT NULL COMMENT '动态类型：0-系统通知，1-视频更新，2-评论回复',
  `content` TEXT NOT NULL COMMENT '内容',
  `related_id` BIGINT(20) DEFAULT NULL COMMENT '关联id',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  CONSTRAINT `fk_activity_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COMMENT='动态表';

-- 消息表
CREATE TABLE IF NOT EXISTS `message` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sender_id` BIGINT(20) DEFAULT NULL COMMENT '发送者id',
  `receiver_id` BIGINT(20) NOT NULL COMMENT '接收者id',
  `content` TEXT NOT NULL COMMENT '内容',
  `status` TINYINT(1) DEFAULT 0 COMMENT '状态：0-未读，1-已读',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_sender_id` (`sender_id`),
  KEY `idx_receiver_id` (`receiver_id`),
  CONSTRAINT `fk_message_sender` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_message_receiver` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COMMENT='消息表';

-- 轮播图表
CREATE TABLE IF NOT EXISTS `carousel` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` VARCHAR(100) NOT NULL COMMENT '标题',
  `image` VARCHAR(255) NOT NULL COMMENT '图片',
  `url` VARCHAR(255) NOT NULL COMMENT '链接',
  `order` INT(11) DEFAULT 0 COMMENT '排序',
  `status` TINYINT(1) DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='轮播图表';

-- 插入初始分类数据（主分类）
INSERT INTO `category` (`name`, `icon`, `parent_id`, `type`, `order`) VALUES
('动画', 'animation.png', NULL, 0, 1),
('音乐', 'music.png', NULL, 0, 2),
('舞蹈', 'dance.png', NULL, 0, 3),
('游戏', 'game.png', NULL, 0, 4),
('科技', 'tech.png', NULL, 0, 5),
('生活', 'life.png', NULL, 0, 6),
('美食', 'food.png', NULL, 0, 7),
('搞笑', 'funny.png', NULL, 0, 8),
('影视', 'film.png', NULL, 0, 9),
('娱乐', 'entertainment.png', NULL, 0, 10),
('体育', 'sports.png', NULL, 0, 11),
('汽车', 'car.png', NULL, 0, 12),
('教育', 'education.png', NULL, 0, 13),
('财经', 'finance.png', NULL, 0, 14),
('时尚', 'fashion.png', NULL, 0, 15),
('军事', 'military.png', NULL, 0, 16),
('纪录片', 'documentary.png', NULL, 0, 17),
('健康', 'health.png', NULL, 0, 18),
('情感', 'emotion.png', NULL, 0, 19),
('职场', 'career.png', NULL, 0, 20),
('艺术', 'art.png', NULL, 0, 21),
('二次元', 'acg.png', NULL, 0, 22) ON DUPLICATE KEY UPDATE name = name;

-- 插入子分类数据
INSERT INTO `category` (`name`, `parent_id`, `type`, `order`) VALUES
-- 动画子分类
('番剧', 100, 1, 1),
('国创', 100, 1, 2),
('动画电影', 100, 1, 3),
('动画短片', 100, 1, 4),
-- 音乐子分类
('流行音乐', 101, 1, 1),
('摇滚', 101, 1, 2),
('民谣', 101, 1, 3),
('电子音乐', 101, 1, 4),
-- 舞蹈子分类
('街舞', 102, 1, 1),
('爵士舞', 102, 1, 2),
('古典舞', 102, 1, 3),
('宅舞', 102, 1, 4),
-- 游戏子分类
('英雄联盟', 103, 1, 1),
('王者荣耀', 103, 1, 2),
('原神', 103, 1, 3),
('和平精英', 103, 1, 4),
-- 科技子分类
('数码', 104, 1, 1),
('计算机', 104, 1, 2),
('互联网', 104, 1, 3),
('AI', 104, 1, 4),
-- 生活子分类
('旅行', 105, 1, 1),
('家居', 105, 1, 2),
('宠物', 105, 1, 3),
('手工', 105, 1, 4),
-- 美食子分类
('中餐', 106, 1, 1),
('西餐', 106, 1, 2),
('甜点', 106, 1, 3),
('饮品', 106, 1, 4),
-- 搞笑子分类
('段子', 107, 1, 1),
('吐槽', 107, 1, 2),
('表情包', 107, 1, 3),
('恶搞', 107, 1, 4),
-- 影视子分类
('电影', 108, 1, 1),
('电视剧', 108, 1, 2),
('综艺', 108, 1, 3),
('动漫', 108, 1, 4),
-- 娱乐子分类
('明星', 109, 1, 1),
('八卦', 109, 1, 2),
('演唱会', 109, 1, 3),
('红毯', 109, 1, 4),
-- 体育子分类
('篮球', 110, 1, 1),
('足球', 110, 1, 2),
('乒乓球', 110, 1, 3),
('羽毛球', 110, 1, 4),
-- 汽车子分类
('轿车', 111, 1, 1),
('SUV', 111, 1, 2),
('新能源', 111, 1, 3),
('跑车', 111, 1, 4),
-- 教育子分类
('中小学', 112, 1, 1),
('大学', 112, 1, 2),
('考研', 112, 1, 3),
('考证', 112, 1, 4),
-- 财经子分类
('股票', 113, 1, 1),
('基金', 113, 1, 2),
('理财', 113, 1, 3),
('保险', 113, 1, 4),
-- 时尚子分类
('美妆', 114, 1, 1),
('穿搭', 114, 1, 2),
('护肤', 114, 1, 3),
('发型', 114, 1, 4),
-- 军事子分类
('武器', 115, 1, 1),
('历史', 115, 1, 2),
('国际', 115, 1, 3),
('战术', 115, 1, 4),
-- 纪录片子分类
('自然', 116, 1, 1),
('历史', 116, 1, 2),
('人文', 116, 1, 3),
('科技', 116, 1, 4),
-- 健康子分类
('健身', 117, 1, 1),
('养生', 117, 1, 2),
('医疗', 117, 1, 3),
('心理', 117, 1, 4),
-- 情感子分类
('爱情', 118, 1, 1),
('友情', 118, 1, 2),
('亲情', 118, 1, 3),
('婚姻', 118, 1, 4),
-- 职场子分类
('求职', 119, 1, 1),
('晋升', 119, 1, 2),
('创业', 119, 1, 3),
('人际关系', 119, 1, 4),
-- 艺术子分类
('绘画', 120, 1, 1),
('书法', 120, 1, 2),
('雕塑', 120, 1, 3),
('设计', 120, 1, 4),
-- 二次元子分类
('动漫', 121, 1, 1),
('漫画', 121, 1, 2),
('游戏', 121, 1, 3),
('周边', 121, 1, 4) ON DUPLICATE KEY UPDATE name = name;

-- 添加10个新的主分类
INSERT INTO `category` (`name`, `icon`, `parent_id`, `type`, `order`) VALUES
('亲子', 'parenting.png', NULL, 0, 23),
('三农', 'agriculture.png', NULL, 0, 24),
('国际', 'international.png', NULL, 0, 25),
('校园', 'campus.png', NULL, 0, 26),
('公益', 'charity.png', NULL, 0, 27),
('星座', 'constellation.png', NULL, 0, 28),
('命理', 'fortune.png', NULL, 0, 29),
('摄影', 'photography.png', NULL, 0, 30),
('vlog', 'vlog.png', NULL, 0, 31),
('其他', 'other.png', NULL, 0, 32) ON DUPLICATE KEY UPDATE name = name;

-- 添加20个新的子分类
INSERT INTO `category` (`name`, `parent_id`, `type`, `order`) VALUES
-- 亲子子分类
('早教', 122, 1, 1),
('玩具', 122, 1, 2),
-- 三农子分类
('农村', 123, 1, 1),
('农业', 123, 1, 2),
-- 国际子分类
('新闻', 124, 1, 1),
('文化', 124, 1, 2),
-- 校园子分类
('大学', 125, 1, 1),
('中学', 125, 1, 2),
-- 公益子分类
('环保', 126, 1, 1),
('助学', 126, 1, 2),
-- 星座子分类
('白羊座', 127, 1, 1),
('金牛座', 127, 1, 2),
-- 命理子分类
('风水', 128, 1, 1),
('面相', 128, 1, 2),
-- 摄影子分类
('人像', 129, 1, 1),
('风景', 129, 1, 2),
-- vlog子分类
('日常', 130, 1, 1),
('旅行', 130, 1, 2),
-- 其他子分类
('杂谈', 131, 1, 1),
('未知', 131, 1, 2) ON DUPLICATE KEY UPDATE name = name;
