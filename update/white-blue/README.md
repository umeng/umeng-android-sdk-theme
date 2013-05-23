### 效果图

![](https://raw.github.com/ntop001/umeng-android-sdk-theme/master/update/white-blue/white-blue.png)

### 结构

自动更新需要的默认资源结构如下：

```
res - drawable - umeng_update_button_cancel_normal.xml
               - umeng_update_button_cancel_tap.xml
               - umeng_update_button_ok_normal.xml
               - umeng_update_button_ok_tap.xml
               - umeng_update_button_cancel_selector.xml
               - umeng_update_button_ok_selector.xml
               - umeng_update_title_bg.xml
               - umeng_update_dialog_bg.xml
               - umeng_update_wifi_disable.png
               
      - layout - umeng_update_dialog.xml
      
      - values - umeng_common_strings.xml
               - umeng_update_string,xml
      - values-zh - umeng_common_strings.xml
                  - umeng_update_string,xml
```

1. `drawable`  路径下定义了按钮和背景所需的图片
2. `layout` 路径是更新对话框显示的布局
3. `values` 路径下是显示的字符串资源， 这些都是可以直接修改的。

### 修改默认实现达到上述效果

#### 1. 修改布局文件

布局文件默认实现如下, 分成 `Title`, `SplitView` , `Content` , `Ok&Cancel Button` 三部分.

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="280dp"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:background="@drawable/umeng_update_dialog_bg">
    
    <!-- Title -->
    <RelativeLayout 
        android:layout_width="fill_parent" 
        android:layout_height="40dp"
        >

        <ImageView android:id="@+id/umeng_update_wifi_indicator"
            android:src="@drawable/umeng_update_wifi_disable"
            android:layout_centerVertical="true"
            android:layout_width="30dp"
            android:layout_height="30dp"
            android:layout_marginLeft="10dp"
            />
            
        <TextView
            android:textAppearance="?android:attr/textAppearanceLarge"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/UMUpdateTitle"
            android:textColor="#63C2FF"
            android:layout_centerInParent="true"
            />
    </RelativeLayout>
    
    <!-- split -->
    <View android:background="#2AACFF" android:layout_width="280dp" android:layout_height="2dp" 
          android:layout_marginLeft="10dp" android:layout_marginRight="10dp"
        />
    <!-- Content -->
   <LinearLayout 
        android:layout_width="fill_parent" android:layout_height="wrap_content"
        android:orientation="vertical" android:layout_margin="10dp">

        <TextView android:id="@+id/umeng_update_content"
            android:layout_height="wrap_content" 
            android:layout_width="280dp" 
            android:layout_marginTop="10dp"
            android:layout_marginLeft="10dp"
            android:textColor="#AAABAF"
            />
    </LinearLayout>

    <!-- OK&Cancel Button -->
    <LinearLayout android:layout_height="wrap_content" android:layout_width="fill_parent">
        <Button android:id="@+id/umeng_update_id_ok"
                           android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" 
            	android:text="@string/UMUpdateNow" 
            	android:background="@drawable/umeng_update_button_ok_selector"
            	android:layout_margin="10dp"
            	android:padding="12dp"
            	android:gravity="center"
            	android:textColor="#FFFFFF"/>
        
        <Button android:id="@+id/umeng_update_id_cancel"
            	android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" 
                android:text="@string/UMNotNow"
                android:background="@drawable/umeng_update_button_cancel_selector"
                android:layout_margin="10dp"
            	android:padding="12dp"
            	android:gravity="center"
            	android:textColor="#AAABAF" />
    </LinearLayout>
</LinearLayout>
```

为了实现途中效果，需要：

1. 删除 `SplitView` 横划线
2. 修改 `Title` 背景色
3. 修改整个 `layout` 背景色
4. 修改 `Button` 风格


分别修改上面四个部分，得到新的 xml 如下：

`umeng_update_dialog.xml` 仅仅删除了 `SplitView` ，并给 `Title` 部分添加了背景

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="280dp"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:background="@drawable/umeng_update_dialog_bg">
    
    <!-- Title -->
    <RelativeLayout 
        android:layout_width="fill_parent" 
        android:layout_height="40dp"
        android:background="@drawable/umeng_update_title_bg"
        >

        <ImageView android:id="@+id/umeng_update_wifi_indicator"
            android:src="@drawable/umeng_update_wifi_disable"
            android:layout_centerVertical="true"
            android:layout_width="30dp"
            android:layout_height="30dp"
            android:layout_marginLeft="10dp"
            />
            
        <TextView
            android:textAppearance="?android:attr/textAppearanceLarge"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/UMUpdateTitle"
            android:textColor="#FFFFFF"
            android:layout_centerInParent="true"
            />
    </RelativeLayout>
    
    <!-- Content -->
   <LinearLayout 
        android:layout_width="fill_parent" android:layout_height="wrap_content"
        android:orientation="vertical" android:layout_margin="10dp">

        <TextView android:id="@+id/umeng_update_content"
            android:layout_height="wrap_content" 
            android:layout_width="280dp" 
            android:layout_marginTop="10dp"
            android:layout_marginLeft="10dp"
            android:textColor="#AAABAF"
            />
    </LinearLayout>

    <!-- OK&Cancel Button -->
    <LinearLayout android:layout_height="wrap_content" android:layout_width="fill_parent">
        <Button android:id="@+id/umeng_update_id_ok"
                           android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" 
            	android:text="@string/UMUpdateNow" 
            	android:background="@drawable/umeng_update_button_ok_selector"
            	android:layout_margin="10dp"
            	android:padding="12dp"
            	android:gravity="center"
            	android:textColor="#FFFFFF"/>
        
        <Button android:id="@+id/umeng_update_id_cancel"
            	android:layout_width="0dp" android:layout_weight="1" android:layout_height="wrap_content" 
                android:text="@string/UMNotNow"
                android:background="@drawable/umeng_update_button_cancel_selector"
                android:layout_margin="10dp"
            	android:padding="12dp"
            	android:gravity="center"
            	android:textColor="#AAABAF" />
    </LinearLayout>
</LinearLayout>
```

`umeng_update_title_bg.xml` 修改 `Title` 背景色为蓝色, 圆角修改的略小一些：

```
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="rectangle">
    <corners android:topLeftRadius="8dp" android:topRightRadius="8dp"
                        android:bottomLeftRadius="0dp" android:bottomRightRadius="0dp"/>
    <solid  android:color="#15A5FF"/>
</shape>
```

`umeng_update_dialog_bg.xml` 修改背景的圆角更小一些：

```
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="rectangle">
    <corners android:radius="8dp" />
    <solid  android:color="#FFFFFF"/>
</shape>
```

修改 `Button` 风格，`Button` 涉及文件较多，分别如下：

1. `umeng_update_button_cancel_normal.xml` 取消按钮正常状态
2. `umeng_update_button_cancel_tap.xml` 取消按钮点击状态
3. `umeng_update_button_cancel_selector.xml` 取消按钮背景 `state selector`
4. `umeng_update_button_ok_normal.xml` 确认按钮正常状态
5. `umeng_update_button_ok_tap.xml` 确认按钮点击状态
6. `umeng_update_button_ok_selector.xml` 确认按钮背景 `state selector`

分别按照图中样式修改即可，代码见 `res\drawable\` 此处不再重复粘贴。


完成上面四步，就可以从默认效果转化成现在的样子,作为聪明的开发者你肯定可以用很多种方式(eg:diff file1 file2)发现
相对于默认风格究竟修改了那些东西，从而更好理解我们的布局，以获得和自己App风格更适配的更新提示框。
