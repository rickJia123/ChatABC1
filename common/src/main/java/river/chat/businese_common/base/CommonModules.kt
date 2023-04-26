package river.chat.businese_common.base

import org.koin.dsl.factory
import org.koin.dsl.module
import river.chat.businese_common.net.ApiStorage
import river.chat.businese_common.router.HomePlugin
import river.chat.businese_common.router.UserPlugin
import river.chat.lib_core.router.plugin.core.getPlugin

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