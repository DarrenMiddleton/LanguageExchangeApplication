package com.voyd.kclexchange.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.voyd.kclexchange.R;
import com.voyd.kclexchange.app.ApplicationSettings;
import com.voyd.kclexchange.ui.CustomVideoPanel;
import com.voyd.kclexchange.ui.VideoPanelPreviewRect;
import com.oovoo.core.Utils.LogSdk;
import com.oovoo.sdk.api.ui.VideoPanel;
import com.oovoo.sdk.interfaces.VideoController;

public class AVChatLoginFragment extends BaseFragment {
	private static final int CONFERENCE_ID_LIMIT = 200;
	private static final String TAG = AVChatLoginFragment.class.getSimpleName();
	private EditText sessionIdEditText	= null;
	private MenuItem settingsMenuItem = null;
	private VideoPanelPreviewRect previewRect = null;
	private String Join1, Join2, Join3, Join4;

	public AVChatLoginFragment(){}

	public static final AVChatLoginFragment newInstance(MenuItem settingsMenuItem) {
		AVChatLoginFragment instance = new AVChatLoginFragment();
	    return instance;
	}


	@Override
    public void onResume() {
	    super.onResume();
    }

	@Override
    public void onPause() {
        super.onPause();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);

	    if (app().isTablet()) {
	    	updatePreviewLayout(newConfig);
        }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


		View view = inflater.inflate(R.layout.avchat_login_fragment, container, false);
		VideoPanel panel = (VideoPanel) view.findViewById(R.id.preview_view);
		CustomVideoPanel customPanel = (CustomVideoPanel) view.findViewById(R.id.custom_preview_view);

		TextView displayNameTextView = (TextView) view.findViewById(R.id.display_name_text_view);

		previewRect = (VideoPanelPreviewRect) view.findViewById(R.id.preview_rect);

		if (app().isTablet()) {
			Configuration config = getResources().getConfiguration();
			updatePreviewLayout(config);
		}
		app().checkGL();
		app().selectCamera("FRONT");
		app().changeResolution(VideoController.ResolutionLevel.ResolutionLevelMed);
	    app().openPreview();

		String lastSessionId = settings().get("avs_session_id");

		sessionIdEditText = (EditText) view.findViewById(R.id.session_field);

		if (lastSessionId != null) {
			sessionIdEditText.setText(lastSessionId);
		}


		String useCustomRenderValue = settings().get(ApplicationSettings.UseCustomRender);

		if (useCustomRenderValue != null && Boolean.valueOf(useCustomRenderValue)) {
			panel.setVisibility(View.INVISIBLE);
			customPanel.setVisibility(View.VISIBLE);
			app().bindVideoPanel(ApplicationSettings.PREVIEW_ID, customPanel);
			if (customPanel.isCircleShape()) {
				displayNameTextView.setVisibility(View.INVISIBLE);
			}
		} else {
			customPanel.setVisibility(View.INVISIBLE);
			panel.setVisibility(View.VISIBLE);
			app().bindVideoPanel(ApplicationSettings.PREVIEW_ID, panel);
		}

		Button join = (Button) view.findViewById(R.id.join_button);
		join.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				join();
			}
		});
		final Button join1 = (Button) view.findViewById(R.id.button100);
		join1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
;
				join1(join1.getText().toString());
			}
		});
		final Button join2 = (Button) view.findViewById(R.id.button101);
		join2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				join1(join2.getText().toString());
			}
		});

		final Button join3 = (Button) view.findViewById(R.id.button102);
		join3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				join1(join3.getText().toString());
			}
		});

		final Button join4 = (Button) view.findViewById(R.id.button103);
		join4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				join1(join4.getText().toString());
			}
		});

		return view;
	}

	private void updatePreviewLayout(Configuration config) {
		if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			int width = app().getDisplaySize().x;
	    	int padding = (width - ((int)(app().getDisplaySize().y * 0.75f * (4.0/3.0))))/2;

			String useCustomRenderValue = settings().get(ApplicationSettings.UseCustomRender);
			if (useCustomRenderValue != null && Boolean.valueOf(useCustomRenderValue)) {
				padding = (width - (int)(app().getDisplaySize().y * 0.7f))/2;
			}
			previewRect.setPadding(padding, 0, padding, 0);
		} else if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
	    	previewRect.setPadding(0, 0, 0, 0);
	    }
	}

	private boolean checkSessionId(String sessionId)
	{
		if (sessionId.isEmpty()) {
			Toast.makeText(getActivity(), R.string.enter_conference_id, Toast.LENGTH_LONG).show();

			return false;
		}

		if (sessionId.length() > CONFERENCE_ID_LIMIT) {
			showErrorMessageBox(getString(R.string.join_session), getString(R.string.wrong_conference_id));

			return false;
		}

		return true;
	}

	private void join()
	{
		String sessionId = sessionIdEditText.getText().toString();

		if (!checkSessionId(sessionId)) {
			return;
		}

		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(sessionIdEditText.getWindowToken(), 0);

		if (!app().isOnline()) {
			showErrorMessageBox("Network Error", getString(R.string.no_internet));
			return;
		}

		app().join(sessionId, false);
	}

	private void join1(String s)
	{
		String sessionId = s;

		if (!checkSessionId(sessionId)) {
			return;
		}

		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(sessionIdEditText.getWindowToken(), 0);

		if (!app().isOnline()) {
			showErrorMessageBox("Network Error", getString(R.string.no_internet));
			return;
		}

		app().join(sessionId, false);
	}

	protected void finalize() throws Throwable {
		LogSdk.d(TAG, "ooVooCamera -> VideoPanel -> finalize AVChatLoginFragment ->");
		super.finalize();
	}

	public void showErrorMessageBox(String title,String msg)
	{
		try {
				AlertDialog.Builder popupBuilder = new AlertDialog.Builder(getActivity());
				TextView myMsg = new TextView(getActivity());
				myMsg.setText(msg);
				myMsg.setGravity(Gravity.CENTER);
				popupBuilder.setTitle(title);
				popupBuilder.setPositiveButton("OK", null);
				popupBuilder.setView(myMsg);

				popupBuilder.show();
		} catch( Exception e) {
		}
	}

	public BaseFragment getBackFragment() {
		return LoginFragment.newInstance(settingsMenuItem);
	}

	public boolean onBackPressed() {
		app().releaseAVChat();

		return true;
    }
}
