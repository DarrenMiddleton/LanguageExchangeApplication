package com.voyd.kclexchange;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ProfileOwnActivityInstTests extends ActivityInstrumentationTestCase2<ProfileOwnActivity> {

    public ProfileOwnActivityInstTests() {
        super(ProfileOwnActivity.class);
    }

    public void testActivityExists() {
        ProfileOwnActivity profileOwnActivity = getActivity();
        assertNotNull(profileOwnActivity);
    }

    public void testHolderScrollViewExists() {
        ProfileOwnActivity profileOwnActivity = getActivity();
        ScrollView scrollView = (ScrollView) profileOwnActivity.findViewById(R.id.scrollView7);
        assertNotNull(scrollView);
    }

    public void testProfileImageViewExists() {
        ProfileOwnActivity profileOwnActivity = getActivity();
        ImageView imageView = (ImageView) profileOwnActivity.findViewById(R.id.imgProfileImage);
        assertNotNull(imageView);
    }

    public void testNameTextViewExists(){
        ProfileOwnActivity profileOwnActivity = getActivity();
        TextView textView = (TextView) profileOwnActivity.findViewById(R.id.txtProfileName);
        assertNotNull(textView);
    }

    public void testAgeGenderTextViewExists(){
        ProfileOwnActivity profileOwnActivity = getActivity();
        TextView textView = (TextView) profileOwnActivity.findViewById(R.id.txtAgeSex);
        assertNotNull(textView);
    }

    public void testProfileIDTextViewExists(){
        ProfileOwnActivity profileOwnActivity = getActivity();
        TextView textView = (TextView) profileOwnActivity.findViewById(R.id.txtProfileID);
        assertNotNull(textView);
    }

    public void testEditProfileImageViewExists() {
        ProfileOwnActivity profileOwnActivity = getActivity();
        ImageView imageView = (ImageView) profileOwnActivity.findViewById(R.id.imageView8);
        assertNotNull(imageView);
    }

    public void testLanguagesHolderScrollViewExists(){
        ProfileOwnActivity profileOwnActivity = getActivity();
        ScrollView scrollView = (ScrollView) profileOwnActivity.findViewById(R.id.scrollView5);
        assertNotNull(scrollView);
    }

    public void testLanguagesHolderLinearLayoutExists(){
        ProfileOwnActivity profileOwnActivity = getActivity();
        LinearLayout linearLayout = (LinearLayout) profileOwnActivity.findViewById(R.id.linLayLanguages);
        assertNotNull(linearLayout);
    }

    public void testBioTextViewExists(){
        ProfileOwnActivity profileOwnActivity = getActivity();
        TextView textView = (TextView) profileOwnActivity.findViewById(R.id.txtProfileBio);
        assertNotNull(textView);
    }

    public void testScoreTextViewExists(){
        ProfileOwnActivity profileOwnActivity = getActivity();
        TextView textView = (TextView) profileOwnActivity.findViewById(R.id.textView16);
        assertNotNull(textView);
    }

    public void testBadgeTextViewExists(){
        ProfileOwnActivity profileOwnActivity = getActivity();
        TextView textView = (TextView) profileOwnActivity.findViewById(R.id.textView17);
        assertNotNull(textView);
    }

    public void testSpacing1ImageViewExists(){
        ProfileOwnActivity profileOwnActivity = getActivity();
        ImageView imageView = (ImageView) profileOwnActivity.findViewById(R.id.imageView10);
        assertNotNull(imageView);
    }

    public void testSpacing2ImageViewExists(){
        ProfileOwnActivity profileOwnActivity = getActivity();
        ImageView imageView = (ImageView) profileOwnActivity.findViewById(R.id.imageView11);
        assertNotNull(imageView);
    }
}
