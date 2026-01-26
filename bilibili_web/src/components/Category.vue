<template>
  <div class="category-container">
    <div class="category-content">
      <!-- 左侧热门图标 -->
      <div class="hot-icon">
        <div class="hot-circle">
          <span class="hot-text">热门</span>
        </div>
      </div>
      
      <!-- 中间分类宫格 -->
      <div class="category-grid">
        <div class="category-row">
          <!-- 第一行分类 -->
          <div 
            v-for="category in displayCategories.slice(0, 10)" 
            :key="category.id"
            class="category-item"
          >
            <el-popover
              placement="top"
              :width="400"
              trigger="hover"
              :effect="'light'"
            >
              <template #reference>
                <div class="category-content-wrapper" @click="navigateToCategory(category)">
                  <div class="category-icon">
                    <img :src="getCategoryIcon(category.name)" :alt="category.name">
                  </div>
                  <div class="category-name">{{ category.name }}</div>
                </div>
              </template>
              <div class="subcategory-list">
                <div 
                  v-for="sub in category.subcategories" 
                  :key="sub.id"
                  class="subcategory-item"
                  @click="navigateToSubcategory(sub, category)"
                >
                  {{ sub.name }}
                </div>
              </div>
            </el-popover>
          </div>
        </div>
        
        <div class="category-row">
          <!-- 第二行分类 -->
          <div 
            v-for="(category, index) in displayCategories.slice(10, 19)" 
            :key="category.id"
            class="category-item"
          >
            <el-popover
              placement="bottom"
              :width="400"
              trigger="hover"
              :effect="'light'"
            >
              <template #reference>
                <div class="category-content-wrapper" @click="navigateToCategory(category)">
                  <div class="category-icon">
                    <img :src="getCategoryIcon(category.name)" :alt="category.name">
                  </div>
                  <div class="category-name">{{ category.name }}</div>
                </div>
              </template>
              <div class="subcategory-list">
                <div 
                  v-for="sub in category.subcategories" 
                  :key="sub.id"
                  class="subcategory-item"
                  @click="navigateToSubcategory(sub, category)"
                >
                  {{ sub.name }}
                </div>
              </div>
            </el-popover>
          </div>
          
          <!-- 更多分类 -->
          <div 
            v-if="categories.length > 20" 
            class="category-item more-item"
          >
            <el-popover
              placement="bottom"
              :width="400"
              trigger="hover"
              :effect="'light'"
            >
              <template #reference>
                <div class="category-content-wrapper" @click="navigateToMoreCategory()">
                  <div class="category-icon">
                    <img src="https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=more%20icon%2C%20simple%20design%2C%20gray%20color&image_size=square" alt="更多">
                  </div>
                  <div class="category-name">更多</div>
                </div>
              </template>
              <div class="subcategory-list">
                <div 
                  v-for="category in categories.slice(20)" 
                  :key="category.id"
                  class="subcategory-item"
                  @click="navigateToCategory(category)"
                >
                  {{ category.name }}
                </div>
              </div>
            </el-popover>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 固定分类栏 -->
    <transition name="fixed-category-fade">
      <div v-if="showFixedCategory" class="fixed-category" @mouseenter="expandFixedCategory" @mouseleave="collapseFixedCategory">
        <div class="fixed-category-content">
          <div class="fixed-category-main">
            <div 
              v-for="category in displayCategories.slice(0, 10)" 
              :key="category.id"
              class="fixed-category-item"
            >
              <div class="fixed-category-name" @click="navigateToCategory(category)">{{ category.name }}</div>
            </div>
            <div class="fixed-category-arrow" :class="{ rotated: isFixedCategoryExpanded }"></div>
          </div>
          <transition name="fixed-category-expand">
            <div v-if="isFixedCategoryExpanded" class="fixed-category-expanded">
              <div class="fixed-category-grid">
                <div 
                  v-for="category in displayCategories.slice(10)" 
                  :key="category.id"
                  class="fixed-category-expanded-item"
                >
                  <div class="fixed-category-expanded-name" @click="navigateToCategory(category)">{{ category.name }}</div>
                </div>
              </div>
            </div>
          </transition>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';

// 分类数据
const categories = ref([]);

