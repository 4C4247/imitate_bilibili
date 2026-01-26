<template>
  <div class="login-dialog-overlay" v-if="visible">
    <div class="login-dialog">
      <!-- 标题栏 -->
      <div class="login-dialog-header">
        <div class="login-tabs">
          <div class="login-tab" :class="{ active: activeTab === 'login' }" @click="activeTab = 'login'">登录</div>
          <div class="login-tab" :class="{ active: activeTab === 'register' }" @click="activeTab = 'register'">注册</div>
        </div>
        <button class="close-btn" @click="close">
          <i class="iconfont icon-close"></i>
        </button>
      </div>
      
      <!-- 登录表单 -->
      <div class="login-dialog-content" v-if="activeTab === 'login'">
        <form @submit.prevent="handleLogin">
          <div class="form-item">
            <label for="login-username">账号</label>
            <input type="text" id="login-username" v-model="loginForm.username" placeholder="请输入账号" required>
          </div>
          <div class="form-item">
            <label for="login-password">密码</label>
            <input type="password" id="login-password" v-model="loginForm.password" placeholder="请输入密码" required>
          </div>
          <div class="form-actions">
            <button type="submit" class="login-btn">登录</button>
          </div>
          <div class="form-links">
            <a href="#" class="forgot-password">忘记密码？</a>
          </div>
        </form>
      </div>
      
      <!-- 注册表单 -->
      <div class="login-dialog-content" v-if="activeTab === 'register'">
        <form @submit.prevent="handleRegister">
          <div class="form-item">
            <label for="register-username">账号</label>
            <input type="text" id="register-username" v-model="registerForm.username" placeholder="请输入账号" required>
          </div>
          <div class="form-item">
            <label for="register-password">密码</label>
            <input type="password" id="register-password" v-model="registerForm.password" placeholder="请输入密码" required>
          </div>
          <div class="form-item">
            <label for="register-confirm-password">确认密码</label>
            <input type="password" id="register-confirm-password" v-model="registerForm.confirmPassword" placeholder="请再次输入密码" required>
          </div>
          <div class="form-item">
            <label for="register-email">邮箱</label>
            <input type="email" id="register-email" v-model="registerForm.email" placeholder="请输入邮箱" required>
          </div>
          <div class="form-item">
            <label for="register-captcha">验证码</label>
            <div class="captcha-container">
              <input type="text" id="register-captcha" v-model="registerForm.captcha" placeholder="请输入验证码" required>
              <div class="captcha-image" @click="generateCaptcha">
                <canvas ref="captchaCanvas" width="120" height="40"></canvas>
              </div>
            </div>
          </div>
          <div class="form-actions">
            <button type="submit" class="register-btn">注册</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';

// Props
const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  }
});

// Emits
const emit = defineEmits(['close', 'login-success']);

// 激活的标签
const activeTab = ref('login');

// 登录表单
const loginForm = ref({
  username: '',
  password: ''
});

// 注册表单
const registerForm = ref({
  username: '',
  password: '',
  confirmPassword: '',
  email: '',
  captcha: ''
});

// 验证码相关
const captchaCanvas = ref(null);
const captchaAnswer = ref(0);

// 生成随机验证码
const generateCaptcha = () => {
  const canvas = captchaCanvas.value;
  if (!canvas) return;
  
  const ctx = canvas.getContext('2d');
  const width = canvas.width;
  const height = canvas.height;
  
  // 清空画布
  ctx.clearRect(0, 0, width, height);
  
  // 生成随机背景
  ctx.fillStyle = '#f5f5f5';
  ctx.fillRect(0, 0, width, height);
  
  // 生成随机数字和运算符
  const num1 = Math.floor(Math.random() * 10) + 1;
  const num2 = Math.floor(Math.random() * 10) + 1;
  const operators = ['+', '-', '*'];
  const operator = operators[Math.floor(Math.random() * operators.length)];
  
  // 计算答案
  switch (operator) {
    case '+':
      captchaAnswer.value = num1 + num2;
      break;
    case '-':
      captchaAnswer.value = num1 - num2;
      break;
    case '*':
      captchaAnswer.value = num1 * num2;
      break;
  }
  
  // 绘制验证码
  ctx.font = '20px Arial';
  ctx.fillStyle = '#333';
  ctx.textAlign = 'center';
  ctx.textBaseline = 'middle';
  ctx.fillText(`${num1} ${operator} ${num2} = ?`, width / 2, height / 2);
  
  // 绘制干扰线
  ctx.strokeStyle = '#ddd';
  for (let i = 0; i < 5; i++) {
    ctx.beginPath();
    ctx.moveTo(Math.random() * width, Math.random() * height);
    ctx.lineTo(Math.random() * width, Math.random() * height);
    ctx.stroke();
  }
};

// 监听标签切换，切换到注册标签时生成验证码，切换标签时清除对应表单内容
watch(activeTab, (newTab, oldTab) => {
  // 当从注册标签切换到其他标签时，清除注册表单内容
  if (oldTab === 'register') {
    registerForm.value = {
      username: '',
      password: '',
      confirmPassword: '',
      email: '',
      captcha: ''
    };
  }
  // 当从其他标签切换到登录标签时，清除登录表单内容
  if (newTab === 'login') {
    loginForm.value = {
      username: '',
      password: ''
    };
  }
  // 当切换到注册标签时生成验证码
  if (newTab === 'register') {
    setTimeout(() => {
      generateCaptcha();
    }, 0);
  }
});

