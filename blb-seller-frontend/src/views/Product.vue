<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="4">
        <div>
          <el-input placeholder="搜索商品"></el-input>
          <el-menu
            default-active="order"
            >
            <el-menu-item index="1">
              <span slot="title">1号店铺黄焖系列</span>
            </el-menu-item>
            <el-menu-item index="2">
              <span slot="title">沙县小吃系列</span>
            </el-menu-item>
            <el-menu-item index="3">
              <span slot="title">饿点招牌面系列</span>
            </el-menu-item>
            <el-menu-item index="4">
              <span slot="title">沙县小吃系列</span>
            </el-menu-item>
          </el-menu>
        </div>
      </el-col>
      <el-col :span="20">
        <ul class="product-list">
          <li class="product-list-item" v-for="item in 8" :key="item">
            <el-card>
              <el-row>
                <el-col :span="16">
                  <h3 class="product-name">黄焖鸡</h3>
                  <p class="danger product-price">￥13.0~￥18.0</p>
                  <el-dropdown class="product-spec" :hide-on-click="false">
                    <el-button type="primary" size="mini">
                      规格<i class="el-icon-arrow-down el-icon--right"></i>
                    </el-button>
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item v-for="item in 3" :key="item">
                        规格{{item}}: 9959
                        <el-button-group>
                          <el-button type="success" size="mini">置满</el-button> <el-button type="info" size="mini">沽清</el-button>
                        </el-button-group>
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                  <el-button type="success" size="mini" style="margin-left:10px;margin-top:40px">编辑</el-button>
                </el-col>
                <el-col :span="8">
                  <el-image  fit="cover" style="height:100px;width:100px" src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1587391694057&di=4d47fb790e2cd562749ffed056651d44&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fbainuo%2Fcrop%3D0%2C15%2C500%2C303%3Bw%3D470%3Bq%3D80%2Fsign%3D09f5f05bf51fbe090811995456502002%2Fd1a20cf431adcbef0437b12babaf2edda3cc9f98.jpg"></el-image>
                </el-col>
              </el-row>
            </el-card>
          </li>
        </ul>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data () {
    return {}
  }
}
</script>

<style lang="less" scoped>
  .product-name {
    margin: 0;
    font-size: 16px;
  }
  .product-price {
    margin-top: 10px;
    font-size: 14px;
  }
  .product-list {
    display: flex;
    flex-flow: row wrap;
    justify-content: center;
    .product-list-item {
      flex: 0 0 32%;
      margin-bottom: 10px;
      margin-right: 10px;
    }
  }
  .product-spec {
    margin-top: 40px;
  }
</style>
