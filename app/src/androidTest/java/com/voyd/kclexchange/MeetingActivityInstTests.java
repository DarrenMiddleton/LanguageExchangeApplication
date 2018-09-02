package com.voyd.kclexchange;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MeetingActivityInstTests extends ActivityInstrumentationTestCase2<MeetingActivity> {

    public MeetingActivityInstTests() {
        super(MeetingActivity.class);
    }

    public void testActivityExists() {
        MeetingActivity meetingActivity = getActivity();
        assertNotNull(meetingActivity);
    }

    public void testCampusSpinnerExists(){
        MeetingActivity meetingActivity = getActivity();
        Spinner spinner = (Spinner) meetingActivity.findViewById(R.id.spinnerCampuses);
        assertNotNull(spinner);
    }

    public void testSetTimeEditTextExists(){
        MeetingActivity meetingActivity = getActivity();
        EditText editText = (EditText) meetingActivity.findViewById(R.id.txtSetTime);
        assertNotNull(editText);
    }

    public void testSetDateExists(){
        MeetingActivity meetingActivity = getActivity();
        EditText editText = (EditText) meetingActivity.findViewById(R.id.txtSetDate);
        assertNotNull(editText);
    }

    public void testConfirmButtonExists(){
        MeetingActivity meetingActivity = getActivity();
        Button button = (Button) meetingActivity.findViewById(R.id.btnSchConfirm);
        assertNotNull(button);
    }

    public void testCancelButtonExists(){
        MeetingActivity meetingActivity = getActivity();
        Button button = (Button) meetingActivity.findViewById(R.id.btnSchCancel);
        assertNotNull(button);
    }
}
