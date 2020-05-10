<template>
    <div>
        <van-cell-group>

            <van-field
                    label="当前密码"
                    v-model="oldPassword"
                    type="password"
                    placeholder="请输入当前密码"
            />

            <van-field
                    label="新密码"
                    v-model="password"
                    type="password"
                    placeholder="请输入新密码"
            />
        </van-cell-group>

        <div class="bottom_btn">
            <van-button size="large" type="danger" @click="modifypassword">保存</van-button>
        </div>
    </div>
</template>


<script>
    import {authCaptcha, authReset, authLogout,authProfile} from '@/api/api';
    import {removeLocalStorage} from '@/utils/local-storage';
    import {Field} from 'vant';

    export default {
        data: () => ({
            oldPassword:'',
            password: '',
            mobile: '',
            code: '',
            nickName:'',
            counting: false
        }),

        created() {
            this.getNick();
        },

        methods: {
            getNick() {
                this.nickName = localStorage.getItem('nickName') || '';
            },

            modifypassword() {
                if (this.passwordValid()) {
                    authProfile({
                        password: this.password,
                        mobile: this.nickName,
                        oldPassword: this.oldPassword
                    })
                        .then(() => {
                            this.$dialog.alert({message: '保存成功, 请重新登录.'})
                            authLogout().then(res => {
                                removeLocalStorage('Authorization')
                                removeLocalStorage('avatar')
                                removeLocalStorage('nickName')
                                this.$router.push({name: 'home'});
                            });
                        });
                }
            },
            passwordValid() {
                return true;
            },
            getCode() {
                if (this.mobile === '') {
                    this.$toast.fail('请输入号码');
                    return
                }

                if (!this.counting) {
                    authCaptcha({
                        mobile: this.mobile,
                        type: 'change-password'
                    }).then(() => {
                        this.$toast.success('发送成功');
                        this.counting = true;
                    }).catch(error => {
                        this.$toast.fail(error.data.errmsg);
                        this.counting = false;
                    })

                }
            },
        },

        components: {
            [Field.name]: Field
        }
    };
</script>


<style lang="scss" scoped>
    @import '../../../../assets/scss/var';
    @import '../../../../assets/scss/mixin';

    .bottom_btn {
        padding: 30px 15px 0 15px;
    }

    .verifi_code {
        @include one-border;
        padding-left: 10px;

        &::after {
            border-bottom: 0;
            border-left: 1px solid $border-color;
        }

        &_counting {
            color: $font-color-gray;
        }
    }
</style>
