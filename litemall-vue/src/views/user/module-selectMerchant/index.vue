<template>
  <div>
    <van-nav-bar title="选择门店" left-text="返回" left-arrow @click-left="goback"/>
    <van-address-list v-model="chosenAddressId"   add-button-text="确定" :list="merchantList" @add="onAdd" @edit="onEdit" @select="onSelect"/>
  </div>
</template>

<script>
import { merchantStatusList,authInfo } from '@/api/api';
import { AddressList, NavBar,Toast,Dialog } from 'vant';
import { setLocalStorage } from '@/utils/local-storage';
export default {
  data() {
    return {
      chosenAddressId: -1,
      addressList: [],
      merchantList:[],
      userId: '',
      shipData:{
        userId:"",
        merchantStatus:2,
      }
    };
  },

  created() {
    //this.loadAddress();
    this.findM()
  },
  methods: {
    getUserInfo() {
      authInfo().then(res => {
        this.avatar = res.data.data.avatar;
        this.nickName = res.data.data.nickName;
        this.gender = res.data.data.gender;
        this.mobile = res.data.data.mobile;
        this.shipData.userId = res.data.data.userId;
      })
    },
    findM(){
      this.getUserInfo();
      merchantStatusList(this.shipData).then(res => {
        var list = res.data.data.list;
        if(list.length==0){
          Dialog.confirm({
            title: '提示',
            message: '没有通过审核的门店！点击确定添加门店，点击取消返回。'
          }).then(() => {
            // on confirm
            this.$router.push({ name: 'merchant' });
          }).catch(() => {
            // on cancel
            this.$router.go(-1);
          })
        }
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
      //this.$router.push({ name: 'addMerchant'});
    },
    // onEdit(item, index) {
    //   this.$router.push({ name: 'merchant-detail', query: { storeId: item.id } });
    // },
    onSelect(item, index) {
      setLocalStorage({ storeId: item.id });
      this.$router.go(-1);
    },         
    goback() {
      this.$router.go(-1);
    },
    // loadAddress() {
    //   addressList().then(res => {
    //     var list = res.data.data.list;
    //     for(var i = 0; i < list.length; i++ ){
    //       var item = list[i]
    //       this.addressList.push({
    //         id: item.id,
    //         name: item.name,
    //         tel: item.tel,
    //         address: item.province + item.city + item.county + " " + item.addressDetail
    //       })
    //     }
    //   })
    // }
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
