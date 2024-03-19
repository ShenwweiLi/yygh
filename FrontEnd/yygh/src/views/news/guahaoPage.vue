<!-- NewsPage.vue -->
<template>
  <div class="news-page">
    <div class="search-box">
      <input type="text" v-model="search" placeholder="按名称搜索" />

      <button @click="fetchNews">搜索</button>
    </div>
    <div v-if="loading">
      正在加载列表...
    </div>
    <div v-else class="news-list">
      <table>
        <thead>
          <tr>
            <th>姓名</th>
            <th>科室</th>
            <th>职称</th>
            <th>性别</th>
            <th>手机</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(doctor, index) in newsList" :key="index">
            <td>{{ doctor.yishengxingming }}</td>
            <td>{{ doctor.keshi }}</td>
            <td>{{ doctor.zhicheng }}</td>
            <td>{{ doctor.xingbie }}</td>
            <td>{{ doctor.shouji }}</td>
            <td>
              <button @click="openModalWithDoctorInfo(doctor)">预约</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="pagination">
        <button @click="changePage(-1)" :disabled="page <= 1">上一页</button>
        <span>当前页：{{ page }}</span>
        <button @click="changePage(1)" :disabled="page >= totalPages">下一页</button>
      </div>
    </div>
    <div v-if="isModalVisible" class="modal" @click.self="closeModal()">
      <div class="modal-content">
        <span class="close" @click="closeModal()">&times;</span>
        <h3>预约信息填写</h3>
        <form @submit.prevent="submitAppointment">
          <!-- 表单内容，例如姓名、时间等 -->
          <!-- 显示医生姓名 -->
          <div class="form-group">
            <label for="yishengxingming">医生姓名：</label>
            <span>{{ appointmentForm.yishengxingming }}</span>
          </div>

          <!-- 显示医生科室 -->
          <div class="form-group">
            <label for="yishengkeshi">医生科室：</label>
            <span>{{ appointmentForm.yishengkeshi }}</span>
          </div>
          <div class="form-group">
            <label for="guahaofeiyong">挂号费用：</label>
            <span id="guahaofeiyong">{{ appointmentForm.guahaofeiyong }}￥</span>
          </div>
          <div class="form-group">
            <label for="guahaoshijian">挂号时间：</label>
            <input type="datetime-local" id="guahaoshijian" v-model="appointmentForm.guahaoshijian" required>
          </div>
          <div class="form-group">
            <label for="beizhu">备注：</label>
            <textarea id="beizhu" v-model="appointmentForm.beizhu"></textarea>
          </div>
          <div class="form-group">
            <label for="xingming">姓名：</label>
            <input type="text" id="xingming" v-model="appointmentForm.xingming" required>
          </div>
          <div class="submit-button">
            <button type="submit">提交预约</button>
          </div>

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
      page: 1,
      size: 5,
      totalPages: 0,
      search: '',
      newsList: [],
      isModalVisible: false,
      selectedDoctorId: null,
      appointmentForm: {
        yishengxingming: '',
        yishengkeshi: '',
        guahaofeiyong: 22,
        guahaoshijian: null,
        beizhu: null,
        xingming: null,
        yishenggonghao: null,
      },
    };
  },
  created() {
    this.fetchNews();
  },
  methods: {
    async fetchNews() {
      const params = {
        currentPage: this.page,
        pageSize: this.size,
        search: this.search,
      };
      try {
        this.loading = true;
        const response = await axios.get('/doctor/doctorList', { params });
        console.log('获取列表:', response);
        this.newsList = response.data.data.doctorList;
        this.page = response.data.data.current;
        this.totalPages = response.data.data.pages;
      } catch (error) {
        console.error('获取列表失败:', error);
      } finally {
        this.loading = false;
      }
    },

    toDetail(newsId) {
      this.$router.push({ path: `postDetail/${newsId}` });
      console.log(`Navigating to news details with ID: ${newsId}`);
    },

    changePage(delta) {
      const nextPage = this.page + delta;
      if (nextPage > 0 && nextPage <= this.totalPages) {
        this.page = nextPage;
        this.fetchNews();
      }
    },

    openModal(doctorId) {
      this.selectedDoctorId = doctorId;
      this.isModalVisible = true;
    },

    closeModal() {
      this.isModalVisible = false;
    },

    async submitAppointment() {
  // 首先格式化日期时间
  if (this.appointmentForm.guahaoshijian) {
    this.appointmentForm.yishenggonghao = this.selectedDoctorId;
    try {
      const formattedDateTime = this.formatDateTimeLocalToBackend(this.appointmentForm.guahaoshijian);
      // 将格式化后的时间设置到表单数据中
      this.appointmentForm.guahaoshijian = formattedDateTime;
    } catch (error) {
      console.error('日期时间格式化失败:', error);
      // 适当处理错误
      return;
    }
  } else {
    console.error('挂号时间为空或无效');
    // 适当处理错误
    return;
  }

  // 设置医生工号
  this.appointmentForm.yishenggonghao = this.selectedDoctorId;

  // 提交表单到后端
  try {
    const response = await axios.post('http://localhost:8204/yygh/yuyueguahao/saveOrUpdateGuaHao', this.appointmentForm);
    console.log('服务器响应:', response.data);
    if (response.data.code ==="200") {
      this.resetAppointmentForm();
      this.isModalVisible = false;
    } else {
      console.error('预约失败:', response.data.message);
    }
  } catch (error) {
    console.error('请求过程中发生错误:', error);
  }
},

    resetAppointmentForm() {
      this.appointmentForm = {
        yishengxingming: '',
        yishengkeshi: '',
        guahaofeiyong: 22,
        guahaoshijian: null,
        beizhu: null,
        xingming: null,
        yishenggonghao: null,
      };
    },

    openModalWithDoctorInfo(doctor) {
  // 重置表单以清除之前的数据
  this.resetAppointmentForm();
  
  // 设置选中的医生信息到表单
  this.appointmentForm.yishengxingming = doctor.yishengxingming;
  this.appointmentForm.yishengkeshi = doctor.keshi;
  this.appointmentForm.yishenggonghao = doctor.yishenggonghao; // 确保这里使用的是正确的字段

  this.selectedDoctorId = doctor.id; // 正确设置selectedDoctorId

  this.isModalVisible = true;
    },

    formatDateTimeLocalToBackend(dateTime) {
      // 创建一个新的Date对象
      const date = new Date(dateTime);

      // 检查日期是否合法
      if (isNaN(date.getTime())) {
        throw new Error('Invalid date');
      }

      // 转换为本地时间格式
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const day = date.getDate().toString().padStart(2, '0');
      const hours = date.getHours().toString().padStart(2, '0');
      const minutes = date.getMinutes().toString().padStart(2, '0');

      // 返回格式化的日期时间字符串
      return `${year}-${month}-${day} ${hours}:${minutes}:00`; // 添加了秒
    },
    // submitForm() {
    //   // 在进行任何提交操作前，先格式化日期时间
    //   const formattedDateTime = this.formatDateTimeLocalToBackend(this.appointmentForm.guahaoshijian);

    //   // 使用转换后的日期时间来更新预约表单数据
    //   const formData = {
    //     ...this.appointmentForm,
    //     guahaoshijian: formattedDateTime
    //   };

    //   // 调用 sendDataToBackend 方法并传入格式化后的表单数据
    //   this.sendDataToBackend(formData);
    // },

  },
  
};


</script>

<style scoped>
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
</style>
