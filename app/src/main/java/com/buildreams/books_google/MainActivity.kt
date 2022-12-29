package com.buildreams.books_google

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.buildreams.books_google.ui.books.BookActivity
import com.buildreams.books_google.ui.books.CompositeDisposableBookActivity
import com.buildreams.books_google.ui.books.DisposableBookActivity
import com.buildreams.books_google.ui.theme.BooksgoogleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksgoogleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth(),
                            onClick = {
                                context.startActivity(
                                    Intent(
                                        context,
                                        BookActivity::class.java
                                    )
                                )
                            }) {
                            Text(text = "Show books")
                        }
                        Button(
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth(),
                            onClick = {
                                context.startActivity(
                                    Intent(
                                        context,
                                        DisposableBookActivity::class.java
                                    )
                                )
                            }) {
                            Text(text = "Show disposable books")
                        }
                        Button(
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth(),
                            onClick = {
                                context.startActivity(
                                    Intent(
                                        context,
                                        CompositeDisposableBookActivity::class.java
                                    )
                                )
                            }) {
                            Text(text = "Show composite disposable books")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BooksgoogleTheme {
        Greeting("Android")
    }
}