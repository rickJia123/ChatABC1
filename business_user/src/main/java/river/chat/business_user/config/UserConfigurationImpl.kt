package river.chat.business_user.config

import android.content.Context
import river.chat.lib_core.app.AppLifeCycles
import river.chat.lib_core.config.ConfigModule

class UserConfigurationImpl : ConfigModule {
    override fun applyOptions(
        context: Context
    ) {

    }

    override fun injectAppLifecycle(
        context: Context,
        lifeCycles: MutableList<AppLifeCycles>
    ) {
        lifeCycles.add(UserLifeCyclesImpl())
    }
}