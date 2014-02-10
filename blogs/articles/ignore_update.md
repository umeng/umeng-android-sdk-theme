## 忽略更新

使用友盟自动更新模块实现忽略更新的示例：
有时候对于某些更新很小的版本，用户并没有更新意愿，但是即使选择了下次再说之后，下次启动应用的时候仍然会弹窗提醒用户，友盟自动更新模块在V2.3增加了忽略更新功能，但不是默认开启的，这里给出忽略更新的使用说明。

修改`umeng_update_dialog.xml`中相关控件

```xml
<Button
	android:id="@+id/umeng_update_id_close"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	android:layout_alignParentRight="true"
	android:layout_centerVertical="true"
	android:layout_marginRight="10dp"
	android:focusable="true"
	android:visibility="gone"
	android:background="@drawable/umeng_update_button_close_selector"/>
```

去除其中`android:visibility="gone"`属性，该控件是位于dialog右上角的关闭按钮，用来代替以后再说功能按钮。

修改`umeng_update_dialog.xml`中相关控件

```xml
<Button
	android:id="@+id/umeng_update_id_ignore"
	android:visibility="gone"
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

去除其中`android:visibility="gone"`属性，该控件是实现忽略更新功能的按钮。
	
同时修改`umeng_update_dialog.xml`中相关控件

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

在其中增加`android:visibility="gone"`属性，该控件是实现以后再说功能的按钮，隐藏该按钮的原因是在dialog中同时显示3个按钮会显得过于拥挤，该功能可以通过点击右上角的关闭按钮或者按回退键来实现。
	
