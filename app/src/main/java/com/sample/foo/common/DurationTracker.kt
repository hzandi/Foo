package com.sample.foo.common

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.*
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

/**
 * DurationTracker keeps track of the duration of a task. As soon as a timer starts
 * it starts counting the seconds until the task is executed.
 */
class DurationTracker @Inject constructor() : DefaultLifecycleObserver {

    companion object {
        private const val ONE_SECOND_MS = 1000L
    }

    private val durationLiveData = MutableLiveData<Long>(0)
    private val handler: Handler = Handler(Looper.getMainLooper())
    private var isTimerStarted = false
    private var value: AtomicLong = AtomicLong(0)
    private var onTaskEvent: OnTaskEvent? = null
    private var taskSecondInterval = 10 // default interval

    // every second update timer
    private val runnable = object : Runnable {
        override fun run() {
            setValue(value.get() + 1)
            if (value.get() % taskSecondInterval == 0L){
                setValue(0)
                handler.post(taskRunnable)
            }
            handler.postDelayed(this, ONE_SECOND_MS)
        }
    }

    // every taskSecondInterval second raise the task
    private val taskRunnable = Runnable {
        onTaskEvent?.execute()
    }

    fun setTaskWithInterval(onTaskEvent: OnTaskEvent, taskSecondInterval: Int = 10): DurationTracker {
        this.onTaskEvent = onTaskEvent
        this.taskSecondInterval = taskSecondInterval
        return this
    }

    private fun setValue(newValue: Long){
        value.set(newValue)
        durationLiveData.value = value.get()
    }

    override fun onStart(owner: LifecycleOwner) {
        startTimer()
        super.onStart(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        stopTimer()
        super.onStop(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        stopTimer()
        stopTaskEvent()
        super.onDestroy(owner)
    }

    fun startTimer() {
        if (!isTimerStarted) {
            isTimerStarted = true
            handler.post(runnable)
        }
    }

    fun stopTimer() {
        if (isTimerStarted) {
            isTimerStarted = false
            handler.removeCallbacks(runnable)
        }
    }

    fun restartTimer() {
        value.set(0)
        durationLiveData.value = 0
        stopTimer()
        startTimer()
    }

    private fun stopTaskEvent(){
        handler.removeCallbacks(taskRunnable)
    }

    interface OnTaskEvent {
        fun execute()
    }

    internal fun getDurationAsLiveData(): LiveData<Long> = durationLiveData

    internal fun getDurationInSeconds(): Long = value.get()

}
