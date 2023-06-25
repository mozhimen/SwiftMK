package com.mozhimen.uicorek.textk

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.layoutk.commons.ILayoutK


/**
 * @ClassName TextKMarquee
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/21 10:43
 * @Version 1.0
 */
class TextKMarquee @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr), ILayoutK {

    override val TAG: String = "TextKMarquee>>>>>"

    init {
        initView()
    }

    override fun initView() {
        ellipsize = TextUtils.TruncateAt.MARQUEE
        if (Build.VERSION.SDK_INT >= CVersionCode.V_26_8_O) {
            focusable = View.FOCUSABLE
        }
        isFocusableInTouchMode = true
        marqueeRepeatLimit = -1
        isSingleLine = true
        isSelected = false
        this.post { isSelected = true }
    }
}