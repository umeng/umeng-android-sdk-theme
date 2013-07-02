#如何更好的使用自动更新

更新提示的三种设计思路

1. 后台检测，弹出更新提示框<br>
![example](http://)
2. 在设置界面提供更新检测按钮，即时检测<br>
![example]()
3. 提前检测,在设置界面提示最新版本<br>
![example]()

这三种实现都基于友盟的自动更新服务，友盟自动更新集成文档在[*这里*](http://dev.umeng.com/doc/document_update_android.html)

## 1. 后台检测，弹出更新提示框

使用友盟统计分析服务，调用如下一行代码即可完成：

```
public void onCreate(Bundle  savedInstanceState) {
    super.onCreate(savedInstanceState);
	...
    UmengUpdateAgent.update(this);
	...
}
```

这行代码会链接友盟服务器，判断是否有新版应用程序，如果发现可更新的应用程序安装包，会提示用户是否更新。用户选择更新后，安装包会在后台下载自动安装更新。

## 2. 在设置界面提供更新检测按钮，即时检测<br>

很多App都会在设置界面提供 **新版本检测** 功能，如图示，假如点击按钮之后会调用如下的方法：

```
public void onCheckUpdateClicked(){
	final ProgressDialog dialog = new ProgressDialog(this);
	
	UmengUpdateAgent.setUpdateAutoPopup(false);
	UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
        @Override
        public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
			dialog.dismiss();

            switch (updateStatus) {
            case 0: // has update
                UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
                break;
            case 1: // has no update
                Toast.makeText(mContext, "没有更新", Toast.LENGTH_SHORT)
                        .show();
                break;
            case 2: // none wifi
                Toast.makeText(mContext, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT)
                        .show();
                break;
            case 3: // time out
                Toast.makeText(mContext, "超时", Toast.LENGTH_SHORT)
                        .show();
                break;
            }
        }
	});

	UmengUpdateAgent.update(this);
	dialog.show();	
}
```

## 3. 提前检测,在设置界面提示最新版本<br>

这种方式或许更友好一点，第一种方式频繁的给用户提示新版本不是很友好，第二种方式又过于被动，很多情况下用户不会主动检测新版本，如果在设置界面就告诉用户最新版本或许是一种折中的做法。

1. 需要先写两个工具方法，记录和恢复更新状态

```
	private void saveResponse(Context context,UpdateResponse updateInfo){
			SharedPreferences update = context.getSharedPreferences("um_update_info", MODE_PRIVATE);	
			if( update != null ) {
				update.edit().putString("serial_update_info", updateInfo.toString()).commit();
			}
	}

	private UpdateResponse parseResponse(Context context){
	
			SharedPreferences update = context.getSharedPreferences("um_update_info", MODE_PRIVATE);
			JSONObject json = null;
			
			if(update != null ){
				try{
					json = new JSONObject(update.getString("serial_update_info", null));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			return new UpdateResponse( json );
	}
```

1. 在程序入口调用如下方法，检测新版,并保存更新状态

```
public void checkUpdate(){
	UmengUpdateAgent.setUpdateAutoPopup(false);
	UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
        @Override
        public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
			dialog.dismiss();

            switch (updateStatus) {
            case 0: // has update
            case 1: // has no update
                saveResponse（ mContext , updateInfo );
                break;
            case 2: // none wifi
                Toast.makeText(mContext, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT)
                        .show();
                break;
            case 3: // time out
                Toast.makeText(mContext, "超时", Toast.LENGTH_SHORT)
                        .show();
                break;
            }
        }
	});

	UmengUpdateAgent.update(this);
}

```

2. 在打开设置页面的时候，读取缓存的状态，提示用户


```
UpdateResponse updateInfo = parseResponse(mContext);

if( updateInfo.hasUpdate) {
	UmengUpdateAgent.showUpdateDialog( mContext, updateInfo );
}

// 或者直接强制更新：
// if( updateInfo.hasUpdate) {
//	UmengUpdateAgent.startDownload( updateInfo );
// }

```

### 4.其他小技巧

默认总是在程序启动的时候检测版本，浪费流量，那么加一些自己的小策略，比如按一定时间间隔更新：

```
	private static final String KEY_LAST_UPDATE_TIME = "umeng_last_update_time";

	/**
	 * 自动更新，在main activity 中调用，此方法会请求服务器，检查是否有最新版本
	 * 
	 * @param context
	 *            当前Activity
	 * @param internal
	 *            控制自动更新请求的频率，单位毫秒,eg：update(context,24*60*60*1000) ，每天更新一次
	 */
	public static void update(Context context, final long internal) {
		final Context mContext = context;
		if (mContext == null) {
			Log.i(TAG, "unexpected null Context");
			return;
		}

		SharedPreferences preference = getUpdateSettingPreferences(mContext);
		long lastUpdateTime = preference.getLong(KEY_LAST_UPDATE_TIME, 0);
		long now = System.currentTimeMillis();

		if ((now - lastUpdateTime) > internal) {
			update(mContext);
			preference.edit().putLong(KEY_LAST_UPDATE_TIME, now).commit();
		}
	}
```

这样调用 `update( mContext, 24*60*60*1000 );` 就可以实现按天更新。

自动更新对话框默认两个按钮 “立即更新” “以后再说”， 我想把以后再说改成 “不再提示” 怎么办 ？

修改对应的 xml 文件如下：

```
    <!-- OK&Cancel Button -->
    <LinearLayout android:layout_height="wrap_content" android:layout_width="fill_parent">
        <Button android:onClick="onButtonClick"
            	android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" 
            	android:text="不再提示" 
            	android:background="@drawable/umeng_update_button_ok_selector"
            	android:layout_margin="10dp"
            	android:padding="12dp"
            	android:gravity="center"
            	android:textColor="#FFFFFF"/>
        <Button android:id="@+id/umeng_update_id_ok"
            	android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" 
            	android:text="@string/UMUpdateNow" 
            	android:background="@drawable/umeng_update_button_ok_selector"
            	android:layout_margin="10dp"
            	android:padding="12dp"
            	android:gravity="center"
            	android:textColor="#FFFFFF"
            	android:visibility="gone"  />     
        <Button android:id="@+id/umeng_update_id_cancel"
            	android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" 
                android:text="@string/UMNotNow"
                android:background="@drawable/umeng_update_button_cancel_selector"
                android:layout_margin="10dp"
            	android:padding="12dp"
            	android:gravity="center"
            	android:textColor="#AAABAF" />
    </LinearLayout>
```

上面的修改首先隐藏了原来的更新提示按钮（注意只能隐藏，不能删除），然后添加一个新的按钮，回调函数是 `onButtonClick` , 这样在调用update 的地方实现回调函数就可以了。

```
	public void onButtonClick(View v){
		// 处理新的逻辑
		Log.i("--->", "onButtonClick");
	}
```



