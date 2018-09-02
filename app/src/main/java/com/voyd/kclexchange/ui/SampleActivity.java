package com.voyd.kclexchange.ui;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.voyd.kclexchange.R;
import com.voyd.kclexchange.UserInformation;
import com.voyd.kclexchange.app.ApplicationSettings;
import com.voyd.kclexchange.app.ooVooSdkSampleShowApp;
import com.voyd.kclexchange.app.ooVooSdkSampleShowApp.CallNegotiationListener;
import com.voyd.kclexchange.app.ooVooSdkSampleShowApp.Operation;
import com.voyd.kclexchange.app.ooVooSdkSampleShowApp.OperationChangeListener;
import com.voyd.kclexchange.call.CNMessage;
import com.voyd.kclexchange.services.RegistrationIntentService;
import com.voyd.kclexchange.ui.fragments.AVChatLoginFragment;
import com.voyd.kclexchange.ui.fragments.AVChatSessionFragment;
import com.voyd.kclexchange.ui.fragments.BaseFragment;
import com.voyd.kclexchange.ui.fragments.InformationFragment;
import com.voyd.kclexchange.ui.fragments.LoginFragment;
import com.voyd.kclexchange.ui.fragments.ReautorizeFragment;
import com.voyd.kclexchange.ui.fragments.SplashScreen;
import com.voyd.kclexchange.ui.fragments.WaitingFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.oovoo.core.Utils.LogSdk;
import com.oovoo.sdk.api.ooVooClient;

public class SampleActivity extends Activity implements OperationChangeListener, CallNegotiationListener {


