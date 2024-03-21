<template>
  <div class="user">
    <my-table :data="tableData" @page="userPageQuery" @delete="handleDelete" @status="handleStatus"
      @mulitOpen="handleMulitOpen" @mulitBan="handleMulitBan" @mulitDel="handleMulitDel"></my-table>
    <div v-if="showModal" class="modal-overlay" @click.self="showModal = false">
      <div v-if="showModal" class="modal-overlay" @click.self="showModal = false">
        <div class="modal">
          <div class="modal-header">
            <span class="close" @click="showModal = false">&times;</span>
            <h2>新增条目</h2>
          </div>
          <div class="modal-body">
            <form @submit.prevent="addNewItem">
              <div class="form-group">
                <label for="title">标题</label>
                <input type="text" id="title" v-model="newItem.title" required>
              </div>
              <div class="form-group">
                <label for="content">内容</label>
                <textarea id="content" v-model="newItem.content" required></textarea>
              </div>
              <div class="form-group">
                <label for="category">类别</label>
                <select id="category" v-model="newItem.category" required>
                  <option value="">请选择类别</option>
                  <option value="1">新闻</option> <!-- 实际传给后端的值是 1 -->
                  <option value="2">教程</option> <!-- 实际传给后端的值是 -->
                  <option value="3">博客</option> <!-- 实际传给后端的值是 3 -->
                </select>
              </div>
              <div class="form-group">
                <label for="summary">概述</label>
                <input type="text" id="summary" v-model="newItem.summary" required>
              </div>
              <div class="form-group">
                <button type="submit">提交</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>


    <button @click="showAddItemModal" class="floating-add-button">+</button>


  </div>

</template>

<script>
import MyTable from "../components/Table";
import { ArticleCols } from "../components/articleCols";
//   import { Toolbar } from "../components/Toolbar";
import { PageInfo } from "../components/PageInfo";
import QS from "qs";

export default {
  data() {
    return {
      tableData: {
        cols: ArticleCols,
        rowData: [],
        pageinfo: PageInfo,
        //   toolbar: Toolbar,
        searchFilterConfig: {
          isSearch: true,
          placeholder: "搜索ID、用户名",
          filters: ["id", "username"]
        },
        isOperation: true,
        operation: {
          label: "操作选项",
          width: "",
          data: [{ label: "删除", type: "danger", emit: "delete" }]
        }
      },
      showModal: false, // 控制模态框是否显示
      newItem: { // 新增条目的数据
      title: '',
      content: '',
      category: '', // 类别将根据选择设置为相应的数字
      summary: '' // 新增字段：概述
    }


    };
  },

  components: { MyTable },

  methods: {
    /**
     * 删除单个记录
     */
    handleDelete(data) {
      this.$confirm(
        `确定要将名为 <strong>: ${data.row.title}</strong>的文章 <strong style="color:#ff6161">删除</strong>吗？`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
          dangerouslyUseHTMLString: true
        }
      ).then(() => {
        // 修改这里，创建一个对象而不是数组
        const article = {
          id: data.row.id
        };

        // 发送POST请求，请求体是一个对象，不是数组
        this.$http
          .post("delArticle", article)
          .then(() => {
            this.$message({
              message: `已将文章: ${data.row.title} 删除`,
              type: "success"
            });
            this.userPageQuery();
          })
          .catch(() => {
            this.$message({
              message: "操作失败",
              type: "error"
            });
          });
      });
    },


    userPageQuery() {
      const page = {};
      page.page = this.tableData.pageinfo.currentPage;
      page.size = this.tableData.pageinfo.pageSize;
      this.$http.get("articleList", { pageSize: 100 }, QS.stringify(page)).then(res => {
        const data = res.data;
        this.tableData.rowData = data.articleList;
        this.tableData.pageinfo.total = data.total;
        this.tableData.pageinfo.currentPage = data.current;
        this.tableData.pageinfo.pages = data.pages;
      });
    },
    addNewItem() {
  // 确保POST请求路径与后端定义的接口路径一致
  this.$http.post('/addArticle', this.newItem)
    .then(response => {
      console.log(response)
      // 处理成功的响应
      this.$message({
        message: '新增成功',
        type: 'success'
      });
      this.showModal = false; // 关闭模态框
      this.userPageQuery(); // 可能需要重新查询列表以显示新添加的条目
    })
    .catch(error => {
      console.log(error)
      // 处理错误的响应
      this.$message({
        message: '新增失败',
        type: 'error'
      });
    });
},
    showAddItemModal() {
      this.showModal = true; // 显示模态框
    },
  },

  mounted() {
    this.userPageQuery();
  }
};
</script>

