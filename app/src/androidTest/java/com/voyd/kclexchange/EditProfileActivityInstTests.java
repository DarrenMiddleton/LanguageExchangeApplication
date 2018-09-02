package com.voyd.kclexchange;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class EditProfileActivityInstTests extends ActivityInstrumentationTestCase2<EditProfileActivity> {

    public EditProfileActivityInstTests() {
        super(EditProfileActivity.class);
    }

    public void testActivityExists() {
        EditProfileActivity editProfileActivity = getActivity();
        assertNotNull(editProfileActivity);
    }

    public void testEditScrollViewExists(){
        EditProfileActivity editProfileActivity = getActivity();
        ScrollView scrollView = (ScrollView) editProfileActivity.findViewById(R.id.scrollView4);
        assertNotNull(scrollView);
    }

    public void testEditImageImageViewExists(){
        EditProfileActivity editProfileActivity = getActivity();
        ImageView imageView = (ImageView) editProfileActivity.findViewById(R.id.imgUser);
        assertNotNull(imageView);
    }

    public void testEditSmallImageImageViewExists(){
        EditProfileActivity editProfileActivity = getActivity();
        ImageView imageView = (ImageView) editProfileActivity.findViewById(R.id.imageView12);
        assertNotNull(imageView);
    }

    public void testEditNameEditTextExists(){
        EditProfileActivity editProfileActivity = getActivity();
        EditText editText = (EditText) editProfileActivity.findViewById(R.id.txtName);
        assertNotNull(editText);
    }

    public void testEditPasswordEditTextExists() {
        EditProfileActivity editProfileActivity = getActivity();
        EditText editText = (EditText) editProfileActivity.findViewById(R.id.txtRegisterPassword);
        assertNotNull(editText);
    }

    public void testEditConfirmPasswordEditTextExists() {
        EditProfileActivity editProfileActivity = getActivity();
        EditText editText = (EditText) editProfileActivity.findViewById(R.id.txtRegisterConfirmPassword);
        assertNotNull(editText);
    }

    public void testEditGenderTextViewExists(){
        EditProfileActivity editProfileActivity = getActivity();
        TextView textView = (TextView) editProfileActivity.findViewById(R.id.textView20);
        assertNotNull(textView);
    }

    public void testEditRadioGroupExists(){
        EditProfileActivity editProfileActivity = getActivity();
        RadioGroup radioGroup = (RadioGroup) editProfileActivity.findViewById(R.id.radioGroupGender);
        assertNotNull(radioGroup);
    }

    public void testEditFemaleRadioButtonExists(){
        EditProfileActivity editProfileActivity = getActivity();
        RadioButton radioButton = (RadioButton) editProfileActivity.findViewById(R.id.radioBtnFemale);
        assertNotNull(radioButton);
    }

    public void testEditMaleRadioButtonExists(){
        EditProfileActivity editProfileActivity = getActivity();
        RadioButton radioButton = (RadioButton) editProfileActivity.findViewById(R.id.radioBtnMale);
        assertNotNull(radioButton);
    }

    public void testEditAgeEditTextExists(){
        EditProfileActivity editProfileActivity = getActivity();
        EditText editText = (EditText) editProfileActivity.findViewById(R.id.txtAge);
        assertNotNull(editText);
    }

    public void testEditrLanguagesTeachTextViewExists(){
        EditProfileActivity editProfileActivity = getActivity();
        TextView textView = (TextView) editProfileActivity.findViewById(R.id.textView3);
        assertNotNull(textView);
    }

    public void testEditAddLanguageTeachButtonExists(){
        EditProfileActivity editProfileActivity = getActivity();
        Button button = (Button) editProfileActivity.findViewById(R.id.btnAddLangTeach);
        assertNotNull(button);
    }

    public void testEditRemoveLanguageTeachButtonExists(){
        EditProfileActivity editProfileActivity = getActivity();
        Button button = (Button) editProfileActivity.findViewById(R.id.btnRemoveLangTeach);
        assertNotNull(button);
    }

    public void testEditLanguagesLearnTextViewExists(){
        EditProfileActivity editProfileActivity = getActivity();
        TextView textView = (TextView) editProfileActivity.findViewById(R.id.textView18);
        assertNotNull(textView);
    }

    public void testEditAddLanguageLearnButtonExists(){
        EditProfileActivity editProfileActivity = getActivity();
        Button button = (Button) editProfileActivity.findViewById(R.id.btnAddLangLearn);
        assertNotNull(button);
    }

    public void testEditRemoveLanguageLearnButtonExists(){
        EditProfileActivity editProfileActivity = getActivity();
        Button button = (Button) editProfileActivity.findViewById(R.id.btnRemoveLangLearn);
        assertNotNull(button);
    }

    public void testEditAboutEditTextExists(){
        EditProfileActivity editProfileActivity = getActivity();
        EditText editText = (EditText) editProfileActivity.findViewById(R.id.txtAbout);
        assertNotNull(editText);
    }

    public void testEditConfirmSaveButtonExists(){
        EditProfileActivity editProfileActivity = getActivity();
        Button button = (Button) editProfileActivity.findViewById(R.id.btnConfirmSave);
        assertNotNull(button);
    }

}
