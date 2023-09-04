package river.chat.lib_core.wx

/**
 * Created by beiyongChao on 2023/7/19
 * Description:
 */
class WxAccessTokenBean {
    var access_token:String=""
    var expires_in:Long=0
    var refresh_token:String=""

    var openid:String=""
    var scope:String=""
    var unionid:String=""

    var errcode:Long=0
    var errmsg:String=""
}