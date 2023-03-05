package river.chat.businese_common.net

import river.chat.lib_core.net.retrofit.BaseApi

/**
 * Created by beiyongChao on 2023/2/24
 * Description:
 */
class ApiStorage {

    /**
     * 获取全局网络仓库
     */
      val commonApi: CommonApiService
          get() =  BaseApi.retrofit().create(CommonApiService::class.java)

}