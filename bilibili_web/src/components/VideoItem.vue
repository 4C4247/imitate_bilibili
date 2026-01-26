<template>
  <div class="video-item">
    <div class="video-thumbnail">
      <img :src="video.thumbnailUrl" :alt="video.title" class="thumbnail-img">
      <div class="video-duration" v-if="video.duration">{{ formatDuration(video.duration) }}</div>
    </div>
    <div class="video-info">
      <div class="video-title">{{ video.title }}</div>
      <div class="video-meta">
        <span class="video-views">{{ formatNumber(video.views) }} 次观看</span>
        <span class="video-date">{{ formatDate(video.publishDate) }}</span>
      </div>
      <div class="video-author">
        <div class="author-avatar" v-if="video.authorAvatar">
          <img :src="video.authorAvatar" :alt="video.authorName">
        </div>
        <span class="author-name">{{ video.authorName }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';

// Props
const props = defineProps({
  video: {
    type: Object,
    required: true
  }
});

// 格式化视频时长
const formatDuration = (duration) => {
  if (!duration) return '';
  const minutes = Math.floor(duration / 60);
  const seconds = duration % 60;
  return `${minutes}:${seconds.toString().padStart(2, '0')}`;
};

// 格式化数字（如观看次数）
const formatNumber = (num) => {
  if (!num) return '0';
  if (num >= 10000) {
    return (num / 10000).toFixed(1) + '万';
  }
  return num.toString();
};

// 格式化日期
const formatDate = (date) => {
  if (!date) return '';
  const now = new Date();
  const publishDate = new Date(date);
  const diffTime = now - publishDate;
  const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));
  
  if (diffDays === 0) {
    return '今天';
  } else if (diffDays === 1) {
    return '昨天';
  } else if (diffDays < 7) {
    return `${diffDays}天前`;
  } else if (diffDays < 30) {
    return `${Math.floor(diffDays / 7)}周前`;
  } else if (diffDays < 365) {
    return `${Math.floor(diffDays / 30)}个月前`;
  } else {
    return `${Math.floor(diffDays / 365)}年前`;
  }
};
</script>

<style scoped>
.video-item {
  width: 100%;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.video-item:hover {
  transform: translateY(-4px);
}

.video-thumbnail {
  position: relative;
  width: 100%;
  padding-top: 56.25%; /* 16:9 宽高比 */
  overflow: hidden;
  border-radius: 8px;
  margin-bottom: 10px;
}

.thumbnail-img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.video-item:hover .thumbnail-img {
  transform: scale(1.05);
}

.video-duration {
  position: absolute;
  bottom: 8px;
  right: 8px;
  background-color: rgba(0, 0, 0, 0.7);
  color: white;
  font-size: 12px;
  padding: 2px 6px;
  border-radius: 4px;
}

.video-info {
  padding: 0 4px;
}

.video-title {
  font-size: 14px;
  font-weight: 500;
  color: #333;
  line-height: 1.4;
  margin-bottom: 8px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

.video-meta {
  font-size: 12px;
  color: #999;
  display: flex;
  justify-content: space-between;
  margin-bottom: 6px;
}

.video-views {
  flex: 1;
}

.video-date {
  flex: 1;
  text-align: right;
}

.video-author {
  display: flex;
  align-items: center;
  font-size: 12px;
  color: #666;
}

.author-avatar {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  overflow: hidden;
  margin-right: 6px;
}

.author-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.author-name {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 120px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .video-title {
    font-size: 13px;
  }
  
  .video-meta,
  .video-author {
    font-size: 11px;
  }
}
</style>