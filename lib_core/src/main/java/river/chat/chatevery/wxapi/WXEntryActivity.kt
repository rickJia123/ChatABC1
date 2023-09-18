package river.chat.chatevery.wxapi

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth.Resp
import com.umeng.socialize.weixin.view.WXCallbackActivity
import org.koin.android.ext.android.get
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.wx.WxManager

/**
 * Created by beiyongChao on 2023/7/19
 * Description:
 */
class WXEntryActivity : WXCallbackActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onNewIntent(paramIntent: Intent?) {
        super.onNewIntent(paramIntent)
        WxManager.api?.handleIntent(intent, this)
    }

    override fun onReq(req: BaseReq?) {
        req?.let { ("微信 onReq: ${req.openId}").log() }
//        super.onReq(req)
    }

    override fun onResp(resp: BaseResp?) {
        resp?.let {
            ("微信 onResp: ${resp.errCode}").log()
            var errorCode = resp?.errCode
            when (errorCode) {
                BaseResp.ErrCode.ERR_OK -> {
                    (resp as Resp).let {
                        ("微信 onResp: ${resp.errCode}" + "---${resp.code}").log()
                        get<UserPlugin>().loginByWechat(it.code)
                    }
                }

                BaseResp.ErrCode.ERR_USER_CANCEL -> {

                }
            }


        }
    }
}

