<template>
  <div class="header-container">
    <!-- 头部背景 -->
    <div class="header-background">
      <!-- 背景图像 -->
    </div>
    
    <!-- 固定头部栏 -->
    <div class="header-bar" :class="{ 'transparent-bar': scrollTop < 30 }">
      <div class="header-content">
        <!-- 左侧导航 -->
        <div class="header-left">
          <router-link to="/" class="logo">
            <i class="iconfont icon-logo logo-icon"></i>
          </router-link>
          <router-link to="/" class="nav-item active" :class="{ 'white-text': scrollTop < 30 }">首页</router-link>
        </div>
        
        <!-- 中间搜索框 -->
        <div class="header-center">
          <div class="search-box">
            <input type="text" placeholder="搜索你感兴趣的内容" class="search-input" :class="{ 'transparent-input': scrollTop < 30 }">
            <button class="search-btn" :class="{ 'transparent-btn': scrollTop < 30 }">
              <i class="iconfont icon-search"></i>
            </button>
          </div>
        </div>
        
        <!-- 右侧用户区 -->
        <div class="header-right">
          <!-- 用户头像 -->
          <div class="user-avatar-container" @mouseenter="handleMouseEnter" @mouseleave="handleMouseLeave">
            <div class="avatar-wrapper" @click="handleAvatarClick">
              <Avatar :src="userAvatar" :size="32" />
            </div>
            <!-- 登录提示框 -->
            <div class="login-modal" v-if="!isLoggedIn && showUserMenu" @mouseenter="handleMouseEnter" @mouseleave="handleMouseLeave">
              <div class="login-modal-content">
                <h3>登录后你可以：</h3>
                <div class="login-features">
                  <div class="login-feature">
                    <i class="iconfont icon-video"></i>
                    <span>免费看高清视频</span>
                  </div>
                  <div class="login-feature">
                    <i class="iconfont icon-history"></i>
                    <span>多端同步播放记录</span>
                  </div>
                  <div class="login-feature">
                    <i class="iconfont icon-comment"></i>
                    <span>发表弹幕/评论</span>
                  </div>
                  <div class="login-feature">
                    <i class="iconfont icon-play2"></i>
                    <span>热门番剧影视看不停</span>
                  </div>
                </div>
                <button class="login-btn" @click="openLoginDialog">立即登录</button>
                <div class="register-link">
                  首次使用？<a href="#" @click.prevent="openRegisterDialog">点我注册</a>
                </div>
              </div>
            </div>
            <!-- 用户菜单 -->
            <div class="user-menu" v-if="isLoggedIn && showUserMenu" @mouseenter="handleMouseEnter" @mouseleave="handleMouseLeave">
              <div class="user-menu-header">
                <div class="user-info">
                  <Avatar :src="userAvatar" :size="48" />
                  <div class="user-details">
                    <div class="username">{{ userInfo.nickname }}</div>
                    <div class="vip-status" v-if="userInfo.vipStatus">
                      <span class="vip-tag">LIVE</span>
                    </div>
                  </div>
                </div>
                <div class="user-stats">
                  <div class="stat-item">
                    <div class="stat-value">{{ userInfo.following || 0 }}</div>
                    <div class="stat-label">关注</div>
                  </div>
                  <div class="stat-item">
                    <div class="stat-value">{{ userInfo.followers || 0 }}</div>
                    <div class="stat-label">粉丝</div>
                  </div>
                  <div class="stat-item">
                    <div class="stat-value">{{ userInfo.activities || 0 }}</div>
                    <div class="stat-label">动态</div>
                  </div>
                </div>
                <div class="vip-expire" v-if="userInfo.vipStatus && userInfo.vipExpireTime">
                  <span>您的大会员已过期</span>
                  <a href="/vip" class="vip-center">会员中心</a>
                </div>
              </div>
              <div class="user-menu-body">
                <a href="/profile" class="menu-item">
                  <i class="iconfont icon-user"></i>
                  <span>个人中心</span>
                </a>
                <a href="/upload" class="menu-item">
                  <i class="iconfont icon-upload"></i>
                  <span>投稿管理</span>
                </a>
                <a href="/services" class="menu-item">
                  <i class="iconfont icon-service"></i>
                  <span>推荐服务</span>
                </a>
              </div>
              <div class="user-menu-footer">
                <a href="#" @click.prevent="logout" class="menu-item logout">
                  <i class="iconfont icon-logout"></i>
                  <span>退出登录</span>
                </a>
              </div>
            </div>
          </div>
          
          <!-- 会员 -->
          <router-link to="/vip" class="user-item vip" :class="{ 'white-text': scrollTop < 30 }">
            <span>大会员</span>
          </router-link>
          
          <!-- 消息 -->
          <router-link to="/message" class="user-item message" :class="{ 'white-text': scrollTop < 30 }">
            <i class="iconfont icon-message"></i>
            <span>消息</span>
          </router-link>
          
          <!-- 动态 -->
          <router-link to="/activity" class="user-item" :class="{ 'white-text': scrollTop < 30 }">
            <i class="iconfont icon-sys-message"></i>
            <span>动态</span>
          </router-link>
          
          <!-- 收藏 -->
          <router-link to="/collection" class="user-item" :class="{ 'white-text': scrollTop < 30 }">
            <i class="iconfont icon-collect"></i>
            <span>收藏</span>
          </router-link>
          
          <!-- 投稿按钮 -->
          <router-link to="/upload" class="submit-btn">
            <i class="iconfont icon-upload"></i>
            <span>投稿</span>
          </router-link>
        </div>
      </div>
    </div>
    
    <!-- 登录对话框 -->
    <LoginDialog 
      :visible="loginDialogVisible" 
      @close="loginDialogVisible = false"
      @login-success="handleLoginSuccess"
    />
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import Avatar from './Avatar.vue';

