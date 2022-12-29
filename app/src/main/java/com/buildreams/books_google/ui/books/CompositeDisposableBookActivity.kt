package com.buildreams.books_google.ui.books

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.buildreams.books_google.ui.theme.BooksgoogleTheme
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class CompositeDisposableBookActivity : ComponentActivity() {

    //implement observer and disposable
    private lateinit var observerPosition: DisposableObserver<Int>
    private lateinit var observerName: DisposableObserver<String>

    private lateinit var observablePosition: Observable<Int>
    private lateinit var observableName: Observable<String>

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksgoogleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ButtonList()
                }
            }
        }

        observableName = Observable.just(
            "In Search of Lost Time by Marcel Proust",
            "Ulysses by James Joyce",
            "Don Quixote by Miguel de Cervantes",
            "One Hundred Years of Solitude by Gabriel Garcia Marquez",
            "The Great Gatsby by F",
            "Moby Dick by Herman Melville"
        )

        observablePosition = Observable.just(1, 2, 3, 4, 5, 6)

        observerPosition = object : DisposableObserver<Int>() {
            override fun onNext(t: Int) {
                Log.d("TAG1", "onNext number: $t")
            }

            override fun onError(e: Throwable) {
                Log.d("TAG1", "onError")
            }

            override fun onComplete() {
                Log.d("TAG1", "onComplete position")
            }
        }

        observerName = object : DisposableObserver<String>() {
            override fun onNext(t: String) {
                Log.d("TAG1", "onNext name: $t")
            }

            override fun onError(e: Throwable) {
                Log.d("TAG1", "onError")
            }

            override fun onComplete() {
                Log.d("TAG1", "onComplete name")
            }
        }

        compositeDisposable.add(
            observablePosition
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observerPosition)
        )

        compositeDisposable.add(
            observableName
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observerName)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    @Composable
    fun ButtonList() {
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
        }
    }
}