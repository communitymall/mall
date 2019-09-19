<template>
  <div class="order_list">
    <van-nav-bar title="门店详情" left-text="返回" left-arrow @click-left="goback"/>

    <van-tabs  v-model="activeIndex"
              :swipe-threshold="5"
              @click="handleTabClick">

      <van-tab v-for="(tabTitle, index) in tabTitles"
               :title="tabTitle"
               :key="index">
          <van-cell-group v-if="activeIndex===0">
            <van-cell title="门店照片" class="cell_middle">
              <van-uploader :afterRead="avatarAfterRead">
                <div class="user_avatar_upload">
                  <img
                          :src="avatar + '?x-oss-process=image/resize,m_fill,h_50,w_50'"
                          alt="你的头像"
                          v-if="avatar"
                  >
                  <van-icon name="camera_full" v-else></van-icon>
                </div>
              </van-uploader>
            </van-cell>
            <van-cell title="门店营业执照" class="cell_middle">
              <van-uploader :afterRead="avatarAfterRead">
                <div class="user_avatar_upload">
                  <img
                          :src="avatar + '?x-oss-process=image/resize,m_fill,h_50,w_50'"
                          alt="你的头像"
                          v-if="avatar"
                  >
                  <van-icon name="camera_full" v-else></van-icon>
                </div>
              </van-uploader>
            </van-cell>
            <van-cell title="商户名称" @click="onSetManchantName" :value="merchantName" isLink/>
            <van-cell title="商户地址" @click="onSetManchantAddress" :value="merchantAddress" isLink/>
            <van-cell title="商户电话" @click="onSetManchantPhone"  :value="merchantPhone" isLink/>
            <van-cell title="商户负责人" @click="onSetManchantLeader" :value="merchantLeader"  isLink/>
          </van-cell-group>


        <van-cell-group v-if="activeIndex===1">

          <van-cell value="" is-link @click="onEditLeader()">
            <!-- 使用 title 插槽来自定义标题 -->
            <template slot="title">
              <span class="custom-title">{{leaderData.merchantLeader}}</span>
              <van-tag type="danger">创建人</van-tag>
              <br/>
              <span>{{leaderData.merchantPhone}}</span>
            </template>
          </van-cell>

          <div v-for="(item, i) in merchantList" :key="i" class="card-goods__item">
            <van-cell value="编辑" is-link @click="onEdit(item,i)">
              <!-- 使用 title 插槽来自定义标题 -->
              <template slot="title">
                <span class="custom-title">{{item.name}}</span>
                <van-tag type="danger">{{ item.roleType | detailStatusFilter }}</van-tag>
                <br/>
               <span>{{item.mobile}}</span>
              </template>
            </van-cell>
          </div>

          <div align="center"> <van-button type="primary" size="small" @click="addMerchantUser">添加人员</van-button></div>
        </van-cell-group>
      </van-tab>

    </van-tabs>
  </div>
</template>

<script>
import { merchantDetail,listUserStore,findMerchantLeader} from '@/api/api';
import _ from 'lodash';
import { Tab, Tabs, Panel, Card, List, Tag,NavBar } from 'vant';

const detailMap = {
  0: '管理员',
  1: '厨师',
  2: '店员',
}

export default {
  filters: {
    detailStatusFilter(status){
      return detailMap[status]
    }
  },

  name: 'merchant-detail',
  props: {
    active: {
      type: [String, Number],
      default: 0
    }
  },
  created() {
    this.init();
    this.findStrorUser()
  },
  data() {
    return {
      activeIndex: Number(this.active),
      tabTitles: ['门店信息', '人员信息'],
      orderList: [],
      merchantList:[],
      page: 0,
      limit: 10,
      loading: false,
      finished: false,
      shipData :{
        storeId:''
      },
      leaderData : {
        merchantLeader : '',
        merchantPhone : '',
      },
      merchantName : '',
      merchantCode : '',
      merchantAddress : '',
      merchantPic : '',
      merchantPhone : '',
      merchantLeader : '',


      usId : '',
      uId : '',
      roleType : '',
      mobile : '',
      storeId : '',

    };
  },

  methods: {
    init() {
      this.page = 0;
      this.orderList = [];
      this.getOrderList();
    },
    onSetManchantName() {
      let id = this.$route.query.storeId
      this.$router.push({ name: 'merchant-detail-setMerchantName', query: { storeId: id } });
    },
    onSetManchantAddress() {
      let id = this.$route.query.storeId
      this.$router.push({ name: 'merchant-detail-setMerchantAddress', query: { storeId: id } });
    },
    onSetManchantLeader() {
      let id = this.$route.query.storeId
      this.$router.push({ name: 'merchant-detail-setMerchantLeader', query: { storeId: id } });
    },
    onSetManchantPhone() {
      let id = this.$route.query.storeId
      this.$router.push({ name: 'merchant-detail-setMerchantPhone', query: { storeId: id } });
    },

    onEdit(item, index) {
      this.$router.push({ name: 'merchant-detail-setMerchantUser', query: { userId: item.uId } });
    },

    addMerchantUser(){
      let id = this.$route.query.storeId
      this.$router.push({ name: 'merchant-detail-addMerchantUser', query: { storeId: id } });
    },

    goback(){
      this.$router.go(-1);
    },

    handleTabClick(){
      if(this.activeIndex===1){
        this.findStrorUser()
        this.findLeader()
      }
    },

    findStrorUser(){
      this.shipData.storeId = this.$route.query.storeId
      listUserStore(this.shipData).then(res => {
        var list = res.data.data;
        this.merchantList =res.data.data;
        for(var i = 0; i < list.length; i++ ){
          var item = list[i]
        }
      });
    },
    findLeader(){
      this.shipData.storeId = this.$route.query.storeId
      findMerchantLeader(this.shipData).then(res => {
          this.leaderData.merchantLeader = res.data.data.merchantLeader;
          this.leaderData.merchantPhone = res.data.data.merchantPhone;
      });
    },
    getOrderList() {
      let id = this.$route.query.storeId
      this.shipData.storeId = id
      merchantDetail(this.shipData).then(res => {
        this.merchantName = res.data.data.merchantName;
        this.merchantAddress = res.data.data.merchantAddress;
        this.merchantPic = res.data.data.merchantPic;
        this.merchantCode = res.data.data.merchantCode;

        this.merchantPhone = res.data.data.merchantPhone;
        this.merchantLeader = res.data.data.merchantLeader;
      });
      this.page++;
      orderList({
        showType: this.activeIndex,
        page: this.page,
        limit: this.limit
      }).then(res => {
        this.orderList.push(...res.data.data.list);
        this.loading = false;
        this.finished = res.data.data.page >= res.data.data.pages;
      });
    },
  },
  components: {
    [Tab.name]: Tab,
    [Tabs.name]: Tabs,
    [Panel.name]: Panel,
    [Card.name]: Card,
    [List.name]: List,
    [Tag.name]: Tag,
    [NavBar.name]: NavBar,
  }
};
</script>

<style lang="scss" scoped>
.order_list {
  .van-panel {
    margin-top: 20px;
  }

  .van-card {
    background-color: #fff;
  }

  .total {
    text-align: right;
    padding: 10px;
  }

  .footer_btn {
    text-align: right;
    .van-button {
      margin-left: 10px;
    }
  }
}
</style>
