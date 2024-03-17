package com.mozhimen.basicktest.taskk

import android.os.Bundle
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.taskk.executor.TaskKExecutor
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.java.lang.UtilKThread
import com.mozhimen.basicktest.databinding.ActivityTaskkExecutorBinding
import kotlinx.coroutines.launch

/**
 * @ClassName ExecutorKActivity
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/14 20:05
 * @Version 1.0
 */
class TaskKExecutorActivity : BaseActivityVDB<ActivityTaskkExecutorBinding>() {

    private var _isPaused = false

    override fun initView(savedInstanceState: Bundle?) {
        vdb.taskkExecutorBtnOrder.setOnClickListener {
            for (priority in 0..10) {
                TaskKExecutor.execute(TAG, priority) {
                    try {
                        Thread.sleep((1000 - priority * 100).toLong())
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                        e.message?.et(TAG)
                    }
                }
            }
        }

        vdb.taskkExecutorBtnAllTask.setOnClickListener {
            if (_isPaused) {
                TaskKExecutor.resume()
            } else {
                TaskKExecutor.pause()
            }
            _isPaused = !_isPaused
        }

        vdb.taskkExecutorBtnAsync.setOnClickListener {
            TaskKExecutor.execute(TAG, runnable = object : TaskKExecutor.ExecutorKCallable<String>() {
                override fun onBackground(): String {
                    UtilKLogWrapper.et(TAG, "onBackground: 当前线程: ${UtilKThread.getCur()}")
                    return "我是异步任务的结果"
                }

                override fun onCompleted(t: String?) {
                    UtilKLogWrapper.et(TAG, "onCompleted: 当前线程是: ${UtilKThread.getCurName()}")
                    UtilKLogWrapper.et(TAG, "onCompleted: 任务结果是: $t")
                }
            })
        }

        //这里演示ExectorK转化为协程调度器, 使用use是因为我们需要使用完毕主动关闭以免线程泄露
        TaskKExecutor.getTaskKExecutorCoroutineDispatcher().use {
            lifecycleScope.launch(it/*Dispatchers.IO*/) {
                /////////////////////
            }
        }
    }
}