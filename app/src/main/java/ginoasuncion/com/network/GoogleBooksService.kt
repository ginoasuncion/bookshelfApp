package ginoasuncion.com.bookshelfapp

import ginoasuncion.com.bookshelfapp.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksService {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String = "AIzaSyC_vhyKtMRJTIyQUH64lts77SmqWtIjpSY"
    ): BookResponse
}
