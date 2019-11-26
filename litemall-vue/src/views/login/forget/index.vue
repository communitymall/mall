<template>
    <md-field-group class="foget_view">
        <md-field
                v-model="mobile"
                icon="mobile"
                placeholder="请输入手机号"/>

        <md-field
                v-model="code"
                icon="lock"
                placeholder="请输入短信验证码"
        >
            <div slot="rightIcon" @click="getCode" class="getCode red">
                <countdown v-if="counting" :time="60000" @countdownend="countdownend">
                    <template slot-scope="props">{{ +props.seconds || 60 }}秒后获取</template>
                </countdown>
                <span v-else>获取验证码</span>
            </div>
        </md-field>

        <md-field
                v-model="password"
                type="password"
                icon="lock"
                :is-error="isErrow"
                placeholder="请输入新密码"/>

        <md-field
                v-model="passwordRepeat"
                type="password"
                icon="lock"
                :is-error="isErrow"
                placeholder="请再次输入密码"/>
        <div class="red" v-show="isErrow">两次密码输入不一致</div>

        <div class="foget_submit">
            <van-button size="large" type="danger" :loading="isLogining" @click="submitCode">重置</van-button>
        </div>
    </md-field-group>
</template>

<script>
    import field from '@/components/field/';
    import fieldGroup from '@/components/field-group/';
    //导入请求验证码的方法
    import {authCaptcha} from '@/api/api';

    import {authReset} from '@/api/api';

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
                isLogining: false,
                counting: false,
                mobile: '',
                data: '',
                password: '',
                passwordRepeat: '',
                isErrow: '',
                code: ''
            };
        },

        methods: {
            submitCode() {
                const password = this.password;
                const passwordRepeat = this.passwordRepeat;
                if (password === passwordRepeat) {
                    let getResetData = this.getResetData();
                    getResetData[NxtMobileName()]= NxtMobileValue(this.mobile);
                    authReset(getResetData).then(res => {
                        alert("您已经修改密码成功，点击跳转登录页面！")
                        this.$router.push({name: 'login'});
                        return
                    }).catch(error => {
                        Toast.fail(error.data.errmsg);

                    });
                    //this.$router.push({name: 'forgetReset'});
                }else {
                    this.isErrow = true;
                }

            },
            getCode() {
                this.counting = true;
                let mobile = {};
                mobile[NxtMobileName()]= NxtMobileValue(this.mobile);
                authCaptcha(mobile).then(res => {
                    this.data = res.data.data
                }).catch(error => {
                    Toast.fail(error.data.errmsg);
                });

            },
            countdownend() {
                this.counting = false;
            },

            getData() {
                const data = this.data
            },
            getResetData() {
                const password = this.password;
                const passwordRepeat = this.passwordRepeat;
                const code = this.code;
                return {
                    passwordRepeat: passwordRepeat,
                    password: password,
                    code: code
                };
            }

        },

        components: {
            [field.name]: field,
            [fieldGroup.name]: fieldGroup
        }
    };
</script>

<style lang="scss" scoped>
    @import '../../../assets/scss/mixin';

    div.foget_view {
        background-color: #fff;
        padding-top: 30px;
    }

    div.foget_submit {
        padding-top: 30px;
        padding-bottom: 20px;
    }

    .getCode {
        @include one-border(left);
        text-align: center;
    }

    .time_down {
        color: $red;
    }
</style>
