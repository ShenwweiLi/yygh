<!-- NewsPage.vue -->
<template>
  <div class="news-page">
    <div class="search-box">
      <input type="text" v-model="search" placeholder="按名称搜索"/>

      <button @click="fetchNews">搜索</button>
    </div>
    <div v-if="loading">
      正在加载帖子...
    </div>
    <div v-else class="news-list">
      <div v-for="(newsItem, index) in newsList" :key="index" class="news-item">
        
        <h2>{{ newsItem.title }}</h2>
        <p>{{ newsItem.summary }}</p>
        <button @click="toDetail(newsItem.id)">查看详情</button>
      </div>
      <div class="pagination">
        <button @click="changePage(-1)" :disabled="page <= 1">上一页</button>
        <span>当前页：{{ page }}</span>
        <button @click="changePage(1)" :disabled="page >= totalPages">下一页</button>
      </div>
    </div>
    <button @click="showAddModalMethod" class="floating-add-button">+</button>
     <!-- 添加新闻的模态框 -->
 <!-- 添加新闻的模态框 -->
 <div v-if="showAddModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="showAddModal = false">&times;</span>
        <h3>发布帖子</h3>
        <form @submit.prevent="AddPost">
          <div class="form-group">
            <label for="newsTitle">标题：</label>
            <input type="text" id="newsTitle" v-model="newNews.title" required>
          </div>
          <div class="form-group">
            <label for="newsSummary">摘要：</label>
            <textarea id="newsSummary" v-model="newNews.content" required></textarea>
          </div>
          <button type="submit" class="submit-button" >提交</button>
        </form>
      </div>
    </div>
  </div>
</template>


<script>
import axios from 'axios';
export default {
  name: 'NewsPage',
  data() {
    return {
      loading: true,
      page: 1, // 初始页面加载第1页
      size: 5, // 每页显示的新闻数量
      totalPages: 0, // 总页数，需要从后端获取
      search: '', // 添加这行代码
      newsList: [], // 初始为空
      showAddModal: false, // 控制添加新闻模态框的显示
      newNews: { // 新闻的标题和摘要数据模型
        title: '',
        content: ''
      }
    
    };
  },
  created() {
    this.fetchNews();
  },
  methods: {
    async fetchNews() {
    const params={
            currentPage: this.page, // 注意这里传递的是当前页码
            pageSize: this.size
          };
          if (this.search) {
      params.search = this.search;
    }
    // if (this.category) {
    //   params.category = this.category;
    // }
      try {
        this.loading = true;
        const response = await axios.get('/posts/listPost', {
       params
        });
                // eslint-disable-next-line no-console
                console.log('获取帖子列表:', response);
        this.newsList = response.data.data.postsList;
        // 更新当前页码和总页数
        this.page = response.data.data.current;
        this.totalPages = response.data.data.pages;
      } catch (error) {
           // eslint-disable-next-line no-console
        console.error('获取帖子失败:', error);

      } finally {
        this.loading = false;
      }
    },
    toDetail(newsId) {
      // 导航到新闻详情页面，应传递newsId以显示正确的新闻
      this.$router.push({ path: `postDetail/${newsId}` });
         // eslint-disable-next-line no-console
      console.log(`Navigating to news details with ID: ${newsId}`);
    },
    changePage(delta) {
      // 计算下一页
      const nextPage = this.page + delta;

      // 检查下一页是否在有效范围内
      if (nextPage > 0 && nextPage <= this.totalPages) {
        this.page = nextPage; // 更新页码
        this.fetchNews(); // 重新加载新闻列表
      }
    },
    async  AddPost() {
      // 这里编写添加的逻辑
      console.log('添加或更新Post:', this.newNews);

      try {
    // 发送POST请求到服务器
    const response = await axios.post('http://localhost:8204/yygh/posts/insertOrUpdatePost', this.newNews);

    // 处理响应
    console.log(response.data);
    // 您可以根据需要使用response.data来做进一步的处理，比如：
    // if (response.data.success) {
    //   // 成功逻辑
    // } else {
    //   // 错误逻辑
    // }
    this.fetchNews();
    // 成功后重置数据和关闭模态框
    this.newNews = { title: '', summary: '' };
    this.showAddModal = false;


  } catch (error) {
    // 处理错误
    console.error('添加或更新Post失败:', error);
    // 根据您的应用需求，您可能需要在这里实现错误处理逻辑
  }
    },
    showAddModalMethod() {
    this.$set(this, 'showAddModal', true);
  }
  }
};
</script>
<style scoped>
.news-page {
  max-width: 800px; /* 与详情页面的宽度一致 */
  margin: 40px auto; /* 上下40px间距，左右自动居中 */
  padding: 20px; /* 内部间距 */
  background: #fff; /* 白色背景 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 细致的阴影效果 */
  border-radius: 8px; /* 圆角 */
}

