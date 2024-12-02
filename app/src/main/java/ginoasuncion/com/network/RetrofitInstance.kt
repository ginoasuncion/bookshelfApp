package ginoasuncion.com.bookshelfapp.network

import ginoasuncion.com.bookshelfapp.GoogleBooksService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: GoogleBooksService = retrofit.create(GoogleBooksService::class.java)
}
