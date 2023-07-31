<template>
  <div>
    <div class="project-container">
      <!-- type="flex"使得el-col与el-row高度一致  align="middle"使得内容居中 -->
      <el-row class="header" type="flex" align="middle">
        <el-col :span="2">
          <div class="grid-content bg-purple-dark" style="font-size: 35px;font-weight: 500">项目管理</div>
        </el-col>
        <el-col :span="20">
          <div class="grid-content bg-purple-dark"></div>
        </el-col>
        <el-col :span="2">
          <div class="grid-content bg-purple-dark">
            <el-button type="primary" icon="el-icon-circle-plus" @click="showDialog">新建项目</el-button>
          </div>
        </el-col>
      </el-row>
      <el-row type="flex" align="middle" :gutter="10" class="rowSearch">
        <el-col :span="3">
          <el-input
            placeholder="请输入项目名称"
            prefix-icon="el-icon-search"
            v-model="name">
          </el-input>
        </el-col>
        <el-col :span="1">
          显示:
        </el-col>
        <el-col :span="6" style="text-align: left">
          <el-select v-model="showProject" @change="handleChange" placeholder="请选择">
            <el-option
              key="0"
              label="已创建项目"
              value="0">
            </el-option>
            <el-option
              key="1"
              label="待处理项目"
              value="1">
            </el-option>
            <el-option
              key="2"
              label="已处理项目"
              value="2">
            </el-option>
          </el-select>
        </el-col>
      </el-row>
      <el-row type="flex" align="middle" class="table-row">
        <el-table
          :data="tableData"
          :key="Math.random()"
          :header-cell-style="{'text-align':'center',background:'#eef1f6',color:'#606266'}"
          :cell-style="{'text-align':'center'}"
          @row-click="handleRowClick"
          border
          style="width: 100%">
          <!--序号列-->
          <el-table-column
            type="index"
            width="50">
          </el-table-column>
          <!--表头的v-for中的key设置成随机数，避免出现"数据更新，列表头数据不更新"的bug-->
          <el-table-column v-for="item in tableHeader" :key="Math.random()" :prop="item.prop" :label="item.label"
                           :sortable="item.sortable" :width="item.width">
            <template slot-scope="scope">
              <!--如果是待办等级描述列则显示-->
              <el-tag v-if="item.prop === 'gradeDescribe'"
                      :type="getTag(scope.$index,scope.row)"
                      disable-transitions>{{ scope.row.gradeDescribe }}
              </el-tag>
              <!--如果不是该列则正常显示该列的标签-->
              <span v-else>{{ scope.row[item.prop] }}</span>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
      <el-row type="flex" align="middle" class="table-row">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10,100,150]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalItems"
        ></el-pagination>
      </el-row>
    </div>

    <!--两个div避免project-container居中影响弹出层表单-->
    <div>
      <ProjectDialog v-model="value" @updateValue="updateValue"></ProjectDialog>
    </div>
  </div>
</template>

<script>

import ProjectDialog from "@/components/ProjectDialog/index.vue";
import {listMyCompletedProjectsByName, listMyCreateProjectsByName, listMyProjectsByName} from "@/api/manager/project";

