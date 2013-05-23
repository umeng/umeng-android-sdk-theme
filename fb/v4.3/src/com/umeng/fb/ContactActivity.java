package com.umeng.fb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.fb.model.UserInfo;

/**
 * Activity for user to fill plain messy contact information. Developers are
 * encouraged to implement their own activities by following this example using
 * the API provided by SDK.
 * 
 * @author lucas
 * 
 */
public class ContactActivity extends Activity {

	/**
	 * The predefined key used by Umeng Feedback SDK to store non-structural
	 * plain text messy contact info. Info can be retrieved from
	 * {@link UserInfo#getContact()} with key
	 * {@link #KEY_UMENG_CONTACT_INFO_PLAIN_TEXT}. <br />
	 * This key is reserved by Umeng. Third party developers DO NOT USE this
	 * key.
	 */
	private static final String KEY_UMENG_CONTACT_INFO_PLAIN_TEXT = "plain";

	private ImageView backBtn;
	private ImageView saveBtn;

	private EditText contactInfoEdit;
	private FeedbackAgent agent;

	private TextView lastUpdateAtText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.umeng.fb.res.LayoutMapper
				.umeng_fb_activity_contact(this));
		agent = new FeedbackAgent(this);

		backBtn = (ImageView) this.findViewById(com.umeng.fb.res.IdMapper
				.umeng_fb_back(this));
		saveBtn = (ImageView) this.findViewById(com.umeng.fb.res.IdMapper
				.umeng_fb_save(this));
		contactInfoEdit = (EditText) this
				.findViewById(com.umeng.fb.res.IdMapper
						.umeng_fb_contact_info(this));
		lastUpdateAtText = (TextView) this
				.findViewById(com.umeng.fb.res.IdMapper
						.umeng_fb_contact_update_at(this));

		try {
			String contact_info = agent.getUserInfo().getContact()
					.get(KEY_UMENG_CONTACT_INFO_PLAIN_TEXT);
			contactInfoEdit.setText(contact_info);

			long time = agent.getUserInfoLastUpdateAt();

			if (time > 0) {
				Date date = new Date(time);
				String prefix = this.getResources().getString(
						com.umeng.fb.res.StringMapper
								.umeng_fb_contact_update_at(this));
				lastUpdateAtText.setText(prefix
						+ SimpleDateFormat.getDateTimeInstance().format(date));
				lastUpdateAtText.setVisibility(View.VISIBLE);
			} else {
				lastUpdateAtText.setVisibility(View.GONE);
			}

			// If user has never entered any contact information, request focus
			// on the edittext on startup.
			if (com.umeng.common.util.Helper.isEmpty(contact_info)) {
				contactInfoEdit.requestFocus();
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null)
					imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				back();
			}

		});
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					UserInfo info = agent.getUserInfo();
					if (info == null)
						info = new UserInfo();
					Map<String, String> contact = info.getContact();
					if (contact == null)
						contact = new HashMap<String, String>();
					String contact_info = contactInfoEdit.getEditableText()
							.toString();
					contact.put(KEY_UMENG_CONTACT_INFO_PLAIN_TEXT, contact_info);
					info.setContact(contact);
					
					
//					Map<String, String> remark = info.getRemark();
//					if (remark == null)
//						remark = new HashMap<String, String>();
//					remark.put("tag1", "game");
//					info.setRemark(remark);

					agent.setUserInfo(info);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				back();
			}

		});
	}

	@SuppressLint("NewApi")
	void back() {
		ContactActivity.this.finish();

		// add transition animation for exit.
		if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.DONUT) {
			overridePendingTransition(
					com.umeng.fb.res.AnimMapper
							.umeng_fb_slide_in_from_left(ContactActivity.this),
					com.umeng.fb.res.AnimMapper
							.umeng_fb_slide_out_from_right(ContactActivity.this));
		}
	}
}
