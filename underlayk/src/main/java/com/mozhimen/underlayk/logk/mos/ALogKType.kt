package com.mozhimen.underlayk.logk.mos

import androidx.annotation.IntDef

/**
 * @ClassName LogKType
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:59
 * @Version 1.0
 */
@IntDef(
    CLogKType.V,
    CLogKType.D,
    CLogKType.I,
    CLogKType.W,
    CLogKType.E,
    CLogKType.A
)
@Retention(AnnotationRetention.SOURCE)
annotation class ALogKType
