package ginoasuncion.com.bookshelfapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import ginoasuncion.com.bookshelfapp.viewmodel.BooksViewModel
import ginoasuncion.com.bookshelfapp.model.Book
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import ginoasuncion.com.bookshelfapp.R
import androidx.compose.ui.res.dimensionResource

@Composable
fun BookshelfApp(viewModel: BooksViewModel = viewModel()) {
    val books by viewModel.books
    var query by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.app_padding))
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.vertical_spacing))
        )

        OutlinedTextField(
            value = query,
            onValueChange = { newQuery ->
                query = newQuery
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(id = R.dimen.vertical_spacing)),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_hint),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (query.isNotEmpty()) {
                        viewModel.searchBooks(query)
                    }
                    keyboardController?.hide()
                }
            ),
            shape = MaterialTheme.shapes.small,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        if (books.isNotEmpty()) {
            BookGrid(books)
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.no_books_message),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
fun BookGrid(books: List<Book>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.horizontal_spacing)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.vertical_spacing)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.horizontal_spacing)),
        modifier = Modifier.fillMaxSize()
    ) {
        items(books) { book ->
            BookItem(book)
        }
    }
}

@Composable
fun BookItem(book: Book) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.card_padding))
    ) {
        SubcomposeAsyncImage(
            model = book.imageUrl,
            contentDescription = stringResource(id = R.string.book_cover_message),
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.book_image_height))
                .clip(MaterialTheme.shapes.small),
            contentScale = androidx.compose.ui.layout.ContentScale.Fit
        ) {
            SubcomposeAsyncImageContent()
            if (painter == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = stringResource(id = R.string.no_image_message),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        Text(
            text = book.title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.vertical_spacing)),
            fontSize = dimensionResource(id = R.dimen.title_font_size).value.sp
        )
    }
}
