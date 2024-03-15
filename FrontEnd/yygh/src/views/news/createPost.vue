<template>
    <div class="post-form-container">
      <h1>发布新帖子</h1>
      <form @submit.prevent="submitPost">
        <div class="form-group">
          <label for="title">标题:</label>
          <input type="text" id="title" v-model="post.title" required>
        </div>
        
        <div class="form-group">
          <label for="content">内容:</label>
          <textarea id="content" v-model="post.content" required></textarea>
        </div>
        
        <div class="form-group">
          <label for="category">类别:</label>
          <select id="category" v-model="post.category">
            <option disabled value="">请选择一个类别</option>
            <!-- 假设 categories 是一个包含类别的数组 -->
            <option v-for="category in categories" :key="category.value" :value="category.value">
              {{ category.text }}
            </option>
          </select>
        </div>
        
        <!-- 附加元素，例如文件上传等 -->
        
        <button type="submit">发布</button>
        <button type="button" @click="cancelPost">取消</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        post: {
          title: '',
          content: '',
          category: ''
        },
        categories: [
          { value: 'news', text: '新闻' },
          { value: 'tutorial', text: '教程' },
          // ...其他类别
        ]
      };
    },
    methods: {
      submitPost() {
        // 在这里处理帖子的提交逻辑，例如使用 axios 发送到 API
        console.log('提交帖子: ', this.post);
      },
      cancelPost() {
        // 处理取消逻辑，例如清空表单或返回上一页
        console.log('取消发布');
        this.$router.go(-1); // 假设你使用了 Vue Router
      }
    }
  };
  </script>
  
  <style scoped>
  /* 在这里添加CSS样式 */
  .form-group {
    margin-bottom: 1em;
  }
  /* ...其他样式 */
  </style>
  