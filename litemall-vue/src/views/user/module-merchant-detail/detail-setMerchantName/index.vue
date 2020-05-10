<template>
    <div class="set_nickname">
        <van-nav-bar title="修改商户名称" left-text="返回" left-arrow @click-left="goback"/>
        <van-cell-group>
            <van-field v-model="merchantName" label="商户名称"
                       placeholder="请输入新商户名称"
            />
        </van-cell-group>

        <div class="bottom_btn">
            <van-button size="large" type="danger" @click="saveMerchantName">保存</van-button>
        </div>
    </div>
</template>


<script>
    import {merchantUpdate} from '@/api/api';
    import {Field,Toast,NavBar} from 'vant';

    export default {
        data() {
            return {
                shipData: {
                    id: '',
                    merchantName: ''
                },

            };
        },

        created() {
            this.getNick();
        },

        methods: {
            getNick() {
                this.merchantName = localStorage.getItem('merchantName') || '';
            },
            saveMerchantName() {
                if (true) {
                    let id = this.$route.query.storeId
                    this.shipData.id = id
                    this.shipData.merchantName = this.merchantName
                    merchantUpdate(this.shipData).then(res => {
                        //localStorage.setItem('merchantName', res.data.data.merchantName);
                        return this.$dialog.alert({message: '保存成功'});
                    }).then(() => {
                        this.$router.go(-1);
                    }).catch(res => {
                        Toast.fail("门店名称已被注册！")
                        return false;
                    })
                }
            },
            goback() {
                this.$router.go(-1);
            },
        },


        components: {
            [Field.name]: Field,
            [NavBar.name]: NavBar,
        }
    };
</script>


<style scoped>
    .bottom_btn {
        padding: 30px 15px 0 15px;
    }
</style>
