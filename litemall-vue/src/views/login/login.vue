<template>
    <div class="login">
        <div class="store_header">
            <div class="store_avatar">
                <img src="../../assets/images/avatar_default.png" alt="头像" width="55" height="55">
            </div>
            <div class="store_name">litemall-vue</div>
        </div>

        <div class="login_warp">
            <div class="login_header">
                <span @click="cur=0" :class="{active:cur==0}">手机登录</span>
                <span @click="cur=1" :class="{active:cur==1}">短信登录</span>
            </div>

            <div class="login_content">
                <div v-show="cur==0" class="Cbody_item">
                    <md-field-group>
                        <md-field
                                v-model="account"
                                icon="mobile"
                                placeholder="请输入测试手机号 15110693641"
                                right-icon="clear-full"
                                name="user"
                                data-vv-as="帐号"
                                @right-click="clearText"
                        />
                        <md-field
                                v-model="password"
                                icon="lock"
                                placeholder="请输入测试密码 123"
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

                        <van-button size="large" type="danger" :loading="isLogining" @click="loginSubmit">登录
                        </van-button>
                    </md-field-group>
                </div>

                <div v-show="cur==1" class="Cbody_item">
                    <md-field-group>
                        <md-field
                                v-model="account"
                                icon="account"
                                placeholder="请输入测试手机号 15110693641"
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
                                <span v-else>获取验证码</span>
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

                        <van-button size="large" type="danger" :loading="isLogining" @click="loginSubmit">登录
                        </van-button>
                    </md-field-group>
                </div>
            </div>
        </div>
        <div class="text-desc text-center bottom_positon">技术支持: litemall</div>

    </div>

</template>


<script>
    import field from '@/components/field/';
    import fieldGroup from '@/components/field-group/';

    import {authLoginByAccount} from '@/api/api';
    import {setLocalStorage} from '@/utils/local-storage';
    import {emailReg, mobileReg} from '@/utils/validate';

    import {Toast} from 'vant';

    import {authRegisterCaptcha} from '@/api/api';
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


        methods: {
            clearText() {
                this.account = '';
            },

            getCode() {
                authRegisterCaptcha(this.getMobile());
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
                        ? 'email'
                        : 'username';
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

    .login_header span {
        text-align: center;
        margin-right: 80px;
        cursor: pointer;
    }

    .getCode {
        @include one-border(left);
        text-align: center;
    }

</style>
