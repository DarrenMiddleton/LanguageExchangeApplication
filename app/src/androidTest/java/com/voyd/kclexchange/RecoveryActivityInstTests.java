package com.voyd.kclexchange;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RecoveryActivityInstTests extends ActivityInstrumentationTestCase2<RecoveryActivity> {

    public RecoveryActivityInstTests() {
        super(RecoveryActivity.class);
    }

    public void testActivityExists() {
        RecoveryActivity recoveryActivity = getActivity();
        assertNotNull(recoveryActivity);
    }

    public void testExplanationTextViewExists() {
        RecoveryActivity recoveryActivity = getActivity();
        TextView textView = (TextView) recoveryActivity.findViewById(R.id.textView15);
        assertNotNull(textView);
    }

    public void testKNumberEditTextExists() {
        RecoveryActivity recoveryActivity = getActivity();
        EditText editText = (EditText) recoveryActivity.findViewById(R.id.txtKnumber);
        assertNotNull(editText);
    }

    public void testDoneButtonExists() {
        RecoveryActivity recoveryActivity = getActivity();
        Button button = (Button) recoveryActivity.findViewById(R.id.button);
        assertNotNull(button);
    }

}
