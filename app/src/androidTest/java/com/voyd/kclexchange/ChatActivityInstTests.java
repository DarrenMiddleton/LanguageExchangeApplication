package com.voyd.kclexchange;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class ChatActivityInstTests extends ActivityInstrumentationTestCase2<ChatActivity> {

    public ChatActivityInstTests() {
        super(ChatActivity.class);
    }

    public void testActivityExists() {
        ChatActivity chatActivity = getActivity();
        assertNotNull(chatActivity);
    }

    public void testMessagesScrollViewExists(){
        ChatActivity chatActivity = getActivity();
        ScrollView scrollView = (ScrollView) chatActivity.findViewById(R.id.scrollView8);
        assertNotNull(scrollView);
    }

    public void testMessageLinearLayoutExists(){
        ChatActivity chatActivity = getActivity();
        LinearLayout linearLayout = (LinearLayout) chatActivity.findViewById(R.id.linLayMessages);
        assertNotNull(linearLayout);
    }

    public void testSpacingImageViewExists(){
        ChatActivity chatActivity = getActivity();
        ImageView imageView = (ImageView) chatActivity.findViewById(R.id.imageView13);
        assertNotNull(imageView);
    }

    public void testInputMessageEditTextExists(){
        ChatActivity chatActivity = getActivity();
        EditText editText = (EditText) chatActivity.findViewById(R.id.txtInput);
        assertNotNull(editText);
    }

    public void testSendButtonExists(){
        ChatActivity chatActivity = getActivity();
        Button button = (Button) chatActivity.findViewById(R.id.btnSend);
        assertNotNull(button);
    }

}
