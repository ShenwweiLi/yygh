<template>
  <div class="article-container">
    <div class="article-header">
      <h1 class="article-title">{{ newsDetail.title }}</h1>
      <div class="article-meta">
        <span class="author">作者：{{ newsDetail.user?.username || '未知' }}</span>
        <!-- 使用过滤器或计算属性来格式化日期 -->
        <span class="publish-date">发布日期：{{ formatDate(newsDetail.createdAt) }}</span>
      </div>
      <div v-if="newsDetail.coverImage" class="article-image-container">
        <img :src="newsDetail.coverImage" class="article-image">
      </div>
    </div>
    

    <div class="article-interactions">
      <button class="favorite-button" @click="toggleFavorite">
       <span class="star">{{ isFavorite ? '★' : '☆' }}</span>
       {{ isFavorite ? '取消收藏' : '收藏' }} <!-- 更新这里的文本显示 -->
      </button>
      <div class="share-buttons">分享到: <a href="#">Facebook</a> | <a href="#">Twitter</a> | <a href="#">LinkedIn</a></div>
    </div>
    <div class="article-content" v-html="newsDetail.content"></div>


    <router-link to="/news" class="back-link">返回新闻列表</router-link>
  </div>
</template>

  
  <script>
  import axios from 'axios';
  export default {
    
    name: 'NewsDetail',

    data() {
      return {
        // 假设这是从API获取的新闻详情对象
        // 实际应用中，应从API动态获取新闻详情
        newsDetail: {
          title: '新闻标题',
          publishDate: '2023-04-01',
          content: '<p>这里是新闻的详细内容...</p><p>更多内容...</p>'
        },
        isFavorite: false, // 收藏状态，默认不收藏
      };
    },
    created() {
      this.fetchNewsDetail();
   
    },
    methods: {
      async fetchNewsDetail() {
  try {
    this.loading = true;
    const response = await axios.get('http://localhost:8204/yygh/selectArticleById', {
      params: {
        ArticleId: this.$route.params.id
      }
    });
    // 假设响应结构是{ data: { ... } }
    this.newsDetail = {
      title: response.data.data.title,
      publishDate: response.data.data.createdAt,
      content: response.data.data.content,
      id : response.data.data.id,
      createdAt: response.data.data.createdAt,
      user: response.data.data.user, // 添加用户信息
      // 可以添加其他字段
    };
    // eslint-disable-next-line no-console
    console.log(this.newsDetail.title)
    this.TestFavorite();
  } catch (error) {
    // eslint-disable-next-line no-console
    console.error('获取新闻失败:', error);
  } finally {
    this.loading = false;
  }
},

formatDate(dateString) {
    if (!dateString) return '日期未知';
    // 使用JavaScript的Date对象来格式化日期，也可以使用moment.js等库
    const date = new Date(dateString);
    return date.toLocaleDateString("zh-CN"); // 格式化日期，例如："2023年4月1日"
  },

toggleFavorite() {
   // 预先切换收藏状态
  const newFavoriteState = !this.isFavorite;

// 根据预先切换的收藏状态决定要调用的API
const apiUrl = newFavoriteState 
  ? `http://localhost:8204/yygh/addFavorite` 
  : `http://localhost:8204/yygh/removeFavorite`;

// 构建要发送的Favorite对象
const favoriteData = {
  articleId: this.newsDetail.id, // 新闻详情对象的id
  userId: this.getCookieValue("SURVEY_UID")
};

// 发送请求更新收藏状态
axios.post(apiUrl, favoriteData)
  .then(response => {
    if (response.data.code === "200") {
      // 如果响应码为 "200"，则更新成功，现在可以更新按钮状态
      this.isFavorite = newFavoriteState;
      console.log('Favorite status updated successfully.');
    } else {
      // 如果操作失败，不要切换按钮状态
      console.error('Failed to update favorite status.', response.data);
    }
  })
  .catch(error => {
    // 如果出现错误，不要切换按钮状态
    console.error('Error occurred while updating favorite status:', error);
  });
},
    
    async TestFavorite() {
      const favoriteData = {
        articleId:this.newsDetail.id, // 假设这是从API获取的新闻详情对象的id
        userId: this.getCookieValue("SURVEY_UID")
      };  
      console.log(this.newsDetail);
  
      const apiUrl = `http://localhost:8204/yygh/selectFavorite`;
      axios.post(apiUrl, favoriteData)
        .then(response => { 
          if (response.data.code === "200") {
              // eslint-disable-next-line no-console
           this.isFavorite = response.data.data; // 根据API返回的数据更新收藏状态
          } else {
              // eslint-disable-next-line no-console
            console.error('Failed to update favorite status.', response.data);
          }
        })
        .catch(error => {
            // eslint-disable-next-line no-console
          console.error('Error occurred while updating favorite status:', error);
          // 出错时重新切换回原来的状态
          this.isFavorite = !this.isFavorite;
        });
    },

         getCookieValue(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(';').shift();
}

    }
  };
  </script>

<style scoped>
.article-container {
  max-width: 800px;
  margin: 40px auto;
  padding: 20px;
  background: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}

.article-header {
  margin-bottom: 30px;
}

.article-title {
  font-size: 2.5rem;
  color: #333;
  margin-bottom: 15px;
}

.article-meta {
  font-size: 0.85rem;
  color: #777;
  margin-bottom: 20px;
}

.article-meta .author,
.article-meta .publish-date {
  display: block;
}

.article-image-container {
  margin-bottom: 20px;
}

.article-image {
  width: 100%;
  border-radius: 4px;
}

.article-content {
  margin-bottom: 30px;
  font-size: 1rem;
  color: #333;
  line-height: 1.8;
}

.article-content p {
  margin-bottom: 15px;
}

.article-interactions {
  margin-bottom: 20px;
}

.favorite-button {
  border: 1px solid #42b983;
  padding: 8px 12px;
  border-radius: 4px;
  background-color: #fff;
  color: #42b983;
  cursor: pointer;
  font-weight: bold;
  transition: all 0.3s;
  margin-top: 10px;
}

.favorite-button:hover,
.favorite-button:focus {
  background-color: #42b983;
  color: white;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.favorite-button.is-favorite {
  background-color: #ff6868;
  color: white;
}

.favorite-button.is-favorite:hover,
.favorite-button.is-favorite:focus {
  background-color: #ff4747;
}

.share-buttons {
  font-size: 0.9rem;
  color: #555;
  margin-top: 10px;
}

.share-buttons a {
  color: #007bff;
  text-decoration: none;
  margin-right: 10px;
}

.share-buttons a:hover {
  text-decoration: underline;
}

.article-comments,
.related-articles {
  margin-top: 40px;
}

.back-link {
  display: inline-block;
  margin-top: 20px;
  padding: 10px 15px;
  text-decoration: none;
  color: #fff;
  background-color: #007bff;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.back-link:hover {
  background-color: #0056b3;
}

.favorite-button .star {
  margin-right: 5px;
}

/* 可以使用第三方库或 CDN 来引入图标 */
.share-buttons a:before {
  content: '🔗'; /* 可以替换为相应的图标字符或图片 */
  margin-right: 5px;
}
</style>