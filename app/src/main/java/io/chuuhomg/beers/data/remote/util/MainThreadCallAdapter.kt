package io.chuuhomg.beers.data.remote.util

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

class MainThreadCallAdapter private constructor(private val scheduler: Scheduler) : CallAdapter.Factory() {

    companion object {
        fun create(scheduler: Scheduler): MainThreadCallAdapter {
            return MainThreadCallAdapter(scheduler)
        }
    }

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (CallAdapter.Factory.getRawType(returnType) == Observable::class.java) {
            return observableCallAdapter(returnType, annotations, retrofit)
        }

        if (CallAdapter.Factory.getRawType(returnType) == Single::class.java) {
            return singleCallAdapter(returnType, annotations, retrofit)
        }

        return null
    }

    @Suppress("UNCHECKED_CAST")
    private fun observableCallAdapter(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        val delegate = retrofit.nextCallAdapter(
            this, returnType,
            annotations
        ) as CallAdapter<Any, Observable<*>>

        return object : CallAdapter<Any, Any> {
            override fun adapt(call: Call<Any>): Any {
                return delegate.adapt(call).observeOn(scheduler)
            }

            override fun responseType(): Type {
                return delegate.responseType()
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun singleCallAdapter(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        val delegate = retrofit.nextCallAdapter(
            this, returnType,
            annotations
        ) as CallAdapter<Any, Single<*>>

        return object : CallAdapter<Any, Any> {
            override fun adapt(call: Call<Any>): Any {
                return delegate.adapt(call).observeOn(scheduler)
            }

            override fun responseType(): Type {
                return delegate.responseType()
            }
        }
    }
}