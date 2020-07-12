package br.com.leonardo.wledremote.ui.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EffectsViewModel : ViewModel() {
    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    fun setLoading(value: Boolean) {
        _isLoading.value = value
        if (value) {
            _isError.value = false
        }
    }

    private val _isVisible = MutableLiveData(false)
    val isVisible: LiveData<Boolean> = _isVisible

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    fun setError(value: Boolean, message: String = "") {
        _isError.value = value
        _errorMessage.value = message
        _isVisible.value = !value
    }
}