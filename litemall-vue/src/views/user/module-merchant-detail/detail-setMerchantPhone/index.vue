<template>
	<div class="set_nickname">
		<van-nav-bar title="修改商户电话" left-text="返回" left-arrow @click-left="goback"/>
		<van-cell-group>
			<van-field v-model="merchantPhone" label="商户电话"
					   placeholder="请输入商户电话"
			/>
		</van-cell-group>

		<div class="bottom_btn">
			<van-button size="large" type="danger" @click="saveMerchantPhone">保存</van-button>
		</div>
	</div>
</template>


<script>
	import {merchantUpdate} from '@/api/api';
	import {Field,NavBar} from 'vant';

	export default {
		data() {
			return {
				shipData :{
					id:'',
					merchantPhone: ''
				},

			};
		},

		created() {
			this.getNick();
		},

		methods: {
			getNick() {
				this.merchantPhone = localStorage.getItem('merchantPhone') || '';
			},
			saveMerchantPhone() {
				if (true) {
					let id = this.$route.query.storeId
					this.shipData.id = id
					this.shipData.merchantPhone =this.merchantPhone
					if (!(/^1[34578]\d{9}$/.test(this.merchantPhone))) {
						alert("电话号码格式错误");
						return false;
					}
					merchantUpdate(this.shipData)
							.then(res => {
								//localStorage.setItem('merchantName', res.data.data.merchantName);
								return this.$dialog.alert({message: '保存成功'});
							})
							.then(() => {
								this.$router.go(-1);
							});
				}
			},
			goback() {
				this.$router.go(-1);
			},
		},

		components: {
			[Field.name]: Field,
			[NavBar.name]:NavBar
		}
	};
</script>


<style scoped>
	.bottom_btn {
		padding: 30px 15px 0 15px;
	}
</style>
