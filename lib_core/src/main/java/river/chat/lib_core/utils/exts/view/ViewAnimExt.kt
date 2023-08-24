package river.chat.lib_core.utils.exts.view

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

/**
 * Created by beiyongChao on 2023/8/24
 * Description:
 */


@JvmOverloads
fun <T : View> T.startSpringAnima(property: FloatPropertyCompat<T>,
                                   startValue: Float,
                                   endValue: Float,
                                   stiffness: Float = SpringForce.STIFFNESS_VERY_LOW,//硬度 越高弹性越低
                                   dampingRation: Float = SpringForce.DAMPING_RATIO_LOW_BOUNCY,//越高回弹效果越好
                                   endListener: DynamicAnimation.OnAnimationEndListener? = null,
                                   startVelocity: Float = 0f) {
    val springAnimation = SpringAnimation(this, property, endValue)
    springAnimation.spring.stiffness = stiffness
    springAnimation.spring.dampingRatio = dampingRation
    springAnimation.setStartValue(startValue)
    springAnimation.addEndListener(endListener)
    springAnimation.setStartVelocity(startVelocity)
    springAnimation.start()
}


@JvmOverloads
fun <T : View> T.startExpandAnim(property: FloatPropertyCompat<T>,
                                 startValue: Float,
                                 endValue: Float,
                                 stiffness: Float = SpringForce.STIFFNESS_VERY_LOW,//硬度 越高弹性越低
                                 dampingRation: Float = SpringForce.DAMPING_RATIO_LOW_BOUNCY,//越高回弹效果越好
                                 endListener: DynamicAnimation.OnAnimationEndListener? = null,
                                 startVelocity: Float = 0f) {
    val springAnimation = SpringAnimation(this, property, endValue)
    springAnimation.spring.stiffness = stiffness
    springAnimation.spring.dampingRatio = dampingRation
    springAnimation.setStartValue(startValue)
    springAnimation.addEndListener(endListener)
    springAnimation.setStartVelocity(startVelocity)
    springAnimation.start()
}