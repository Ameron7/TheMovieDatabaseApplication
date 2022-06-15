package id.indocyber.common.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavDirections
import id.indocyber.common.lang_ext.SingleLiveEvent


open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val navigationtEvent = SingleLiveEvent<NavDirections>()

    fun navigate(nav: NavDirections) {
        navigationtEvent.postValue(nav)
    }
}