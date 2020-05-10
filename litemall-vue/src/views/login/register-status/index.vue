<template>
	<div class="payment_status">
		<div class="status_top">
			<van-icon :name="statusIcon" :class="statusClass" />
			<div>{{statusText}}</div>
		</div>

		<div class="status_text"><span class="red">3秒</span>后返回到登录页, 您也可以<router-link to="/login" class="red">点此登录</router-link></div>
	</div>
</template>

<script>
export default {
  name: 'payment-status',

  props: {
    status: String
  },

  data() {
    return {
      isSuccess: true
    };
  },

	created() {
		this.goGrdoupRecor();
	},
	methods: {
//  3秒后进入群发记录
		goGrdoupRecor(){
			const TIME_COUNT = 3;
			if(!this.timer){
				this.count = TIME_COUNT;
				this.show = false;
				this.timer = setInterval(()=>{
					if(this.count > 0 && this.count <= TIME_COUNT){
						this.count--;
					}else{
						this.show = true;
						clearInterval(this.timer);
						this.timer = null;
						//跳转的页面写在此处
						this.$router.push({name: 'login'});
					}
				},1000)
			}
		},
	},


  computed: {
    statusText() {
      return this.isSuccess ? '注册成功' : '注册失败';
    },
    statusIcon() {
      return this.isSuccess ? 'checked' : 'fail';
    },
    statusClass() {
      return this.isSuccess ? 'success_icon' : 'fail_icon';
    }
  },

  activated() {
    this.isSuccess = this.status === 'success';
  }
};
</script>


<style lang="scss" scopd>
.payment_status {
  padding-top: 30px;
  box-sizing: border-box;
  background-color: #fff;
  text-align: center;
}

.status_top {
  margin-bottom: 15px;
  i {
    margin-bottom: 5px;
  }
  > div {
    font-size: 18px;
  }
}

.status_text {
  color: $font-color-gray;
  margin-bottom: 50px;
}

.status_icon {
  font-size: 80px;
}

i.success_icon {
  @extend .status_icon;
  color: #06bf04;
}

i.fail_icon {
  @extend .status_icon;
  color: #f44;
}
</style>
