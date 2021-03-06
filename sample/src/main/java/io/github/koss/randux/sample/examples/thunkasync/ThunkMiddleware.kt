package io.github.koss.randux.sample.examples.thunkasync

import io.github.koss.randux.utils.*

typealias ThunkFunction = (Dispatch, () -> State) -> Any?

/**
 * Naive implementation of JS thunk middleware. This could likely be made better through the use of
 * reified generics, but is closer to the standard implementation currently
 */
class ThunkMiddleware: Middleware {
    override fun invoke(api: MiddlewareAPI): (next: Dispatch) -> Dispatch = { next -> inner@ { action ->
        return@inner if (action !is AsyncAction) {
            try {
                // Sadly, generics are the limiting factor for the moment which breaks a proper thunk implementation
                @Suppress("UNCHECKED_CAST")
                (action as ThunkFunction)(api.dispatch, api.getState)
            } catch (e: Exception) {
                // Pass to next middleware
                next(action)
            }
        } else {
            next(action)
        }
    }}
}
