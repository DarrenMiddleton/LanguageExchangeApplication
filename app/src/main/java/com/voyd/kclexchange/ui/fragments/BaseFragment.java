package com.voyd.kclexchange.ui.fragments;

import android.app.Fragment;

import com.voyd.kclexchange.app.ApplicationSettings;
import com.voyd.kclexchange.app.ooVooSdkSampleShowApp;

public class BaseFragment extends Fragment
{
	
	private BaseFragment back_fragment = null ;
	
	public void setKeepScreenOn(boolean state)
	{
		if(getView() != null)
			getView().setKeepScreenOn(state);
	}

	ooVooSdkSampleShowApp app(){
		return ((ooVooSdkSampleShowApp) getActivity().getApplication()) ;
	}

	public ApplicationSettings settings() {
		return app().getSettings();
	}
	
	public BaseFragment getBackFragment() {
		return back_fragment;
	}

	public void setBackFragment(BaseFragment back_fragment) {
		this.back_fragment = back_fragment;
	}

	public boolean onBackPressed() {
	   return true ;
    }
}
