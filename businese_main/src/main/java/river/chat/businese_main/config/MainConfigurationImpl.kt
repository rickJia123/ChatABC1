package river.chat.businese_main.config

import android.content.Context
import river.chat.lib_core.app.AppLifeCycles
import river.chat.lib_core.config.ConfigModule

class MainConfigurationImpl : ConfigModule {
    override fun applyOptions(
        context: Context
    ) {

    }

    override fun injectAppLifecycle(
        context: Context,
        lifeCycles: MutableList<AppLifeCycles>
    ) {
        lifeCycles.add(MainLifeCyclesImpl())
    }
}