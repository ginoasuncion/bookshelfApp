package ginoasuncion.com.bookshelfapp.repository

import android.util.Log
import ginoasuncion.com.bookshelfapp.GoogleBooksService
import ginoasuncion.com.bookshelfapp.model.Book

class BooksRepository(private val api: GoogleBooksService) {
    suspend fun searchBooks(query: String): List<Book> {
        val response = api.searchBooks(query)
        Log.d("API_RESPONSE", "Response: $response")

        return response.items?.map { item ->
            val title = item.volumeInfo.title
            val imageUrl = item.volumeInfo.imageLinks?.thumbnail?.let { ensureHttps(it) } ?: ""
            Log.d("API_RESPONSE", "Title: $title, Image URL: $imageUrl")
            Book(
                title = title,
                imageUrl = imageUrl
            )
        } ?: emptyList()
    }

    private fun ensureHttps(url: String): String {
        return if (url.startsWith("http://")) url.replace("http://", "https://") else url
    }
}