// 用户登录状态
const isLoggedIn = ref(false);

// 用户头像
const userAvatar = ref('../assets/user.png');

// 用户信息
const userInfo = ref({
  nickname: '',
  vipStatus: 0,
  vipExpireTime: null,
  following: 0,
  followers: 0,
  activities: 0
});

// 用户菜单显示状态
const showUserMenu = ref(false);

// 鼠标离开定时器ID
const mouseLeaveTimer = ref(null);

// 登录对话框显示状态
const loginDialogVisible = ref(false);

// 滚动高度
const scrollTop = ref(0);

// 处理头像点击
const handleAvatarClick = () => {
  if (isLoggedIn.value) {
    // 登录状态下跳转到个人主页
    window.location.href = '/profile';
  } else {
    // 未登录状态下打开登录对话框
    openLoginDialog();
  }
};

// 打开登录对话框
const openLoginDialog = () => {
  showUserMenu.value = false;
  loginDialogVisible.value = true;
};

// 打开注册对话框
const openRegisterDialog = () => {
  showUserMenu.value = false;
  loginDialogVisible.value = true;
  // 这里需要通过ref访问LoginDialog组件并切换到注册标签
  // 由于使用的是全局组件，这里简化处理，用户可以在对话框内自行切换
};

// 处理鼠标进入
const handleMouseEnter = () => {
  // 清除之前的定时器
  if (mouseLeaveTimer.value) {
    clearTimeout(mouseLeaveTimer.value);
  }
  showUserMenu.value = true;
};

// 处理鼠标离开
const handleMouseLeave = () => {
  // 清除之前的定时器
  if (mouseLeaveTimer.value) {
    clearTimeout(mouseLeaveTimer.value);
  }
  // 添加更长的延迟，确保用户可以顺利从头像移动到登录提示框
  mouseLeaveTimer.value = setTimeout(() => {
    showUserMenu.value = false;
  }, 200);
};

// 处理登录成功
const handleLoginSuccess = (loginData) => {
  // 更新登录状态
  isLoggedIn.value = true;
  // 更新用户信息
  if (loginData.user) {
    userInfo.value = {
      nickname: loginData.user.nickname,
      vipStatus: loginData.user.vipStatus,
      vipExpireTime: loginData.user.vipExpireTime,
      following: 124, // 模拟数据
      followers: 9, // 模拟数据
      activities: 368 // 模拟数据
    };
    // 更新用户头像
    if (loginData.user.avatar) {
      userAvatar.value = loginData.user.avatar;
    }
    // 存储用户信息
    localStorage.setItem('user', JSON.stringify(loginData.user));
  }
  // 存储token
  if (loginData.token) {
    localStorage.setItem('token', loginData.token);
  }
};

// 处理退出登录
const logout = () => {
  // 清除登录状态
  isLoggedIn.value = false;
  // 清除用户信息
  userInfo.value = {
    nickname: '',
    vipStatus: 0,
    vipExpireTime: null,
    following: 0,
    followers: 0,
    activities: 0
  };
  // 清除用户头像
  userAvatar.value = '../assets/user.png';
  // 清除token和用户信息
  localStorage.removeItem('token');
  localStorage.removeItem('user');
  // 关闭用户菜单
  showUserMenu.value = false;
};

