<template>
    <div class="order_list">
        <van-nav-bar title="门店审核" left-text="返回" left-arrow @click-left="goback"/>
        <van-cell-group>
            <van-cell title="商户名称" :value="merchantName" isLink/>
            <van-cell title="商户状态">
                <span>  {{ merchantStatus | detailMerchantStatusFilter }}</span>
            </van-cell>
            <van-cell title="门店照片" class="cell_middle">
                <van-uploader :afterRead="afterRead" name="camera_full" accept=".jpg,.jpeg,.png,.gif">
                    <div class="user_avatar_upload">
                        <van-icon :after-read="afterRead" name="camera_full"></van-icon>
                    </div>
                </van-uploader>
                <!--                                    <van-uploader v-model="fileList" multiple name="camera_full" :after-read="afterRead" />-->
            </van-cell>

            <img :src="pic" width="100%" >

            <van-cell title="门店营业执照照片" class="cell_middle">
                <van-uploader :afterRead="afterRead1" name="camera_full" accept=".jpg,.jpeg,.png,.gif">
                    <div class="user_avatar_upload">
                        <van-icon :after-read="afterRead1" name="camera_full"></van-icon>
                    </div>
                </van-uploader>
                <!--                                    <van-uploader v-model="fileList" multiple name="camera_full" :after-read="afterRead" />-->
            </van-cell>
            <img :src="codePic" width="100%">
        </van-cell-group>

    </div>

</template>

<script>
    import {merchantDetail, listUserStore, merchantCodePicUpload, merchantPicVueUpload} from '@/api/api';
    import _ from 'lodash';
    import {Tab, Tabs, Panel, Card, List, Tag, NavBar} from 'vant';

    import Vue from 'vue';
    import {Uploader, Image} from 'vant';

    Vue.use(Uploader, Image);
    const merchantStatusMap = {
        0: '审核中',
        1: '审核未通过',
        2: '审核通过'
    }

    export default {
        filters: {
            detailStatusFilter(status) {
                return detailMap[status]
            },
            detailMerchantStatusFilter(status) {
                return merchantStatusMap[status]
            },
        },
        name: 'merchant-audit',
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
                fileList: [
                ],
                shipData: {
                    storeId: ''
                },
                leaderData: {
                    id: '',
                    merchantLeader: '',
                    merchantPhone: '',
                },
                merchantName:'',
                merchantStatus:'',
                merchantPic: '',
                merchantCodePic: '',
                storeId: '',
                pic: '',
                codePic:'',
            };
        },

        methods: {
            afterRead(file) {
                var isFlag = (file.file.type == 'image/jpeg' || file.file.type == 'image/png')? true : false;
                if(!isFlag){
                    this.$message({
                        type: 'warning',
                        message: '上传图片只能是 JPG、PNG 格式!'
                    })
                    return false
                }
                // 此时可以自行将文件上传至服务器
                let id = this.$route.query.storeId
                let content = file.file;
                let merchantPic = this.merchantPic
                let data = new FormData();
                data.append('imagefile', content);
                data.append('storeId', id);
                data.append('merchantPic', merchantPic);
                merchantPicVueUpload(data).then(res => {
                    this.pic = res.data.data + "?" + Math.random();
                    //this.$router.go(0)
                })
            },
            afterRead1(file) {
                var isFlag = (file.file.type == 'image/jpeg' || file.file.type == 'image/png')? true : false;
                if(!isFlag){
                    this.$message({
                        type: 'warning',
                        message: '上传图片只能是 JPG、PNG 格式!'
                    })
                    return false
                }
                // 此时可以自行将文件上传至服务器
                let id = this.$route.query.storeId
                let content = file.file;
                let merchantPic = this.merchantPic
                let data = new FormData();
                data.append('imagefile', content);
                data.append('storeId', id);
                data.append('merchantPic', merchantPic);
                merchantCodePicUpload(data).then(res => {
                    this.codePic = res.data.data + "?" + Math.random();
                    //this.$router.go(0)
                })
            },
            init() {
                this.page = 0;
                this.getMerchantList();
            },


            onEdit(item, index) {
                this.$router.push({
                    name: 'merchant-detail-setMerchantUser',
                    query: {userId: item.uId, storeId: item.storeId, roleType: item.roleType}
                });
            },
            goback() {
                this.$router.go(-1);
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
            getMerchantList() {
                let id = this.$route.query.storeId
                this.shipData.storeId = id
                merchantDetail(this.shipData).then(res => {
                    this.merchantName = res.data.data.merchantName;
                    this.merchantStatus = res.data.data.merchantStatus;
                    this.pic = res.data.data.merchantPic + "?" + Math.random();
                });
                this.page++;
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
