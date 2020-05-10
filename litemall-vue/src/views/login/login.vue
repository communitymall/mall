<template>
    <div class="login">
        <div class="store_header">
            <div class="store_avatar">
                <img src="../../assets/images/avatar_default.png" alt="头像" width="55" height="55">
            </div>
            <div class="store_name">菜鸟易购</div>
        </div>
        <div class="login_warp">
            <div class="login_header">
                <van-tabs>
                    <van-tab title="手机登录">
                        <div class="Cbody_item">
                            <md-field-group>
                                <md-field
                                        v-model="account"
                                        icon="mobile"
                                        placeholder="请输入手机号 "
                                        right-icon="clear-full"
                                        name="user"
                                        data-vv-as="帐号"
                                        @right-click="clearText"
                                />
                                <md-field
                                        v-model="password"
                                        icon="lock"
                                        placeholder="请输入密码"
                                        :type="visiblePass ? 'text' : 'password'"
                                        :right-icon="visiblePass ? 'eye-open' : 'eye-close'"
                                        data-vv-as="密码"
                                        name="password"
                                        @right-click="visiblePass = !visiblePass"
                                />

                                <md-field
                                        v-if="false"
                                        v-model="sign"
                                        name="sign"
                                />
                                <md-field
                                        v-if="false"
                                        v-model="mobileName"
                                        name="mobileName"
                                />

                                <div class="clearfix">
                                    <div class="float-l">
                                        <router-link to="/login/registerGetCode">免费注册</router-link>
                                    </div>
                                    <div class="float-r">
                                        <router-link to="/login/forget">忘记密码</router-link>
                                    </div>
                                </div>
                            </md-field-group>
                        </div>
                    </van-tab>
                    <van-tab title="短信登录">
                        <div class="Cbody_item">
                            <md-field-group>
                                <md-field
                                        v-model="account"
                                        icon="mobile"
                                        placeholder="请输入手机号"
                                        right-icon="clear-full"
                                        name="user"
                                        data-vv-as="帐号"
                                        @right-click="clearText"
                                />
                                <md-field
                                        v-model="code"
                                        icon="lock"
                                        placeholder="请输入验证码"
                                        data-vv-as="验证码"
                                        name="code"
                                >

                                    <div  slot="rightIcon" @click="getCode" class="getCode red">
                                        <p  class="yzm" >获取验证码</p>
                                    </div>

                                </md-field>

<!--                                <md-field v-model="code" icon="lock" placeholder="请输入验证码">-->
<!--                                    <div slot="rightIcon" @click="getCode" class="getCode red">-->
<!--                                        <countdown v-if="counting" :time="60000" @end="countdownend">-->
<!--                                            <p>-->
<!--                                                -->
<!--                                            </p>-->
<!--                                           -->
<!--                                        </countdown>-->
<!--                                        <span  v-else>获取验证码</span>-->
<!--                                    </div>-->
<!--                                </md-field>-->

                                <div class="clearfix">
                                    <div class="float-l">
                                        <router-link to="/login/registerGetCode">免费注册</router-link>
                                    </div>
                                    <div class="float-r">
                                        <router-link to="/login/forget">忘记密码</router-link>
                                    </div>
                                </div>
                            </md-field-group>
                        </div>
                    </van-tab>
                </van-tabs>
            </div>
        </div>

        <van-button size="large" type="primary" :loading="isLogining" @click="login">
            <span class="button_login">登录</span>
        </van-button>

        <div class="text-desc text-center bottom_positon">技术支持: 北京华夏景泓科技有限公司</div>

    </div>

</template>

