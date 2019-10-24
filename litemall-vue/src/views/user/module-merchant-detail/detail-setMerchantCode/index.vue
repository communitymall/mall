<template>
	<div class="set_nickname">
		<van-cell-group>
			<van-field v-model="merchantCode" label="商户商户营业执照编号"
					   placeholder="请输入商户营业执照编号"
			/>
		</van-cell-group>

		<div class="bottom_btn">
			<van-button size="large" type="danger" @click="saveMerchantCode">保存</van-button>
		</div>
	</div>
</template>


<script>
	import {merchantUpdate} from '@/api/api';
	import {Field} from 'vant';

	export default {
		data() {
			return {
				shipData :{
					id:'',
					merchantCode: ''
				},

			};
		},

		created() {
			this.getNick();
		},

		methods: {
			getNick() {
				this.merchantCode = localStorage.getItem('merchantCode') || '';
			},
			saveMerchantCode() {
				if (true) {
					let id = this.$route.query.storeId
					this.shipData.id = id
					this.shipData.merchantCode =this.merchantCode
					merchantUpdate(this.shipData)
							.then(res => {
								//localStorage.setItem('merchantName', res.data.data.merchantName);
								return this.$dialog.alert({message: '保存成功'});
							})
							.then(() => {
								this.$router.go(-1);
							});
				}
			}
		},

		components: {
			[Field.name]: Field
		}
	};
</script>


<style scoped>
	.bottom_btn {
		padding: 30px 15px 0 15px;
	}
</style>
