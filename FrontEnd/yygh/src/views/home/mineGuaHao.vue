<template>
    <div class="appointments-page">
      <div v-if="loading">
        正在加载预约信息...
      </div>
      <div v-else class="appointments-list">
        <table>
          <thead>
            <tr>
              <th>医生姓名</th>
              <th>挂号费用</th>
              <th>挂号时间</th>
              <th>备注</th>
              <th>是否审核</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(appointment, index) in appointmentsList" :key="index">
              <td>{{ appointment.yishengxingming }}</td>
              <td>{{ appointment.guahaofeiyong }}</td>
              <td>{{ appointment.guahaoshijian }}</td>
              <td>{{ appointment.beizhu }}</td>
              <td>{{ appointment.sfsh }}</td>
              <td>
                <button @click="auditAppointment(appointment)" :disabled="appointment.sfsh === '是'">审核</button>
    </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    name: 'AppointmentsPage',
    data() {
      return {
        loading: true,
        appointmentsList: []
      };
    },
    created() {
      this.fetchAppointments();
    },
    methods: {
        async fetchAppointments() {
  // 假设已经存在某种方法来获取当前登录用户的信息
  try {
    this.loading = true;
    const response = await axios.get(`http://localhost:8204/yygh/yuyueguahao/guahaoListWithDoctor`);
    console.log('获取我的预约列表:', response);
    if (response.data.code === "200") {
      // 注意这里，我们需要将 yueGuaHaoList 赋值给 appointmentsList
      this.appointmentsList = response.data.data.yueGuaHaoList;
    } else {
      console.error('获取预约列表失败:', response.data.msg);
    }
  } catch (error) {
    console.error('获取预约列表失败:', error);
  } finally {
    this.loading = false;
  }
},

      getCurrentUserId() {
        // 实现获取当前用户ID的逻辑
        // 这里用一个假的用户ID作为示例
        return 'current-user-id';
      },
      async auditAppointment(appointment) {
      // 这里插入调用审核服务的逻辑
      // 例如，发送一个请求到您的服务器，传递appointment的数据，如ID等
      // 
      console.log(appointment.id)
      try {
        const response = await axios.post('http://localhost:8204/yygh/yuyueguahao/saveOrUpdateGuaHao', {
          id: appointment.id,
          sfsh:"是"
        });
        if(response.data.code === "200") {
          alert('审核成功');
          // 在这里您也可以刷新预约列表来展示更新后的审核状态
          await this.fetchAppointments()
        } else {
          alert('审核失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('审核请求失败:', error);
        alert('审核请求失败');
      }
    }  
    },
};
  </script>
  
  <style scoped>
  /* 可以复用您之前的样式，并根据需要进行调整 */
  .appointments-page {
    max-width: 800px;
    margin: 40px auto;
    padding: 20px;
    background: #fff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
  }

  .news-page {
  max-width: 800px;
  /* 与详情页面的宽度一致 */
  margin: 40px auto;
  /* 上下40px间距，左右自动居中 */
  padding: 20px;
  /* 内部间距 */
  background: #fff;
  /* 白色背景 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  /* 细致的阴影效果 */
  border-radius: 8px;
  /* 圆角 */
}

.news-page h1 {
  font-size: 2rem;
  /* 大标题字号 */
  color: #333;
  /* 深色字体 */
  text-align: center;
  /* 标题居中 */
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
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.news-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

.news-item h2 {
  font-size: 1.5em;
  /* 适中的标题字号 */
  color: #42b983;
  /* 与详情页面的链接颜色相同 */
  margin-bottom: 10px;
}

.news-item p {
  color: #666;
  line-height: 1.8;
  /* 和详情页面一致的行间距 */
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
  font-size: 1rem;
  /* 保持与新闻详情页面文本大小一致 */
  color: #333;
}

/* 如果页面有额外的空间，可以考虑添加响应式设计 */
@media (max-width: 768px) {
  .news-page {
    margin: 20px;
    /* 屏幕较小时减小外边距 */
  }

  .news-page h1 {
    font-size: 1.5rem;
    /* 屏幕较小时减小标题字号 */
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
  position: fixed;
  /* 固定位置 */
  right: 30px;
  /* 距离右边界30px */
  bottom: 30px;
  /* 距离底边界30px */
  padding: 15px 20px;
  /* 按钮内部边距 */
  border-radius: 50%;
  /* 圆形按钮 */
  background-color: #42b983;
  /* 按钮背景颜色 */
  color: white;
  /* 按钮文字颜色 */
  border: none;
  /* 无边框 */
  cursor: pointer;
  /* 鼠标悬停时指针样式 */
  font-size: 24px;
  /* 文字大小 */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
  /* 阴影效果 */
  z-index: 1000;
  /* 确保按钮位于页面上的其他元素之上 */
}

.floating-add-button:hover {
  background-color: #367f6b;
  /* 鼠标悬停时的背景颜色变化 */
}

/* 模态框样式 */
.modal {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2000;
  /* 确保模态框在所有其他元素之上 */
}

.modal-content {
  position: relative;
  /* 添加这个声明 */
  background: #fff;
  padding: 40px;
  /* 增加或调整这个值以增大模态框内容的内边距 */
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
  color: black;
  /* 或者您期望的颜色 */
}

.form-group {
  margin-bottom: 20px;
  /* 增加或调整这个值以为每个表单组添加更多的底部外边距 */
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
  margin: 10px 0;
  /* 增加上下外边距 */
  padding: 12px;
  /* 增加或调整这个值以添加更多内边距 */
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th,
td {
  padding: 10px;
  border: 1px solid #ddd;
  text-align: left;
}

th {
  background-color: #f4f4f4;
}

tr:nth-child(even) {
  background-color: #f9f9f9;
}
  
  /* 可以添加或调整更多样式 */

  button {
  padding: 5px 10px;
  margin-top: 3px;
  background-color: #5cb85c;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #4cae4c;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

  </style>
  