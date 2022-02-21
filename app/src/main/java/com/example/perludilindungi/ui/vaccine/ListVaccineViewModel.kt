package com.example.perludilindungi.ui.vaccine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListVaccineViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is list vaccine Fragment"
    }
    val text: LiveData<String> = _text
}