// 滚动事件处理函数
const handleScroll = () => {
  scrollTop.value = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
};

// 挂载时添加滚动事件监听
onMounted(() => {
  // 将滚动位置重置到页面顶部
  window.scrollTo(0, 0);
  
  window.addEventListener('scroll', handleScroll);
  // 检查本地存储中是否有token
  const token = localStorage.getItem('token');
  if (token) {
    // 尝试从本地存储中获取用户信息
    const userStr = localStorage.getItem('user');
    if (userStr) {
      try {
        const user = JSON.parse(userStr);
        isLoggedIn.value = true;
        userInfo.value = {
          nickname: user.nickname,
          vipStatus: user.vipStatus,
          vipExpireTime: user.vipExpireTime,
          following: 124, // 模拟数据
          followers: 9, // 模拟数据
          activities: 368 // 模拟数据
        };
        if (user.avatar) {
          userAvatar.value = user.avatar;
        }
      } catch (e) {
        console.error('解析用户信息失败:', e);
        // 解析失败时使用默认值
        isLoggedIn.value = true;
        userInfo.value = {
          nickname: '用户',
          vipStatus: 0,
          vipExpireTime: null,
          following: 0,
          followers: 0,
          activities: 0
        };
      }
    } else {
      // 没有用户信息时使用默认值
      isLoggedIn.value = true;
      userInfo.value = {
        nickname: '用户',
        vipStatus: 0,
        vipExpireTime: null,
        following: 0,
        followers: 0,
        activities: 0
      };
    }
  }
});

// 卸载时移除滚动事件监听
onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
});
</script>

<style scoped>
.header-container {
 position: relative;
  width: 100%;
  padding: 0;
  margin: 0;
}

/* 头部背景 */
.header-background {
  height: 180px;
  background-image: url('../assets/12573bbe82fe6e24e49c199186936b5b15d6572d.png@3840w_360h_1c_看图王.jpg');
  background-size: cover;
  background-position: center;
  background-color: transparent;
  transition: all 0.3s ease;
  width: 100%;
  position: relative;
  z-index: 1;
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}

/* 头部栏 */
.header-bar {
  background-color: rgba(255, 255, 255, 0.95);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  width: 100%;
  transition: all 0.3s ease;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 9999;
}

/* 透明头部栏 */
.transparent-bar {
  background-color: transparent;
  box-shadow: none;
}

/* 白色文本 */
.white-text {
  color: white !important;
}

/* 透明输入框 */
.transparent-input {
  background-color: rgba(255, 255, 255, 0.8) !important;
  border-color: rgba(255, 255, 255, 0.8) !important;
}

/* 透明按钮 */
.transparent-btn {
  background-color: rgba(255, 255, 255, 0.8) !important;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 60px;
  padding: 0 30px;
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
  box-sizing: border-box;
}

/* 左侧导航 */
.header-left {
  flex: 0 0 auto;
  display: flex;
  align-items: center;
}

/* 中间搜索框 */
.header-center {
  flex: 1;
  max-width: 450px;
  margin: 0 40px;
  min-width: 200px;
}

/* 右侧用户区 */
.header-right {
  flex: 0 0 auto;
  display: flex;
  align-items: center;
}

/* 页面内容 */
body {
  margin: 0;
  padding: 0;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  box-sizing: border-box;
}

* {
  box-sizing: inherit;
}

.header-left {
  display: flex;
  align-items: center;
}

.logo {
  margin-right: 30px;
}

.logo-icon {
  font-size: 36px;
  color: #fb7299;
  cursor: pointer;
}

.nav-item {
  margin-right: 30px;
  font-size: 14px;
  color: #333;
  cursor: pointer;
  transition: color 0.3s;
}

.nav-item:hover {
  color: #00a1d6;
}

.nav-item.active {
  color: #00a1d6;
  font-weight: 500;
}

.header-center {
  flex: 1;
  max-width: 400px;
  margin: 0 40px;
}

.search-box {
  position: relative;
  width: 100%;
}

.search-input {
  width: 100%;
  height: 36px;
  padding: 0 15px;
  border: 1px solid #e5e5e5;
  border-radius: 18px;
  font-size: 14px;
  outline: none;
  transition: all 0.3s;
}

.search-input:focus {
  border-color: #00a1d6;
  box-shadow: 0 0 0 2px rgba(0, 161, 214, 0.2);
}

.search-btn {
  position: absolute;
  right: 0;
  top: 0;
  height: 36px;
  width: 36px;
  background-color: #f0f0f0;
  border: none;
  border-radius: 0 18px 18px 0;
  cursor: pointer;
  transition: background-color 0.3s;
}

