<template>
  <div>
    <el-button type="primary" size="mini" @click="handleAddCategory">新增目录</el-button>
    <el-table
      :data="categoryList"
      style="width: 100%">
      <el-table-column
        prop="categoryId"
        label="ID"
        width="180">
      </el-table-column>
      <el-table-column
        prop="categoryName"
        label="目录名称"
        width="180">
      </el-table-column>
      <el-table-column
        prop="categoryDesc"
        label="目录描述">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="success" size="mini" @click="handleEditCategory(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="更新目录"
      :visible.sync="editDialogShow"
      width="50%">
      <el-form :model="editForm" :rules="editFormRules" ref="editForm" label-width="80px" class="demo-ruleForm">
        <el-form-item label="目录ID" prop="categoryId">
          <el-input v-model="editForm.categoryId" readonly></el-input>
        </el-form-item>
        <el-form-item label="目录名称" prop="categoryName">
          <el-input v-model="editForm.categoryName"></el-input>
        </el-form-item>
        <el-form-item label="目录描述" prop="categoryDesc">
          <el-input v-model="editForm.categoryDesc"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogShow = false">取 消</el-button>
        <el-button type="primary" @click="updateCategory">保 存</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="新增目录"
      :visible.sync="addDialogShow"
      width="50%">
      <el-form :model="addForm" :rules="addFormRules" ref="addForm" label-width="80px" class="demo-ruleForm">
        <el-form-item label="目录名称" prop="categoryName">
          <el-input v-model="addForm.categoryName"></el-input>
        </el-form-item>
        <el-form-item label="目录描述" prop="categoryDesc">
          <el-input v-model="addForm.categoryDesc"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogShow = false">取 消</el-button>
        <el-button type="primary" @click="saveCategory">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import productService from '../service/ProductService'
export default {
  data () {
    return {
      categoryList: [],
      editDialogShow: false,
      addDialogShow: false,
      editForm: {},
      addForm: {}
    }
  },
  methods: {
    async getCategoryList () {
      try {
        this.categoryList = await productService.getProductCategory()
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    handleEditCategory (category) {
      this.editForm = category
      this.editDialogShow = true
    },
    async updateCategory () {
      try {
        if (await productService.updateProductCategory(this.editForm)) {
          this.$message.success('更新目录成功')
          this.editForm = {}
          this.editDialogShow = false
          this.getCategoryList()
        }
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    handleAddCategory () {
      this.addDialogShow = true
    },
    async saveCategory () {
      try {
        if (await productService.saveProductCategory(this.addForm)) {
          this.$message.success('新增目录成功')
          this.addForm = {}
          this.addDialogShow = false
          this.getCategoryList()
        }
      } catch (e) {
        this.$message.error(e.message)
      }
    }
  },
  created () {
    this.getCategoryList()
  }
}
</script>

<style lang="less" scoped>

</style>
