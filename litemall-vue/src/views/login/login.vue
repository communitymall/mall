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
                <van-tabs >
                    <van-tab title="手机登录"><div  class="Cbody_item">
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
                            <div class="clearfix">
                                <div class="float-l">
                                    <router-link to="/login/registerGetCode">免费注册</router-link>
                                </div>
                                <div class="float-r">
                                    <router-link to="/login/forget">忘记密码</router-link>
                                </div>
                            </div>
                            <van-button  size="large" type="primary" :loading="isLogining" @click="loginSubmit">
                                <span class="button_login">登录</span>
                            </van-button>
                        </md-field-group>
                    </div>
                    </van-tab>
                    <van-tab title="短信登录"><div  class="Cbody_item">
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

                                <div slot="rightIcon" @click="getCode" class="getCode red">
                                    <countdown v-if="counting" :time="60000" @countdownend="countdownend">
                                        <template slot-scope="props">{{ +props.seconds || 60 }}秒后获取</template>
                                    </countdown>
                                    <span class="yzm" v-else>获取验证码</span>
                                </div>

                            </md-field>
                            <div class="clearfix">
                                <div class="float-l">
                                    <router-link to="/login/registerGetCode">免费注册</router-link>
                                </div>
                                <div class="float-r">
                                    <router-link to="/login/forget">忘记密码</router-link>
                                </div>
                            </div>
                            <van-button  size="large" type="primary" :loading="isLogining" @click="loginSubmit">
                                <span class="button_login">登录</span>
                            </van-button>
                        </md-field-group>
                    </div>
                    </van-tab>
                </van-tabs>
            </div>
        </div>
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
    import {authCaptcha,getMobiles} from '@/api/api';
    import Vue from 'vue';
    import { Tab, Tabs } from 'vant';

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
                cur: 0, //默认选中第一个tab
                account: '',
                password: '',
                mobile: '',
                code: '',
                visiblePass: false,
                isLogining: false,
                userInfo: {}
            };
        },
        created(){
            this.init();
        },

        methods: {
            init(){
                //默认手机号
                if(typeof NXT_GET_TOKEN === "function"){
                    getMobiles(NXT_GET_TOKEN()).then(res=> {
                        if(res != null && res.data != null && res.data.length>0){
                            this.account = res.data[0];
                        }
                    }).catch(error => {
                        console.log("defalut mobile error");
                    })
                }
            },

            clearText() {
                this.account = '';
            },
            getCode() {
                authCaptcha(this.getMobile()).then(res => {

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
            validate() {

            },

            login() {
                let loginData = this.getLoginData();
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
                this.isLogining = true;
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
                // const { query } = this.$route;
                // this.$router.replace({
                //   name: query.redirect || 'home',
                //   query: query
                // });
                window.location = '#/user/';
            },

            getLoginData() {
                const password = this.password;
                const account = this.getUserType(this.account);
                const code = this.code;
                return {
                    [account]: this.account,
                    password: password,
                    code: code
                };
            },
            getUserType(account) {
                const accountType = mobileReg.test(account)
                    ? 'mobile'
                    : emailReg.test(account)
                        ? 'mobile'
                        : 'mobile';
                return accountType;
            }
        }
    };
</script>
<style lang="scss" scoped>
    @import '../../assets/scss/mixin';
    .login {
        position: relative;
        background-color: #fff;
    }
    .button_login{
        margin-left: 180px;
    }
    .yzm{
        margin-left: 400px;
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
    .register {
        padding-top: 40px;
        color: $font-color-gray;
        a {
            color: $font-color-gray;
        }
        > div {
            width: 50%;
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