.search-btn:hover {
  background-color: #e0e0e0;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-item {
  margin-right: 20px;
  cursor: pointer;
  font-size: 14px;
  color: #333;
  transition: color 0.3s;
  display: flex;
  align-items: center;
}

.user-item:hover {
  color: #00a1d6;
}

.user-item i {
  margin-right: 4px;
  font-size: 16px;
}

.user-avatar-container {
  position: relative;
  margin-right: 20px;
  cursor: pointer;
}

.avatar-wrapper {
  cursor: pointer;
  display: inline-block;
}

.login-modal {
  position: absolute;
  top: 40px;
  left: 50%;
  transform: translateX(-50%);
  width: 280px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  z-index: 1001;
  overflow: hidden;
}

.login-modal-content {
  padding: 20px;
}

.login-modal-content h3 {
  margin: 0 0 16px 0;
  font-size: 16px;
  font-weight: 500;
  color: #333;
}

.login-features {
  margin-bottom: 20px;
}

.login-feature {
  display: flex;
  align-items: center;
  margin-bottom: 12px;
  font-size: 14px;
  color: #666;
}

.login-feature i {
  margin-right: 8px;
  font-size: 16px;
  color: #00a1d6;
}

.login-btn {
  width: 100%;
  height: 36px;
  background-color: #00a1d6;
  color: white;
  border: none;
  border-radius: 18px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-bottom: 12px;
}

.login-btn:hover {
  background-color: #00b5e5;
}

.register-link {
  text-align: center;
  font-size: 12px;
  color: #999;
}

.register-link a {
  color: #00a1d6;
  text-decoration: none;
}

.register-link a:hover {
  text-decoration: underline;
}

.vip {
  color: #f97300;
}

.vip:hover {
  color: #ff8c00;
}

.message {
  text-decoration: none;
  color: #333;
}

.message:hover {
  color: #00a1d6;
}

.submit-btn {
  height: 32px;
  padding: 0 16px;
  background-color: #fb7299;
  color: white;
  border: none;
  border-radius: 16px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s;
  display: flex;
  align-items: center;
}

.submit-btn i {
  margin-right: 4px;
  font-size: 16px;
}

.submit-btn:hover {
  background-color: #ff85ad;
}

.fixed-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
}

.carousel {
  margin-top: 60px;
  position: relative;
  height: 360px;
  overflow: hidden;
}

.carousel-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 用户菜单样式 */
.user-menu {
  position: absolute;
  top: 40px;
  left: 50%;
  transform: translateX(-50%);
  width: 280px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  z-index: 1001;
  overflow: hidden;
}

.user-menu-header {
  padding: 20px;
  border-bottom: 1px solid #e5e5e5;
}

.user-info {
  display: flex;
  align-items: center;
  margin-bottom: 16px;
}

.user-details {
  margin-left: 12px;
}

.username {
  font-size: 16px;
  font-weight: 500;
  color: #333;
  margin-bottom: 4px;
}

.vip-status {
  display: flex;
  align-items: center;
}

.vip-tag {
  background-color: #fb7299;
  color: white;
  font-size: 12px;
  padding: 0 4px;
  border-radius: 2px;
}

.user-stats {
  display: flex;
  justify-content: space-around;
  margin-bottom: 16px;
}

.stat-item {
  text-align: center;
}

.stat-value {
  font-size: 16px;
  font-weight: 500;
  color: #333;
  margin-bottom: 4px;
}

.stat-label {
  font-size: 12px;
  color: #999;
}

.vip-expire {
  background-color: #fff2f7;
  padding: 12px;
  border-radius: 4px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.vip-expire span {
  font-size: 12px;
  color: #fb7299;
}

.vip-center {
  font-size: 12px;
  color: #fb7299;
  text-decoration: none;
}

.vip-center:hover {
  text-decoration: underline;
}

.user-menu-body {
  padding: 8px 0;
}

.menu-item {
  display: flex;
  align-items: center;
  padding: 12px 20px;
  font-size: 14px;
  color: #333;
  text-decoration: none;
  transition: all 0.3s;
}

.menu-item:hover {
  background-color: #f5f5f5;
  color: #00a1d6;
}

.menu-item i {
  margin-right: 8px;
  font-size: 16px;
}

.user-menu-footer {
  padding: 8px 0;
  border-top: 1px solid #e5e5e5;
}

.menu-item.logout {
  color: #999;
}

.menu-item.logout:hover {
  color: #ff4d4f;
  background-color: #fff2f0;
}
</style>