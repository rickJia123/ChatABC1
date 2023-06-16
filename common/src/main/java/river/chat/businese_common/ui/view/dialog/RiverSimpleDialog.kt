//package river.chat.businese_common.ui.view.dialog
//
//import android.app.Dialog
//import android.content.Context
//import android.view.Gravity
//import android.view.View
//import river.chat.common.R
//
///**
//@Author  :rickBei
//@Date    :2019/7/18 11:06
//@Descripe: 克拉dialog
// *            demo for java:
// *            KlDialog.builder(getContext())
// *            .setNegativeButton(R.string.know, dialog -> {
// *            dialog.dismiss();
// *            return  null;
// *            })
// *            .setPositiveButton("哈哈", dialog -> {
// *            dialog.dismiss();
// *            return  null;
// *            })2
// *            .setLeftColor(R.color.colorAccent)
// *            .setMessage("何事秋风悲画扇")
// *            .setDialogTitle("人生若只如初见")
// *            .show();
// *            });
// *             **/
//
//class RiverSimpleDialog(context: Context) : Dialog(context, R.style.Dialog_Transparent) {
//
//    companion object {
//        @JvmStatic
//        fun builder(context: Context): RiverSimpleDialog = RiverSimpleDialog(context)
//    }
//
//    interface OnKlDialogDismissListener {
//        fun onDismiss()
//    }
//
//    private var onKlDialogDismissListener: OnKlDialogDismissListener? = null
//    private var autoDismiss = false
//
//    init {
//        setContentView(R.layout.dialog)
//        window.setGravity(Gravity.CENTER)
//        setOnDismissListener {
//            onKlDialogDismissListener?.onDismiss()
//        }
//    }
//
//    fun setDismissListener(onKlDialogDismissListener: OnKlDialogDismissListener): RiverSimpleDialog {
//        this.onKlDialogDismissListener = onKlDialogDismissListener
//        return this
//    }
//
//
//    fun autoDismiss(): RiverSimpleDialog {
//        this.autoDismiss = true
//        return this
//    }
//
//    fun setDialogTitle(title: Any?): RiverSimpleDialog {
//        if (title != null) {
//            tv_dialog_title.setText(title)
//            tv_dialog_title.visibility = View.VISIBLE
//        }
//        return this
//    }
//
//    fun setMessage(message: Any?): RiverSimpleDialog {
//        if (message != null) {
//            tv_dialog_desc.setText(message)
//        }
//        return this
//    }
//
//    fun setNegativeButton(text: Any): RiverSimpleDialog {
//        tv_dialog_l_button.setOnClickListener {
//            if (autoDismiss)
//                dismiss()
//        }
//        tv_dialog_l_button.setText(text)
//        tv_dialog_l_button.visibility = View.VISIBLE
//        view_dialog_dividing.visibility = if (tv_dialog_r_button.visibility == View.GONE) View.GONE else View.VISIBLE
//        return this
//    }
//
//    fun setNegativeButton(text: Any, leftClick: (dialog: RiverSimpleDialog) -> Unit): RiverSimpleDialog {
//        tv_dialog_l_button.setOnClickListener {
//            leftClick(this)
//            if (autoDismiss)
//                dismiss()
//        }
//        tv_dialog_l_button.setText(text)
//        tv_dialog_l_button.visibility = View.VISIBLE
//        view_dialog_dividing.visibility = if (tv_dialog_r_button.visibility == View.GONE) View.GONE else View.VISIBLE
//        return this
//    }
//
//    fun setPositiveButton(text: Any, rightClick: (dialog: RiverSimpleDialog) -> Unit): RiverSimpleDialog {
//        tv_dialog_r_button.click {
//                rightClick(this)
//            if (autoDismiss)
//                dismiss()
//        }
//        tv_dialog_r_button.setText(text)
//        tv_dialog_r_button.visibility = View.VISIBLE
//        view_dialog_dividing.visibility = if (tv_dialog_l_button.visibility == View.GONE) View.GONE else View.VISIBLE
//        return this
//    }
//
//    fun setPositiveButton(text: Any): RiverSimpleDialog {
//        tv_dialog_r_button.click {
//            if (autoDismiss)
//                dismiss()
//        }
//        tv_dialog_r_button.setText(text)
//        tv_dialog_r_button.visibility = View.VISIBLE
//        view_dialog_dividing.visibility = if (tv_dialog_l_button.visibility == View.GONE) View.GONE else View.VISIBLE
//        return this
//    }
//
//    fun setLeftColor(leftColor: Int): RiverSimpleDialog {
//        tv_dialog_l_button.setTextColor(leftColor.getColor())
//        return this
//    }
//
//    fun setRightColor(rightColor: Int): RiverSimpleDialog {
//        tv_dialog_r_button.setTextColor(rightColor.getColor())
//        return this
//    }
//
//    fun setCanceloutside(cancel: Boolean): RiverSimpleDialog {
//        this.setCanceledOnTouchOutside(cancel)
//        return this
//    }
//
//}