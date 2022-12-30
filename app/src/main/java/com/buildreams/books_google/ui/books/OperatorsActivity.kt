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
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class OperatorsActivity : ComponentActivity() {
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

//        usingOperatorJust()
//        usingOperatorJustArray()
//        usingOperatorFromArray()
//        usingOperatorRange()
//        usingOperatorRepeat()
        usingOperatorCreate()
    }


    private fun usingOperatorCreate() {
        Log.d("TAG1", "------ Create ------")
        Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                //we define the data that emmit the subscribe
                try {
                    Log.d("TAG1", "subscribe: Hilo: ${Thread.currentThread().name}")
                    emitter.onNext("1")
                    emitter.onNext("2")
                    emitter.onNext("3")
                    emitter.onNext("4")
                    emitter.onNext("5")
                    emitter.onNext("6")
                    emitter.onNext("7")
                    emitter.onNext("8")
                    emitter.onNext("9")
                    emitter.onNext("0")
                } catch (e: Exception) {
                    emitter.onError(e)
                }
            }
        })
            .repeat(4)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: String) {
                    Log.d("TAG1", "Create->onNext: $t onSubscribe Hilo: ${Thread.currentThread().name}")
                }

                override fun onError(e: Throwable) {}

                override fun onComplete() {}
            })
    }

    //Repeat: n times the observable received
    private fun usingOperatorRepeat() {
        Log.d("TAG1", "------ Repeat ------")
        Observable.range(10, 3)
            .repeat(4)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: Int) {
                    Log.d("TAG1", "Repeat->onNext: $t")
                }

                override fun onError(e: Throwable) {}

                override fun onComplete() {}
            })
    }

    //Range: start in the number 7 an emit 10 times the start value plus 1
    private fun usingOperatorRange() {
        Log.d("TAG1", "------ Range ------")
        Observable.range(7, 10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: Int) {
                    Log.d("TAG1", "Range->onNext: $t")
                }

                override fun onError(e: Throwable) {}

                override fun onComplete() {}
            })
    }

    //FromArray: we receive an iterator and this create an observable
    private fun usingOperatorFromArray() {
        Log.d("TAG1", "------ FromArray ------")
        val numberString = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        Observable.fromArray(*numberString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {

                override fun onSubscribe(d: Disposable) {}

                override fun onNext(s: String) {
                    Log.d("TAG1", "FromArray->onNext: $s")
                }

                override fun onError(e: Throwable) {}

                override fun onComplete() {}
            })
    }

    //JustArray: we receive the data as an unique element which is an array
    private fun usingOperatorJustArray() {
        Log.d("TAG1", "------ JustArray ------")
        val numberString = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        Observable.just(numberString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<String>> {
                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: List<String>) {
                    Log.d("TAG1", "JustArray->onNext: $t")
                }

                override fun onError(e: Throwable) {}

                override fun onComplete() {}

            })
    }

    //Just: take an element or a list of elements max 10 an convert this in observable
    private fun usingOperatorJust() {
        Log.d("TAG1", "------ Just ------")
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: Int) {
                    Log.d("TAG1", "Just->onNext: $t")
                }

                override fun onError(e: Throwable) {}

                override fun onComplete() {}
            })


    }
}