-- 先在video表中插入测试数据
INSERT INTO video (user_id, title, description, cover, url, duration, view_count, like_count, comment_count, category_id, tags, status) VALUES
(10000, '2024年度游戏大作盘点', '带你了解2024年最值得期待的游戏大作', 'https://neeko-copilot.bytedance.net/api/text2image?prompt=game%20cover%202024&size=800x450', 'https://example.com/video1.mp4', 600, 10000, 5000, 1000, 103, '游戏,盘点,2024', 1),
(10000, '零基础学习Python编程', '适合初学者的Python编程入门教程', 'https://neeko-copilot.bytedance.net/api/text2image?prompt=python%20programming%20tutorial&size=800x450', 'https://example.com/video2.mp4', 900, 5000, 2000, 500, 104, '编程,Python,教程', 1),
(10000, '家常菜制作技巧', '简单易学的家常菜制作方法', 'https://neeko-copilot.bytedance.net/api/text2image?prompt=homemade%20food%20cooking&size=800x450', 'https://example.com/video3.mp4', 480, 8000, 3000, 800, 106, '美食,烹饪,家常菜', 1),
(10000, '2024最新手机评测', '市面上主流手机的详细评测对比', 'https://neeko-copilot.bytedance.net/api/text2image?prompt=smartphone%20review%202024&size=800x450', 'https://example.com/video4.mp4', 720, 12000, 6000, 1200, 104, '科技,手机,评测', 1),
(10000, '健身入门指南', '适合初学者的健身计划和技巧', 'https://neeko-copilot.bytedance.net/api/text2image?prompt=fitness%20beginner%20guide&size=800x450', 'https://example.com/video5.mp4', 540, 6000, 2500, 600, 117, '健身,运动,健康', 1),
(10000, '旅行vlog：日本东京', '东京旅行的精彩瞬间和攻略', 'https://neeko-copilot.bytedance.net/api/text2image?prompt=tokyo%20travel%20vlog&size=800x450', 'https://example.com/video6.mp4', 840, 9000, 4000, 900, 105, '旅行,vlog,日本', 1),
(10000, '动漫推荐：2024必看新番', '2024年最值得一看的动漫新番', 'https://neeko-copilot.bytedance.net/api/text2image?prompt=anime%20recommendation%202024&size=800x450', 'https://example.com/video7.mp4', 660, 15000, 7000, 1500, 100, '动漫,推荐,2024', 1),
(10000, '音乐制作教程：零基础入门', '如何从零开始学习音乐制作', 'https://neeko-copilot.bytedance.net/api/text2image?prompt=music%20production%20tutorial&size=800x450', 'https://example.com/video8.mp4', 1080, 4000, 1500, 400, 101, '音乐,制作,教程', 1),
(10000, '职场生存指南', '职场新人必知的生存技巧', 'https://neeko-copilot.bytedance.net/api/text2image?prompt=workplace%20survival%20guide&size=800x450', 'https://example.com/video9.mp4', 540, 7000, 3000, 700, 119, '职场,指南,新人', 1),
(10000, '电影解说：2024年度最佳影片', '2024年上映的优秀电影解说', 'https://neeko-copilot.bytedance.net/api/text2image?prompt=movie%20review%202024&size=800x450', 'https://example.com/video10.mp4', 780, 11000, 5500, 1100, 108, '电影,解说,2024', 1),
(10000, '投资理财入门', '个人理财和投资的基础知识', 'https://neeko-copilot.bytedance.net/api/text2image?prompt=investment%20finance%20beginner&size=800x450', 'https://example.com/video11.mp4', 840, 5000, 2000, 500, 113, '财经,投资,理财', 1),
(10000, '摄影技巧：手机拍出大片感', '如何用手机拍摄出专业级照片', 'https://neeko-copilot.bytedance.net/api/text2image?prompt=mobile%20photography%20tips&size=800x450', 'https://example.com/video12.mp4', 600, 8000, 3500, 800, 129, '摄影,手机,技巧', 1);

-- 创建推荐视频表
CREATE TABLE IF NOT EXISTS video_recommend (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '推荐视频ID',
    video_id BIGINT NOT NULL COMMENT '视频ID',
    recommend_type VARCHAR(50) NOT NULL COMMENT '推荐类型：hot, new, follow等',
    score DOUBLE NOT NULL COMMENT '推荐分数',
    rank INT NOT NULL COMMENT '推荐排名',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (video_id) REFERENCES video(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='推荐视频表';

-- 创建索引
CREATE INDEX idx_recommend_type ON video_recommend(recommend_type);
CREATE INDEX idx_score ON video_recommend(score);
CREATE INDEX idx_rank ON video_recommend(rank);

-- 插入测试数据（使用video表中实际存在的ID）
INSERT INTO video_recommend (video_id, recommend_type, score, rank) VALUES
(1000, 'hot', 99.5, 1),
(1001, 'hot', 98.2, 2),
(1002, 'hot', 97.8, 3),
(1003, 'hot', 96.5, 4),
(1004, 'hot', 95.9, 5),
(1005, 'hot', 94.7, 6),
(1006, 'new', 99.0, 1),
(1007, 'new', 98.5, 2),
(1008, 'new', 97.2, 3),
(1009, 'follow', 99.8, 1),
(1010, 'follow', 98.9, 2),
(1011, 'follow', 97.7, 3);