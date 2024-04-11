package com.mozhimen.xmlk.bases

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.mozhimen.xmlk.commons.ILayoutK

/**
 * @ClassName LayoutKLinear
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
abstract class BaseLayoutKLinear @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr), ILayoutK {
}