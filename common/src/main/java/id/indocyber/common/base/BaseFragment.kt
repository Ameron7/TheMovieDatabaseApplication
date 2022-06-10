package id.indocyber.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import id.indocyber.common.BR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseFragment<Binding : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    lateinit var binding: Binding
    abstract val layoutResourceId: Int
    abstract val vm: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.navigationtEvent.observe(this) {
            findNavController().navigate(it)
        }
        vm.popBackStackEvent.observe(this) {
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        binding.setVariable(BR.vm, vm)
        binding.lifecycleOwner = this
        initBinding()
        observeLiveData()
        return binding.root
    }

    open fun initBinding() {}

    open fun observeLiveData() {}

    fun <T> observeResponseData(
        data: MutableLiveData<ResponseApp<T>>,
        success: ((T?) -> Unit),
        error: ((Throwable) -> Unit)?,
        loading: (() -> Unit)? = {}
    ) {

        data.observe(this) {
            when (it) {
                is ResponseSuccess -> {
                    success.invoke(it.data)
                }
                is ResponseError -> {
                    it.error?.let {
                        error?.invoke(it)
                    }
                }
                is ResponseLoading -> {
                    loading?.invoke()
                }
            }
        }
    }

    fun <T> observePagingData(data: MutableLiveData<T>, success: (T) -> Unit) {
        data.observe(this) {
            CoroutineScope(Dispatchers.Main).launch {
                success.invoke(it)
            }
        }
    }
}