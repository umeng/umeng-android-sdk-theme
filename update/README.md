## 自定义自动更新对话框UI

### 默认效果图如下：

<img src="https://raw.github.com/ntop001/umeng-android-sdk-theme/master/update/default/light.png" width="500" />

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


### 固定ID资源

`umeng_update_dialog.xml` 中定义的 如下资源类型和ID 是不可以修改的：

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tbody><tr>
    <th scope="col" width="180">资源类型</th>
    <th scope="col">ID</th>
    <th scope="col">用途</th>

  </tr>
  <tr>
    <td>ImageView</td>
    <td>umeng_update_wifi_indicator</td>
    <td>显示当前Wifi是否可用</td>
  </tr>
  <tr>
    <td>TextView</td>
   <td>umeng_update_content</td>
   <td>显示更新内容</td>
   
  </tr>
  <tr>
    <td>Button</td>
    <td>umeng_update_id_ok</td>
    <td>确定按钮</td>
  </tr>
  <tr>

    <td>Button</td>
    <td>umeng_update_id_cancel</td>
    <td>取消按钮</td>
  </tr>
</tbody></table>


`values`路径下定义的字符串资源 `name` 属性不可更改，值可以随意修改.
`drawable` 路径下的图片资源，在`SDK`代码中没有直接引用，可以直接更换图片内容
（如果修改名称需要同时修改其他 `xml` 中的引用，一般不要这么做)



### 生成对话框
SDK 在显示对话框之前会做如下步骤的操作：

1.  找到 `umeng_update_dialog.xml` 作为对话框的内部 `View`
2.  找到 `umeng_update_wifi_indicator` 根据当前wifi状态设置是否可见
3.  找到 `umeng_update_content`  文本区设置更新日志
4.  分别找到 `umeng_update_id_ok` 和 `umeng_update_id_cancel` 按钮设置监听
5. 显示对话框

### 如何自定义UI

通过上面的描述，应该可以清楚的自定义弹出对话框的样式， 通过  `umeng_update_dialog.xml`  修改对话框布局样式即可, 除了固定ID
的资源类型和Id不可变动外，


下面链接是我们提供的一些不同风格的UI：

#### 1. [默认](https://github.com/ntop001/umeng-android-sdk-theme/tree/master/update/default)
<img src="https://raw.github.com/ntop001/umeng-android-sdk-theme/master/update/default/light.png" width="200" />
#### 2. [蓝色](https://github.com/ntop001/umeng-android-sdk-theme/tree/master/update/white-blue)
<img src="https://raw.github.com/ntop001/umeng-android-sdk-theme/master/update/white-blue/white-blue.png" width="200"/>







