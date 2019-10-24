<template>
    <div class="set_nickname">
        <van-cell-group>
            <van-field v-model="merchantInfo.merchantLeader" label="创建人姓名"
                       placeholder="请输入用户姓名"
            />
            <van-field v-model="merchantInfo.merchantPhone" label="创建人手机"
                       placeholder="创建人手机"
            />
<!--            <van-radio-group v-model="shipData.roleType">-->
<!--                <van-cell-group>-->
<!--                    <van-cell title="管理员" clickable @click="shipData.roleType = '0'">-->
<!--                        <van-radio slot="right-icon" name="0"/>-->
<!--                    </van-cell>-->
<!--                    <van-cell title="厨师" clickable @click="shipData.roleType = '1'">-->
<!--                        <van-radio slot="right-icon" name="1"/>-->
<!--                    </van-cell>-->
<!--                    <van-cell title="店员" clickable @click="shipData.roleType = '2'">-->
<!--                        <van-radio slot="right-icon" name="2"/>-->
<!--                    </van-cell>-->
<!--                </van-cell-group>-->
<!--            </van-radio-group>-->

        </van-cell-group>

        <div class="bottom_btn">
            <van-button size="large" type="danger" @click="setConsignee">设置为默认收货人</van-button>
        </div>

        <div class="bottom_btn">
            <van-button size="large" type="danger" @click="saveMerchantUser">返回</van-button>
        </div>
    </div>
</template>


<script>
    import {updateUserStore, findOneMerchantUser, setConsignee, findMerchantLeader} from '@/api/api';
    import {Field, RadioGroup, Radio} from 'vant';
    import {Cell, CellGroup} from 'vant';

    Vue.use(RadioGroup);
    Vue.use(Radio);
    Vue.use(Cell).use(CellGroup);

    import Vue from 'vue';

    export default {
        data() {
            return {
                name: '',
                roleType: '',
                shipData: {
                    usId: '',
                    name: '',
                    roleType: '',
                },
                merchantInfo:{},
                userData: {
                    storeId: '',
                },
                consigneeData: {
                    userId: '',
                    storeId: '',
                },
            };
        },
        init() {
            this.findMerchantUser();
            this.findLeader();
        },
        created() {
            this.findMerchantUser()
        },
        methods: {
            saveMerchantUser() {
                this.$router.go(-1);
                // if (true) {
                //     let id = this.$route.query.userId
                //     this.shipData.usId = id
                //     this.shipData.name = this.userData.name
                //     updateUserStore(this.shipData)
                //         .then(res => {
                //             //localStorage.setItem('merchantName', res.data.data.merchantName);
                //             return this.$dialog.alert({message: '保存成功'});
                //         })
                //         .then(() => {
                //             this.$router.go(-1);
                //         });
                // }
            },
            findMerchantUser() {
                let id = this.$route.query.id
                this.userData.storeId = id
                findMerchantLeader(this.userData)
                    .then(res => {
                        this.merchantInfo = res.data.data
                    })
                    .then(() => {
                    });
            },
            setConsignee() {
                let userId = -2
                let storeId = this.merchantInfo.id
                this.consigneeData.storeId = storeId
                this.consigneeData.userId = userId
                setConsignee(this.consigneeData)
                    .then(res => {
                        return this.$dialog.alert({message: '已经设为默认收货人！'});
                    })
                    .then(() => {
                    });
            },

        },
        components: {
            [Field.name]: Field
        }
    };
</script>


<style scoped>
    .bottom_btn {
        padding: 30px 15px 0 15px;
    }
</style>
