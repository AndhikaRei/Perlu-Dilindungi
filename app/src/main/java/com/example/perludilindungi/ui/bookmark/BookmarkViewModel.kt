package com.example.perludilindungi.ui.bookmark

import android.app.Application
import androidx.lifecycle.*
import com.example.perludilindungi.database.FaskesDao
import com.example.perludilindungi.database.FaskesEntity
import kotlinx.coroutines.launch

class BookmarkViewModel(val database: FaskesDao, application: Application) : AndroidViewModel(application) {

    private val listFaskes = database.getAllFaskes()


}