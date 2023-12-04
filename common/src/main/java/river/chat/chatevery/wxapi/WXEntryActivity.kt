package  river.chat.chatevery.wxapi

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth.Resp
import com.umeng.socialize.weixin.view.WXCallbackActivity
import org.koin.android.ext.android.get
import river.chat.lib_core.router.plugin.core.getPlugin
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
        intent = paramIntent
        WxManager.api?.handleIntent(intent, this)
    }

    override fun onReq(req: BaseReq?) {

        req?.let { ("微信 onReq: ${req.openId}").log() }

    }

    override fun onResp(resp: BaseResp?) {

        //需要区分微信登录/支付/分享
        resp?.let {
            ("微信 onResp: ${resp.errCode}   type${resp.type}").log()
            var errorCode = resp.errCode
            // 支付
            when (resp.type) {
                ConstantsAPI.COMMAND_PAY_BY_WX -> {
                    when (errorCode) {
                        BaseResp.ErrCode.ERR_OK -> {
                            if (!getPlugin<UserPlugin>().isLogin()) {
                                (resp as Resp).let {
                                    ("微信 onResp: ${resp.errCode}" + "---${resp.code}").log()
                                    get<UserPlugin>().loginByWechat(it.code)
                                }
                            }
                        }

                        BaseResp.ErrCode.ERR_USER_CANCEL -> {

                        }
                    }
                }
                //分享
                ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX -> {

                }
                //登录
                ConstantsAPI.COMMAND_SENDAUTH -> {
                    when (errorCode) {
                        BaseResp.ErrCode.ERR_OK -> {
                            if (!getPlugin<UserPlugin>().isLogin()) {
                                (resp as Resp).let {
                                    ("微信 onResp: ${resp.errCode}" + "---${resp.code}").log()
                                    get<UserPlugin>().loginByWechat(it.code)
                                }
                            }
                        }

                        BaseResp.ErrCode.ERR_USER_CANCEL -> {

                        }
                    }
                }

            }
        }
        this.finish()
    }
}

