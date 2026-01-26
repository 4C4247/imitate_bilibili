import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './style.css'
import '../assets/icon/iconfont.css'
import App from './App.vue'
import components from './components'
import { createRouter, createWebHistory } from 'vue-router'

// 创建路由实例
const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'Home',
      component: () => import('./views/HomePage.vue')
    },
    {
      path: '/message',
      name: 'Message',
      component: () => import('./views/message.vue')
    },
    {
      path: '/vip',
      name: 'Vip',
      component: () => import('./views/vip.vue')
    },
    {
      path: '/activity',
      name: 'Activity',
      component: () => import('./views/activity.vue')
    },
    {
      path: '/collection',
      name: 'Collection',
      component: () => import('./views/collection.vue')
    },
    {
      path: '/upload',
      name: 'Upload',
      component: () => import('./views/upload.vue')
    }
  ]
})

const app = createApp(App)
const pinia = createPinia()

app.use(pinia)
app.use(ElementPlus)
app.use(components)
app.use(router)
app.mount('#app')
