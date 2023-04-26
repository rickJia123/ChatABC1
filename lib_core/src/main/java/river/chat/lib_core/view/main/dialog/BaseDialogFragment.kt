package river.chat.lib_core.view.main.dialog

import androidx.fragment.app.DialogFragment
import org.greenrobot.eventbus.EventBus

/**
 *Author: chengminghui
 *Time: 2019-09-03
 *Description: xxx
 */
open class BaseDialogFragment : DialogFragment() {

    /**
     * 是否注册eventBus
     *
     * @return
     */
    protected open fun registerEventBus(): Boolean {
        return false
    }


    override fun onStart() {
        super.onStart()
        if (registerEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onStop() {
        super.onStop()
        if (registerEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }

}