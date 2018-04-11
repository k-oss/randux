/*
 * MIT License
 *
 * Copyright (c) 2018 K-OSS Development
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.koss.randux.sample

import android.app.Application
import arrow.core.Option
import arrow.core.Some
import io.github.koss.randux.applyMiddleware
import io.github.koss.randux.combineReducers
import io.github.koss.randux.createStore
import io.github.koss.randux.sample.di.DaggerSampleComponent
import io.github.koss.randux.sample.di.SampleComponent
import io.github.koss.randux.sample.di.SampleModule
import io.github.koss.randux.sample.main.Empty
import io.github.koss.randux.sample.main.MainModule
import io.github.koss.randux.utils.State
import io.github.koss.randux.utils.Store

class SampleApp: Application() {

    lateinit var store: Store

    lateinit var component: SampleComponent

    private val preloadedState: Option<State>
        get() = Option.fromNullable(loadState())

    override fun onCreate() {
        super.onCreate()

        component = DaggerSampleComponent.builder()
                .sampleModule(SampleModule())
                .mainModule(MainModule)
                .build()

        val reducers = component.reducers().toTypedArray()
        val middleware = component.middleware().toTypedArray()

        store = createStore(
                reducer = combineReducers(*reducers),
                preloadedState = preloadedState,
                enhancer = Some(applyMiddleware(*middleware))
        )
    }

    private fun loadState(): State? {
        // TODO: Load state from somewhere
        return Empty
    }
}