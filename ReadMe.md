#### 代办

1：分享和复制改版
2:支付接入
3:合规弹窗，合规入口
4:隐私框架
5:合规文本
6:兑换码
9:埋点
10:vip相关内容(付费引导条，权益提示ui)
11:支付引导条
9:分享(引导下载分享)
10:聊天逻辑优化（次数超频/未登录等报错不显示）,成功后才清空输入框
11:分享数据
## 优化

风险控制(短信超超频，问答次数过多)
arouter的scheme
混淆(友盟，微信登录，支付)

#### 接口
字段补充(分享背景)
下载页相关配置目前为空
app更新用户信息和登录字段不一致
增加配置(兑换码提示,直接加上跳转链接)
聊天接口加上status回参： 未发送 1 发送(接收)中 2 成功 3 失败
测试账号666666登录，有会期账号

#### 风控

（兑换码限制(同一个用户每天错误三次)）

#### 测试点

下载更新
合规
启动app更新用户信息

#### bug
意见反馈没有居中
友盟没有自定义事件
反馈输入框没有光标
设置中登录和注销切换位置
设置中需要头像，跳转登录
退出账号在为登录下不展示