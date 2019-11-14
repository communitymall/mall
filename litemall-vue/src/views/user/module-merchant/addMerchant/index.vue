<template>
    <van-cell-group>
        <van-field
                v-model="shipData.merchantName"
                required
                label="门店名称"
                placeholder="请输入门店名称"
        />

        <van-field
                v-model="shipData.merchantAddress"
                label="门店地址"
                placeholder="请输入门店地址"
        />
        <van-field
                v-model="shipData.merchantPhone"
                label="门店电话"
                placeholder="请输入门店电话"
        />
        <van-field
                v-model="shipData.merchantCode"
                label="营业执照编号"
                placeholder="请输入营业执照编号"
                required
        />

        <van-field
                v-model="shipData.merchantLeader"
                label="门店负责人"
                placeholder="请输入门店负责人"
                required
        />
        <van-cell title="门店照片" class="cell_middle">
            <van-uploader :after-read="afterRead"/>
        </van-cell>
        <img  :src="pic" width="100%">
        <van-button type="primary" size="large" @click="submit">提交</van-button>
    </van-cell-group>
</template>

<script>
    import field from '@/components/field/';
    import fieldGroup from '@/components/field-group/';

    import {merchantCreate, authInfo, merchantPicVueUpload} from '@/api/api';
    //导入错误的验证
    import {Toast, Uploader, Field} from 'vant';
    import Vue from 'vue';

    Vue.use(Uploader);
    Vue.use(Field);


    export default {
        data() {
            return {
                shipData: {
                    merchantName: '',
                    merchantAddress: '',
                    merchantPhone: '',
                    merchantLeader: '',
                    merchantPic: '',
                    merchantCode: '',
                    userId: '',
                },
                avatar: '',
                formData: {
                    imagefile: '',
                    storeId: '',
                },
                pic: '',
            };

        },

        created() {
            this.getUserInfo();
        },
        methods: {
            afterRead(file) {
                var isFlag = (file.file.type == 'image/jpeg' || file.file.type == 'image/png')? true : false;
                if(!isFlag){
                    this.$message({
                        type: 'warning',
                        message: '上传图片只能是 JPG、PNG 格式!'
                    })
                    return false
                }
                let content = file.file;
                let data = new FormData();
                data.append('imagefile',content);
                data.append('merchantPic',this.shipData.merchantPic,);
                merchantPicVueUpload(data).then(res =>{
                    this.shipData.merchantPic = res.data.data
                    this.pic = res.data.data +"?"+Math.random();
                })
            },
            submit() {//提交的请求
                if (!(/^1[34578]\d{9}$/.test(this.shipData.merchantPhone))) {
                    alert("电话号码格式错误");
                    return false;
                }
                let id = null;
                merchantCreate(this.shipData).then(res => {
                    alert(res.data.data.storeid)
                    return this.$dialog.alert({message: '保存成功'});
                    id = res.data.data.storeid
                }).then(res => {
                    this.$router.go(-1);
                })

            },

            getUserInfo() {
                authInfo().then(res => {
                    this.shipData.userId = res.data.data.userId;
                })
            },

        },

        components: {
            [field.name]: field,
            [fieldGroup.name]: fieldGroup,
            [Uploader.name]: Uploader,
        }
    };
</script>


<style lang="scss" scoped>


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


    .user_information {
        .user_avatar_upload {
            position: relative;
            width: 50px;
            height: 50px;
            border: 1px solid $border-color;

            img {
                max-width: 100%;
                max-height: 100%;
            }

            i {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                font-size: 20px;
                color: $border-color;
            }
        }

        .user_quit {
            margin-top: 20px;
        }
    }
</style>