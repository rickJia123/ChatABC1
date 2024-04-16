package river.chat.lib_core.view.ktx

import android.os.Bundle
import river.chat.lib_core.view.main.fragment.BaseFragment

/**
 * Created by beiyongChao on 2024/3/8
 * Description:
 */
/**
 * 创建fragment的静态方法，方便传递参数
 *
 * @param params 传递的参数
 * @return
 */
fun <T : BaseFragment> T.newInstance(vararg params: Any?): T? {
    var mFragment: T? = null
    try {
        mFragment = this
        val bundle = Bundle()
        bundle.putSerializable(BaseFragment.PARAMS, params)
        mFragment.arguments = bundle
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return mFragment
}