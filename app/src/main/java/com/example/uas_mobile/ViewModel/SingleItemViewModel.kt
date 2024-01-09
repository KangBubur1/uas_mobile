package com.example.uas_mobile.ViewModel

// SingleItemViewModel.kt
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.uas_mobile.DataBuku.DataKatalogBuku

class SingleItemViewModel : ViewModel() {
    private val _selectedBookLiveData = MutableLiveData<DataKatalogBuku>()

    val selectedBookLiveData: LiveData<DataKatalogBuku>
        get() = _selectedBookLiveData

    fun setSelectedBook(book: DataKatalogBuku){
        if (book != null) {
            _selectedBookLiveData.value = book
            Log.d("ViewModel", "setSelectedBook: $book")
        }
    }

}
