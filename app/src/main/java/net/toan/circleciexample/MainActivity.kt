package net.toan.circleciexample

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

     val test: TestObject by lazy(LazyThreadSafetyMode.PUBLICATION) {
         TestObject("Java")
     }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        compositeDisposable.add(
            Observable.just("Device").observeOn(Schedulers.io()).subscribe({
                //test.printProgramName()
                Log.d("MainActivity", "device info: $it")
            }, {
                Log.d("MainActivity", "error info: $it")
            })
        )

        val packageManager = packageManager
        val packageName = packageName
        val targetSdkVersion: Int
        val minSdkVersion: Int
        val versionCode: Int
        val versionName: String
        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)

            /*
            versionCode was deprecated in API level 28.
            Use getLongVersionCode() instead, which includes both this
            and the additional versionCodeMajor attribute. The version
            number of this package, as specified by the <manifest>
            tag's versionCode attribute.
             */versionCode = packageInfo.versionCode
            versionName = packageInfo.versionName
            val applicationInfo = packageInfo.applicationInfo

            /*
            targetSdkVersion added in API level 4
            minSdkVersion added in API level 24
             */targetSdkVersion = applicationInfo.targetSdkVersion
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                minSdkVersion = applicationInfo.minSdkVersion
            } else {
                minSdkVersion = 0
            }
            Log.d("MainActivity", "targetSdkVersion = $targetSdkVersion minSdkVersion = $minSdkVersion versionCode: $versionCode versionName: $versionName")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            Toast.makeText(
                applicationContext,
                "NameNotFoundException: " + e.message,
                Toast.LENGTH_LONG
            ).show()
        }



        test.printProgramName()
    }

    fun getAiTechSupport(): Single<Boolean> {
        return Single.just(true)
    }

    fun getDeviceInfo(): Single<String> {
        return Single.just("device info")
    }

    fun asList(vararg ts: Int, b: Int = 0) : List<Int> {
        val result = ArrayList<Int>()
        for (t in ts) // ts is an Array
            result.add(t)
        result.add(b)
        return result
    }

    infix fun Int.count(a: Int) : Int {
        return this + a
    }

//    fun <T, R> Collection<T>.fold(initial: R, combine: (acc: R, nextElement: T) -> R): R {
//        var accumulator: R = initial
//        this.forEach { element ->
//            accumulator = combine(accumulator, element)
//        }
//        return accumulator
//    }

    fun show() {
        var a = 1
        var b = 2
        a = b.also { b = a }
    }

    class MyStringCollection {
        infix fun add(s: String) {
            Log.d("MainActivity", "add: $s")
        }

        fun build() {
            this add "abc"   // Correct
            add("abc2")       // Correct
            this add "abc3"        // Incorrect: the receiver must be specified
        }

    }
}