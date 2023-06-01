package com.mozhimen.basick.utilk.view.bar

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.mozhimen.basick.utilk.os.thread.UtilKHandler
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.content.activity.isFinishingOrDestroyed
import java.lang.Exception


/**
 * @ClassName UtilKToast
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/12 1:11
 * @Version 1.0
 */
fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.showToast(this, duration)
}

fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.showToast(this, duration)
}

fun String.showToastLong() {
    UtilKToast.showToast(this, Toast.LENGTH_LONG)
}

fun Int.showToastLong() {
    UtilKToast.showToast(this, Toast.LENGTH_LONG)
}

////////////////////////////////////////////////////////////////

fun String.showToastOnMain(duration: Int = Toast.LENGTH_LONG) {
    UtilKToast.showToastOnMain(this, duration)
}

fun Int.showToastOnMain(duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.showToastOnMain(this, duration)
}

////////////////////////////////////////////////////////////////

fun Context.showToastOnMain(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.showToastOnMain(msg, duration)
}

fun Context.showToastOnMain(id: Int, duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.showToastOnMain(getString(id), duration)
}

////////////////////////////////////////////////////////////////

fun Exception.showToastOnMain(duration: Int = Toast.LENGTH_LONG) {
    this.message?.let { UtilKToast.showToastOnMain(it, duration) }
}

object UtilKToast {
    private val _context = UtilKApplication.instance.get()

    /**
     * 用法1: "...".showToast(context)
     * @param msg String
     * @param duration Int
     */
    @JvmStatic
    fun showToast(msg: String, duration: Int = Toast.LENGTH_SHORT, context: Context = _context) {
        if (context is Activity) {
            if (!context.isFinishingOrDestroyed()) {
                makeToast(context, msg, duration)
            }
        } else {
            makeToast(context, msg, duration)
        }
    }

    /**
     * 用法2: R.string.app_name.showToast(context)
     * @param msgId Int
     * @param duration Int
     */
    @JvmStatic
    fun showToast(msgId: Int, duration: Int = Toast.LENGTH_SHORT, context: Context = _context) {
        if (context is Activity) {
            if (!context.isFinishingOrDestroyed()) {
                makeToast(context, msgId, duration)
            }
        } else {
            makeToast(context, msgId, duration)
        }
    }

    /**
     * 在主线程show
     * 用法1: "...".showToastOnMain(context)
     * @param msg String
     * @param duration Int
     */
    @JvmStatic
    fun showToastOnMain(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        if (UtilKHandler.isMainLooper()) {
            showToast(msg, duration)
        } else {
            UtilKHandler.postOnMain { showToast(msg, duration) }
        }
    }

    /**
     * 在主线程show
     * 用法2: R.string.app_name.showToastOnMain(context)
     * @param msgId Int
     * @param duration Int
     */
    @JvmStatic
    fun showToastOnMain(msgId: Int, duration: Int = Toast.LENGTH_SHORT) {
        if (UtilKHandler.isMainLooper()) {
            showToast(msgId, duration)
        } else {
            UtilKHandler.postOnMain { showToast(msgId, duration) }
        }
    }

    @JvmStatic
    fun makeToast(context: Context, msg: String, duration: Int) {
        Toast.makeText(context, msg, duration).show()
    }

    @JvmStatic
    fun makeToast(context: Context, msgId: Int, duration: Int) {
        Toast.makeText(context, msgId, duration).show()
    }
}