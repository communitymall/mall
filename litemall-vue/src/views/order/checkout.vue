<template>
    <div class="order">
        <van-cell-group>
            <van-cell v-if="" isLink @click="goAddressList()" title="收货信息">
                <div slot="label">
                    <div>
                        <span>{{ merchantInfo.merchantLeader }} </span>
                        <span>{{ merchantInfo.merchantPhone }} </span>
                    </div>
                    <div>
                        {{ merchantInfo.merchantAddress }}
                    </div>
                </div>
            </van-cell>
        </van-cell-group>

        <van-cell-group>
            <van-cell class="order-coupon" title="优惠券" is-link :value="getCouponValue()" @click="getCoupons"/>
        </van-cell-group>

        <!-- 优惠券列表 -->
        <van-popup v-model="showList" position="bottom">
            <van-coupon-list
                    :coupons="coupons"
                    :chosen-coupon="chosenCoupon"
                    :disabled-coupons="disabledCoupons"
                    @change="onChange"
                    @exchange="onExchange"
            />
        </van-popup>

        <van-cell-group>
            <van-cell class="order-coupon" title="支付类型">
                <van-dropdown-menu class="select-payType">
                    <van-dropdown-item v-model="payType" :options="option"/>
                </van-dropdown-menu>
            </van-cell>
        </van-cell-group>

        <van-card
                v-for="item in checkedGoodsList"
                :key="item.id"
                :title="item.goodsName"
                :num="item.number"
                :price="item.price +'.00'"
                :thumb="item.picUrl"
        >
            <div slot="desc">
                <div class="van-card__desc">
                    <van-tag plain style="margin-right:6px;" v-for="(spec, index) in item.specifications" :key="index">
                        {{spec}}
                    </van-tag>
                </div>
            </div>
        </van-card>

        <van-cell-group>
            <van-cell title="商品金额">
                <span class="red">{{goodsTotalPrice * 100 | yuan}}</span>
            </van-cell>
            <van-cell title="邮费">
                <span class="red">{{ freightPrice * 100| yuan}}</span>
            </van-cell>
            <van-cell title="优惠券">
                <span class="red">-{{ couponPrice * 100| yuan}}</span>
            </van-cell>


            <van-field v-model="message" placeholder="请输入备注" label="订单备注">
                <template slot="icon">{{message.length}}/50</template>
            </van-field>
        </van-cell-group>

        <van-submit-bar
                :price="actualPrice*100"
                label="总计："
                buttonText="提交订单"
                :disabled="isDisabled"
                @submit="onSubmit"
        />
    </div>
</template>

