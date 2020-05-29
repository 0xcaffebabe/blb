<template>
  <div>
    <el-dialog
      title="新增商品"
      :visible.sync="value"
      width="50%">
      <el-form :model="productForm" :rules="productFormRules" ref="productForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="商品分类" prop="productCategory">
            <el-select placeholder="请选择" v-model="productForm.productCategory">
              <el-option
              v-for="item in productCategories"
              :key="item.categoryId"
              :label="item.categoryName"
              :value="item.categoryId"
              />
            </el-select>
        </el-form-item>
        <el-form-item label="商品名称" prop="productName">
          <el-input v-model="productForm.productName"></el-input>
        </el-form-item>
        <el-form-item label="商品详情" prop="productDesc">
          <el-input type="textarea" v-model="productForm.productDesc"></el-input>
        </el-form-item>
        <el-form-item label="商品图片" prop="productImg">
         <input type="file" ref="productImgFile"> <el-button size="mini" type="primary" @click="uploadProductImg">上传商品图片</el-button>
         <div></div>
         <img :src="productForm.productImg" v-if="productForm.productImg" alt="" style="max-width:100px;max-height:100px">
        </el-form-item>
        <el-form-item>
          <el-button size="mini" type="success" class="fr" @click="showSpecAdd=true">新增规格</el-button>
           <el-table
      :data="productSpecList"
      style="width: 100%">
      <el-table-column
        prop="specName"
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
              <el-input size="mini" v-model="temSpec.specName"/>
            </el-col>
            <el-col :span="4">
              <el-input-number size="mini" style="width:100px" v-model="temSpec.price"/>
            </el-col>
            <el-col :span="4" :offset="2">
              <el-input-number size="mini" style="width:100px" v-model="temSpec.packageFee"/>
            </el-col>
            <el-col :span="4" :offset="2">
              <el-button size="mini" icon="el-icon-check" round @click="addSpec"></el-button>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="value = false">取 消</el-button>
        <el-button type="primary" @click="addProduct">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import productService from '../../service/ProductService'
import shopService from '../../service/ShopService'
export default {
  props: ['value'],
  data () {
    return {
      productForm: {
        productCategory: undefined,
        productName: '',
        productDesc: '',
        productImg: ''
      },
      temSpec: {
        specName: '',
        price: 0,
        packageFee: 0
      },
      productFormRules: {
        categoryId: [
          { required: true, message: '商品分类不得为空', trigger: 'blur' }
        ],
        productName: [
          { required: true, message: '商品名称不得为空', trigger: 'blur' }
        ],
        productDesc: [
          { required: true, message: '商品描述不得为空', trigger: 'blur' }
        ],
        productImg: [
          { required: true, message: '商品图片不得为空', trigger: 'blur' }
        ]
      },
      showSpecAdd: false,
      productCategories: [],
      productSpecList: []
    }
  },
  methods: {
    async getProductCategory () {
      try {
        this.productCategories = await productService.getProductCategory()
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    addSpec () {
      this.productSpecList.push(this.temSpec)
      this.temSpec = {}
    },
    async uploadProductImg () {
      try {
        this.productForm.productImg = await shopService.upload(this.$refs.productImgFile.files)
        this.$message.success('上传商品图片成功')
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    async addProduct () {
      this.productForm.productSpecList = this.productSpecList
      try {
        if (await productService.addProduct(this.productForm)) {
          this.$message.success('新增商品成功')
        }
      } catch (e) {
        this.$message.error(e.message)
      }
    }
  },
  created () {
    this.getProductCategory()
  }
}
</script>

<style lang="less" scoped>

</style>
