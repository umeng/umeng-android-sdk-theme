## 首次启动不更新

有的时候，开发者会希望在应用在下载后第一次启动时不要启动自动更新，这样即使下载的不是最近版本也不会在首次启动时提醒更新。

在应用程序入口`Activity`里的`OnCreate()`方法中调用
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
	boolean isFirstStart = sharedPreferences.getBoolean(KEY_FIRST_START, true);
	if (isFirstStart) {
		sharedPreferences.edit().putBoolean(KEY_FIRST_START, false).commit();
	} else {
		UmengUpdateAgent.update(this);
	}
```
其中`SHARED_PREFERENCES_NAME`为sharedPreference文件名，`KEY_FIRST_START`为判断是否为第一次启动值的key
