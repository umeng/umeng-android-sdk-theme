## 手动更新

使用友盟自动更新模块实现手动更新的示例：
除了在进入应用的第一个页面使用自动更新检测版本更新以外，很多应用会在应用的设置页面提供手动检测更新的功能，并手动处理更新逻辑，这里以使用控件点击监听为例展示怎用使用手动更新的功能。

```java
private View.OnClickListener listener = new View.OnClickListener() {
	public void onClick(View v) {
		//禁止自动弹框，手动处理相关逻辑
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
					
			@Override
			public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
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
				case 4: // is updating
					// We show toast already, do not show again.
					break;
				}
			}
		});
		//当希望手动更新可以在非wifi环境下工作，可以无视版本忽略时，使用此方法
		UmengUpdateAgent.forceUpdate(mContext);
	}
};
```

因为这些关于更新的设置是全局的，所以如果不希望手动更新中的设置运用到自动更新中去，则可以在当前页面退出时将设置恢复成默认，或者在自动更新之前恢复设置，例如：
```java
@Override
protected void onStop() {
	super.onStop();
	UmengUpdateAgent.setUpdateOnlyWifi(true);
	UmengUpdateAgent.setUpdateAutoPopup(true);
	UmengUpdateAgent.setUpdateListener(null);
}
```
或
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	UmengUpdateAgent.setUpdateOnlyWifi(true);
	UmengUpdateAgent.setUpdateAutoPopup(true);
	UmengUpdateAgent.setUpdateListener(null);
	UmengUpdateAgent.update(this);
}
```
