<template>
	<div class="set_nickname">
		<van-nav-bar title="修改商户负责人" left-text="返回" left-arrow @click-left="goback"/>
		<van-cell-group>
			<van-field v-model="merchantLeader" label="商户负责人"
					   placeholder="请输入商户负责人"
			/>
		</van-cell-group>

		<div class="bottom_btn">
			<van-button size="large" type="danger" @click="saveMerchantLeader">保存</van-button>
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
					merchantLeader: ''
				},

			};
		},

		created() {
			this.getNick();
		},

		methods: {
			getNick() {
				this.merchantLeader = localStorage.getItem('merchantLeader') || '';
			},
			saveMerchantLeader() {
				if (true) {
					let id = this.$route.query.storeId
					this.shipData.id = id
					this.shipData.merchantLeader =this.merchantLeader
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
			[NavBar.name]:NavBar,
		}
	};
</script>


<style scoped>
	.bottom_btn {
		padding: 30px 15px 0 15px;
	}
</style>
