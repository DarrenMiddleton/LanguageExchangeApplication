package com.voyd.kclexchange.ui.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.voyd.kclexchange.R;
import com.voyd.kclexchange.ui.SampleActivity;


public class LoginFragment extends BaseFragment {

	private String errorDescription 		= null;
	private TextView usernameView 		= null;
	private TextView errorTextView			= null;
	private MenuItem settingsMenuItem 		= null;
	private ImageView UserImage;

	public LoginFragment()
	{
	}

	public static LoginFragment newInstance(MenuItem settingsMenuItem)
	{
		LoginFragment fragment = new LoginFragment();
		fragment.settingsMenuItem = settingsMenuItem;

		return fragment;
	}




	public static final LoginFragment newInstance(String errorDescription)
	{
		LoginFragment instance = new LoginFragment();
		instance.setErrorDescription(errorDescription);
		return instance;
	}





	public void setErrorDescription(String errorDescription)
	{
		this.errorDescription = errorDescription;
	}




	@Override
	public void onResume()
	{
		super.onResume();

		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		if (settingsMenuItem != null)
		{
			settingsMenuItem.setVisible(true);
		}
	}





	@Override
	public void onPause()
	{
		super.onPause();

		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		if (settingsMenuItem != null)
		{
			settingsMenuItem.setVisible(false);
		}
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.login_fragment_layout, container, false);


		Button loginButton = (Button) view.findViewById(R.id.login_button);

		usernameView = (TextView) view.findViewById(R.id.txtViewUserName);
		UserImage = (ImageView) view.findViewById(R.id.imgUserImage);

		SampleActivity activity = (SampleActivity) getActivity();
		String username = activity.getUsername();
		String ImageString = activity.getImage();
		byte[] decodedString = Base64.decode(ImageString, 0);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
		UserImage.setImageBitmap(decodedByte);
		username = username.replace(' ', '_');


		if (username != null)
		{
			usernameView.setText(username);
		}


		loginButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onLoginClick();
			}
		});


		errorTextView = (TextView)view.findViewById(R.id.error_label);
		errorTextView.setVisibility(View.INVISIBLE);
		errorTextView.setText("");
		if (!app().isOnline())
		{
			errorTextView.setVisibility(View.VISIBLE);
			errorTextView.setText(getActivity().getResources().getString(R.string.no_internet));
		}
		else if (errorDescription != null)
		{
			errorTextView.setVisibility(View.VISIBLE);
			errorTextView.setText(errorDescription);
		}

		return view;
	}

	public void onLoginClick()
	{
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(usernameView.getWindowToken(), 0);

		app().login(usernameView.getText().toString(), usernameView.getText().toString());
	}

	public boolean onBackPressed()
	{
		this.getActivity().finish();
		return false ;
	}

}