<script>

    import {Card, Tag, ard, Field, SubmitBar, Toast} from 'vant';
    import {CouponCell, CouponList, Popup} from 'vant';
    import {cartCheckout, orderSubmit, couponSelectList, merchantDetail,findOneMerchantUser} from '@/api/api';
    import {getLocalStorage, setLocalStorage} from '@/utils/local-storage';
    import dayjs from 'dayjs';

    import Vue from 'vue';
    import {DropdownMenu, DropdownItem} from 'vant';

    Vue.use(DropdownMenu).use(DropdownItem)


    export default {
        data() {
            return {
                checkedGoodsList: [],
                checkedAddress: {},
                merchantInfo: {}, //门店的信息
                consigneeInfo: {},//收货人的信息
                consigneeId: -1,
                payType: 0, //支付类型
                availableCouponLength: 0, // 可用的优惠券数量
                goodsTotalPrice: 0, //商品总价
                freightPrice: 0, //快递费
                couponPrice: 0, //优惠券的价格
                grouponPrice: 0, //团购优惠价格
                orderTotalPrice: 0, //订单总价
                actualPrice: 0, //实际需要支付的总价
                message: '',

                isDisabled: false,
                showList: false,
                chosenCoupon: -1,
                coupons: [],
                disabledCoupons: [],
                option: [
                    {text: '请选择支付类型', value: 0},
                    {text: '在线支付', value: 1},
                    {text: '货到付款', value: 2},
                ],
            };
        },
        created() {
            this.init();
        },

        methods: {
            onSubmit() {
                // const {AddressId, CartId, CouponId} = getLocalStorage('AddressId', 'CartId', 'CouponId');
                // if (AddressId === null) {
                //     Toast.fail('请设置收货地址');
                //     return;
                // }
                const {CartId, CouponId} = getLocalStorage('CartId', 'CouponId');

                if (this.payType === 0) {
                    Toast.fail('请选择支付类型');
                    return;
                }
                const {storeId} = getLocalStorage('storeId');
                if (storeId === null) {
                    Toast.fail('请设置收货门店');
                    return;
                }
                this.isDisabled = true;
                orderSubmit({
                    //addressId: AddressId,
                    cartId: CartId,
                    couponId: CouponId,
                    grouponLinkId: 0,
                    grouponRulesId: 0,
                    message: this.message,
                    payType: this.payType,
                    storeId: storeId,
                }).then(res => {
                    // 下单成功，重置下单参数。
                    setLocalStorage({AddressId: 0, CartId: 0, CouponId: 0});
                    //如果是货到付款的订单，直接跳转到相应的页面
                    if(this.payType==2){
                        this.$router.push({
                            name: 'payResult',
                            params: {orderId: orderId}
                        });
                        return;
                    };
                    let orderId = res.data.data.orderId;
                    this.$router.push({
                        name: 'payment',
                        params: {orderId: orderId}
                    });
                }).catch(error => {
                    this.isDisabled = false;
                    this.$toast("下单失败");
                })
            },
            goAddressList() {
                this.$router.push({
                    path: '/user/selectMerchant'
                });
            },
            getCouponValue() {
                if (this.couponPrice !== 0) {
                    return "-¥" + this.couponPrice + ".00元"
                }
                if (this.availableCouponLength !== 0) {
                    return this.availableCouponLength + "张可用"
                }
                return '没有可用优惠券'
            },
            getPayType() {//支付类型
                if (this.payType == 1) {
                    return '在线支付'
                }
                if (this.payType == 2) {
                    return '货到付款'
                }
                return '请选择支付类型'
            },
            getCoupons() {
                const {AddressId, CartId, CouponId} = getLocalStorage('AddressId', 'CartId', 'CouponId');
                couponSelectList({cartId: CartId, grouponRulesId: 0}).then(res => {
                    var cList = res.data.data
                    this.coupons = []
                    for (var i = 0; i < cList.length; i++) {
                        var c = cList[i]
                        var coupon = {
                            id: c.id,
                            name: c.name,
                            condition: c.min,
                            value: c.discount * 100,
                            description: c.desc,
                            startAt: new Date(c.startTime).getTime() / 1000,
                            endAt: new Date(c.endTime).getTime() / 1000,
                            valueDesc: c.discount,
                            unitDesc: '元'
                        }
                        this.coupons.push(coupon)
                        if (c.id === this.couponId) {
                            this.chosenCoupon = i;
                            break;
                        }
                    }
                    this.showList = true
                })
            },
            init() {
                const {AddressId, CartId, CouponId} = getLocalStorage('AddressId', 'CartId', 'CouponId');

                cartCheckout({
                    cartId: CartId,
                    addressId: AddressId,
                    couponId: CouponId,
                    grouponRulesId: 0
                }).then(res => {
                    var data = res.data.data

                    this.checkedGoodsList = data.checkedGoodsList;
                    this.checkedAddress = data.checkedAddress;
                    this.availableCouponLength = data.availableCouponLength;
                    this.actualPrice = data.actualPrice;
                    this.couponPrice = data.couponPrice;
                    this.grouponPrice = data.grouponPrice;
                    this.freightPrice = data.freightPrice;
                    this.goodsTotalPrice = data.goodsTotalPrice;
                    this.orderTotalPrice = data.orderTotalPrice;

                    setLocalStorage({AddressId: data.addressId, CartId: data.cartId, CouponId: data.couponId});
                });

                const {storeId} = getLocalStorage('storeId');
                if (storeId != null) {
                    merchantDetail({
                        storeId: storeId,
                    }).then(res => {
                        this.merchantInfo = res.data.data;
                        this.consigneeId =res.data.data.consigneeId;
                        if(this.consigneeId !=-1){
                            findOneMerchantUser({
                                id: this.consigneeId,
                            }).then(res => {
                                this.merchantInfo.merchantLeader = res.data.data.name;
                                this.merchantInfo.merchantPhone = res.data.data.mobile;
                            }).catch(error => {

                            })

                        }
                    }).catch(error => {
                    })
                }



            },
            onChange(index) {
                this.showList = false;
                this.chosenCoupon = index;

                if (index === -1) {
                    setLocalStorage({CouponId: -1});
                } else {
                    const couponId = this.coupons[index].id;
                    setLocalStorage({CouponId: couponId});
                }

                this.init()
            },
            onExchange() {
                this.$toast("兑换暂不支持");
            }
        },

        components: {
            [Toast.name]: Toast,
            [SubmitBar.name]: SubmitBar,
            [Card.name]: Card,
            [Field.name]: Field,
            [Tag.name]: Field,
            [CouponCell.name]: CouponCell,
            [CouponList.name]: CouponList,
            [Popup.name]: Popup
        }
    };
</script>


<style lang="scss" scoped>
    .order-coupon {
        margin-top: 10px;
    }

    .select-payType {
        margin-left: 250px;
        bottom: 15px;
    }
</style>