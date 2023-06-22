<template>
  <div>
    <!-- 弹出层设计  -->
    <el-dialog title="创建项目" :visible.sync="isShow" @closed="updateIsShow">
      <el-form ref="elForm" :model="formData" :rules="rules"
               label-position="top">
        <el-form-item label="项目名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入项目名称" clearable :style="{width: '100%'}">
          </el-input>
        </el-form-item>
        <el-form-item label="有效时间" prop="time">
          <el-date-picker type="daterange" v-model="formData.time" format="yyyy-MM-dd"
                          value-format="yyyy-MM-dd" :style="{width: '100%'}" placeholder="请选择日期选择发布时间"
                          clearable>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="所属人员" prop="usernames">
          <el-select v-model="formData.usernames" placeholder="请选择所属人员" multiple clearable
                     :style="{width: '100%'}">
            <el-option v-for="(item, index) in usernamesOptions" :key="index" :label="item.label"
                       :value="item.value" :disabled="item.disabled"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="项目权限" prop="projectPermission">
          <el-select v-model="formData.projectPermission" placeholder="请选择项目权限" clearable
                     :style="{width: '100%'}">
            <el-option v-for="(item, index) in projectPermissionOptions" :key="index" :label="item.label"
                       :value="item.value" :disabled="item.disabled"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="待办等级" prop="grade" required>
          <el-button type="primary" circle style="width: 40px;height: 40px" :icon="iconData[0]"
                     @click="updateGrade(0)"></el-button>
          <el-button type="success" circle style="width: 40px;height: 40px" :icon="iconData[1]"
                     @click="updateGrade(1)"></el-button>
          <el-button type="warning" circle style="width: 40px;height: 40px" :icon="iconData[2]"
                     @click="updateGrade(2)"></el-button>
          <el-button type="danger" circle style="width: 40px;height: 40px" :icon="iconData[3]"
                     @click="updateGrade(3)"></el-button>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="submitForm()">创建</el-button>
        <el-button @click="resetForm()">重置</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {createProject, listUser} from "@/api/manager/project";

export default {
  name: "ProjectDialog",
  props: {
    value: Boolean//使用value可以接收v-model传来的值
  },
  data() {
    return {
      isShow: this.value,
      /*创建项目表单数据*/
      formData: {
        name: '',
        time: null,//是个数组:0是发布时间，1是截止时间
        usernames: [],
        projectPermission: undefined,
        grade: null,
        publisher: ''//发布人账号
      },
      iconData: ["", "", "", ""],  //待办等级按钮图标集:默认无图标、没被选择
      rules: {
        name: [{
          required: true,
          message: '请输入项目名称',
          trigger: 'blur'
        }],
        time: [{
          required: true,
          message: '请选择日期选择发布时间',
          trigger: 'change'
        }],
        usernames: [{
          required: true,
          type: 'array',
          message: '请至少选择一个usernames',
          trigger: 'change'
        }],
        projectPermission: [{
          required: true,
          message: '请选择项目权限',
          trigger: 'change'
        }],
      },
      /*下拉框值*/
      usernamesOptions: [], //所属用户下拉框
      projectPermissionOptions: [{
        "label": "公开(不可编辑)",
        "value": "公开(不可编辑)"
      }, {
        "label": "公开(可编辑的)",
        "value": "公开(可编辑的)"
      }, {
        "label": "成员的",
        "value": "成员的"
      }
      ],  //项目权限下拉框
    }
  },
  watch: {
    value(newVal, OldVal) {
      this.isShow = newVal
    }
  },
  mounted() {//模板渲染为html之后执行
    this.listUser();
  }, methods: {
    //获取系统用户列表
    listUser() {
      let options = [];
      listUser().then(response => {
        let data = response.rows;
        console.log(data)
        for (let i = 0; i < data.length; i++) {
          let usernamesOption = {
            "label": data[i].nickName,
            "value": data[i].userName,
          }
          options.push(usernamesOption);
        }
        this.usernamesOptions = options;
      })
    },

    //只改isShow变量，不要改props
    updateIsShow() {
      this.$emit('updateValue',this.isShow);
    },

    //颜色选择按钮更新待办等级
    updateGrade(grade) {
      //更改按钮图标：被选中的图标添加值，其他的归空
      let iconDatas = ["", "", "", ""]
      iconDatas[grade] = "el-icon-check"
      this.iconData = iconDatas //注意数组与引用的关系
      //表单信息确定
      this.formData.grade = grade;
    },
    submitForm() {
      this.formData.publisher = this.$store.state.user.name
      this.$refs['elForm'].validate(valid => {
        if (valid) {
          console.log(this.formData)
          createProject(this.formData).then(res => {
            //弹出创建成功
            this.$message({
              message: res.msg,
              type: 'success'
            })
          })//提交数据，创建项目
        }
      })
      this.$refs['elForm'].resetFields();//清空表单
      this.$emit('updateValue',this.isShow);
    },

    resetForm() {
      this.$refs['elForm'].resetFields();//清空表单
    },

  }
}
</script>

<style scoped>

</style>
