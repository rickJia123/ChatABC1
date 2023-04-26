package river.chat.lib_core.view.main.fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import org.greenrobot.eventbus.EventBus
import pub.devrel.easypermissions.EasyPermissions
import river.chat.lib_core.utils.permission.permission.PermissionHelper

/**
 *Author: chengminghui
 *Time: 2019-09-03
 *Description: xxx
 */
open class BaseFragment : Fragment() , EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks{

    /**
     * 是否注册eventBus
     *
     * @return
     */
    protected open fun registerEventBus(): Boolean {
        return false
    }


    fun requestPermissions(
        permissions: Array<out String>,
        showPermanentlyDeniedDialog: Boolean = false,
        onDenied: (() -> Unit)? = null,
        onGranted: () -> Unit
    ) {
        PermissionHelper.requestPermissions(this, permissions, showPermanentlyDeniedDialog, onDenied, onGranted)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * 权限申请失败
     */
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        PermissionHelper.onPermissionsDenied(this, requestCode, perms)
    }


    /**
     * 权限申请成功
     */
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        PermissionHelper.onGranted(requestCode, perms)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        context?.let {
            PermissionHelper.onActivityResult(it, requestCode, resultCode, data)
        }

    }

    /**
     * 权限申请提示弹出框点击拒绝
     */
    override fun onRationaleDenied(requestCode: Int) {
        PermissionHelper.onRationaleDenied(requestCode)
    }

    /**
     * 权限申请提示框点击确定
     */
    override fun onRationaleAccepted(requestCode: Int) {

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