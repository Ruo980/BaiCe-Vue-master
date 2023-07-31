<template>
  <div class="card-container">
    <div class="card" v-for="card in cards" :key="card.id">
      <div class="header">
        <h2>{{ card.title }}</h2>
        <p>{{ card.description }}</p>
      </div>
      <div class="body">
        <ul>
          <li v-for="item in card.items" :key="item.id">
            {{ item.text }}
            <i :class="item.iconClass"></i>
          </li>
        </ul>
      </div>
      <div class="footer">
        <el-button type="primary">编辑</el-button>
      </div>
    </div>
    <!--"添加"卡片-->
    <div class="card special-card">
      <i class="el-icon-plus"  style="color: lightgray; font-size: 100px; transform: scale(2);" @click="showDialog"></i>
    </div>
    <el-dialog :visible.sync="dialogVisible" title="对话框标题">
      <el-form ref="form" :model="formData" label-width="80px">
        <!-- 在这里放置表单字段 -->
        <el-form-item label="字段1">
          <el-input v-model="formData.field1"></el-input>
        </el-form-item>
        <el-form-item label="字段2">
          <el-input v-model="formData.field2"></el-input>
        </el-form-item>
        <!-- 其他表单字段 -->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      cards: [
        {
          id: 1,
          title: 'Card 1',
          description: 'Description for Card 1',
          items: [
            { id: 1, text: 'Item 1', iconClass: 'el-icon-circle-check' },
            { id: 2, text: 'Item 2', iconClass: 'el-icon-circle-check' },
            { id: 3, text: 'Item 3', iconClass: 'el-icon-circle-check' },
          ],
        },
        {
          id: 2,
          title: 'Card 2',
          description: 'Description for Card 2',
          items: [
            { id: 1, text: 'Item 1', iconClass: 'el-icon-circle-check' },
            { id: 2, text: 'Item 2', iconClass: 'el-icon-circle-check' },
            { id: 3, text: 'Item 3', iconClass: 'el-icon-circle-check' },
          ],
        },
        // Add more cards as needed
      ],
      dialogVisible: false,
      formData: {
        field1: '',
        field2: ''
        // 其他表单字段
      }
    };
  },
  methods:{
    showDialog() {
      this.dialogVisible = true;
    },
    submitForm() {
      // 在这里处理表单提交逻辑
      // 可以通过this.formData访问表单数据
      console.log(this.formData);
      // 提交表单的其他逻辑
      // ...
      // 关闭对话框
      this.dialogVisible = false;
    }
  }
};
</script>

<style scoped>
.card-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
}

.card {
  width: 20%;
  height: 300px;
  margin: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  overflow: hidden;
}

.header {
  height: 25%;
  background-color: rgb(64, 158, 255);
  padding: 10px;
  color: #fff;
}

.header h2 {
  font-weight: bold;
  margin: 0;
}

.body {
  height: 50%;
  padding: 10px;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 5px;
}

.footer {
  height: 25%;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px;
}

.special-card {
  width: 20%;
  height: 300px;
  margin: 10px;
  border: 1px dashed #ccc;
  border-radius: 4px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 24px;
}
</style>