<script>
    import field from '@/components/field/';
    import fieldGroup from '@/components/field-group/';
    import {authLoginByAccount} from '@/api/api';
    import {setLocalStorage} from '@/utils/local-storage';
    import {emailReg, mobileReg} from '@/utils/validate';
    import {Toast} from 'vant';
    import {authCaptcha, getMobiles} from '@/api/api';
    import Vue from 'vue';
    import {Tab, Tabs} from 'vant';
    Vue.use(Tab).use(Tabs);

    export default {
        name: 'login-request',
        components: {
            [field.name]: field,
            [fieldGroup.name]: fieldGroup,
            Toast
        },
        data() {
            return {
                counting: false,
                account: '',
                password: '',
                mobile: '',
                code: '',
                visiblePass: false,
                isLogining: false,
                userInfo: {},

                // 超时定时器
                overTimer: null,
                // 是否超时
                isOvertime: false,
            };
        },
        created() {
            // 开启定时器
            this.overTimer = setTimeout(() => {
                this.isOvertime = true;
            }, 900000)
        },
        destroyed () {
            // 销毁定时器
            clearTimeout(this.overTimer)
        },
        methods: {
            clearText() {
                this.account = '';
            },
            getCode() {
                // if (this.isOvertime) {
                //     alert('页面超时，重新刷新！');
                //     window.location.reload()
                // }
                let obj = {};
                obj[NxtMobileName()] = NxtMobileValue(this.account);
                obj[NxtInputKeyName()] = NxtInputKey();
                authCaptcha(obj).then(res => {
                    if(res.data.errmsg===409){
                        Toast.fail(res.data.errmsg);
                    }
                    Toast.success("验证码已发送！")
                }).catch(error => {
                    Toast.fail(error.data.errmsg);
                })
            },
            getMobile() {
                return {
                    mobile: this.account,
                };
            },
            countdownend() {
                this.counting = false;
            },
            login() {
                let loginData = this.getLoginData();
                loginData[NxtMobileName()]= NxtMobileValue(this.account);
                loginData[NxtInputKeyName()] = NxtInputKey();
                authLoginByAccount(loginData).then(res => {
                    this.userInfo = res.data.data.userInfo;
                    setLocalStorage({
                        Authorization: res.data.data.token,
                        avatar: this.userInfo.avatarUrl,
                        nickName: this.userInfo.nickName
                    });
                    this.routerRedirect();
                }).catch(error => {
                    Toast.fail(error.data.errmsg);
                });
            },
            loginSubmit() {
                //this.isLogining = true;
                try {
                    this.validate();
                    this.login();
                    this.isLogining = false;
                } catch (err) {
                    console.log(err.message);
                    this.isLogining = false;
                }
            },
            routerRedirect() {
                const { query } = this.$route;
                this.$router.replace({
                  name: query.redirect || 'home',
                  query: query
                });
                window.location = '#/user/';
            },

            getLoginData() {
                const password = this.password;
                const code = this.code;
                return {
                     password: password,
                     code: code,
                };
            },
            getUserType(account) {
                const accountType = mobileReg.test(account)
                    ? 'mobile'
                    : emailReg.test(account)
                        ? 'mobile'
                        : 'mobile';
                return accountType;
            },
        }
    };
</script>
<style lang="scss" scoped>
    @import '../../assets/scss/mixin';

    .login {
        position: relative;
        background-color: #fff;
    }

    .yzm {
        font-size: 10px;
        margin-right: 10px;
    }

    .store_header {
        text-align: center;
        padding: 30px 0;

        .store_avatar img {
            border-radius: 50%;
        }

        .store_name {
            padding-top: 5px;
            font-size: 16px;
        }
    }

    .Cbody_item {
        padding-top: 10px;
        a {
            color: $font-color-gray;
        }

        > div {
            width: 100%;
            box-sizing: border-box;
            padding: 0 20px;
        }
        .connect {
            @include one-border(right);
            text-align: right;
        }
    }

    .bottom_positon {
        position: absolute;
        bottom: 30px;
        width: 100%;
    }

    .login_header {
        text-align: center;
        margin-bottom: 20px;
        border-right: 20px;
    }

    .dx_txt {
        margin-right: 0px;
    }

    .mobile_txt {
        margin-left: 100px;
    }

    .login_header span {
        text-align: center;
        margin-right: 90px;
        cursor: pointer;
    }

    .getCode {

        @include one-border(left);
        text-align: center;
    }
</style>
