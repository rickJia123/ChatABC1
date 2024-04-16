package river.chat.businese_main.creation

import river.chat.lib_core.net.cache.DynamicCacheHelper

/**
 * Created by beiyongChao on 2024/3/8
 * Description: 创作模块辅助类
 */
object CreationHelper {
    //选中的模式
    const val SP_SELECTED_MODE = "SP_SELECTED_MODE"

    /**
     * 保存选中的模版
     */
    fun getSelectedModels(): CreationBeans? {
       return DynamicCacheHelper.getCacheByKey(SP_SELECTED_MODE)
    }

    fun  saveSelectedModels(bean: CreationBeans){
        DynamicCacheHelper.saveCache(SP_SELECTED_MODE, bean)
    }
}