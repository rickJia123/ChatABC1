package river.chat.businese_main.mine

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import river.chat.business_main.R
import river.chat.lib_core.utils.exts.getColor
import river.chat.lib_core.utils.exts.view.loadSimple


/**
@Author  :rickBei
@Date    :2019/7/26 14:24
@Descripe: item通用控件
 **/

class SettingItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val TYPE_NONE = -1
        private const val TYPE_TEXT = 0
        private const val TYPE_ICON = 1
        private const val TYPE_SWITCH = 2
    }


    private var tvName: AppCompatTextView? = null
    private var tvTip: AppCompatTextView? = null
    private var tvSub: AppCompatTextView? = null
    private var ivRight: AppCompatImageView? = null

    private val DEFAULT_TITLE_COLOR = river.chat.lib_resource.R.color.defaultTitleColor.getColor()
    private var leftTitle: String? = null
    private var arrowEnable = true
    private var subEnable = false
    private var leftTitleColor = DEFAULT_TITLE_COLOR

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingItem)
        typedArray?.let {
            leftTitle = it.getString(R.styleable.SettingItem_item_left_title)
            arrowEnable = it.getBoolean(R.styleable.SettingItem_item_left_enable_arrow, true)
            subEnable = it.getBoolean(R.styleable.SettingItem_item_left_enable_sub, false)
            leftTitleColor =
                it.getColor(R.styleable.SettingItem_item_left_color, DEFAULT_TITLE_COLOR)
            when (it.getInt(R.styleable.SettingItem_item_type, TYPE_NONE)) {
                TYPE_ICON -> {
                    View.inflate(context, R.layout.item_setting_simple, this)
                    findView()
                }

                TYPE_SWITCH -> {
//                    View.inflate(context, R.layout.layout_moreitem_3, this)
//                    tvName = findViewById(R.id.tv_name)
//                    scToggle = findViewById(R.id.sc_toggle)
                }

                else -> {
                    View.inflate(context, R.layout.item_setting_simple, this)
                    findView()
                }
            }
            ivRight?.visibility = if (arrowEnable) View.VISIBLE else View.GONE
            tvSub?.visibility = if (subEnable) View.GONE else View.VISIBLE
            tipVisible = false

        }
        typedArray.recycle()

        tvName?.text = leftTitle
        tvName?.setTextColor(leftTitleColor)
        setBackgroundColor(R.color.front_1_BgColor.getColor())
    }

    private fun findView() {
        tvName = findViewById(R.id.tvName)
        tvSub = findViewById(R.id.tvSub)
//        ivIcon = findViewById(R.id.iv_icon)
        ivRight = findViewById(R.id.ivRight)
        tvTip = findViewById(R.id.tvTip)
    }


    var name: String? = null
        set(value) {
            field = value
            tvName?.text = value
        }

    var sub: String? = null
        set(value) {
            field = value
            tvSub?.text = value
        }

    var rightArrow: Any? = 0
        set(value) {
            field = value
            value?.apply { ivRight?.loadSimple(value) }
        }

    var tipVisible: Boolean? = false
        set(value) {
            field = value
            value?.apply { tvTip?.visibility = if (value) View.VISIBLE else View.GONE }
        }


    var click: () -> Unit = {}
        set(value) {
            field = value
            setOnClickListener { value() }
        }

    fun getTitle(): String {
        return tvName?.text.toString()
    }

    /**
     * 初始化设置条目
     */
    fun action(action: ItemActionSimple) {
        action.let {
            name = it.name
            sub = it.sub
            rightArrow = it.ivIcon
            click = it.onClickListener
        }
    }


}

data class ItemActionSimple(
    val name: String?,
    val sub: String?,
    val ivIcon: Any?,
    val onClickListener: () -> Unit
)