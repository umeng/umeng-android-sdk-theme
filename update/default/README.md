### 效果图

![](https://raw.github.com/ntop001/umeng-android-sdk-theme/master/update/default/light.png)

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
