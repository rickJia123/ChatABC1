package river.chat.chatevery.wxapi

import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.modelmsg.SendAuth.Resp
import com.umeng.socialize.weixin.view.WXCallbackActivity
import river.chat.businese_common.net.CommonRequestViewModel
import river.chat.businese_common.wx.WxManager
import river.chat.lib_core.utils.longan.log

/**
 * Created by beiyongChao on 2023/7/19
 * Description:
 */
class WXEntryActivity : WXCallbackActivity() {
    override fun onReq(req: BaseReq?) {
        super.onReq(req)
    }

    override fun onResp(resp: BaseResp?) {
        ("微信 onResp: ${resp}").log()
        super.onResp(resp)
        (resp as Resp).let {
            CommonRequestViewModel().requestWechatAccessToken(it.code.toString(), {

            })
        }

    }
}