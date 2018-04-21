package io.github.koss.randux.sample.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import io.github.koss.randux.sample.feature.simpleasync.SimpleAsyncActivity
import io.github.koss.randux.sample.feature.thunkasync.ThunkActivity

val examples = listOf(
        Example("Simple Async Middleware", SimpleAsyncActivity::class.java),
        Example("Thunk Async Middleware", ThunkActivity::class.java)
)

data class Example(
        val name: String,
        private val activityClass: Class<out AppCompatActivity>
) {
    fun start(activity: AppCompatActivity) {
        activity.startActivity(Intent(activity, activityClass))
    }
}