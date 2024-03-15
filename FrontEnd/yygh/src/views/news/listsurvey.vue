<template>
  <div class="container">
    <div class="survey">
      <my-table :data="tableData" @page="surveyPageQuery" @check="handleCheck" @delete="handleDelete"
        @visibility="handleVisibility" @mulitOpen="handleMulitOpen" @mulitBan="handleMulitBan"
        @mulitDel="handleMulitDel"></my-table>
    </div>
  </div>
</template>
<style>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
</style>

<script>
import axios from 'axios';
import MyTable from "../admin/components/Table.vue";
import { PageInfo } from "../admin/components/PageInfo";
import { SurveyCols } from "../admin/components/SurveyCols copy";
import QS from "qs";
export default {
  data() {
    return {
      tableData: {
        cols: SurveyCols,
        rowData: [],
        pageinfo: PageInfo,
        searchFilterConfig: {
          isSearch: true,
          placeholder: "搜索ID、问卷标题、简介",
          filters: ["id", "title", "introduction"]
        },
        isOperation: true,
        operation: {
          label: "操作选项",
          width: "185",
          data: [
            { label: "查看", type: "primary", emit: "check" },

          ]
        }
      }
    };
  },

  components: { MyTable },
  methods: {
    // 格式化日期的函数，根据实际需求使用合适的日期库或编写逻辑
    formatDate(dateString) {
      const date = new Date(dateString);
      return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
    },
    // 页面跳转的方法
    goToSurvey(id) {
      // 根据实际的路由设置进行跳转，此处仅为示例
      this.$router.push(`/survey/${id}`);
    },
    // 获取问卷数据的方法
    // fetchSurveyList() {
    //   // 此处应该是一个API调用，这里只是一个示例
    //     axios.post('survey/manage/list').then(response => {
    //     this.surveyList = response.data.surveyList;
    //    });



    //   // 直接使用提供的数据格式模拟
    //   this.surveyList = [
    //     // ...您的问卷数据
    //   ];
    //   this.count = 24; // 假设总数
    // }
    surveyPageQuery() {
      const page = {};
      page.page = this.tableData.pageinfo.currentPage;
      page.size = this.tableData.pageinfo.pageSize;
      axios.post('survey/list', QS.stringify(page)).then(res => {
        // eslint-disable-next-line no-console
        console.log(res.data.data.surveyList.records);
        this.tableData.pageinfo.total = res.data.data.surveyList.records;
        this.tableData.rowData = res.data.data.surveyList.records;
      });
    },

    handleCheck(data) {
      window.open(`http://localhost:8080/#/answer/pc/?surveyId=${data.row.id}`);
    },
  },
  mounted() {
    this.surveyPageQuery();
  }
};
</script>
