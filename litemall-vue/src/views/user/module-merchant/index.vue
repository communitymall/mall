<template>
  <div>
    <van-nav-bar title="门店管理" left-text="返回" left-arrow @click-left="goback"/>
    <van-address-list v-model="chosenAddressId"   add-button-text="新增门店" :list="merchantList" @add="onAdd" @edit="onEdit" @select="onEdit"/>
  </div>
</template>

<script>
import { addressList,merchantList,authInfo } from '@/api/api';
import { AddressList, NavBar} from 'vant';
import { setLocalStorage } from '@/utils/local-storage';
export default {
  data() {
    return {
      chosenAddressId: -1,
      addressList: [],
      merchantList:[],
      userId:'',
    };
  },

  created() {
    this.loadAddress();
    this.findM()
    this.getUserInfo();
  },
  methods: {
    getUserInfo() {
      authInfo().then(res => {
        this.avatar = res.data.data.avatar;
        this.nickName = res.data.data.nickName;
        this.gender = res.data.data.gender;
        this.mobile = res.data.data.mobile;
        this.userId = res.data.data.userId;
      })
    },
    findM(){
      merchantList(this.userId).then(res => {
        var list = res.data.data.list;
        for(var i = 0; i < list.length; i++ ){
          var item = list[i]
          this.merchantList.push({
            id: item.id,
            name: item.merchantName,
            tel: item.merchantLeader,
            address:item.merchantAddress+"  "+item.merchantPhone
          })
        }
      })
    },

    onAdd() {
      this.$router.push({ name: 'addMerchant'});
    },
    onEdit(item, index) {
      this.$router.push({ name: 'merchant-detail', query: { storeId: item.id } });
    },
    onSelect(item, index) {
      setLocalStorage({ AddressId: item.id });
      this.$router.go(-1);
    },         
    goback() {
      this.$router.go(-1);
    },
    loadAddress() {
      addressList().then(res => {
        var list = res.data.data.list;
        for(var i = 0; i < list.length; i++ ){
          var item = list[i]
          this.addressList.push({
            id: item.id,
            name: item.name,
            tel: item.tel,
            address: item.province + item.city + item.county + " " + item.addressDetail
          })
        }
      })
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
