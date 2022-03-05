package com.example.perludilindungi.ui.bookmark

import android.app.Application
import androidx.lifecycle.*
import com.example.perludilindungi.database.FaskesDao
import com.example.perludilindungi.database.FaskesDatabase
import com.example.perludilindungi.database.FaskesEntity
import kotlinx.coroutines.launch

class BookmarkViewModel(application: Application) : AndroidViewModel(application) {
    private var faskesDao : FaskesDao?
    private var faskesDb : FaskesDatabase?
    private var list: LiveData<List<FaskesEntity>>

    init {
        faskesDb = FaskesDatabase.getInstance(application)
        faskesDao = faskesDb?.faskesDao
        list = faskesDao?.getAllFaskes()!!
    }

    fun getListFaskes(): LiveData<List<FaskesEntity>> = list



}