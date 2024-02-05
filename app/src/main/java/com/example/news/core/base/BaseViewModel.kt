package com.example.news.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.core.utility_class.Resource
import com.example.news.core.enumerates.ViewState
import com.example.news.core.utility_class.SendRequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber

open class BaseViewModel : ViewModel() {
    var tagProcess: String? = null

    private val queue = arrayListOf<String>()

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> = _state

    private val _error = MutableLiveData<String?>() // Deprecated; please use _errorResource
    val error: LiveData<String?> = _error

    private val _errorResource = MutableLiveData<Resource.Error>()
    val errorResource: LiveData<Resource.Error> = _errorResource

    protected fun changeViewState(ViewState: ViewState) = viewModelScope.launch(Dispatchers.Main) {
        _state.postValue(ViewState)
    }

    fun clearErrorResource() {
        _errorResource.value = Resource.Error(
            code = 0,
            message = "",
            errBody = null,
        )
    }

    fun updateErrorResource(err: Resource.Error) {
        _errorResource.value = err
    }

    fun changeUIViewState(viewState: ViewState) = viewModelScope.launch(Dispatchers.Main) {
        _state.postValue(viewState)
    }

    fun <T> Flow<T>.send(
        onStart: suspend () -> Unit = {},
        catch: () -> Unit = {},
        action: (value: T) -> Unit = {},
    ) = viewModelScope.launch(Dispatchers.IO) {
        changeViewState(ViewState.LOADING)
        onStart.invoke()

        onStart {
            remove()

            viewModelScope.launch(Dispatchers.Main) {
                _error.value = null
            }
        }.catch {
            catch()
            if (queue.isEmpty()) changeViewState(ViewState.ERROR)

            when (it) {
                is SendRequestException -> viewModelScope.launch(Dispatchers.Main) {
                    _errorResource.value = Resource.Error(
                        tag = it.tag, code = it.code, message = it.message, errBody = it.errMsg
                    )

                    _error.value = it.message
                }

                is Exception -> viewModelScope.launch(Dispatchers.Main) {
                    _error.value = it.message

                    Timber.e(it.message)
                    it.printStackTrace()
                }
            }
        }.collect {
            viewModelScope.launch(Dispatchers.Main) {
                remove()
                action.invoke(it)

                if (queue.isEmpty()) changeViewState(ViewState.SUCCESS)
            }
        }
    }

    fun addQueue(vararg process: String) = queue.addAll(process)

    private fun remove() {
        val new = queue.filterIndexed { index, _ -> index > 0 }.toTypedArray()

        queue.clear()
        queue.addAll(new)
    }
}