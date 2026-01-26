<template>
  <div class="avatar-container">
    <img 
      :src="src" 
      :alt="alt" 
      class="avatar-img"
      :style="{ width: size + 'px', height: size + 'px' }"
    >
  </div>
</template>

<script setup>
const props = defineProps({
  src: {
    type: String,
    default: '../assets/user.png'
  },
  alt: {
    type: String,
    default: '用户头像'
  },
  size: {
    type: [Number, String],
    default: 32
  }
});
</script>

<style scoped>
.avatar-container {
  position: relative;
  display: inline-block;
  cursor: pointer;
}

.avatar-img {
  border-radius: 50%;
  transition: all 0.3s;
}

.avatar-img:hover {
  transform: scale(1.05);
}
</style>