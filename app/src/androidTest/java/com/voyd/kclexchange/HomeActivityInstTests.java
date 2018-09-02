package com.voyd.kclexchange;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by maheshuk31 on 23/03/2016.
 */
public class HomeActivityInstTests extends ActivityInstrumentationTestCase2<HomeActivity> {

    public HomeActivityInstTests() {
        super(HomeActivity.class);
    }

    public void testActivityExists() {
        HomeActivity homeActivity = getActivity();
        assertNotNull(homeActivity);
    }

    public void testHolderScrollViewExists(){
        HomeActivity homeActivity = getActivity();
        ScrollView scrollView = (ScrollView) homeActivity.findViewById(R.id.scrollView3);
        assertNotNull(scrollView);
    }

    public void testSearchEditTextExists(){
        HomeActivity homeActivity = getActivity();
        EditText editText = (EditText) homeActivity.findViewById(R.id.txtSearch);
        assertNotNull(editText);
    }

    public void testSearchIconImageViewExists(){
        HomeActivity homeActivity = getActivity();
        ImageView imageView = (ImageView) homeActivity.findViewById(R.id.imageView7);
        assertNotNull(imageView);
    }

    public void testPlaceholderImageViewExists(){
        HomeActivity homeActivity = getActivity();
        ImageView imageView = (ImageView) homeActivity.findViewById(R.id.imageView6);
        assertNotNull(imageView);
    }

    public void testProfileButtonExists(){
        HomeActivity homeActivity = getActivity();
        Button button = (Button) homeActivity.findViewById(R.id.button7);
        assertNotNull(button);
    }

    public void testMapButtonExists(){
        HomeActivity homeActivity = getActivity();
        Button button = (Button) homeActivity.findViewById(R.id.button6);
        assertNotNull(button);
    }

    public void testResourcesButtonExists(){
        HomeActivity homeActivity = getActivity();
        Button button = (Button) homeActivity.findViewById(R.id.button5);
        assertNotNull(button);
    }

    public void testNotificationScrollViewExists(){
        HomeActivity homeActivity = getActivity();
        ScrollView scrollView = (ScrollView) homeActivity.findViewById(R.id.scrollView);
        assertNotNull(scrollView);
    }

    public void testNotificationLinearLayoutExists(){
        HomeActivity homeActivity = getActivity();
        LinearLayout linearLayout = (LinearLayout) homeActivity.findViewById(R.id.linearLayout1);
        assertNotNull(linearLayout);
    }

    public void testNotificationTextViewExists(){
        HomeActivity homeActivity = getActivity();
        TextView textView = (TextView) homeActivity.findViewById(R.id.textView2);
        assertNotNull(textView);
    }

    public void testFriendsScrollViewExists(){
        HomeActivity homeActivity = getActivity();
        ScrollView scrollView = (ScrollView) homeActivity.findViewById(R.id.scrollView2);
        assertNotNull(scrollView);
    }

    public void testFriendsLinearLayoutExists(){
        HomeActivity homeActivity = getActivity();
        LinearLayout linearLayout = (LinearLayout) homeActivity.findViewById(R.id.linLayFriends);
        assertNotNull(linearLayout);
    }

    public void testFriendsTextViewExists(){
        HomeActivity homeActivity = getActivity();
        TextView textView = (TextView) homeActivity.findViewById(R.id.textView4);
        assertNotNull(textView);
    }

}
