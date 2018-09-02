package com.voyd.kclexchange;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class RegisterActivityInstTests extends ActivityInstrumentationTestCase2<RegisterActivity> {

    public RegisterActivityInstTests() {
        super(RegisterActivity.class);
    }

    public void testActivityExists() {
        RegisterActivity registerActivity = getActivity();
        assertNotNull(registerActivity);
    }

    public void testScrollViewExists(){
        RegisterActivity registerActivity = getActivity();
        ScrollView scrollView= (ScrollView) registerActivity.findViewById(R.id.scrollView4);
        assertNotNull(scrollView);
    }

    public void testInformationTextViewExists(){
        RegisterActivity registerActivity = getActivity();
        TextView textView= (TextView) registerActivity.findViewById(R.id.textView19);
        assertNotNull(textView);
    }

    public void testRegisterImageViewExists(){
        RegisterActivity registerActivity = getActivity();
        ImageView imageView = (ImageView) registerActivity.findViewById(R.id.imgUser);
        assertNotNull(imageView);
    }

    public void testRegisterNameEditTextExists(){
        RegisterActivity registerActivity = getActivity();
        EditText editText = (EditText) registerActivity.findViewById(R.id.txtName);
        assertNotNull(editText);
    }

    public void testRegisterKingIDEditTextExists(){
        RegisterActivity registerActivity = getActivity();
        EditText editText = (EditText) registerActivity.findViewById(R.id.txtRegisterKingsID);
        assertNotNull(editText);
    }

    public void testRegisterPasswordEditTextExists() {
        RegisterActivity registerActivity = getActivity();
        EditText editText = (EditText) registerActivity.findViewById(R.id.txtRegisterPassword);
        assertNotNull(editText);
    }

    public void testRegisterConfirmPasswordEditTextExists() {
        RegisterActivity registerActivity = getActivity();
        EditText editText = (EditText) registerActivity.findViewById(R.id.txtRegisterConfirmPassword);
        assertNotNull(editText);
    }

    public void testRegisterGenderTextViewExists(){
        RegisterActivity registerActivity = getActivity();
        TextView textView = (TextView) registerActivity.findViewById(R.id.textView20);
        assertNotNull(textView);
    }

    public void testRegisterRadioGroupExists(){
        RegisterActivity registerActivity = getActivity();
        RadioGroup radioGroup = (RadioGroup) registerActivity.findViewById(R.id.radioGroupGender);
        assertNotNull(radioGroup);
    }

    public void testRegisterFemaleRadioButtonExists(){
        RegisterActivity registerActivity = getActivity();
        RadioButton radioButton = (RadioButton) registerActivity.findViewById(R.id.radioBtnFemale);
        assertNotNull(radioButton);
    }

    public void testRegisterMaleRadioButtonExists(){
        RegisterActivity registerActivity = getActivity();
        RadioButton radioButton = (RadioButton) registerActivity.findViewById(R.id.radioBtnMale);
        assertNotNull(radioButton);
    }

    public void testRegisterAgeEditTextExists(){
        RegisterActivity registerActivity = getActivity();
        EditText editText = (EditText) registerActivity.findViewById(R.id.txtAge);
        assertNotNull(editText);
    }

    public void testRegisterLanguagesTeachTextViewExists(){
        RegisterActivity registerActivity = getActivity();
        TextView textView = (TextView) registerActivity.findViewById(R.id.textView3);
        assertNotNull(textView);
    }

    public void testRegisterAddLanguageTeachButtonExists(){
        RegisterActivity registerActivity = getActivity();
        Button button = (Button) registerActivity.findViewById(R.id.btnAddLangTeach);
        assertNotNull(button);
    }

    public void testRegisterRemoveLanguageTeachButtonExists(){
        RegisterActivity registerActivity = getActivity();
        Button button = (Button) registerActivity.findViewById(R.id.btnRemoveLangTeach);
        assertNotNull(button);
    }

    public void testRegisterLanguagesLearnTextViewExists(){
        RegisterActivity registerActivity = getActivity();
        TextView textView = (TextView) registerActivity.findViewById(R.id.textView18);
        assertNotNull(textView);
    }

    public void testRegisterAddLanguageLearnButtonExists(){
        RegisterActivity registerActivity = getActivity();
        Button button = (Button) registerActivity.findViewById(R.id.btnAddLangLearn);
        assertNotNull(button);
    }

    public void testRegisterRemoveLanguageLearnButtonExists(){
        RegisterActivity registerActivity = getActivity();
        Button button = (Button) registerActivity.findViewById(R.id.btnRemoveLangLearn);
        assertNotNull(button);
    }

    public void testRegisterAboutEditTextExists(){
        RegisterActivity registerActivity = getActivity();
        EditText editText = (EditText) registerActivity.findViewById(R.id.txtAbout);
        assertNotNull(editText);
    }

    public void testRegisterConfirmButtonExists(){
        RegisterActivity registerActivity = getActivity();
        Button button = (Button) registerActivity.findViewById(R.id.btnConfirmRegister);
        assertNotNull(button);
    }

    public void testRegisteringAUser() {
        RegisterActivity registerActivity = getActivity();
        final EditText txtName = (EditText) registerActivity.findViewById(R.id.txtName);
        final EditText txtRegisterKingsID = (EditText) registerActivity.findViewById(R.id.txtRegisterKingsID);
        final EditText txtRegisterPassword = (EditText) registerActivity.findViewById(R.id.txtRegisterPassword);
        final EditText txtRegisterConfirmPassword = (EditText) registerActivity.findViewById(R.id.txtRegisterConfirmPassword);
        final RadioButton radioBtnMale = (RadioButton) registerActivity.findViewById(R.id.radioBtnMale);
        final EditText txtAge = (EditText) registerActivity.findViewById(R.id.txtAge);
        final EditText txtAbout = (EditText) registerActivity.findViewById(R.id.txtAbout);
        final Button btnConfirmRegister = (Button) registerActivity.findViewById(R.id.btnConfirmRegister);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                txtName.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("John Doe");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                txtRegisterKingsID.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("K1461984");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                txtRegisterPassword.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("testing123");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                txtRegisterConfirmPassword.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("testing123");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                radioBtnMale.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_ENTER);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                txtAge.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("21");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                txtAbout.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Interested in Testing");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                btnConfirmRegister.requestFocus();
                btnConfirmRegister.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
    }
}