// 组件挂载时生成验证码（如果默认是注册标签）
onMounted(() => {
  if (activeTab.value === 'register') {
    setTimeout(() => {
      generateCaptcha();
    }, 0);
  }
});

// 处理登录
const handleLogin = async () => {
  try {
    // 发送登录请求
    const response = await fetch('/api/user/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        username: loginForm.value.username,
        password: loginForm.value.password
      })
    });
    
    // 尝试解析 JSON 响应
    const result = await response.json();
    
    if (result.code === 200) {
      // 显示登录成功提示
      alert('登录成功！');
      // 登录成功后触发事件并关闭对话框
      emit('login-success', result.data);
      close();
    } else {
      // 显示后端返回的错误信息
      alert(result.message || '账号或密码错误！请检查输入！');
    }
  } catch (error) {
    // 网络错误或 JSON 解析错误
    alert('账号或密码错误！请检查输入！');
  }
};

// 处理注册
const handleRegister = async () => {
  // 验证验证码
  if (parseInt(registerForm.value.captcha) !== captchaAnswer.value) {
    alert('验证码错误，请重新输入');
    generateCaptcha();
    return;
  }
  
  // 验证密码
  if (registerForm.value.password !== registerForm.value.confirmPassword) {
    alert('两次输入的密码不一致');
    return;
  }
  
  try {
    // 发送注册请求
    const response = await fetch('/api/user/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        username: registerForm.value.username,
        password: registerForm.value.password,
        email: registerForm.value.email
      })
    });
    
    if (!response.ok) {
      // 服务器返回 HTTP 错误
      alert('注册失败，请稍后重试');
      return;
    }
    
    try {
      const result = await response.json();
      
      if (result.code === 200) {
        // 注册成功后切换到登录标签
        activeTab.value = 'login';
        // 清空表单
        registerForm.value = {
          username: '',
          password: '',
          confirmPassword: '',
          email: '',
          captcha: ''
        };
        alert('注册成功，请登录');
      } else {
        alert(result.message || '注册失败，请稍后重试');
      }
    } catch (jsonError) {
      // JSON 解析错误，可能是服务器返回了非 JSON 数据
      alert('注册失败，请稍后重试');
    }
  } catch (error) {
    // 网络错误
    alert('注册失败，请稍后重试');
  }
};

// 关闭对话框
const close = () => {
  emit('close');
};

// 监听visible变化
watch(() => props.visible, (newVal) => {
  if (newVal) {
    // 生成验证码
    generateCaptcha();
  }
});

// 挂载时生成验证码
onMounted(() => {
  if (props.visible) {
    generateCaptcha();
  }
});
</script>

<style scoped>
.login-dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}

.login-dialog {
  width: 360px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  overflow: hidden;
}

.login-dialog-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  border-bottom: 1px solid #e5e5e5;
}

.login-tabs {
  display: flex;
}

.login-tab {
  padding: 0 20px;
  font-size: 16px;
  cursor: pointer;
  color: #666;
  transition: all 0.3s;
}

.login-tab:hover {
  color: #00a1d6;
}

.login-tab.active {
  color: #00a1d6;
  font-weight: 500;
  border-bottom: 2px solid #00a1d6;
}

.close-btn {
  background: none;
  border: none;
  font-size: 16px;
  cursor: pointer;
  color: #999;
  transition: color 0.3s;
}

.close-btn:hover {
  color: #333;
}

.login-dialog-content {
  padding: 20px;
}

.form-item {
  margin-bottom: 20px;
}

.form-item label {
  display: block;
  margin-bottom: 8px;
  font-size: 14px;
  color: #333;
}

.form-item input {
  width: 100%;
  height: 40px;
  padding: 0 12px;
  border: 1px solid #e5e5e5;
  border-radius: 4px;
  font-size: 14px;
  transition: all 0.3s;
}

.form-item input {
  width: 100%;
  height: 36px;
  padding: 0 12px;
  border: 1px solid #e5e5e5;
  border-radius: 4px;
  font-size: 14px;
  transition: all 0.3s;
}

.captcha-container {
  display: flex;
  gap: 8px;
}

.captcha-container input {
  flex: 1;
}

.captcha-image {
  width: 100px;
  height: 36px;
  cursor: pointer;
  border: 1px solid #e5e5e5;
  border-radius: 4px;
  overflow: hidden;
}

.form-actions {
  margin-top: 24px;
}

.login-btn,
.register-btn {
  width: 100%;
  height: 36px;
  background-color: #00a1d6;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.login-btn:hover,
.register-btn:hover {
  background-color: #00b5e5;
}

.form-links {
  margin-top: 15px;
  text-align: center;
}

.forgot-password {
  font-size: 14px;
  color: #00a1d6;
  text-decoration: none;
}

.forgot-password:hover {
  text-decoration: underline;
}
</style>