// 从后端获取分类数据
const fetchCategories = async () => {
  try {
    const response = await fetch('http://localhost:8080/api/category/all');
    if (!response.ok) {
      throw new Error('Failed to fetch categories');
    }
    const result = await response.json();
    if (result.code === 200) {
      categories.value = result.data;
      // 获取到分类数据后，计算显示的分类
      calculateDisplayCategories();
    }
  } catch (error) {
    console.error('Error fetching categories:', error);
    // 使用mock数据
    categories.value = [
      { id: 1, name: '动画', subcategories: [{ id: 101, name: '番剧' }, { id: 102, name: '国创' }, { id: 103, name: '电影' }, { id: 104, name: '电视剧' }] },
      { id: 2, name: '音乐', subcategories: [{ id: 201, name: '原创音乐' }, { id: 202, name: '翻唱' }, { id: 203, name: 'VOCALOID' }, { id: 204, name: '电音' }] },
      { id: 3, name: '舞蹈', subcategories: [{ id: 301, name: '宅舞' }, { id: 302, name: '街舞' }, { id: 303, name: '爵士舞' }, { id: 304, name: '现代舞' }] },
      { id: 4, name: '游戏', subcategories: [{ id: 401, name: '单机游戏' }, { id: 402, name: '网络游戏' }, { id: 403, name: '手机游戏' }, { id: 404, name: '电子竞技' }] },
      { id: 5, name: '知识', subcategories: [{ id: 501, name: '科学科普' }, { id: 502, name: '人文历史' }, { id: 503, name: '财经商业' }, { id: 504, name: '教育' }] },
      { id: 6, name: '科技', subcategories: [{ id: 601, name: '数码' }, { id: 602, name: '软件应用' }, { id: 603, name: '计算机技术' }, { id: 604, name: '网络安全' }] },
      { id: 7, name: '运动', subcategories: [{ id: 701, name: '篮球' }, { id: 702, name: '足球' }, { id: 703, name: '羽毛球' }, { id: 704, name: '乒乓球' }] },
      { id: 8, name: '汽车', subcategories: [{ id: 801, name: '汽车评测' }, { id: 802, name: '汽车改装' }, { id: 803, name: '赛车' }, { id: 804, name: '新能源汽车' }] },
      { id: 9, name: '生活', subcategories: [{ id: 901, name: '美食' }, { id: 902, name: '旅游' }, { id: 903, name: '家居' }, { id: 904, name: '宠物' }] },
      { id: 10, name: '鬼畜', subcategories: [{ id: 1001, name: '鬼畜调教' }, { id: 1002, name: '音MAD' }, { id: 1003, name: '人力VOCALOID' }, { id: 1004, name: '鬼畜剧场' }] },
      { id: 11, name: '时尚', subcategories: [{ id: 1101, name: '美妆' }, { id: 1102, name: '服饰' }, { id: 1103, name: '街拍' }, { id: 1104, name: '健身' }] },
      { id: 12, name: '娱乐', subcategories: [{ id: 1201, name: '明星' }, { id: 1202, name: '综艺' }, { id: 1203, name: '电影' }, { id: 1204, name: '电视剧' }] },
      { id: 13, name: '影视', subcategories: [{ id: 1301, name: '电影' }, { id: 1302, name: '电视剧' }, { id: 1303, name: '纪录片' }, { id: 1304, name: '动画电影' }] },
      { id: 14, name: '亲子', subcategories: [{ id: 1401, name: '育儿' }, { id: 1402, name: '早教' }, { id: 1403, name: '亲子游戏' }, { id: 1404, name: '儿歌' }] },
      { id: 15, name: '纪录片', subcategories: [{ id: 1501, name: '历史' }, { id: 1502, name: '地理' }, { id: 1503, name: '人文' }, { id: 1504, name: '科技' }] },
      { id: 16, name: '军事', subcategories: [{ id: 1601, name: '武器装备' }, { id: 1602, name: '军事历史' }, { id: 1603, name: '国际军情' }, { id: 1604, name: '军事科技' }] },
      { id: 17, name: '法律', subcategories: [{ id: 1701, name: '法律常识' }, { id: 1702, name: '案例分析' }, { id: 1703, name: '法律咨询' }, { id: 1704, name: '法律职业' }] },
      { id: 18, name: '财经', subcategories: [{ id: 1801, name: '股票' }, { id: 1802, name: '基金' }, { id: 1803, name: '期货' }, { id: 1804, name: '理财' }] },
      { id: 19, name: '心理', subcategories: [{ id: 1901, name: '心理学' }, { id: 1902, name: '心理测试' }, { id: 1903, name: '心理咨询' }, { id: 1904, name: '心理科普' }] },
      { id: 20, name: '健康', subcategories: [{ id: 2001, name: '养生' }, { id: 2002, name: '医疗' }, { id: 2003, name: '营养' }, { id: 2004, name: '运动健康' }] },
      { id: 21, name: '动物圈', subcategories: [{ id: 2101, name: '宠物' }, { id: 2102, name: '野生动物' }, { id: 2103, name: '动物保护' }, { id: 2104, name: '动物科普' }] }
    ];
    // 计算显示的分类
    calculateDisplayCategories();
  }
};

