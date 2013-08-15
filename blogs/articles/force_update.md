## 强制更新

有的时候，开发者会想要用户必须进行更新，下面给出使用友盟自动更新模块(V2.3)进行强制更新的示例：

修改`umeng_update_dialog.xml`中相关控件

```xml
<Button
	android:id="@+id/umeng_update_id_cancel"
	android:layout_width="0dp"
	android:layout_height="wrap_content"
	android:layout_margin="5dp"
	android:layout_weight="1"
	android:background="@drawable/umeng_update_button_cancel_selector"
	android:gravity="center"
	android:padding="12dp"
	android:text="@string/UMNotNow"
	android:focusable="true"
	android:textColor="#AAABAF" />
```

在其中增加`android:visibility="gone"`属性，该控件是实现以后再说功能的按钮，隐藏该按钮让dialog只显示更新的选项。

再增加关于对话框按键的监听，对于未选择更新的行为，关闭app

```java
UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {

	@Override
	public void onClick(int status) {
		switch (status) {
		case UpdateStatus.Update:
			break;
		default:
			//close the app
			MyActivity.this.finish();
		}
	}
});
```
