自动更新组件主题皮肤
=======================

## 自定义自动更新对话框UI

### 结构

自动更新需要的默认资源结构如下：

```
res - drawable - umeng_update_button_cancle_normal.xml
                          - umeng_update_button_cancle_tap.xml
                          - umeng_update_button_ok_normal.xml
                          - umeng_update_button_ok_tap.xml
                          - umeng_update_button_cancle_selector.xml
                          - umeng_update_button_ok_selector.xml
                          - umeng_update_title_bg.xml
                          - umeng_update_dialog_bg.xml
                          - umeng_update_wifi_disable.png
      - layout      - umeng_update_dialog.xml
      - values      - umeng_common_strings.xml
      			  - umeng_update_string,xml
      - values-zh -  umeng_common_strings.xml
      			  - umeng_update_string,xml
```

`drawable`  路径下定义了按钮和背景所需的图片， `layout` 路径是更新对话框显示的布局， `values` 路径下是显示的字符串资源， 这些都是可以直接修改的。


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
    <td>umeng_update_id_cancle</td>
    <td>取消按钮</td>
  </tr>
</tbody></table>

### 生成对话框
SDK 在显示对话框之前会做如下步骤的操作：

1.  找到 `umeng_update_dialog.xml` 作为对话框的内部 `View`
2.  找到 `umeng_update_wifi_indicator` 根据当前wifi状态设置是否可见
3.  找到 `umeng_update_content`  文本区设置更新日志
4.  分别找到 `umeng_update_id_ok` 和 `umeng_update_id_cancle` 按钮设置监听
5. 显示对话框

### 如何自定义UI

通过上面的描述，应该可以清楚的自定义弹出对话框的样式， 通过  `umeng_update_dialog.xml`  修改对话框布局样式即可。


下面链接是我们提供的一些不同风格的UI：







