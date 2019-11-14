<template>
  <div>
    <van-nav-bar title="门店列表" left-text="返回" left-arrow @click-left="goback"/>
<!--    <van-address-list v-model="chosenAddressId"   add-button-text="新增门店" :list="merchantList" @add="onAdd" @edit="onEdit" @select="onEdit"/>-->

    <van-list
            v-model="loading"
            :finished="true"
            @load="onLoad"
    >
<!--      <van-cell-->
<!--              v-for="item in merchantList"-->
<!--              :key="item"-->
<!--              :title="item.name"-->
<!--              :value="item.leader"-->
<!--      />-->
      <van-cell value="详情" is-link v-for="item in merchantList" size="large" @click="onSelect(item.id)">
        <!-- 使用 title 插槽来自定义标题 -->
        <template slot="title">
          <span class="custom-title">{{item.name}}</span>
        </template>
      </van-cell>
      <van-button size="large" type="primary" @click="onAdd()">新增门店</van-button>
    </van-list>
  </div>
</template>

<script>
import { addressList,merchantList,authInfo } from '@/api/api';
import { AddressList, NavBar} from 'vant';
import { setLocalStorage } from '@/utils/local-storage';
import Vue from 'vue';
import { List ,Cell, CellGroup,Button } from 'vant';

Vue.use(List,Button);
Vue.use(Cell).use(CellGroup);

export default {
  data() {
    return {
      chosenAddressId: -1,
      addressList: [],
      merchantList:[],
      userId:'',

      list: [],
      loading: false,
      finished: false
    };
  },

  created() {
    this.onLoad()
    //this.getUserInfo();
  },
  methods: {
    onLoad() {
      merchantList(this.userId).then(res => {
        var list = res.data.data.list;
        for(var i = 0; i < list.length; i++ ){
          var item = list[i]
          this.merchantList.push({
            id: item.id,
            name: item.merchantName,
            leader: item.merchantLeader,
            address:item.merchantAddress+"  "+item.merchantPhone
          })
        }
      })
    },

    getUserInfo() {
      authInfo().then(res => {
        this.avatar = res.data.data.avatar;
        this.nickName = res.data.data.nickName;
        this.gender = res.data.data.gender;
        this.mobile = res.data.data.mobile;
        this.userId = res.data.data.userId;
      })
    },
    // findM(){
    //   merchantList(this.userId).then(res => {
    //     var list = res.data.data.list;
    //     for(var i = 0; i < list.length; i++ ){
    //       var item = list[i]
    //       this.merchantList.push({
    //         id: item.id,
    //         name: item.merchantName,
    //         tel: item.merchantLeader,
    //         address:item.merchantAddress+"  "+item.merchantPhone
    //       })
    //     }
    //   })
    // },

    onAdd() {
      this.$router.push({ name: 'addMerchant'});
    },
    // onEdit(item, index) {
    //   this.$router.push({ name: 'merchant-detail', query: { storeId: item.id } });
    // },
    onSelect(id) {
      this.$router.push({ name: 'merchant-detail', query: { storeId: id } });
    },         
    goback() {
      this.$router.go(-1);
    },
    loadAddress() {
    }
  },

  components: {
    [NavBar.name]: NavBar,
    [AddressList.name]: AddressList
  }
};
</script>


<style lang="scss" scoped>
.addressGroup {
  margin-bottom: 10px;
  &:last-child {
    margin-bottom: 0;
  }
}

.bottom_btn {
  position: fixed;
  bottom: 0;
}
</style>
