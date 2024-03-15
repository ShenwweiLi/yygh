<template>
  <div class="forum-container">
    <div class="post">
      <div class="post-header">
        <h1 class="post-title">{{ newsDetail.title }}</h1>
        <div class="post-meta">
          <span class="post-author">作者：{{ newsDetail.user?.username || '匿名' }}</span>
          <span class="post-date">发布日期：{{ formatDate(newsDetail.createdDate) }}</span>
        </div>
      </div>
      <div class="post-content" v-html="newsDetail.content"></div>
    </div>
  <!-- 回复按钮 -->
  <button @click="showReplyForm = true" class="reply-button">回复</button>
  
  <!-- 回复表单 -->
  <div v-if="showReplyForm" class="reply-form-container">
    <textarea v-model="replyContent" placeholder="输入你的回复..."></textarea>
    <button @click="submitReply">提交回复</button>
  </div>


    <div class="comments-section">
      <h2>评论 ({{ commentsList.length }})</h2>
      <div v-if="commentsList.length > 0">
        <ul class="comments-list">
          <li v-for="comment in commentsList" :key="comment.id" class="comment">
            <div class="comment-author">{{ comment.username }}</div>
            <div class="comment-content">{{ comment.content }}</div>
            <div class="comment-date">{{ formatDate(comment.createdDate) }}</div>
          </li>
        </ul>
      </div>
      <div v-else>
        <p>还没有评论。</p>
      </div>
    </div>

    <router-link to="/forum" class="back-link">返回论坛列表</router-link>
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
        commentsList: [],  // 添加这一行
        showReplyForm: false, // 控制回复表单的显示
    replyContent: '', // 绑定回复内容
      };
    },
    created() {
      this.fetchNewsDetail();
      // this.fetchcomment();
    },
    methods: {
      async fetchNewsDetail() {
  try {
    this.loading = true;
    const response = await axios.get('http://localhost:8204/yygh/posts/getPostById', {
      params: {
        PostId: this.$route.params.id
      }
    });
      
      // eslint-disable-next-line no-console
      console.log(response)
    // 假设响应结构是{ data: { ... } }
    this.newsDetail = {
      title: response.data.data.posts.title,
      publishDate: response.data.data.posts.createdAt,
      content: response.data.data.posts.content,
      id : response.data.data.posts.id,
      createdDate: response.data.data.posts.createdDate,
      user: response.data.data.posts.user, // 添加用户信息
      // 可以添加其他字段
      
    };
    this.commentsList = response.data.data.commentsList; // 添加这一行
    // eslint-disable-next-line no-console
    console.log(response)
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


    

  getCookieValue(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(';').shift();
},  submitReply() {
    if (this.replyContent.trim() === '') {
      alert('回复内容不能为空！');
      return;
    }
    
    const replyData = {
      content: this.replyContent,
      postId: this.newsDetail.id,
      // 其他需要的数据
    };

    axios.post('http://localhost:8204/yygh/comments/addComment', replyData)
    .then(response => {
      console.log('回复成功:', response.data);

      // 清空回复表单，并关闭表单
      this.replyContent = '';
      this.showReplyForm = false;

      // 重新调用fetchNewsDetail来刷新页面数据，包括新闻详情和评论
      this.fetchNewsDetail();
    })
    .catch(error => {
      console.error('回复失败', error);
    });
  },

    }
  };
  </script>

<style scoped>
.forum-container {
  max-width: 800px;
  margin: auto;
  padding: 20px;
  margin-top: 50px; /* 设置与导航栏高度相同或更大的上边距 */
}

.post {
  border-bottom: 1px solid #eee;
  padding-bottom: 20px;
  margin-bottom: 20px;
}

.post-title {
  font-size: 24px;
}

.post-meta {
  display: flex;
  justify-content: space-between;
  font-size: 14px;
  color: #666;
}

.post-content {
  margin-top: 20px;
}

.comments-section {
  margin-top: 40px;
}

.comments-list {
  list-style-type: none;
  padding: 0;
}

.comment {
  border-bottom: 1px solid #eee;
  padding: 10px 0;
}

.comment-author {
  font-weight: bold;
}

.comment-content {
  margin-top: 5px;
}

.comment-date {
  font-size: 12px;
  color: #888;
}

.back-link {
  display: block;
  margin-top: 20px;
  text-align: center;
  color: #333;
  text-decoration: none;
}

.back-link:hover {
  text-decoration: underline;
}

.reply-button {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}

.reply-button:hover {
  background-color: #0056b3;
}

.reply-form-container {
  margin-top: 20px;
}

.reply-form-container textarea {
  width: 100%;
  height: 100px;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
}

.reply-form-container button {
  padding: 10px 20px;
  background-color: #28a745;
  color: white;
  border: none;
  cursor: pointer;
}

.reply-form-container button:hover {
  background-color: #218838;
}
</style>