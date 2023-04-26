package river.chat.businese_common.net

import io.reactivex.Observable
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import river.chat.lib_core.net.bean.BaseRequestBean
import river.chat.lib_core.net.retrofit.BaseApi
import river.chat.lib_core.net.retrofit.BaseApiService

/**
 * Created by beiyongChao on 2023/2/24
 * Description:
 */
interface CommonApiService : BaseApi {

   
    /**
     * 上传文件，用户会员身份证、手持照、银行卡照片等隐私度比较高的照片上传接口
     */
    @Multipart
    @POST(CommonHttpUrl.USER_SEND_CODE)
    fun testQuery(@Part body: Part): Observable<BaseRequestBean<String>>

}