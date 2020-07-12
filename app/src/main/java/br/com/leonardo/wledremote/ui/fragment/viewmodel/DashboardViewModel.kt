package br.com.leonardo.wledremote.ui.fragment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.leonardo.wledremote.R

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val _isVisible = MutableLiveData(false)
    val isVisible: LiveData<Boolean> = _isVisible

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    val checkedButton = MutableLiveData<Int>(R.id.button1)

    fun setError(value: Boolean, message: String = "") {
        _isError.value = value
        _errorMessage.value = message
        _isVisible.value = !value
    }
}