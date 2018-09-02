package com.voyd.kclexchange;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ProfileOtherActivityInstTests extends ActivityInstrumentationTestCase2<ProfileOtherActivity> {

    public ProfileOtherActivityInstTests() {
        super(ProfileOtherActivity.class);
    }

    public void testActivityExists() {
        ProfileOtherActivity profileOtherActivity = getActivity();
        assertNotNull(profileOtherActivity);
    }

    public void testHolderScrollViewExists(){
        ProfileOtherActivity profileOtherActivity = getActivity();
        ScrollView scrollView = (ScrollView) profileOtherActivity.findViewById(R.id.scrollView6);
        assertNotNull(scrollView);
    }

    public void testProfileImageViewExists(){
        ProfileOtherActivity profileOtherActivity = getActivity();
        ImageView imageView = (ImageView) profileOtherActivity.findViewById(R.id.imgProfileImage);
        assertNotNull(imageView);
    }

    public void testNameTextViewExists(){
        ProfileOtherActivity profileOtherActivity = getActivity();
        TextView textView = (TextView) profileOtherActivity.findViewById(R.id.txtProfileName);
        assertNotNull(textView);
    }

    public void testAgeGenderTextViewExists(){
        ProfileOtherActivity profileOtherActivity = getActivity();
        TextView textView = (TextView) profileOtherActivity.findViewById(R.id.txtAgeSex);
        assertNotNull(textView);
    }

    public void testProfileIDTextViewExists(){
        ProfileOtherActivity profileOtherActivity = getActivity();
        TextView textView = (TextView) profileOtherActivity.findViewById(R.id.txtProfileID);
        assertNotNull(textView);
    }

    public void testAddContactImageViewExists(){
        ProfileOtherActivity profileOtherActivity = getActivity();
        ImageView imageView = (ImageView) profileOtherActivity.findViewById(R.id.imgAddContact);
        assertNotNull(imageView);
    }

    public void testMessageContactImageViewExists(){
        ProfileOtherActivity profileOtherActivity = getActivity();
        ImageView imageView = (ImageView) profileOtherActivity.findViewById(R.id.imageView8);
        assertNotNull(imageView);
    }

    public void testMeetingContactImageViewExists(){
        ProfileOtherActivity profileOtherActivity = getActivity();
        ImageView imageView = (ImageView) profileOtherActivity.findViewById(R.id.imageView9);
        assertNotNull(imageView);
    }

    public void testLanguagesHolderScrollViewExists(){
        ProfileOtherActivity profileOtherActivity = getActivity();
        ScrollView scrollView = (ScrollView) profileOtherActivity.findViewById(R.id.scrollView5);
        assertNotNull(scrollView);
    }

    public void testLanguagesHolderLinearLayoutExists(){
        ProfileOtherActivity profileOtherActivity = getActivity();
        LinearLayout linearLayout = (LinearLayout) profileOtherActivity.findViewById(R.id.linLayLanguages);
        assertNotNull(linearLayout);
    }

    public void testBioTextViewExists(){
        ProfileOtherActivity profileOtherActivity = getActivity();
        TextView textView = (TextView) profileOtherActivity.findViewById(R.id.txtProfileBio);
        assertNotNull(textView);
    }

    public void testScoreTextViewExists(){
        ProfileOtherActivity profileOtherActivity = getActivity();
        TextView textView = (TextView) profileOtherActivity.findViewById(R.id.textView16);
        assertNotNull(textView);
    }

    public void testBadgeTextViewExists(){
        ProfileOtherActivity profileOtherActivity = getActivity();
        TextView textView = (TextView) profileOtherActivity.findViewById(R.id.textView17);
        assertNotNull(textView);
    }

    public void testSpacing1ImageViewExists(){
        ProfileOtherActivity profileOtherActivity = getActivity();
        ImageView imageView = (ImageView) profileOtherActivity.findViewById(R.id.imageView10);
        assertNotNull(imageView);
    }

    public void testSpacing2ImageViewExists(){
        ProfileOtherActivity profileOtherActivity = getActivity();
        ImageView imageView = (ImageView) profileOtherActivity.findViewById(R.id.imageView11);
        assertNotNull(imageView);
    }

}
