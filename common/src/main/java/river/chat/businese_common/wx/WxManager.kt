package river.chat.businese_common.wx

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import kotlin.random.Random


/**
 * Created by beiyongchao on 2023/7/25.
 * Description:
 */
object WxManager {

    const val APP_ID = "wx3e388bc28ef77aae"
    const val SECRET = "1cb88656052791001ce68a43cac81936"

    /**
     * 用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止 csrf 攻击（跨站请求伪造攻击）
     * ，建议第三方带上该参数，可设置为简单的随机数加 session 进行校验。在state传递的过程中会将该参数作为url的一部分进行处理
     * ，因此建议对该参数进行url encode操作，防止其中含有影响url解析的特殊字符（如'#'、'&'等）导致该参数无法正确回传。
     */
    private const val STATE = "RiverState"

    // IWXAPI 是第三方app和微信通信的openApi接口
    private var api: IWXAPI? = null

    fun regToWx(context: Context) {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(context, APP_ID, true)

        // 将应用的appId注册到微信
        api?.registerApp(APP_ID)

        //建议动态监听微信启动广播进行注册到微信
        context.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {

                // 将该app注册到微信
                api?.registerApp(APP_ID)
            }
        }, IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP))
    }

    /**
     * 获取微信登录code-第一步
     */
    fun getLoginCode() {
        var req = SendAuth.Req()
        req.scope = "snsapi_userinfo";// 只能填 snsapi_userinfo
        req.state = STATE + Random(10000).nextInt(10000) + SECRET + Random(10000).nextInt(500000)
        var result = api?.sendReq(req)
        result
    }

}