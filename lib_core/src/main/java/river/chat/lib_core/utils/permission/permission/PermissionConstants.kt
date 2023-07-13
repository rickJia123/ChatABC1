package river.chat.lib_core.utils.permission.permission

import android.Manifest

/**
 * Created by beiYongChao on 2022/8/25.
 * Description:
 */
object PermissionConstants {


    /**
     * 存储权限
     */
    val storagePermission = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    /**
     * 定位权限
     */
    val locationPermission = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)


    /**
     * 相机权限
     */
    val cameraPermission = arrayOf(Manifest.permission.CAMERA)

    /**
     * 手机状态权限
     */
    val phoneStatusPermission = arrayOf(Manifest.permission.READ_PHONE_STATE)


}