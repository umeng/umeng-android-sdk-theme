## 自定义更新

使用友盟自动更新模块实现自定义更新的示例：<br>
有时候您可能会要求使用自定义的UI方式来展示更新提示，这里给出使用自定义更新的样例，下面使用的是友盟默认的布局的实现，实际您可以根据您自己的需要进行相应的更改。

在Activity的`onCreate`方法中设置更新回调：

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	......
	final Context mContext = this;
	// 设置禁止自动弹窗，自定义弹窗操作
	UmengUpdateAgent.setUpdateAutoPopup(false);
	// 设置更新回调，自主处理更新
	UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {

		@Override
		public void onUpdateReturned(int updateStatus,
				UpdateResponse updateInfo) {
			switch (updateStatus) {
			case UpdateStatus.Yes: // 检测到有更新
				showUpdateDialog(updateInfo);
				break;
			case UpdateStatus.No: // 检测到没有更新
				Toast.makeText(mContext, "没有更新", Toast.LENGTH_SHORT).show();
				break;
			case UpdateStatus.NoneWifi: // 当前不是Wifi环境
				Toast.makeText(mContext, "没有wifi连接， 只在wifi下更新",
						Toast.LENGTH_SHORT).show();
				break;
			case UpdateStatus.Timeout: // 检测超时
				Toast.makeText(mContext, "超时", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	});
	UmengUpdateAgent.update(this);
}
```
定义方法`showUpdateDialog`展示更新提示：
```java
private void showUpdateDialog(UpdateResponse updateInfo) {
	//如果版本已经被忽略，不弹框
	if (UmengUpdateAgent.isIgnore(this, updateInfo)) {
		return;
	}
	//获取下载完的文件，如果未下载则为null
	final File file = UmengUpdateAgent.downloadedFile(this, updateInfo);
	boolean isDownloaded = file != null;
	//创建更新对话框
	createDialog(updateInfo, isDownloaded, file).show();
}
```
定义方法`createDialog`创建更新对话框：
```java
/**
 * 创建更新对话框
 * @param updateInfo 更新信息
 * @param isDownloaded 是否已经下载
 * @param file 下载完的文件，如果未下载为null
 * @return 更新对话框
 */
private Dialog createDialog(final UpdateResponse updateInfo,
		boolean isDownloaded, final File file) {
	final boolean[] isIgnore = { false };
	final int[] result = { UpdateStatus.NotNow };
	final Context context = this;

	// 如果您的应用是全屏的，可以在这里设置Dialog也为全屏
	final Dialog dialog = new Dialog(context,
			android.R.style.Theme_Translucent_NoTitleBar);
	View v = LayoutInflater.from(context).inflate(
			R.layout.umeng_update_dialog, null);

	// 忽略勾选框的状态回调
	OnCheckedChangeListener cl = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			isIgnore[0] = isChecked;
		}
	};
	// 点击回调，记录用户的不同选择
	OnClickListener ll = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (R.id.umeng_update_id_ok == v.getId()) {
				result[0] = UpdateStatus.Update;
			} else if (R.id.umeng_update_id_ignore == v.getId()) {
				result[0] = UpdateStatus.Ignore;
			} else if (isIgnore[0]) {
				result[0] = UpdateStatus.Ignore;
			}
			dialog.dismiss();
		}

	};
	// 对话框消失回调，处理用户的不同选择
	dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialogInterface) {
			switch (result[0]) {
			case UpdateStatus.Update:
				// 用户选择更新
				if (file == null) {
					//开始下载
					UmengUpdateAgent.startDownload(context, updateInfo);
				} else {
					//开始安装
					UmengUpdateAgent.startInstall(context, file);
				}
				break;
			case UpdateStatus.Ignore:
				// 用户选择忽略
				UmengUpdateAgent.ignoreUpdate(context, updateInfo);
				break;
			case UpdateStatus.NotNow:
				// 用户选择取消
				break;
			}
		}
	});

	// 获得网络连接服务
	ConnectivityManager connManager = (ConnectivityManager) context
			.getSystemService(Context.CONNECTIVITY_SERVICE);
	// 获取WIFI网络连接状态
	State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
			.getState();
	// 如果正在使用WIFI网络或已经下载完成不显示无Wifi的图标
	int visibility = (State.CONNECTED == state) || isDownloaded ? View.GONE
			: View.VISIBLE;
	v.findViewById(R.id.umeng_update_wifi_indicator).setVisibility(
			visibility);

	v.findViewById(R.id.umeng_update_id_ok).setOnClickListener(ll);
	v.findViewById(R.id.umeng_update_id_cancel).setOnClickListener(ll);
	v.findViewById(R.id.umeng_update_id_ignore).setOnClickListener(ll);
	v.findViewById(R.id.umeng_update_id_close).setOnClickListener(ll);
	((CheckBox) v.findViewById(R.id.umeng_update_id_check))
			.setOnCheckedChangeListener(cl);
	// 设置对话框显示文本
	String content = updateContentString(updateInfo, isDownloaded);
	TextView tv = (TextView) v.findViewById(R.id.umeng_update_content);
	tv.requestFocus();
	tv.setText(content);

	dialog.setContentView(v);

	return dialog;
}
```
定义方法`updateContentString`处理更新信息，转换为更新对话框显示文本：
```java
/**
 * 根据更新信息编排对话框显示文本
 * 
 * @param updateInfo
 *            更新信息
 * @param isDownloaded
 *            是否已经下载
 * @return 更新对话框显示文本
 */
