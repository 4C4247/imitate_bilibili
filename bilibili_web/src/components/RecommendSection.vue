<template>
  <div class="recommend-section">
    <div class="recommend-header">
      <h2 class="recommend-title">推荐视频</h2>
      <a href="/recommend" class="recommend-more">查看更多 <i class="iconfont icon-right"></i></a>
    </div>
    <div class="recommend-content">
      <!-- 左侧轮播图 -->
      <div class="carousel-container">
        <el-carousel :interval="5000" height="400px" trigger="click">
          <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
            <div class="carousel-item">
              <img :src="item.imageUrl" :alt="item.title" class="carousel-image">
              <div class="carousel-overlay">
                <h3 class="carousel-title">{{ item.title }}</h3>
                <p class="carousel-desc">{{ item.description }}</p>
              </div>
            </div>
          </el-carousel-item>
        </el-carousel>
      </div>
      
      <!-- 右侧推荐视频网格 -->
      <div class="video-grid">
        <div class="video-row">
          <div class="video-col" v-for="(video, index) in recommendVideos.slice(0, 3)" :key="index">
            <VideoItem :video="video" />
          </div>
        </div>
        <div class="video-row">
          <div class="video-col" v-for="(video, index) in recommendVideos.slice(3, 6)" :key="index">
            <VideoItem :video="video" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import VideoItem from './VideoItem.vue';

// 轮播图数据
const carouselItems = ref([
  {
    imageUrl: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=bilibili%20carousel%20image%20anime%20theme&image_size=landscape_16_9',
    title: '2024年夏季新番推荐',
    description: '这个夏天不可错过的精彩动画'
  },
  {
    imageUrl: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=bilibili%20carousel%20image%20game%20theme&image_size=landscape_16_9',
    title: '游戏区年度盛典',
    description: '众多游戏主播齐聚一堂'
  },
  {
    imageUrl: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=bilibili%20carousel%20image%20music%20theme&image_size=landscape_16_9',
    title: '音乐区新星崛起',
    description: '发现下一个音乐天才'
  }
]);

// 推荐视频数据
const recommendVideos = ref([]);

// 组件挂载时从API获取数据
onMounted(() => {
  // 从API获取推荐视频和轮播图数据
  fetchRecommendVideos();
  fetchCarouselItems();
});

// 从API获取推荐视频数据
const fetchRecommendVideos = async () => {
  try {
    const response = await fetch('http://localhost:8080/api/video/recommend/hot');
    if (response.ok) {
      const result = await response.json();
      if (result.code === 200) {
        // 转换推荐视频数据格式以匹配前端期望的结构
        recommendVideos.value = result.data.map(item => ({
          id: item.videoId,
          title: '视频标题', // 实际项目中应该从视频详情中获取
          thumbnailUrl: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=video%20thumbnail&image_size=landscape_16_9',
          duration: 600,
          views: 1000,
          publishDate: new Date().toISOString().split('T')[0],
          authorName: '用户',
          authorAvatar: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=user%20avatar&image_size=square'
        }));
      }
    }
  } catch (error) {
    console.error('Error fetching recommend videos:', error);
    // 如果API调用失败，使用默认数据
    recommendVideos.value = [
      {
        id: 1,
        title: '【教程】Vue 3 完全指南 - 从入门到精通',
        thumbnailUrl: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=vue%203%20tutorial%20thumbnail&image_size=landscape_16_9',
        duration: 600,
        views: 123456,
        publishDate: '2026-01-20',
        authorName: '前端小达人',
        authorAvatar: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=user%20avatar%20frontend%20developer&image_size=square'
      },
      {
        id: 2,
        title: '【游戏】2026年最值得期待的10款游戏',
        thumbnailUrl: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=upcoming%20games%202026%20thumbnail&image_size=landscape_16_9',
        duration: 480,
        views: 98765,
        publishDate: '2026-01-22',
        authorName: '游戏评测君',
        authorAvatar: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=user%20avatar%20game%20reviewer&image_size=square'
      },
      {
        id: 3,
        title: '【美食】在家就能做的米其林级别的料理',
        thumbnailUrl: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=michelin%20food%20recipe%20thumbnail&image_size=landscape_16_9',
        duration: 540,
        views: 76543,
        publishDate: '2026-01-18',
        authorName: '美食家小李',
        authorAvatar: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=user%20avatar%20chef&image_size=square'
      },
      {
        id: 4,
        title: '【科技】AI 如何改变我们的生活',
        thumbnailUrl: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=AI%20technology%20future%20thumbnail&image_size=landscape_16_9',
        duration: 720,
        views: 154321,
        publishDate: '2026-01-25',
        authorName: '科技前沿',
        authorAvatar: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=user%20avatar%20tech%20expert&image_size=square'
      },
      {
        id: 5,
        title: '【动画】2026年春季新番前瞻',
        thumbnailUrl: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=anime%20spring%202026%20preview%20thumbnail&image_size=landscape_16_9',
        duration: 420,
        views: 87654,
        publishDate: '2026-01-23',
        authorName: '动漫爱好者',
        authorAvatar: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=user%20avatar%20anime%20fan&image_size=square'
      },
      {
        id: 6,
        title: '【旅行】性价比最高的国内旅行目的地',
        thumbnailUrl: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=domestic%20travel%20destinations%20thumbnail&image_size=landscape_16_9',
        duration: 660,
        views: 65432,
        publishDate: '2026-01-21',
        authorName: '旅行博主小王',
        authorAvatar: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=user%20avatar%20travel%20blogger&image_size=square'
      }
    ];
  }
};

