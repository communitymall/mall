<template>
    <md-field-group class="register_view">
        <div>我们将发送验证码到您的手机</div>
        <md-field
                v-model="mobile"
                icon="mobile"
                placeholder="请输入手机号"
        >
        </md-field>
        <div class="register_submit">
            <van-button size="large" type="danger" @click="checkMobile">下一步</van-button>
        </div>

        <div class="register_footer">
            已有账号?
            <router-link to="/login" class="red">登录</router-link>
        </div>
    </md-field-group>
</template>

<script>
    import field from '@/components/field/';
    import fieldGroup from '@/components/field-group/';
    import {authRegisterCaptcha} from '@/api/api';
    //导入错误的验证
    import {Toast} from 'vant';

    export default {

        components: {
            [field.name]: field,
            [fieldGroup.name]: fieldGroup,
            Toast
        },
        data() {
            return {
                mobile: '',
                isAvailable: true,
                NxtMobileName:'',
                NxtMobileValue:'',
            };
        },

        methods: {
            //查看手机号是否被注册
            checkMobile() {
                const isAvailable = this.isAvailable;
                let obj = {};
                obj[NxtMobileName()] = NxtMobileValue(this.mobile);

                obj[NxtInputKeyName()] = NxtInputKey();

                this.NxtMobileName =NxtMobileName();
                this.NxtMobileValue = NxtMobileValue(this.mobile);
                authRegisterCaptcha(obj).then(res => {
                    this.isAvailable = true
                    this.$router.push({path: 'registerSubmit', query: {NxtMobileName: this.NxtMobileName ,NxtMobileValue :this.NxtMobileValue = NxtMobileValue(this.mobile)}});
                }).catch(error => {
                    this.isAvailable = false
                    Toast.fail(error.data.errmsg);
                });
            }
        },
    };
</script>

<style lang="scss" scoped>
    div.register_view {
        background-color: #fff;
        padding-top: 30px;
    }

    div.register_submit {
        padding-top: 30px;
        padding-bottom: 20px;
    }

    .register_footer {
        text-align: right;
        color: $font-color-gray;
    }
</style>
