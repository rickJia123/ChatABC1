package river.chat.lib_core.view.main

import river.chat.lib_core.utils.permission.permission.PermissionConstants
import river.chat.lib_core.view.main.activity.BaseActivity

/**
 * Created by beiyongChao on 2023/6/8
 * Description:
 */

/**
 * 请求存储权限
 */
fun BaseActivity.requestStorage(
    onDenied: (() -> Unit)? = null,
    onGranted: () -> Unit
) {
    this.requestPermissions(
        PermissionConstants.storagePermission, false, onDenied,
        onGranted
    )
}