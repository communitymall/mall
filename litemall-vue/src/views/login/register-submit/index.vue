<template>
    <md-field-group class="register_submit">
        <md-field v-model="code" icon="mobile" placeholder="请输入验证码">
            <div slot="rightIcon" @click="getCode" class="getCode red">
                <countdown v-if="counting" :time="60000" @end="countdownend">
                    <template slot-scope="props">{{ +props.seconds || 60 }}秒后获取</template>
                </countdown>
                <span v-else>重新获取验证码</span>
            </div>
        </md-field>
        <md-field v-model="password" icon="lock" placeholder="请输入密码"
                  :type="visiblePass ? 'text' : 'password'"
        />

        <md-field v-model="repeatPassword" icon="lock" placeholder="请再次确认密码"
                  :type="visiblePass ? 'text' : 'password'"
        />
        <div class="red" v-show="isErrow">两次密码输入不一致</div>

        <div class="register_submit_btn">
            <van-button type="danger" size="large" @click="registerSubmit">确定</van-button>
        </div>
    </md-field-group>
</template>

<script>
    import field from '@/components/field/';
    import fieldGroup from '@/components/field-group/';

    import {authRegister} from '@/api/api';
    //导入错误的验证
    import {Toast} from 'vant';

    import {authRegisterCaptcha} from '@/api/api';

    export default {
        data() {
            return {
                NxtMobileName: this.$route.query.NxtMobileName,
                NxtMobileValue: this.$route.query.NxtMobileValue,
                counting: true,
                code: '',
                password: '',
                repeatPassword: '',
                isErrow: false
            };
        },
        methods: {
            registerSubmit() {
                const password = this.password
                const repeatPassword = this.repeatPassword
                let regData = this.getRegData();
                regData[this.NxtMobileName]=this.NxtMobileValue;
                if (password === repeatPassword) {
                    this.isErrow = false;

                    authRegister(regData).then(res => {
                        const status = 'success';
                        this.$router.push({
                            name: 'registerStatus',
                            params: {status: status}
                        });
                    }).catch(error => {
                        Toast.fail(error.data.errmsg);
                    })
                } else {
                    this.isErrow = true;
                }

            },
            getRegData() {
                return {
                    // mobile: this.mobile,
                    code: this.code,
                    password: this.password
                };
            },
            getCode() {
                authRegisterCaptcha(this.getMobile()).then(res => {
                    Toast.success(res.data.errmsg)
                }).catch(error => {
                    Toast.fail(error.data.errmsg);
                });
            },
            getMobile() {
                return {
                    mobile: this.mobile
                };
            },

            countdownend() {
                this.counting = false;
            }
        },

        components: {
            [field.name]: field,
            [fieldGroup.name]: fieldGroup,
            Toast
        }
    };
</script>


<style lang="scss" scoped>
    @import '../../../assets/scss/mixin';

    .register_submit {
        padding-top: 40px;
        background-color: #fff;
    }

    .register_submit_btn {
        padding-top: 30px;
    }

    .getCode {
        @include one-border(left);
        text-align: center;
    }

    .time_down {
        color: $red;
    }
</style>