// 显示的分类（包括更多按钮）
const displayCategories = ref([]);

// 固定分类栏显示状态
const showFixedCategory = ref(false);

// 固定分类栏展开状态
const isFixedCategoryExpanded = ref(false);

// 滚动高度
const scrollTop = ref(0);

// 计算显示的分类
const calculateDisplayCategories = () => {
  if (categories.value.length > 20) {
    // 超过20个分类，显示前19个加上更多
    displayCategories.value = [...categories.value.slice(0, 19)];
  } else {
    // 不超过20个分类，全部显示
    displayCategories.value = [...categories.value];
  }
};

// 获取分类图标
const getCategoryIcon = (name) => {
  // 使用文本生成图像作为分类图标
  return `https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=${encodeURIComponent(name + ' icon, simple design, colorful')}&image_size=square`;
};


// ==========================================
// 滚动事件处理
// ==========================================

// 展开固定分类栏
const expandFixedCategory = () => {
  isFixedCategoryExpanded.value = true;
};

// 关闭固定分类栏
const collapseFixedCategory = () => {
  isFixedCategoryExpanded.value = false;
};

// 滚动事件处理函数
const handleScroll = () => {
  scrollTop.value = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
  // 当滚动超过200px时显示固定分类栏
  showFixedCategory.value = scrollTop.value > 200;
  // 滚动时重置展开状态
  isFixedCategoryExpanded.value = false;
};

// 导航到分类页面
const navigateToCategory = (category) => {
  // 使用_blank模式在新窗口打开分类页面
  window.open(`/category/${category.id}?name=${encodeURIComponent(category.name)}`, '_blank');
};

// 导航到子分类页面
const navigateToSubcategory = (subcategory, parentCategory) => {
  // 使用_blank模式在新窗口打开子分类页面
  window.open(`/category/${parentCategory.id}/subcategory/${subcategory.id}?name=${encodeURIComponent(subcategory.name)}&parent=${encodeURIComponent(parentCategory.name)}`, '_blank');
};

// 导航到更多分类页面
const navigateToMoreCategory = () => {
  // 使用_blank模式在新窗口打开更多分类页面
  window.open('/categories', '_blank');
};

// 组件挂载时从后端获取分类数据
onMounted(() => {
  fetchCategories();
  // 添加滚动事件监听
  window.addEventListener('scroll', handleScroll);
});

// 组件卸载时移除滚动事件监听
onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
});
</script>

<style scoped>
.category-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px 0;
  margin: 0;
  width: 100%;
  box-sizing: border-box;
  position: relative;
  z-index: 2000;
}

/* 主内容区域 */
.category-content {
  display: flex;
  align-items: center;
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  box-sizing: border-box;
}

/* 左侧热门图标 */
.hot-icon {
  margin-right: 30px;
  flex-shrink: 0;
}

.hot-circle {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background: linear-gradient(135deg, #ff6b6b, #ff8e53);
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 12px rgba(255, 107, 107, 0.3);
}

.hot-text {
  color: white;
  font-size: 16px;
  font-weight: bold;
}

/* 中间分类宫格 */
.category-grid {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.category-row {
  display: flex;
  width: 100%;
  max-width: 900px;
  margin-bottom: 8px;
}

/* 最后一行不需要下边距 */
.category-row:last-child {
  margin-bottom: 0;
}

/* 气泡淡入淡出动画 */
.bubble-fade-enter-active,
.bubble-fade-leave-active {
  transition: all 0.1s ease;
}

.bubble-fade-enter-from,
.bubble-fade-leave-to {
  opacity: 0;
  transform: translateX(-50%) translateY(10px);
}

.category-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 15px;
  cursor: pointer;
  position: relative;
  transition: all 0.3s ease;
}

.category-item:hover {
  transform: translateY(-5px);
}

.category-icon {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  background-color: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
  overflow: hidden;
}

.category-icon img {
  width: 40px;
  height: 40px;
  object-fit: contain;
}

.category-name {
  font-size: 14px;
  color: #333;
  text-align: center;
}

/* 子分类气泡 */
.subcategory-bubble {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  padding: 12px 16px;
  z-index: 9999;
  min-width: 320px;
  max-width: 420px;
  margin-top: 10px;
  pointer-events: auto;
}

.subcategory-bubble.top {
  bottom: 100%;
  margin-bottom: 10px;
}

.subcategory-bubble.bottom {
  top: 100%;
  margin-top: 10px;
}

.subcategory-list {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 8px;
  padding: 10px;
}

.subcategory-item {
  padding: 6px 14px;
  border-radius: 4px;
  transition: background-color 0.2s ease;
  flex: 0 0 auto;
  box-sizing: border-box;
  text-align: center;
  white-space: nowrap;
  font-size: 14px;
  cursor: pointer;
}

.subcategory-item:hover {
  background-color: #f0f0f0;
}

/* 更多按钮 */
.more-item {
  position: relative;
}

/* 分类内容包装器 */
.category-content-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
  cursor: pointer;
}

