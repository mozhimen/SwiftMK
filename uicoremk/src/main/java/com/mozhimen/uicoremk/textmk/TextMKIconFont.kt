package com.mozhimen.uicoremk.textmk

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * @ClassName TextMKIconFont
 * @Description 用以支持全局iconfont资源的引用,可以在布局文件中直接设置text
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/18 12:50
 * @Version 1.0
 */
class TextMKIconFont @JvmOverloads constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int = 0) :
    AppCompatTextView(context, attributeSet, defStyleAttr) {

    init {
        val typeface = Typeface.createFromAsset(context.assets, "/fonts/iconfont.ttf")
        setTypeface(typeface)
    }
}