export default {
  name: "Project",
  components: {ProjectDialog},
  data() {
    return {
      value: false,//弹出层数据是否显示,为了实现双向绑定必须使用value
      /*操作行数据绑定*/
      name: "",  //项目名
      showProject: "0",//下拉框选择项目显示方式,默认查询我创建的
      /*表格数据和表头列都需要动态指定，由后端返回*/
      resData: [],//请求得到的全部数据
      tableData: [],//按页显示的结果
      pageSize: 10, // 默认每页显示的条数
      currentPage: 1, // 当前页码
      totalItems:0,//请求的数据总长度
      /*表头修改*/
      tableHeader0: [{
        prop: 'name',
        label: '项目名称',
        sortable: false,
        width: 300
      },
        {
        prop: 'usernames',
        label: '所属人员',
        sortable: false,
        width: 280
      },
        {
        prop: 'gradeDescribe',
        label: '待办等级',
        sortable: true,
        width: 300
      },
        {
        prop: 'publishTime',
        label: '发布时间',
        sortable: true,
        width: 250
      },
        {
        prop: 'endTime',
        label: '结束时间',
        sortable: true,
        width: 250
      },
        {
        prop: 'projectPermission',
        label: '项目权限',
        sortable: true,
        width: '',
      },
        {
        prop: 'projectStatusDescribe',
        label: '项目状态',
        sortable: true,
        width: 150
      }],
      // 设置表头数据
      tableHeader: this.tableHeader0
    }
  }, mounted() {
    this.tableHeader = this.tableHeader0
    this.showMyCreateProject()
  }, methods: {
    getTag(index, row) {
      if (row.gradeDescribe === "不重要不紧急") {
        return 'primary'
      } else if (row.gradeDescribe === "重要不紧急") {
        return 'success'
      } else if (row.gradeDescribe === "紧急不重要") {
        return 'warning'
      } else {//重要且紧急
        return 'danger'
      }
    },
    /**
     * 弹出项目创建对话框
     */
    showDialog() {
      //不使用路由，而是使用组件显示不显示来
      this.value = true;//弹窗可视化
    },
    /**
     * 进入页面时获取当前用户创建的项目
     */
    showMyCreateProject() {
      const username = this.$store.state.user.name
      listMyCreateProjectsByName(username).then(res => {
        console.log(res)
        if (res.code === 200) {//查询成功
          this.resData = res.data
          this.totalItems = res.data.length
          if (this.totalItems > this.pageSize) {
            for (let index = 0; index < this.pageSize; index++) {
              this.tableData.push(this.resData[index]);
            }
          } else {
            this.tableData = this.resData;
          }
        }
      })
    },
    /**
     * 处理下拉框选项事件：已创建项目、待处理项目、已处理项目
     * @param value
     */
    handleChange(value) {
      console.log('选中的值:', value);
      const username = this.$store.state.user.name
      switch (value) {
        case "0":
          //已创建列表：自己创建项目列表
          listMyCreateProjectsByName(username).then(res => {
            console.log(res)
            if (res.code === 200) {//查询成功
              this.resData = res.data
              this.totalItems = res.data.length
              if (this.totalItems > this.pageSize) {
                for (let index = 0; index < this.pageSize; index++) {
                  this.tableData.push(this.resData[index]);
                }
              } else {
                this.tableData = this.resData;
              }
            }
          });
          break;
        case "1":
          console.log("待处理项目")
          //待处理项目：自己为项目处理人员
          listMyProjectsByName(username).then(res => {
            console.log(res)
            if (res.code === 200) {//查询成功
              this.resData = res.data
              this.totalItems = res.data.length
              if (this.totalItems > this.pageSize) {
                for (let index = 0; index < this.pageSize; index++) {
                  this.tableData.push(this.resData[index]);
                }
              } else {
                this.tableData = this.resData;
              }
            }
          });
          break;
        case "2":
          //已处理项目
          listMyCompletedProjectsByName(username).then(res => {
            console.log(res)
            if (res.code === 200) {//查询成功
              this.resData = res.data
              this.totalItems = res.data.length
              if (this.totalItems > this.pageSize) {
                for (let index = 0; index < this.pageSize; index++) {
                  this.tableData.push(this.resData[index]);
                }
              } else {
                this.tableData = this.resData;
              }
            }
          });
          //已处理项目：自己已经处理完成项目
          break;

      }
      // 在这里添加您的事件处理逻辑
    },


    //点击待处理项目


    //子组件传来事件处理函数，修改value值使弹窗消失
    updateValue(value) {
      this.value = value
    },

    /**
     * 单击行事件：只要可进就是可编辑
     *
     * @param row
     */
    handleRowClick(row) {
      console.log(row)
      console.log("进入详情页:可编辑可查看")
      this.$router.push({name: 'Details'})
    },


    //下部分页功能方法
    // 每页显示条数改变触发
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.handleCurrentChange(1);
    },
    // 当前页改变触发
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
      // 判断是否为搜索的数据,传入对应值
      if (!this.flag) {
        this.currentChangePage(this.resData);
      } else {
        this.currentChangePage(this.filterTableData);
      }
    },
    // 根据分页，确定当前显示的数据
    currentChangePage(list) {
      let fromNum = (this.currentPage - 1) * this.pageSize;
      let toNum = this.currentPage * this.pageSize;
      this.tableData = [];
      for (; fromNum < toNum; fromNum++) {
        if (list[fromNum]) {
          this.tableData.push(list[fromNum]);
        }
      }
    },

  }
}

</script>

<style scoped>

.project-container {
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
  text-align: center;
}

/*头部行*/
.header {
  height: 80px;
}

/*查询操作行*/
.rowSearch {
  background-color: #EEEEEE;
  padding: 10px;
}

/*表格行*/
.table-row {
  margin-top: 10px;
}


</style>