/* 固定分类栏淡入动画 */
.fixed-category-fade-enter-active,
.fixed-category-fade-leave-active {
  transition: all 0.5s ease;
}

.fixed-category-fade-enter-from,
.fixed-category-fade-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}

/* 固定分类栏展开/关闭动画 */
.fixed-category-expand-enter-active,
.fixed-category-expand-leave-active {
  transition: all 0.5s ease;
  max-height: 200px;
  overflow: hidden;
}

.fixed-category-expand-enter-from,
.fixed-category-expand-leave-to {
  opacity: 0;
  max-height: 0;
  
  /* 必须把 padding 和 margin 也归零，否则最后会卡一下 */
  padding-top: 0 !important;
  padding-bottom: 0 !important;
  margin-top: 0 !important;
  margin-bottom: 0 !important;
  
  transform: translateY(-10px);
}

/* 固定分类栏 */
.fixed-category {
  position: fixed;
  top: 60px; /* 紧贴在fixed-header-bar下面 */
  left: 0;
  right: 0;
  background-color: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  z-index: 9998; /* 低于fixed-header-bar的9999 */
  transition: all 0.3s ease;
  overflow: hidden;
}

.fixed-category-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 30px;
  box-sizing: border-box;
  transition: all 0.3s ease;
  width: 100%;
}

/* 固定分类栏主内容 */
.fixed-category-main {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 50px;
  transition: all 0.3s ease;
  width: 100%;
}

/* 固定分类栏项目 - 均匀分布 */
.fixed-category-item {
  flex: 1;
  text-align: center;
  cursor: pointer;
  position: relative;
  transition: all 0.3s ease;
  margin: 0 10px;
}

.fixed-category-name {
  font-size: 14px;
  color: #333;
  transition: color 0.3s ease;
  white-space: nowrap;
}

.fixed-category-item:hover .fixed-category-name {
  color: #00a1d6;
}

/* 展开/关闭箭头 */
.fixed-category-arrow {
  width: 16px;
  height: 16px;
  border-left: 2px solid #999;
  border-bottom: 2px solid #999;
  transform: rotate(-45deg);
  transition: all 0.3s ease;
  cursor: pointer;
  margin-left: 10px;
}

.fixed-category-arrow.rotated {
  transform: rotate(135deg);
}

/* 固定分类栏展开状态 */
.fixed-category.expanded {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

/* 展开后的分类内容 */
.fixed-category-expanded {
  padding: 15px 0;
  border-top: 1px solid #f0f0f0;
  width: 100%;
  
  /* 添加这个：让 padding 的变化更平滑 */
  transition: padding 0.3s ease-out;
}

/* 展开后的分类网格 */
.fixed-category-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(100px, 1fr));
  gap: 15px;
  width: 100%;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* 展开后的分类项目 */
.fixed-category-expanded-item {
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  padding: 8px 0;
}

.fixed-category-expanded-name {
  font-size: 14px;
  color: #333;
  transition: color 0.3s ease;
  white-space: nowrap;
}

.fixed-category-expanded-item:hover .fixed-category-expanded-name {
  color: #00a1d6;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .category-content {
    max-width: 960px;
  }
  
  .category-row {
    max-width: 750px;
  }
  
  .category-icon {
    width: 50px;
    height: 50px;
  }
  
  .category-icon img {
    width: 30px;
    height: 30px;
  }
  
  .category-name {
    font-size: 12px;
  }
  
  .fixed-category-content {
    max-width: 960px;
    padding: 0 20px;
  }
  
  .fixed-category-item {
    margin: 0 10px;
  }
  
  .fixed-category-name {
    font-size: 13px;
  }
}

@media (max-width: 768px) {
  .category-content {
    padding: 0 10px;
  }
  
  .hot-icon {
    margin-right: 20px;
  }
  
  .hot-circle {
    width: 50px;
    height: 50px;
  }
  
  .hot-text {
    font-size: 14px;
  }
  
  .category-row {
    max-width: 500px;
  }
  
  .category-item {
    padding: 10px;
  }
  
  .fixed-category {
    top: 50px;
  }
  
  .fixed-category-content {
    height: 40px;
    padding: 0 10px;
  }
  
  .fixed-category-item {
    margin: 0 8px;
  }
  
  .fixed-category-name {
    font-size: 12px;
  }
}
</style>