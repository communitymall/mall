<template>
    <div class="order_list">
        <van-nav-bar title="门店详情" left-text="返回" left-arrow @click-left="goback"/>

        <van-tabs v-model="activeIndex"
                  :swipe-threshold="5"
                  @click="handleTabClick">
            <van-tab v-for="(tabTitle, index) in tabTitles"
                     :title="tabTitle"
                     :key="index">
                <van-cell-group v-if="activeIndex===0">
                    <van-cell title="商户名称" @click="onSetManchantName" :value="merchantName" isLink/>
                    <van-cell title="商户地址" @click="onSetManchantAddress" :value="merchantAddress" isLink/>
                    <van-cell title="商户电话" @click="onSetManchantPhone" :value="merchantPhone" isLink/>
                    <van-cell title="商户负责人" @click="onSetManchantLeader" :value="merchantLeader" isLink/>
                    <van-cell title="商户状态"  @click="onSetManchantAudit"  isLink>
                        <span>  {{ merchantStatus | detailMerchantStatusFilter }}</span>
                    </van-cell>
                    <van-cell title="门店照片" class="cell_middle">
                    </van-cell>
                </van-cell-group>
                <img v-if="activeIndex===0"  :src="pic" width="100%">

                <van-cell-group v-if="activeIndex===1" >
<!--                    <van-cell value="" is-link @click="onEditLeader()">-->
<!--                        &lt;!&ndash; 使用 title 插槽来自定义标题 &ndash;&gt;-->
<!--                        <template slot="title">-->
<!--                            <span class="custom-title">{{leaderData.merchantLeader}}</span>-->
<!--                            <van-tag type="danger">创建人</van-tag>-->
<!--                            <br/>-->
<!--                            <span>{{leaderData.merchantPhone}}</span>-->
<!--                        </template>-->
<!--                    </van-cell>-->
                    <div v-for="(item, i) in merchantList" :key="i" class="card-goods__item">
                        <van-cell v-if="item.roleType===3" value="编辑" is-link @click="onEdit(item,i)">
                            <!-- 使用 title 插槽来自定义标题 -->
                            <template slot="title">
                                <span class="custom-title">{{merchantLeader}}</span>
                                <van-tag type="danger">{{ item.roleType | detailStatusFilter }}</van-tag>
                                <br/>
                                <span>{{merchantPhone}}</span>
                            </template>
                        </van-cell>
                        <van-cell v-if="item.roleType!==3" value="编辑" is-link @click="onEdit(item,i)">
                            <!-- 使用 title 插槽来自定义标题 -->
                            <template slot="title">
                                <span class="custom-title">{{item.name}}</span>
                                <van-tag type="danger">{{ item.roleType | detailStatusFilter }}</van-tag>
                                <br/>
                                <span>{{item.mobile}}</span>
                            </template>
                        </van-cell>
                    </div>
                    <div align="center">
                        <van-button type="primary" size="small" @click="addMerchantUser">添加人员</van-button>
                    </div>
                </van-cell-group>
            </van-tab>

        </van-tabs>
    </div>
</template>

