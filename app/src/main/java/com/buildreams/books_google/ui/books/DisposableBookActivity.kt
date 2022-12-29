package com.buildreams.books_google.ui.books

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.buildreams.books_google.ui.theme.BooksgoogleTheme
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DisposableBookActivity : ComponentActivity() {

    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksgoogleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text(text = "Dispose")
                }
            }
        }

        // an observable is a data source
        val booksObservable: Observable<String> = Observable.just(
            "In Search of Lost Time by Marcel Proust",
            "Ulysses by James Joyce",
            "Don Quixote by Miguel de Cervantes",
            "One Hundred Years of Solitude by Gabriel Garcia Marquez",
            "The Great Gatsby by F",
            "Moby Dick by Herman Melville"
        )

        val booksObserver: Observer<String> = object : Observer<String> {
            //it's called when an observer is subscribed to an observable just one time
            override fun onSubscribe(d: Disposable) {
                disposable = d
                Log.d("TAG1", "onSubscribe Hilo: ${Thread.currentThread().name}")
            }

            //when the observable is emitting data this method is called, on this example this method
            // is called 6 times for each book
            override fun onNext(book: String) {
                Log.d("TAG1", "onNext book: $book Hilo: ${Thread.currentThread().name}")
                Log.d("TAG1", "isDispose ${disposable.isDisposed}")
            }

            //called this method if exist an error when we try to get the list of books
            override fun onError(e: Throwable) {
                Log.d("TAG1", "onError Hilo: ${Thread.currentThread().name}")
            }

            // it is called after the data is obtained, for this example when we get the 6 books
            override fun onComplete() {
                Log.d("TAG1", "onComplete completed Hilo: ${Thread.currentThread().name}")
                //if the user change of activity before to complete the operation e.g
                // (called to the web service) the subscription is maintained and we must dispose
                // this subscription in the onDestroy method
                Log.d("TAG1", "isDispose ${disposable.isDisposed}")

            }
        }

        //how we do the subscription to the books
        booksObservable
            //observable is executed in a background thread
            .subscribeOn(Schedulers.io())
            //observer is executed in the main thread
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(booksObserver)
    }

    //Disposable is basically to dispose(desechar) the subscription when a observer
    // does not want to continue listen the to observable

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG1", "isDispose ${disposable.isDisposed}")
        disposable.dispose()
        Log.d("TAG1", "isDispose ${disposable.isDisposed}")
        Log.d("TAG1", "onDestroy dispose subscription Hilo: ${Thread.currentThread().name}")
    }
}