	private static final String	  	TAG	           	= SampleActivity.class.getSimpleName();
	private static final String 	STATE_FRAGMENT 	= "current_fragment";
	private static final int 		PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private BaseFragment	      	current_fragment	= null;
	private ooVooSdkSampleShowApp	application	   = null;
	private MenuItem 				mSettingsMenuItem = null;
	private MenuItem 				mInformationMenuItem = null;
	private MenuItem 				mSignalStrengthMenuItem = null;
	private MenuItem 				mSecureNetworkMenuItem = null;
	private boolean					mIsAlive = false;
	private boolean					mNeedShowFragment = false;
	private AlertDialog 			callDialogBuilder = null;
	private BroadcastReceiver 		mRegistrationBroadcastReceiver = null;
	private UserInformation userInformation;
	String Username;



	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);


		application = (ooVooSdkSampleShowApp) getApplication();
		application.setContext(this);

		setRequestedOrientation(application.getDeviceDefaultOrientation());

		setContentView(R.layout.host_activity);

		application.addOperationChangeListener(this);
		application.addCallNegotiationListener(this);


		Intent intent = getIntent();
		userInformation = (UserInformation)intent.getSerializableExtra("userinfo");
		String Name = userInformation.getFirstName();
		Username = Name.replace(" ", "_");


		if (savedInstanceState != null) {
			current_fragment = (BaseFragment)getFragmentManager().getFragment(savedInstanceState, STATE_FRAGMENT);
			showFragment(current_fragment);
		} else {
			Fragment newFragment = new SplashScreen();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.add(R.id.host_activity, newFragment).commit();

			if (!ooVooClient.isDeviceSupported()) {
				return;
			}

			try {

				application.onMainActivityCreated();
			} catch( Exception e) {
				Log.e( TAG, "onCreate exception: ", e);
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		try {
			getFragmentManager().putFragment(savedInstanceState, STATE_FRAGMENT, current_fragment);
		} catch( Exception e) {
			Log.e( TAG, "onSaveInstanceState exception: ", e);
		}
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		application.removeOperationChangeListener(this);
		application.removeCallNegotiationListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();


		try {
			if(mRegistrationBroadcastReceiver == null)
			{
				mRegistrationBroadcastReceiver = new BroadcastReceiver() {
					@Override
					public void onReceive(Context context, Intent intent) {
						SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
						boolean sentToken = sharedPreferences.getBoolean(ApplicationSettings.SENT_TOKEN_TO_SERVER, false);
						if (!sentToken) {
							application.showErrorMessageBox(SampleActivity.this, getString(R.string.registering_message), getString(R.string.token_error_message));
						}
					}
				};
				LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(ApplicationSettings.REGISTRATION_COMPLETE));
			}
		}
		catch(Exception err){
			Log.e( TAG, "onResume exception: with ", err);
		}


		mIsAlive = true;


		if(mNeedShowFragment){
			showFragment(current_fragment);
			mNeedShowFragment = false;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		mIsAlive = false;
	}



	public void finish(){
		if(current_fragment != null) {
			this.removeFragment(current_fragment);
			current_fragment = null ;
		}
		application.logout();
		super.finish();
	}

	@Override
	public void onOperationChange(Operation state) {
		try {
			switch (state) {
				case Error:
				{
					switch (state.forOperation()) {
						case Authorized:
							current_fragment = ReautorizeFragment.newInstance(mSettingsMenuItem, state.getDescription());
							break;
						case LoggedIn:
							current_fragment = LoginFragment.newInstance(state.getDescription());
							break;
						case AVChatJoined:
							application.showErrorMessageBox(this, getString(R.string.join_session), state.getDescription());
							current_fragment = AVChatLoginFragment.newInstance(mSettingsMenuItem);
							break;
						default:
							return;
					}
				}
				break;
				case Processing:
					current_fragment = WaitingFragment.newInstance(state.getDescription());
					break;
				case AVChatRoom:
					current_fragment = AVChatLoginFragment.newInstance(mSettingsMenuItem);
					break;
				case AVChatJoined:
					current_fragment = AVChatSessionFragment.newInstance(mSignalStrengthMenuItem,
							mSecureNetworkMenuItem, mInformationMenuItem);
					break;
				case Authorized:
					current_fragment = LoginFragment.newInstance(mSettingsMenuItem);
					break;
				case LoggedIn:
					if (checkPlayServices()) {
						// Start IntentService to register this application with GCM.
						Intent intent = new Intent(this, RegistrationIntentService.class);
						startService(intent);
					}
					current_fragment = AVChatLoginFragment.newInstance(mSettingsMenuItem);
					break;
				case AVChatDisconnected:
					if (application.isCallNegotiation()) {
						return;
					} else {
						current_fragment = AVChatLoginFragment.newInstance(mSettingsMenuItem);
						break;
					}

				default:
					return;
			}

			showFragment(current_fragment);
			System.gc();
			Runtime.getRuntime().gc();

		} catch (Exception err) {
			err.printStackTrace();
		}
	}



	private void showFragment(Fragment newFragment) {
		if(!mIsAlive){
			mNeedShowFragment = true;
			return;
		}

		try {
			if (newFragment != null) {
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.host_activity, newFragment);
				transaction.addToBackStack(newFragment.getClass().getSimpleName());
				transaction.commit();
			}
		}
		catch(Exception err){
			LogSdk.e(TAG, "showFragment " + err);
		}
	}



	private void removeFragment(Fragment fragment) {

		try {
			if (fragment != null) {
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.remove(current_fragment);
				transaction.show(fragment);
				transaction.commit();
			}
		}
		catch(Exception err){
			LogSdk.e(TAG, "removeFragment " + err);
		}
	}



	@Override
	public void onBackPressed() {
		try {
			if (current_fragment != null) {

				if(/*(current_fragment instanceof WaitingFragment) ||*/ !current_fragment.onBackPressed()){
					return ;
				}

				BaseFragment fragment = current_fragment.getBackFragment();
				if (fragment != null) {

					if (current_fragment instanceof InformationFragment) {
						mSignalStrengthMenuItem.setVisible(true);
						mInformationMenuItem.setVisible(true);
						removeFragment(fragment);

					} else {

						showFragment(fragment);
						System.gc();
						Runtime.getRuntime().gc();
					}
					current_fragment = fragment;

					return ;
				}

			}
		} catch (Exception err) {
			Log.e(TAG, "");
		}
		super.onBackPressed();
	}

	@Override
	public void onMessageReceived(final CNMessage cnMessage)
	{
		if (application.getUniqueId().equals(cnMessage.getUniqueId())) {
			return;
		}

		if (cnMessage.getMessageType() == CNMessage.CNMessageType.Calling) {

			if (application.isInConference()) {
				application.sendCNMessage(cnMessage.getFrom(), CNMessage.CNMessageType.Busy, null);
				return;
			}

			callDialogBuilder = new AlertDialog.Builder(this).create();
			LayoutInflater inflater = getLayoutInflater();
			View incomingCallDialog = inflater.inflate(R.layout.incoming_call_dialog, null);
			incomingCallDialog.setAlpha(0.5f);
			callDialogBuilder.setView(incomingCallDialog);

			Button answerButton = (Button) incomingCallDialog.findViewById(R.id.answer_button);
			answerButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					application.setConferenceId(cnMessage.getConferenceId());
					application.sendCNMessage(cnMessage.getFrom(), CNMessage.CNMessageType.AnswerAccept, null);
					callDialogBuilder.hide();

					application.join(application.getConferenceId(), true);
				}
			});

			Button declineButton = (Button) incomingCallDialog.findViewById(R.id.decline_button);
			declineButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					application.sendCNMessage(cnMessage.getFrom(), CNMessage.CNMessageType.AnswerDecline, null);
					callDialogBuilder.hide();
				}
			});

			callDialogBuilder.setCancelable(false);
			callDialogBuilder.show();
		} else if (cnMessage.getMessageType() == CNMessage.CNMessageType.Cancel) {
			callDialogBuilder.hide();
		} else if (cnMessage.getMessageType() == CNMessage.CNMessageType.EndCall) {
			if (application.leave()) {
				int count = getFragmentManager().getBackStackEntryCount();
				String name = getFragmentManager().getBackStackEntryAt(count - 2).getName();
				getFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
		}
	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				LogSdk.i(TAG, "This device is not supported.");
			}
			return false;
		}
		return true;
	}


	public String getUsername()
	{
		return Username;
	}

	public String getImage()
	{
		return userInformation.getImageString();
	}
}
