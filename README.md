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
ENDPOINT_POST = "posts/"
if need to append pathSegmentParam then pass pathSegmentParam("1")
```
        WebConnect.with(this, ENDPOINT_POST)
                            .httpType(WebParam.HttpType.GET) // Really Important
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
        WebConnect.with(this, ENDPOINT_POST)
                            .httpType(WebParam.HttpType.GET) // Really Important
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
#### Multipart
```
 Map<String, RequestBody> multipartMap = new HashMap<>();
        multipartMap.put("key1", RequestBody.create(MediaType.parse("text/plain"), "1"));
        multipartMap.put("key1", RequestBody.create(MediaType.parse("text/plain"), "name"));
        // Adding File 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("attachments_attributes[][attachment]\";");
        stringBuilder.append("filename=");
        stringBuilder.append("\"").append("fileName.jpp");
        multipartMap.put(stringBuilder.toString(), RequestBody.create(MediaType.parse("MIMETYPE"), new File("filepath")));
        WebConnect.with(this,"register/")
                .multipartParam(multipartMap)
                .httpType(WebParam.HttpType.POST).connect();
```
-----
#### Download File/Image (Anything)
```
File file = new File(Environment.getExternalStorageDirectory(),"temp.jpg");
            Map<String, String> requestMap = new LinkedHashMap<>();
            WebConnect.with(this, "624996-pixelponew.jpg")
                    .httpType(WebParam.HttpType.GET)
                    .baseUrl("http://api.staging.moh.clicksandbox1.com:8080/upload/magazins/8/original/")
                    .requestParam(requestMap)
                    .downloadFile(file)
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
                    }).connect();
```

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