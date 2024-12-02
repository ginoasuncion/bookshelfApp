package ginoasuncion.com.bookshelfapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import ginoasuncion.com.bookshelfapp.repository.BooksRepository
import ginoasuncion.com.bookshelfapp.network.RetrofitInstance
import ginoasuncion.com.bookshelfapp.model.Book

class BooksViewModel : ViewModel() {
    private val repository = BooksRepository(RetrofitInstance.api)

    private val _books = mutableStateOf<List<Book>>(emptyList())
    val books: State<List<Book>> = _books

    fun searchBooks(query: String) {
        viewModelScope.launch {
            try {
                val fetchedBooks = repository.searchBooks(query)
                _books.value = fetchedBooks
            } catch (e: Exception) {
                _books.value = emptyList()
                println("Error fetching books: ${e.message}")
            }
        }
    }
}
