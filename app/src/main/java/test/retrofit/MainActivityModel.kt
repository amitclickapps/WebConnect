package test.retrofit

import android.app.Application
import android.arch.lifecycle.*
import android.content.Context
import retrofit2.Response
import webconnect.com.webconnect.WebConnect
import webconnect.com.webconnect.WebHandler
import java.util.*

/**
 * Created by clickapps on 22/12/17.
 */

class MainActivityModel(application: Application) : AndroidViewModel(application), LifecycleObserver {
    //    public static final String ENDPOINT_BASE = "http://api.dev.moh.clicksandbox1.com:8080/v1/";
    private val activity: Context
    //    private MainActivityModel(Activity activity) {
    //        this.activity = activity;
    //    }

    private val get = MutableLiveData<Any>()
    private val post = MutableLiveData<Any>()
    private val put = MutableLiveData<Any>()
    private val delete = MutableLiveData<Any>()

    init {
        activity = application
    }

    fun getGet(): LiveData<Any> {
        return get
    }

    fun getPost(): LiveData<Any> {
        return post
    }

    fun getPut(): LiveData<Any> {
        return put
    }

    fun getDelete(): LiveData<Any> {
        return delete
    }

    fun get() {
        WebConnect.with(this.activity, ENDPOINT_GET)
                .get()
                .cache(true)
                .callback(object : WebHandler.OnWebCallback {
                    override fun <T> onSuccess(`object`: T?, taskId: Int, response: Response<*>?) {
                        get.value = `object`
                    }

                    override fun <T> onError(`object`: T?, error: String, taskId: Int) {
                        get.value = `object`
                        WebConnect.with(activity, ENDPOINT_GET)
                                .get()
                                .callback(object : WebHandler.OnWebCallback {
                                    override fun <T> onSuccess(`object`: T?, taskId: Int, response: Response<*>?) {
                                        get.value = `object`
                                    }

                                    override fun <T> onError(`object`: T?, error: String, taskId: Int) {
                                        get.value = `object`
                                    }

                                })
                                .connect()
                    }

                })
                .connect()


    }

    fun post(): Map<String, String> {
        val requestMap = LinkedHashMap<String, String>()
        requestMap.put("name", "Amit")
        requestMap.put("job", "manager")
        WebConnect.with(this.activity, ENDPOINT_POST)
                .post()
                .cache(true)
                .bodyParam(requestMap)
                .callback(object : WebHandler.OnWebCallback {
                    override fun <T> onSuccess(`object`: T?, taskId: Int, response: Response<*>?) {
                        if (`object` != null) {
                            post.value = `object`
                        }
                    }

                    override fun <T> onError(`object`: T?, error: String, taskId: Int) {
                        post.value = `object`
                        WebConnect.with(activity, ENDPOINT_POST)
                                .post()
                                .bodyParam(requestMap)
                                .callback(object : WebHandler.OnWebCallback {
                                    override fun <T> onSuccess(`object`: T?, taskId: Int, response: Response<*>?) {
                                        if (`object` != null) {
                                            post.value = `object`
                                        }
                                    }

                                    override fun <T> onError(`object`: T?, error: String, taskId: Int) {
                                        post.value = `object`
                                    }
                                }).connect()
                    }
                }).connect()
        return requestMap
    }

    fun put() {
        val requestMap = LinkedHashMap<String, String>()
        requestMap.put("name", "Amit Singh")
        requestMap.put("job", "manager")
        WebConnect.with(activity, ENDPOINT_PUT)
                .put()
                .cache(true)
                .bodyParam(requestMap)
                .callback(object : WebHandler.OnWebCallback {
                    override fun <T> onSuccess(`object`: T?, taskId: Int, response: Response<*>?) {
                        if (`object` != null) {
                            put.value = `object`
                        }
                    }

                    override fun <T> onError(`object`: T?, error: String, taskId: Int) {
                        put.value = `object`
                    }
                }).connect()
    }

    fun delete() {
        val requestMap = LinkedHashMap<String, String>()
        requestMap.put("name", "Amit Singh")
        requestMap.put("job", "manager")
        WebConnect.with(activity, ENDPOINT_PUT)
                .delete()
                .bodyParam(requestMap)
                .callback(object : WebHandler.OnWebCallback {
                    override fun <T> onSuccess(`object`: T?, taskId: Int, response: Response<*>?) {
                        if (`object` != null) {
                            delete.value = `object`
                        }
                    }

                    override fun <T> onError(`object`: T?, error: String, taskId: Int) {
                        delete.value = `object`
                    }
                }).connect()
    }


    class MainActivityModelFactory(private val activity: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainActivityModel(activity) as T
        }
    }

    companion object {
        var ENDPOINT_GET = "offers"
        val ENDPOINT_POST = "users"
        val ENDPOINT_PUT = "users/740"
        val ENDPOINT_BASE = "https://reqres.in/api/"
    }

}
