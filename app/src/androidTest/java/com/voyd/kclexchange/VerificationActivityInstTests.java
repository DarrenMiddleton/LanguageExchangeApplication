package com.voyd.kclexchange;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class VerificationActivityInstTests extends ActivityInstrumentationTestCase2<VerificationActivity> {

    public VerificationActivityInstTests() {
        super(VerificationActivity.class);
    }

    public void testActivityExists() {
        VerificationActivity verificationActivity = getActivity();
        assertNotNull(verificationActivity);
    }

    public void testSentenceTextViewExists(){
        VerificationActivity verificationActivity = getActivity();
        TextView textView = (TextView) verificationActivity.findViewById(R.id.txtVerifSentence);
        assertNotNull(textView);
    }

    public void testVerifyEditTextExists(){
        VerificationActivity verificationActivity = getActivity();
        EditText editText = (EditText) verificationActivity.findViewById(R.id.txtVerificationInput);
        assertNotNull(editText);
    }

    public void testVerifyButtonExists(){
        VerificationActivity verificationActivity = getActivity();
        Button button = (Button) verificationActivity.findViewById(R.id.btnVefSubmit);
        assertNotNull(button);
    }
}
