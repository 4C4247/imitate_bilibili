<template>
  <div class="home-page">
    <!-- Header部分 -->
    <Header />
    
    <!-- Body部分 -->
    <div class="body-container">
      <!-- 分类区域 -->
      <Category />
      
      <!-- 主要内容区域 -->
      <div class="main-content">
        <!-- 推荐视频板块 -->
        <RecommendSection />
      </div>
    </div>
    
    <!-- Footer部分 -->
    <div class="footer">
      <!-- 这里将添加页脚内容 -->
    </div>
  </div>
</template>

<script setup>
import Header from '../components/Header.vue';
import Category from '../components/Category.vue';
import RecommendSection from '../components/RecommendSection.vue';
</script>

<style scoped>
.home-page {
  width: 100%;
  min-height: 100vh;
  background-color: #fff;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.body-container {
  width: 100%;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.main-content {
  width: 100%;
  padding: 20px;
  min-height: 600px;
  box-sizing: border-box;
}

.footer {
  width: 100%;
  height: 200px;
  background-color: #333;
  margin-top: 40px;
  padding: 0;
  box-sizing: border-box;
}
</style>