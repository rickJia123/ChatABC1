package river.chat.businese_common.base

import org.koin.dsl.module
import river.chat.businese_common.net.ApiStorage
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.HomePlugin
import river.chat.lib_core.router.plugin.module.UserPlugin

/**
 * Created by beiyongChao on 2023/3/1
 * Description:
 */
val dataBaseModule = module {

    factory()
    {
        ApiStorage.getBasedBody()
    }

    single {
        getPlugin<UserPlugin>()
    }

    single {
        getPlugin<HomePlugin>()
    }


}