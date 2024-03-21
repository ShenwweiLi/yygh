    <template>
        <div class="user">
            <my-table :data="tableData" @page="userPageQuery" @delete="handleDelete" @mulitDel="handleMulitDel">
            </my-table>
        </div>
    </template>

<script>
import MyTable from "../components/Table";
import { guahaoCols } from "../components/guahaoCols";
//   import { Toolbar } from "../components/Toolbar";
import { PageInfo } from "../components/PageInfo";
//   import QS from "qs";

export default {
    data() {
        return {
            tableData: {
                cols: guahaoCols,
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
                    data: [
                        { label: "删除", type: "danger", emit: "delete" },
                    ]
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

        setApproveButtonDisabled(row) {
            // 假设每行数据有一个字段 `isReviewed` 表示是否审核
            if(row.isReviewed === '是') {
                return true;
            } else {
                return false;
            }
        },

        userPageQuery() {
            const params = {
                currentPage: this.tableData.pageinfo.currentPage,
                pageSize: this.tableData.pageinfo.pageSize,
                search: this.tableData.searchFilterConfig.search || '', // 根据您的Vue data结构可能需要调整获取搜索关键词的方式
            };
            this.$http.get("yuyueguahao/guahaoList", { params: params }).then(res => {
                console.log(res);
                const data = res.data;
                console.log(data.yueGuaHaoList);
                console.log(data.total);
                console.log(res.code);
                if (data && res.code === "200") {
                    // 假设您的YuYueGuaHaoListVo具有与之前类似的结构
                    this.tableData.rowData = data.yueGuaHaoList; // 假设返回的对象具有yuYueGuaHaoItems属性
                    this.tableData.pageinfo.total = data.total; // 更新总条数
                    this.tableData.pageinfo.currentPage = data.currentPage; // 更新当前页码
                    this.tableData.pageinfo.pages = data.pages; // 更新总页数
                } else {
                    this.$message({
                        message: data.msg || "获取数据失败",
                        type: "error"
                    });
                }
            }).catch(error => {
                console.log(error);
                this.$message({
                    message: "请求失败",
                    type: "error"
                });
            });
        }

    },

    mounted() {
        this.userPageQuery();
    }
};
</script>