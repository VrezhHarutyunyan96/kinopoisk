package com.example.kinopoisk.base.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.kinopoisk.utils.ViewState
import com.example.kinopoisk.utils.extensions.getFitSystemWindowSize
import com.example.kinopoisk.utils.extensions.hideKeyBoard
import com.example.kinopoisk.presentation.widget.LoadingView
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<B : ViewBinding> : Fragment(), FragmentResultCallback {

    protected open var hasResizeMode = false
    protected open var bacPressDispatcher = true
    protected lateinit var binding: B

    open fun createBinding(): Class<B> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<B>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val method = createBinding().getMethod("inflate", LayoutInflater::class.java)
        binding = method.invoke(null, inflater) as B
        binding.root.isClickable = true
        binding.root.isFocusable = true
        return binding.root
    }

    open fun configFragmentManager() = activity?.supportFragmentManager?.fragments?.forEach {
        activity?.supportFragmentManager?.beginTransaction()?.detach(it)?.commit()
        activity?.supportFragmentManager?.beginTransaction()?.attach(it)?.commit()
    }

    inline fun <reified F : Fragment> configFragmentManager(currentFragment: F) =
        activity?.supportFragmentManager?.fragments?.forEach {
            if (it !is F) {
                activity?.supportFragmentManager?.beginTransaction()?.detach(it)?.commit()
                activity?.supportFragmentManager?.beginTransaction()?.attach(it)?.commit()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (hasResizeMode) initResizeMode()
        if (bacPressDispatcher) {
            activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, backPressDispatcher)
        }
    }

    private fun changeTexts(it: Map.Entry<String, String>) {
        val selectedView = view?.findViewWithTag<View>(it.key)
        if (selectedView is TextView) {
            selectedView.text = it.value
        }
    }

    open fun handleLoadingEvent(it: ViewState, loadingView: LoadingView) = when (it) {
        ViewState.Loading -> loadingView.showLoading()
        else -> loadingView.hideLoading()
    }

    fun popBackStack() {
        activity?.hideKeyBoard()
        activity?.supportFragmentManager?.popBackStack()
    }

    @SuppressLint("UseRequireInsteadOfGet")
    fun initResizeMode() = binding.root.setOnApplyWindowInsetsListener { _, windowInsets ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val size = context?.getFitSystemWindowSize(binding.root) ?: 0
            val imeHeight = windowInsets.getInsets(WindowInsets.Type.ime()).bottom
            binding.root.setPadding(0, 0, 0, imeHeight + size)
            windowInsets.getInsets(WindowInsets.Type.ime() or WindowInsets.Type.systemGestures())

        }
        windowInsets
    }

    private val backPressDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            dispatchBackPressed()
        }
    }

    protected fun isEnableBackDispatcher(isEnable: Boolean) {
        backPressDispatcher.isEnabled = isEnable
    }

    protected open fun dispatchBackPressed() {
        popBackStack()
    }

    override fun onFragmentResult(key: String, result: Bundle) {

    }
}
