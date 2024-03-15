<!-- NewsPage.vue -->
<template>
  <div class="news-page">
    <div class="search-box">
      <input type="text" v-model="search" placeholder="按名称搜索"/>
      <select v-model="category">
        <option value="">全部分类</option>
        <option v-for="type in categories" :key="type.id" :value="type.id">{{ type.typeName }}</option>
      </select>

      <button @click="fetchNews">搜索</button>
    </div>
    <div v-if="loading">
      正在加载新闻...
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
      newsList: [], // 初始为空
      categories: [], // 用于存储分类信息
      search: '', // 添加这行代码
      category: null, // 初始值设置为null
    };
  },
  created() {
    this.fetchNews();
      this.fetchTypes(); // 获取分类列表
  },
  methods: {
    async fetchNews() {

    console.log("搜索词:", this.search, "分类ID:", this.category);
    const params={
            currentPage: this.page, // 注意这里传递的是当前页码
            pageSize: this.size
          };
      if (this.search) {
          params.search = this.search;
        }
        if (this.category !== null) { // 只有当category不为null时才添加到参数中
           params.category = this.category;
          }
      try {
        this.loading = true;
        const response = await axios.get('/articleList', {
       params
        });
        this.newsList = response.data.data.articleList;
        // 更新当前页码和总页数
        this.page = response.data.data.current;
        this.totalPages = response.data.data.pages;
      } catch (error) {
           // eslint-disable-next-line no-console
        console.error('获取新闻失败:', error);
      } finally {
        this.loading = false;
      }
    },
    toDetail(newsId) {
      // 导航到新闻详情页面，应传递newsId以显示正确的新闻
      this.$router.push({ path: `detail/${newsId}` });
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
    async fetchTypes() {
    try {
      const response = await axios.get('/type/typeList');
      if (response.data && response.data.code === "200") {
        this.categories = response.data.data.map(type => ({
          id: type.id, 
          typeName: type.typeName
        }));
      } else {
        // 处理错误，可以是设置错误消息或者其他逻辑
        console.error('获取类别列表失败', response);
      }
    } catch (error) {
      console.error('获取类别列表出错:', error);
    }
  },
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

</style>
