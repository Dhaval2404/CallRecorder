package com.github.dhaval2404.base

import android.app.Application
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.AndroidViewModel

/**
 * Parent ViewModel for all the app ViewModel
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 22 January 2019
 */
abstract class BaseViewModel<T>(application: Application) : AndroidViewModel(application),
    Observable {

    protected val mContext = application.applicationContext
    protected var mNavigator: T? = null
    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    fun setNavigator(navigator: T) {
        this.mNavigator = navigator
    }

    override fun addOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback
    ) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback
    ) {
        callbacks.remove(callback)
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }
}