<script>
    import {merchantDetail, listUserStore, findMerchantLeader,merchantPicVueUpload} from '@/api/api';
    import _ from 'lodash';
    import {Tab, Tabs, Panel, Card, List, Tag, NavBar} from 'vant';

    import Vue from 'vue';
    import { Uploader, Image} from 'vant';
    Vue.use(Uploader, Image);
    const detailMap = {
        0: '管理员',
        1: '厨师',
        2: '店员',
        3: '负责人',
    }

    const merchantStatusMap ={
        0: '审核中',
        1: '审核未通过',
        2: '审核通过'
    }

    export default {
        filters: {
            detailStatusFilter(status) {
                return detailMap[status]
            },
            detailMerchantStatusFilter(status){
                return merchantStatusMap[status]
            },
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
                merchantList: [],
                page: 0,
                limit: 10,
                loading: false,
                finished: false,
                shipData: {
                    storeId: ''
                },
                leaderData: {
                    id: '',
                    merchantLeader: '',
                    merchantPhone: '',
                },
                merchantName: '',
                merchantCode: '',
                merchantAddress: '',
                merchantPic: '',
                merchantPhone: '',
                merchantLeader: '',
                merchantStatus:'',
                avatar: '',
                usId: '',
                uId: '',
                roleType: '',
                mobile: '',
                storeId: '',
                pic: '',
            };
        },

        methods: {
            afterRead(file) {
                // 此时可以自行将文件上传至服务器
                let id = this.$route.query.storeId
                let content = file.file;
                let merchantPic = this.merchantPic
                let data = new FormData();
                data.append('imagefile',content);
                data.append('storeId',id);
                data.append('merchantPic',merchantPic);
                merchantPicVueUpload(data).then(res =>{
                    this.merchantPic = res.data.data;
                    this.pic = res.data.data+"?"+Math.random();
                })
            },
            init() {
                this.page = 0;
                this.getMerchantList();
            },
            onSetManchantName() {
                let id = this.$route.query.storeId
                this.$router.push({name: 'merchant-detail-setMerchantName', query: {storeId: id}});
            },
            onSetManchantAddress() {
                let id = this.$route.query.storeId
                this.$router.push({name: 'merchant-detail-setMerchantAddress', query: {storeId: id}});
            },
            onSetManchantLeader() {
                let id = this.$route.query.storeId
                this.$router.push({name: 'merchant-detail-setMerchantLeader', query: {storeId: id}});
            },
            onSetManchantPhone() {
                let id = this.$route.query.storeId
                this.$router.push({name: 'merchant-detail-setMerchantPhone', query: {storeId: id}});
            },
            onSetManchantCode() {
                let id = this.$route.query.storeId
                this.$router.push({name: 'merchant-detail-setMerchantCode', query: {storeId: id}});
            },
            onSetManchantAudit() {
                let id = this.$route.query.storeId
                this.$router.push({name: 'merchant-audit', query: {storeId: id}});
            },


            onEdit(item, index) {
                this.$router.push({
                    name: 'merchant-detail-setMerchantUser',
                    query: {userId: item.uId, storeId: item.storeId, roleType: item.roleType}
                });
            },

            onEditLeader() {
                this.$router.push({name: 'merchantLeader-detail', query: {id: this.leaderData.id}});
            },

            addMerchantUser() {
                let id = this.$route.query.storeId
                this.$router.push({name: 'merchant-detail-addMerchantUser', query: {storeId: id}});
            },

            goback() {
                this.$router.go(-1);
            },

            handleTabClick() {
                if (this.activeIndex === 1) {
                    this.findStrorUser()
                    this.findLeader()
                }
            },

            findStrorUser() {
                this.shipData.storeId = this.$route.query.storeId
                listUserStore(this.shipData).then(res => {
                    var list = res.data.data;
                    this.merchantList = res.data.data;
                    for (var i = 0; i < list.length; i++) {
                        var item = list[i]
                    }
                });
            },
            findLeader() {
                this.shipData.storeId = this.$route.query.storeId
                findMerchantLeader(this.shipData).then(res => {
                    this.leaderData.merchantLeader = res.data.data.merchantLeader;
                    this.leaderData.merchantPhone = res.data.data.merchantPhone;
                    this.leaderData.id = res.data.data.id;
                });
            },
            getMerchantList() {
                let id = this.$route.query.storeId
                this.shipData.storeId = id
                merchantDetail(this.shipData).then(res => {
                    this.merchantName = res.data.data.merchantName;
                    this.merchantAddress = res.data.data.merchantAddress;
                    this.merchantPic = res.data.data.merchantPic;
                    this.merchantCode = res.data.data.merchantCode;
                    this.merchantPhone = res.data.data.merchantPhone;
                    this.merchantLeader = res.data.data.merchantLeader;
                    this.merchantStatus = res.data.data.merchantStatus;
                    this.pic=res.data.data.merchantPic+"?"+Math.random();
                });
                this.page++;
                // orderList({
                //     showType: this.activeIndex,
                //     page: this.page,
                //     limit: this.limit
                // }).then(res => {
                //     this.orderList.push(...res.data.data.list);
                //     this.loading = false;
                //     this.finished = res.data.data.page >= res.data.data.pages;
                // });
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
