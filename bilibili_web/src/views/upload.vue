<template>
  <div class="upload-container">
    <h1>投稿中心</h1>
    <div class="upload-form">
      <div class="form-group">
        <label for="title">视频标题</label>
        <input type="text" id="title" v-model="videoForm.title" placeholder="请输入视频标题" required>
      </div>
      
      <div class="form-group">
        <label for="description">视频描述</label>
        <textarea id="description" v-model="videoForm.description" placeholder="请输入视频描述" rows="4"></textarea>
      </div>
      
      <div class="form-group">
        <label for="cover">封面URL</label>
        <input type="text" id="cover" v-model="videoForm.cover" placeholder="请输入封面图片URL">
      </div>
      
      <div class="form-group">
        <label for="url">视频URL</label>
        <input type="text" id="url" v-model="videoForm.url" placeholder="请输入视频地址" required>
      </div>
      
      <div class="form-group">
        <label for="duration">视频时长(秒)</label>
        <input type="number" id="duration" v-model.number="videoForm.duration" placeholder="请输入视频时长" required>
      </div>
      
      <div class="form-group">
        <label for="categoryId">分类ID</label>
        <input type="number" id="categoryId" v-model.number="videoForm.categoryId" placeholder="请输入分类ID" required>
      </div>
      
      <div class="form-group">
        <label for="tags">标签</label>
        <input type="text" id="tags" v-model="videoForm.tags" placeholder="请输入标签，用逗号分隔">
      </div>
      
      <div class="form-actions">
        <button class="submit-btn" @click="submitForm" :disabled="isSubmitting">
          {{ isSubmitting ? '提交中...' : '提交投稿' }}
        </button>
      </div>
      
      <div v-if="message" class="message" :class="messageType">
        {{ message }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';

// 视频表单数据
const videoForm = reactive({
  title: '',
  description: '',
  cover: '',
  url: '',
  duration: 0,
  categoryId: 103, // 默认游戏分类
  tags: ''
});

// 提交状态
const isSubmitting = ref(false);

// 消息提示
const message = ref('');
const messageType = ref('');

// 提交表单
const submitForm = async () => {
  // 表单验证
  if (!videoForm.title || !videoForm.url || !videoForm.duration || !videoForm.categoryId) {
    message.value = '请填写必填字段';
    messageType.value = 'error';
    return;
  }
  
  try {
    isSubmitting.value = true;
    message.value = '';
    
    // 获取当前登录用户ID（这里假设用户已登录，实际项目中应该从登录状态中获取）
    const userId = 10000; // 默认用户ID，实际应该从登录状态获取
    
    // 构建请求数据
    const videoData = {
      ...videoForm,
      userId,
      status: 1 // 直接设置为已发布状态
    };
    
    // 发送请求到后端API
    const response = await fetch('http://localhost:8080/api/video', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(videoData)
    });
    
    if (response.ok) {
      const result = await response.json();
      if (result.code === 200) {
        message.value = '视频上传成功！';
        messageType.value = 'success';
        
        // 重置表单
        Object.keys(videoForm).forEach(key => {
          if (key !== 'categoryId') {
            videoForm[key] = '';
          }
        });
        videoForm.duration = 0;
      } else {
        message.value = result.message || '上传失败';
        messageType.value = 'error';
      }
    } else {
      message.value = '网络错误，请稍后重试';
      messageType.value = 'error';
    }
  } catch (error) {
    console.error('上传错误:', error);
    message.value = '上传失败，请稍后重试';
    messageType.value = 'error';
  } finally {
    isSubmitting.value = false;
  }
};
</script>

<style scoped>
.upload-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
  margin-top: 80px;
}

h1 {
  font-size: 24px;
  color: #333;
  margin-bottom: 30px;
}

.upload-form {
  background-color: #f9f9f9;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  font-size: 14px;
  font-weight: bold;
  color: #333;
  margin-bottom: 8px;
}

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  box-sizing: border-box;
}

.form-group input:focus,
.form-group textarea:focus {
  outline: none;
  border-color: #00a1d6;
  box-shadow: 0 0 0 2px rgba(0, 161, 214, 0.2);
}

.form-actions {
  margin-top: 30px;
  text-align: center;
}

.submit-btn {
  background-color: #00a1d6;
  color: white;
  border: none;
  padding: 12px 30px;
  font-size: 16px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-btn:hover {
  background-color: #00b5e5;
}

.submit-btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.message {
  margin-top: 20px;
  padding: 10px;
  border-radius: 4px;
  text-align: center;
  font-size: 14px;
}

.message.success {
  background-color: #f0f9eb;
  color: #67c23a;
  border: 1px solid #e1f3d8;
}

.message.error {
  background-color: #fef0f0;
  color: #f56c6c;
  border: 1px solid #fbc4c4;
}

@media (max-width: 768px) {
  .upload-container {
    padding: 15px;
  }
  
  .upload-form {
    padding: 20px;
  }
}
</style>