<style scoped>
/* ...已有样式... */
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

.modal-overlay {
  position: fixed;
  /* 固定定位，覆盖整个屏幕 */
  top: 0;
  left: 0;
  width: 100%;
  /* 覆盖整个宽度 */
  height: 100%;
  /* 覆盖整个高度 */
  background-color: rgba(0, 0, 0, 0.5);
  /* 半透明背景 */
  display: flex;
  /* 使用Flexbox布局 */
  justify-content: center;
  /* 水平居中 */
  align-items: center;
  /* 垂直居中 */
  z-index: 1000;
  /* 确保在其他内容之上 */
}

.modal {
  background-color: #ffffff;
  /* 模态框的背景色 */
  padding: 20px;
  border-radius: 10px;
  /* 边框圆角 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  /* 阴影效果 */
  width: auto;
  /* 宽度由内容决定 */
  min-width: 40%;
  /* 最小宽度 */
  max-width: 600px;
  /* 最大宽度 */
  z-index: 10000;
  /* 确保模态框高于遮罩层 */
}


.modal-overlay {
  position: fixed;
  /* 固定位置 */
  top: 0;
  left: 0;
  width: 100vw;
  /* 100% 的视窗宽度 */
  height: 100vh;
  /* 100% 的视窗高度 */
  background-color: rgba(0, 0, 0, 0.6);
  /* 半透明遮罩层 */
  display: flex;
  /* 使用Flexbox进行居中 */
  justify-content: center;
  /* 水平居中 */
  align-items: center;
  /* 垂直居中 */
  z-index: 9999;
  /* 高层级以确保在最顶层 */
}

.modal {
  background-color: #ffffff;
  /* 模态框的背景色 */
  padding: 20px;
  border-radius: 10px;
  /* 边框圆角 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  /* 阴影效果 */
  width: auto;
  /* 宽度由内容决定 */
  min-width: 40%;
  /* 最小宽度 */
  max-width: 600px;
  /* 最大宽度 */
  z-index: 10000;
  /* 确保模态框高于遮罩层 */
}

.modal-header {
  display: flex;
  /* 使用Flexbox进行布局 */
  justify-content: space-between;
  /* 两端对齐 */
  align-items: center;
  margin-bottom: 20px;
  /* 与模态框主体内容的间距 */
}

.modal-header .close {
  font-size: 1.5rem;
  /* 关闭按钮的大小 */
  cursor: pointer;
  /* 鼠标悬浮时的样式 */
}

.modal-header .close:hover {
  color: #ff6161;
  /* 鼠标悬浮时的颜色变化 */
}

.modal-body {
  margin-bottom: 20px;
  /* 与按钮部分的间距 */
}

.modal-body .form-group {
  margin-bottom: 10px;
  /* 表单内部元素间的间距 */
}

.modal-body .form-group label {
  display: block;
  /* 标签独占一行 */
  margin-bottom: 5px;
  /* 与输入框的间距 */
}

.modal-body .form-group input[type="text"],
.modal-body .form-group textarea {
  width: 100%;
  /* 充满容器宽度 */
  padding: 10px;
  border: 1px solid #cccccc;
  /* 边框 */
  border-radius: 5px;
  /* 边框圆角 */
  box-sizing: border-box;
  /* 盒子模型设置 */
}

.form-group button[type="submit"] {
  background-color: #42b983;
  /* 提交按钮背景色 */
  color: white;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  /* 过渡动画 */
}

.form-group button[type="submit"]:hover {
  background-color: #367f6b;
  /* 鼠标悬浮时背景色变化 */
}

.modal-body .form-group select {
  width: 100%;
  /* 充满容器宽度 */
  padding: 10px;
  border: 1px solid #cccccc;
  /* 边框 */
  border-radius: 5px;
  /* 边框圆角 */
  box-sizing: border-box;
  /* 盒子模型设置 */
}
</style>