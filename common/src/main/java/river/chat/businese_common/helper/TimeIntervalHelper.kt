package river.chat.businese_common.helper

import river.chat.lib_core.config.AppLocalConfigKey
import river.chat.lib_core.config.ConfigManager

/**
 * Created by beiyongChao on 2023/6/9
 * Description: 时间间隔管理类
 */
object TimeIntervalHelper {


    /**
     * 更新弹窗最少间隔时间
     */
    private var updateInterval = TimeIntervalBean().apply {
        limitTime = 7 * 24 * 60 * 60 * 1000L
        key = AppLocalConfigKey.UPDATE_DIALOG_SHOW_TIME
    }


    /**
     * 活动弹窗最少间隔时间
     */
    private var activityDialogInterval = TimeIntervalBean().apply {
        limitTime = 24 * 60 * 60 * 1000L
        key = AppLocalConfigKey.ACTIVITY_DIALOG_SHOW_TIME
    }


    /**
     * 更新弹窗最少间隔时间
     */
    fun updateUpdateTime() {
        updateShowTime(updateInterval.key)
    }

    /**
     * 是否在更新弹窗限制时间内
     */
    fun isUpdateLimit(): Boolean {
        return checkInLimitTime(updateInterval)
    }

    /**
     * 是否在活动弹窗限制时间内
     */
    fun isActivityDialogLimit(): Boolean {

        var isTimeLimit = checkInLimitTime(activityDialogInterval)
        var hasClicked = ConfigManager.getAppConfigBoolean(
            AppLocalConfigKey.ACTIVITY_HAS_CLICK,
            false
        )
        //
        /**
         * 在限制时间内,不弹出
         * 在限制时间外，但是已经点击过，不弹出
         */
        return isTimeLimit || hasClicked
    }

    /**
     * 更新活动弹窗最少间隔时间
     */
    fun updateActivityDialogTime() {
        updateShowTime(activityDialogInterval.key)
    }

    /**
     * 是否在活动弹窗限制时间内
     */
    private fun updateShowTime(key: String) {
        ConfigManager.putAppConfig(key, true)
    }

    /**
     * 获取上次展示时间
     */
    private fun getLastShowTime(key: String): Long {
        return ConfigManager.getAppConfigBean(
            key
        )?.updateTime ?: 0
    }

    /**
     * 是否在限制时间内
     */
    private fun checkInLimitTime(timeIntervalBean: TimeIntervalBean): Boolean {
        var endTime = getLastShowTime(timeIntervalBean.key)
        //限制的截止时间有效，并且大于当前时间，则在限制时间内
        if (!timeIntervalBean.hasClicked && endTime > 0 && (System.currentTimeMillis() < (endTime + timeIntervalBean.limitTime))) {
            return true
        }
        return false
    }

    data class TimeIntervalBean(
        var limitTime: Long = 0,
        //缓存的配置key，常量
        var key: String = "",

        /**
         * 活动名称
         */
        var name: String = "",
        //是否点击过
        var hasClicked: Boolean = false

    )
}

