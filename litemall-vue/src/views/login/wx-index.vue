<template>

</template>

<script>
    import {loginByOpenid} from '@/api/api';
    import {Toast} from 'vant';
    import {setLocalStorage} from '@/utils/local-storage';

    export default {
        name: "wx-index",
        data() {
            return {
                userInfo: {},
                shipData:{
                    openId : '',
                },
            };
        },

        created() {
            this.findUser()
        },

        findUser(){
            let id = this.$route.query.openId;
            this.shipData.openId = id;
            loginByOpenid(this.shipData).then(res => {
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
        routerRedirect() {
            // const { query } = this.$route;
            // this.$router.replace({
            //   name: query.redirect || 'home',
            //   query: query
            // });
            window.location = '#/user/';
        },
    }
</script>

<style scoped>

</style>