package com.voyd.kclexchange;


import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivityInstTests extends ActivityInstrumentationTestCase2<LoginActivity> {

    public LoginActivityInstTests() {
        super(LoginActivity.class);
    }

    public void testActivityExists() {
        LoginActivity LoginActivity = getActivity();
        assertNotNull(LoginActivity);
    }

    public void testAppImageViewExists(){
        LoginActivity LoginActivity = getActivity();
        ImageView imageView = (ImageView) LoginActivity.findViewById(R.id.img_kcelogo);
        assertNotNull(imageView);
    }

    public void testLoginEditTextExists(){
        LoginActivity LoginActivity = getActivity();
        EditText editText = (EditText) LoginActivity.findViewById(R.id.txtKNumber);
        assertNotNull(editText);
    }

    public void testPasswordEditTextExists(){
        LoginActivity LoginActivity = getActivity();
        EditText editText = (EditText) LoginActivity.findViewById(R.id.txtPassword);
        assertNotNull(editText);
    }

    public void testSignInButtonExists(){
        LoginActivity LoginActivity = getActivity();
        Button button = (Button) LoginActivity.findViewById(R.id.button);
        assertNotNull(button);
    }

    public void testNewUserTextViewExists(){
        LoginActivity LoginActivity = getActivity();
        TextView textView = (TextView) LoginActivity.findViewById(R.id.textNewUser);
        assertNotNull(textView);
    }

    public void testForgetYourPasswordTextViewExists(){
        LoginActivity LoginActivity = getActivity();
        TextView textView= (TextView) LoginActivity.findViewById(R.id.textView);
        assertNotNull(textView);
    }

}