.news-page h1 {
  font-size: 2rem; /* 大标题字号 */
  color: #333; /* 深色字体 */
  text-align: center; /* 标题居中 */
  margin-bottom: 20px;
}

.news-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.news-item {
  border: 1px solid #eaeaea;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.news-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0,0,0,0.2);
}

.news-item h2 {
  font-size: 1.5em; /* 适中的标题字号 */
  color: #42b983; /* 与详情页面的链接颜色相同 */
  margin-bottom: 10px;
}

.news-item p {
  color: #666;
  line-height: 1.8; /* 和详情页面一致的行间距 */
}

button {
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  background-color: #42b983;
  color: white;
  cursor: pointer;
  transition: background-color 0.2s;
}

button:hover {
  background-color: #367f6b;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
  margin-top: 20px;
}

.pagination span {
  font-size: 1rem; /* 保持与新闻详情页面文本大小一致 */
  color: #333;
}

/* 如果页面有额外的空间，可以考虑添加响应式设计 */
@media (max-width: 768px) {
  .news-page {
    margin: 20px; /* 屏幕较小时减小外边距 */
  }
  .news-page h1 {
    font-size: 1.5rem; /* 屏幕较小时减小标题字号 */
  }
}

.search-box {
  margin-bottom: 20px;
  display: flex;
  gap: 10px;
}

.search-box input,
.search-box select {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.search-box button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  background-color: #42b983;
  color: white;
  cursor: pointer;
}

.search-box button:hover {
  background-color: #367f6b;
}

.floating-add-button {
  position: fixed; /* 固定位置 */
  right: 30px; /* 距离右边界30px */
  bottom: 30px; /* 距离底边界30px */
  padding: 15px 20px; /* 按钮内部边距 */
  border-radius: 50%; /* 圆形按钮 */
  background-color: #42b983; /* 按钮背景颜色 */
  color: white; /* 按钮文字颜色 */
  border: none; /* 无边框 */
  cursor: pointer; /* 鼠标悬停时指针样式 */
  font-size: 24px; /* 文字大小 */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3); /* 阴影效果 */
  z-index: 1000; /* 确保按钮位于页面上的其他元素之上 */
}

.floating-add-button:hover {
  background-color: #367f6b; /* 鼠标悬停时的背景颜色变化 */
}

/* 模态框样式 */
.modal {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2000; /* 确保模态框在所有其他元素之上 */
}

.modal-content {
  position: relative; /* 添加这个声明 */
  background: #fff;
  padding: 40px; /* 增加或调整这个值以增大模态框内容的内边距 */
  border-radius: 8px;
  width: 90%;
  max-width: 500px;
}

.close {
  position: absolute;
  right: 20px;
  top: 20px;
  font-size: 30px;
  cursor: pointer;
  color: black; /* 或者您期望的颜色 */
}

.form-group {
  margin-bottom: 20px; /* 增加或调整这个值以为每个表单组添加更多的底部外边距 */
}

.submit-button {
  /* 按钮样式 */
  background-color: #42b983;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.submit-button:hover {
  /* 鼠标悬停时的按钮样式 */
  background-color: #367f6b;
}

/* 可能需要的表单控件样式 */
input[type="text"],
textarea {
  width: 100%;
  padding: 10px;
  margin-top: 5px;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin: 10px 0; /* 增加上下外边距 */
  padding: 12px; /* 增加或调整这个值以添加更多内边距 */
}

</style>
