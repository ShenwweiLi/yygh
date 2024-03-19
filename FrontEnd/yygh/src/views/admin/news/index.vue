<template>
    <div class="user">
      <my-table
        :data="tableData"
        @page="userPageQuery"
        @delete="handleDelete"
        @status="handleStatus"
        @mulitOpen="handleMulitOpen"
        @mulitBan="handleMulitBan"
        @mulitDel="handleMulitDel"
      ></my-table>
    </div>
  </template>
  
  <script>
  import MyTable from "../components/Table";
  import { ArticleCols } from "../components/articleCols";
//   import { Toolbar } from "../components/Toolbar";
  import { PageInfo } from "../components/PageInfo";
//   import QS from "qs";
  
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
          `确定要将 <strong>用户ID前6位: ${data.row.id.substring(
            0,
            6
          )}, 用户名: ${
            data.row.username
          }</strong> 的用户<strong style="color:#ff6161">删除</strong>吗？`,
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
            dangerouslyUseHTMLString: true
          }
        ).then(() => {
          const user = {};
          user.id = data.row.id;
          this.$http
            .post("user/delete", [user])
            .then(() => {
              this.$message({
                message: `已将用户ID: ${data.row.id} 的用户删除`,
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
      /**
       * 批量删除用户账号
       */
      handleMulitDel(rows) {
        this.$confirm(
          `确定要将这 <strong style="color:#ff6161">${rows.length}个用户</strong> 永久删除吗？`,
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
            dangerouslyUseHTMLString: true
          }
        ).then(() => {
            this.$http
            .post("user/delete", rows)
            .then(() => {
              this.$message({
                message: `已将这 ${rows.length}个用户 删除`,
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
   /**
   * 分页查询方法
   * @param newPage - 可选参数，如果传递了新的页码则使用新的页码进行查询
   */

   
  userPageQuery(newPage) {
    console.log('Requested new page:', newPage)
    
    // 如果提供了新的页码，则更新当前页码
    if (newPage !== undefined) {
      this.tableData.pageinfo.currentPage = newPage;
    }

    // 构建分页请求的参数
    const pageParams = {
      page: this.tableData.pageinfo.currentPage,
      size: this.tableData.pageinfo.pageSize
    };
    
    console.log(typeof this.tableData.pageinfo.currentPage); // 应该打印 "number"
    // 发起请求
    this.$http.get("articleList", { params: pageParams }).then(res => {
      // 假设响应的结构是 { data: { articleList: [], total: Number, current: Number, pages: Number } }
      const data = res.data;
      this.tableData.rowData = data.articleList;
      this.tableData.pageinfo.total = data.total;
      this.tableData.pageinfo.currentPage = data.current;
      this.tableData.pageinfo.pages = data.pages;
    }).catch(error => {
      console.error('Error fetching articles:', error);
      this.$message.error('获取文章列表失败');
    });
  },
    },
  
    mounted() {
      this.userPageQuery();
    }
  };
  </script>
  