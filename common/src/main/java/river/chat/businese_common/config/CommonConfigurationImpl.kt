package river.chat.businese_common.config

import android.content.Context
import river.chat.lib_core.app.AppLifeCycles
import river.chat.lib_core.config.ConfigModule

class CommonConfigurationImpl : ConfigModule {
    override fun applyOptions(
        context: Context
    ) {

    }

    override fun injectAppLifecycle(
        context: Context,
        lifeCycles: MutableList<AppLifeCycles>
    ) {
        lifeCycles.add(CommonLifeCyclesImpl())
    }
}