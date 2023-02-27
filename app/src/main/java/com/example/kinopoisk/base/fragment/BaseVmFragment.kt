package com.example.kinopoisk.base.fragment

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.example.kinopoisk.base.viewmodel.BaseViewModel
import com.example.kinopoisk.base.viewmodel.viewState
import com.example.kinopoisk.utils.ViewState
import com.example.kinopoisk.utils.errorDialog
import com.example.kinopoisk.utils.extensions.subscribe
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

abstract class BaseVmFragment<V : BaseViewModel, B : ViewBinding> : BaseFragment<B>() {

    val viewModel: V by viewModel(createViewModel())

    override fun createBinding(): Class<B> =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<B>

    open fun createViewModel(): KClass<V> {
        val instance =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<V>
        return instance.kotlin
    }

    override fun onStop() {
        viewModel.clearCall()
        super.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.globalErrorLiveFlow.subscribe(viewLifecycleOwner) { message -> handleGlobalErrors(message) }
    }

    private fun handleGlobalErrors(message: String?) {
        viewState.postValue(ViewState.Complete)
        message?.let { messageData -> activity?.errorDialog(messageData) }
    }
}