// 从API获取轮播图数据
const fetchCarouselItems = async () => {
  try {
    const response = await fetch('http://localhost:8080/api/carousel');
    if (response.ok) {
      const result = await response.json();
      if (result.code === 200) {
        // 转换轮播图数据格式以匹配前端期望的结构
        carouselItems.value = result.data.map(item => ({
          imageUrl: item.image,
          title: item.title,
          description: '' // 如果carousel表中有description字段，可以使用item.description
        }));
      }
    }
  } catch (error) {
    console.error('Error fetching carousel items:', error);
  }
};
</script>

<style scoped>
.recommend-section {
  padding: 20px;
  margin-bottom: 20px;
}

.recommend-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #f0f0f0;
}

.recommend-title {
  font-size: 20px;
  font-weight: bold;
  color: #333;
  margin: 0;
}

.recommend-more {
  font-size: 14px;
  color: #00a1d6;
  text-decoration: none;
  display: flex;
  align-items: center;
  transition: color 0.3s ease;
}

.recommend-more:hover {
  color: #00b5e5;
}

.recommend-content {
  display: flex;
  gap: 20px;
}

/* 左侧轮播图 */
.carousel-container {
  flex: 0 0 700px;
  border-radius: 8px;
  overflow: hidden;
}

.carousel-item {
  position: relative;
  width: 100%;
  height: 100%;
}

.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.carousel-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(transparent, rgba(0, 0, 0, 0.8));
  color: white;
  padding: 30px;
}

.carousel-title {
  font-size: 24px;
  font-weight: bold;
  margin: 0 0 12px 0;
}

.carousel-desc {
  font-size: 16px;
  margin: 0;
  opacity: 0.9;
}

/* 轮播图指示器样式 */
:deep(.el-carousel__indicators) {
  bottom: 20px;
}

:deep(.el-carousel__button) {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.5);
  margin: 0 8px;
  transition: all 0.3s ease;
}

:deep(.el-carousel__button:hover) {
  background-color: rgba(255, 255, 255, 0.8);
}

:deep(.el-carousel__button.is-active) {
  background-color: white;
  width: 30px;
  border-radius: 6px;
}

/* 右侧推荐视频网格 */
.video-grid {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.video-row {
  display: flex;
  gap: 12px;
}

.video-col {
  flex: 1;
  min-width: 0;
}

/* 减小视频项的大小 */
.video-col .video-item {
  transform: scale(0.95);
  transform-origin: top left;
  transition: transform 0.3s ease;
}

.video-col .video-item:hover {
  transform: scale(1);
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .recommend-content {
    flex-direction: column;
  }
  
  .carousel-container {
    flex: none;
    width: 100%;
    max-width: 100%;
  }
  
  .video-row {
    gap: 12px;
  }
}

@media (max-width: 768px) {
  .recommend-section {
    padding: 15px;
  }
  
  .recommend-title {
    font-size: 18px;
  }
  
  .carousel-container {
    height: 200px;
  }
  
  .carousel-title {
    font-size: 16px;
  }
  
  .carousel-desc {
    font-size: 12px;
  }
  
  .video-row {
    gap: 10px;
  }
}

@media (max-width: 480px) {
  .video-row {
    flex-direction: column;
  }
  
  .video-col {
    width: 100%;
  }
}
</style>