### Global Configuration
```
        new Configuration.Builder()
                .baseUrl("test")
                .connectTimeOut(1000L)
                .readTimeOut(2000L)
                .config();
```

### Api calling using Webconnect
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
        WebConnect.connect(Services.class,webParam).friends().subscribe(new Callback<Object>(webParam));
```