public String updateContentString(UpdateResponse updateInfo,
		boolean isDownloaded) {
	String versionPrefix = getString(R.string.UMNewVersion);
	String sizePrefix = getString(R.string.UMTargetSize);
	String deltaPrefix = getString(R.string.UMUpdateSize);
	String updateLogPrefix = getString(R.string.UMUpdateContent);
	String installApk = getString(R.string.UMDialog_InstallAPK);
	// 已经下载的情况
	if (isDownloaded) {
		return String.format("%s %s\n" + "%s\n\n" + "%s\n" + "%s\n",
				versionPrefix, updateInfo.version, installApk,
				updateLogPrefix, updateInfo.updateLog);
	}

	String deltaContent;
	// 增量更新和全量更新的情况
	if (updateInfo.delta) {
		deltaContent = String.format("\n%s %s", deltaPrefix,
				getFileSizeDescription(updateInfo.size));
	} else {
		deltaContent = "";
	}
	// 未下载的情况
	return String.format("%s %s\n" + "%s %s%s\n\n" + "%s\n" + "%s\n",
			versionPrefix, updateInfo.version, sizePrefix,
			getFileSizeDescription(updateInfo.target_size), deltaContent,
			updateLogPrefix, updateInfo.updateLog);
}
```
定义方法`getFileSizeDescription`处理文件大小，格式化为需要显示的文本：
```java
/**
 * 将字节数转换为用于显示的文件大小格式
 * 
 * @param size
 *            文件字节数
 * @return 格式化后的文件大小文本
 */
public static String getFileSizeDescription(String size) {
	String value = "";
	long bytes = 0;
	try {
		bytes = Long.valueOf(size).longValue();
	} catch (NumberFormatException e) {
		return size;
	}
	if (bytes < 1024) {
		value = (int) bytes + "B";
	} else if (bytes < 1048576) {
		DecimalFormat df = new DecimalFormat("#0.00");
		value = df.format((float) bytes / 1024.0) + "K";
	} else if (bytes < 1073741824) {
		DecimalFormat df = new DecimalFormat("#0.00");
		value = df.format((float) bytes / 1048576.0) + "M";
	} else {
		DecimalFormat df = new DecimalFormat("#0.00");
		value = df.format((float) bytes / 1073741824.0) + "G";
	}
	return value;
}
```
