package com.mozhimen.componentk.netk.app.download.mos

import com.mozhimen.componentk.netk.app.cons.CNetKAppState
import com.mozhimen.componentk.netk.app.task.cons.CNetKAppTaskState

/**
 * @ClassName AppDownloadProgress
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/8 13:56
 * @Version 1.0
 */
data class MAppDownloadProgress(
    var progressState: Int = CNetKAppTaskState.STATE_TASK_CREATE,
    var progress: Int = 0
) {
    fun isDownloading(): Boolean =
        CNetKAppState.isDownloading(progressState)
}