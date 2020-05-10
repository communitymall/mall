const tab_user = () => import('@/views/user/tabbar-user');
const UserCollect = () => import('@/views/user/module-collect');
const UserAddress = () => import('@/views/user/module-address');
const UserAddressEdit = () => import('@/views/user/module-address-edit');
const UserServer = () => import('@/views/user/module-server');
const UserHelp = () => import('@/views/user/module-help');
const UserFeedback = () => import('@/views/user/module-feedback');

const UserMerchant = () => import('@/views/user/module-merchant');
const UserAddMerchant = () => import('@/views/user/module-merchant/addMerchant');

const UserMerchantDetail = () => import('@/views/user/module-merchant-detail');
const UserSetMerchantName= () => import('@/views/user/module-merchant-detail/detail-setMerchantName');
const UserSetMerchantAddress= () => import('@/views/user/module-merchant-detail/detail-setMerchantAddress');
const UserSetMerchantLeader= () => import('@/views/user/module-merchant-detail/detail-setMerchantLeader');
const UserSetMerchantPhone= () => import('@/views/user/module-merchant-detail/detail-setMerchantPhone');
const UserAddMerchantUser= () => import('@/views/user/module-merchant-detail/detail-addMerchantUser');
const UserSetMerchantCode= () => import('@/views/user/module-merchant-detail/detail-setMerchantCode');

const UserMerchantAudit = () => import('@/views/user/module-merchant-audit');

const UserSelectMerchant = () => import('@/views/user/module-selectMerchant');

const MerchantSetUser= () => import('@/views/user/module-merchant-detail/detail-setMerchantUser');

const UserMerchantLeaderDetail= () => import('@/views/user/module-merchantLeader-detail');

const UserInformation = () => import('@/views/user/user-information-set');
const UserInfo_SetMobile = () => import('@/views/user/user-information-set/set-mobile');
const UserInfo_SetNickname = () => import('@/views/user/user-information-set/set-nickname');
const UserInfo_SetPassword = () => import('@/views/user/user-information-set/set-password');

const UserOrderList = () => import('@/views/user/order-list');
const UserCouponList = () => import('@/views/user/coupon-list');
const UserRefundList = () => import('@/views/user/refund-list');

const Tabbar = () => import('@/components/Tabbar/');

export default [
  {
    path: '/user',
    name: 'user',
    meta: {
      keepAlive: true
    },
    components: { default: tab_user, tabbar: Tabbar }
  },
  {
    path: '/user/collect',
    name: 'collect',
    meta: {
      login: true
    },
    component: UserCollect
  },
  {
    path: '/user/merchant',
    name: 'merchant',
    meta: {
      login: true
    },
    component: UserMerchant
  },
  {
    path: '/user/merchant/addMerchant',
    name: 'addMerchant',
    meta: {
      login: true
    },
    component: UserAddMerchant
  },

  {
    path: '/user/merchant/detail',
    name: 'merchant-detail',
    meta: {
      login: true
    },
    component: UserMerchantDetail
  },

  {
    path: '/user/merchant/audit',
    name: 'merchant-audit',
    meta: {
      login: true
    },
    component: UserMerchantAudit
  },

  {
    path: '/user/merchantLeader/detail',
    name: 'merchantLeader-detail',
    meta: {
      login: true
    },
    component: UserMerchantLeaderDetail
  },
  {
    path: '/user/selectMerchant',
    name: 'selectMerchant',
    meta: {
      login: true
    },
    component: UserSelectMerchant
  },

  {
    path: '/user/merchant/detail/detail-setMerchantName',
    name: 'merchant-detail-setMerchantName',
    component: UserSetMerchantName
  },
  {
    path: '/user/merchant/detail/detail-setMerchantAddress',
    name: 'merchant-detail-setMerchantAddress',
    component: UserSetMerchantAddress
  },
  {
    path: '/user/merchant/detail/detail-setMerchantLeader',
    name: 'merchant-detail-setMerchantLeader',
    component: UserSetMerchantLeader
  },
  {
    path: '/user/merchant/detail/detail-setMerchantPhone',
    name: 'merchant-detail-setMerchantPhone',
    component: UserSetMerchantPhone
  },
  {
    path: '/user/merchant/detail/detail-setMerchantUser',
    name: 'merchant-detail-setMerchantUser',
    component: MerchantSetUser
  },

  {
    path: '/user/merchant/detail/detail-setMerchantCode',
    name: 'merchant-detail-setMerchantCode',
    component: UserSetMerchantCode
  },

  {
    path: '/user/merchant/detail/detail-addMerchantUser',
    name: 'merchant-detail-addMerchantUser',
    component: UserAddMerchantUser
  },


  {
    path: '/user/address',
    name: 'address',
    meta: {
      login: true
    },
    component: UserAddress
  },
  {
    path: '/user/address/edit',
    name: 'address-edit',
    props: true,
    meta: {
      login: true
    },
    component: UserAddressEdit
  },
  {
    path: '/user/server',
    name: 'user-server',
    component: UserServer
  },
  {
    path: '/user/help',
    name: 'user-help',
    component: UserHelp
  },
  {
    path: '/user/feedback',
    name: 'user-feedback',
    component: UserFeedback
  },  
  {
    path: '/user/information',
    name: 'user-information',
    meta: {
      login: true
    },
    component: UserInformation
  },
  {
    path: '/user/information/setMobile',
    name: 'user-info-setMobile',
    component: UserInfo_SetMobile
  },
  {
    path: '/user/information/setNickname',
    name: 'user-info-setNickname',
    component: UserInfo_SetNickname
  },
  {
    path: '/user/information/setPassword',
    name: 'user-info-setPassword',
    component: UserInfo_SetPassword
  },
  {
    path: '/user/order/list/:active',
    name: 'user-order-list',
    props: true,
    component: UserOrderList
  },
  {
    path: '/user/coupon/list/:active',
    name: 'user-coupon-list',
    props: true,
    component: UserCouponList
  },
  {
    path: '/user/refund/list',
    name: 'user-refund-list',
    component: UserRefundList
  }
];
