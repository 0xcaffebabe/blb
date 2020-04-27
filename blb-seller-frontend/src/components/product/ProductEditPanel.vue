<template>
  <div>
    <el-dialog
      title="新增商品"
      :visible.sync="value"
      width="50%">
      <el-form :model="productForm" :rules="productFormRules" ref="productForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="商品分类" prop="name">
            <el-select placeholder="请选择">
              <el-option
              v-for="item in productCategories"
              :key="item"
              :label="item.name"
              :value="item.id"
              />
            </el-select>
        </el-form-item>
        <el-form-item label="商品名称">
          <el-input></el-input>
        </el-form-item>
        <el-form-item label="商品详情">
          <el-input type="textarea"></el-input>
        </el-form-item>
        <el-form-item label="商品图片" prop="name">
         <el-upload
           class="avatar-uploader"
           action="https://jsonplaceholder.typicode.com/posts/"
           :show-file-list="false"
           :on-success="handleAvatarSuccess"
           :before-upload="beforeAvatarUpload">
           <img v-if="imageUrl" :src="imageUrl" class="avatar">
           <i v-else class="el-icon-plus avatar-uploader-icon"></i>
         </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button size="mini" type="success" class="fr" @click="showSpecAdd=true">新增规格</el-button>
           <el-table
      :data="productSpecList"
      style="width: 100%">
      <el-table-column
        prop="name"
        label="规格名称"
        width="100">
      </el-table-column>
      <el-table-column
        prop="price"
        label="价格"
        width="100">
      </el-table-column>
      <el-table-column
        prop="packageFee"
        label="包装费"
        width="100"
        >
      </el-table-column>
      <el-table-column label="操作">
        <template>
          <el-button size="mini" type="danger">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
        </el-form-item>
        <el-form-item v-show="showSpecAdd">
          <el-row :gutter="20">
            <el-col :span="4">
              <el-input size="mini"/>
            </el-col>
            <el-col :span="4">
              <el-input-number size="mini" style="width:100px"/>
            </el-col>
            <el-col :span="4" :offset="2">
              <el-input-number size="mini" style="width:100px"/>
            </el-col>
            <el-col :span="4" :offset="2">
              <el-button size="mini" icon="el-icon-check" round></el-button>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="value = false">取 消</el-button>
        <el-button type="primary" @click="value = false">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: ['value'],
  data () {
    return {
      productForm: {},
      productFormRules: [],
      showSpecAdd: false,
      productCategories: [
        { name: '分类1', id: 1 },
        { name: '分类2', id: 2 }
      ],
      productSpecList: [
        { name: '大份', price: 18, packageFee: 2 },
        { name: '小份', price: 13, packageFee: 0 }
      ]
    }
  }
}
</script>

<style lang="less" scoped>

</style>
