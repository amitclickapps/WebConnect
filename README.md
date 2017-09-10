### Global Configuration
#### Old
```
        new Configuration.Builder()
                .baseUrl("test")
                .connectTimeOut(1000L)
                .readTimeOut(2000L)
                .config();
```
#### New
```
        new ApiConfiguration.Builder()
                        .baseUrl(MainActivity.ENDPOINT_BASE)
                        .timeOut(1000L, 2000L)
                        .debug(true)
                        .config();
```
-----
### Api calling using Webconnect
#### Old
```
        WebParam webParam = WebConnect.with(this,"url")
                .requestParam(new LinkedHashMap<String, Object>())
                .callback(new WebHandler.OnWebCallback() {
                    @Override
                    public <T> void onSuccess(@Nullable T object, int taskId, Response response) {

                    }

                    @Override
                    public <T> void onError(@Nullable T object, String error, int taskId, Response response) {

                    }
                })
                .build();
        WebConnect.connect(Services.class,webParam).friends()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Callback<>(param));
```
### Interface for service
```
public interface Services {

    @GET("friends")
    Observable<Response<Object>> friends();
}
```
#### New
```
        WebConnect.with(this, ENDPOINT_POST)
                            .httpType(WebParam.HttpType.GET) // Really Important
                            .pathSegment("posts")
                            .pathSegmentParam("1") // if needed
                            .callback(new WebHandler.OnWebCallback() {
                                @Override
                                public <T> void onSuccess(@Nullable T object, int taskId, Response response) {
                                    if (object != null) {
                                        Toast.makeText(MainActivity.this, object.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public <T> void onError(@Nullable T object, String error, int taskId) {
                                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                                }
                            }).requestParam(requestMap).connect();
```
OR
```
        WebConnect.with(this, ENDPOINT_POST, "posts")
                            .httpType(WebParam.HttpType.GET) // Really Important
                            .pathSegmentParam("1")
                            .callback(new WebHandler.OnWebCallback() {
                                @Override
                                public <T> void onSuccess(@Nullable T object, int taskId, Response response) {
                                    if (object != null) {
                                        Toast.makeText(MainActivity.this, object.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public <T> void onError(@Nullable T object, String error, int taskId) {
                                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                                }
                            }).requestParam(requestMap).connect();
```

-----


Download
--------
Add the JitPack repository to your root build.gradle:

![](https://jitpack.io/v/amitclickapps/retrofit-util.svg?style=flat-square)


```groovy
	allprojects {
		repositories {
			maven { url "https://jitpack.io" }
		}
	}
```
Add the Gradle dependency:
```groovy
	dependencies {
		compile 'com.github.amitclickapps:retrofit-util:latest'
	}
```