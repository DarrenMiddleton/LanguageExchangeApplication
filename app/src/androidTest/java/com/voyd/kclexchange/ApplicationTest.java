package com.voyd.kclexchange;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.Test;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    /*---------------------------------LoginActivity Class Tests-----------------------------------*/
    @Test
    public void testLoginKingsIDScenario1BeginningWithAnyOtherLetterButK(){
        LoginActivity loginActivity = new LoginActivity();
        boolean holder = loginActivity.getIsValidKingsID("s1234567");
        assertFalse("The King's ID login allows a person to to begin with another letter rather then only k", holder);
    }

    @Test
    public void testLoginKingsIDScenario2BeginningWithSmallK(){
        LoginActivity loginActivity = new LoginActivity();
        boolean holder = loginActivity.getIsValidKingsID("k1234567");
        assertTrue("The King's ID login does not allow small case k's", holder);
    }

    @Test
    public void testLoginKingsIDScenario3BeginningWithBigK(){
        LoginActivity loginActivity = new LoginActivity();
        boolean holder = loginActivity.getIsValidKingsID("K1234567");
        assertTrue("The King's ID login does not allow capital K's", holder);
    }

    @Test
    public void testLoginKingsIDScenario4LettersAfterK(){
        LoginActivity loginActivity = new LoginActivity();
        boolean holder = loginActivity.getIsValidKingsID("K1234j67");
        assertFalse("The King's ID login allows letters after the K", holder);
    }

    @Test
    public void testLoginKingsIDNumberOfCharactersLessThan8(){
        LoginActivity loginActivity = new LoginActivity();
        boolean holder = loginActivity.getIsValidKingsID("K123456");
        assertFalse("The King's ID login allows letters then 8 characters", holder);
    }

    @Test
    public void testLoginKingsIDNumberOfCharactersGreaterThan8() {
        LoginActivity loginActivity = new LoginActivity();
        boolean holder = loginActivity.getIsValidKingsID("K12345678");
        assertFalse("The King's ID login allows more then 8 characters", holder);
    }

    /*---------------------------------LoginActivity Class Ends------------------------------------*/

    /*--------------------------------RegisterActivity Class Tests---------------------------------*/
    @Test
    public void testCodeGeneration(){
        RegisterActivity registerActivity = new RegisterActivity();
        String holder = registerActivity.setCodeHolder(4);
        assertNotNull("Random code is not generating", holder);
    }

    @Test
    public void testCodeGenerationStoring(){
        RegisterActivity registerActivity = new RegisterActivity();
        String holder = registerActivity.setCodeHolder(4);
        String storingCode = holder;
        assertNotNull("Random code is not storing", storingCode);
    }

    @Test
    public void testKingsIDScenario1BeginningWithAnyOtherLetterButK(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidKingsID("s1234567");
        assertFalse("The King's ID allows a person to to begin with another letter rather then only k", holder);
    }

    @Test
    public void testKingsIDScenario2BeginningWithSmallK(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidKingsID("k1234567");
        assertTrue("The King's ID does not allow small case k's", holder);
    }

    @Test
    public void testKingsIDScenario3BeginningWithBigK(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidKingsID("K1234567");
        assertTrue("The King's ID does not allow capital K's", holder);
    }

    @Test
    public void testKingsIDScenario4LettersAfterK(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidKingsID("K1234j67");
        assertFalse("The King's ID allows letters after the K", holder);
    }

    @Test
    public void testKingsIDNumberOfCharactersLessThan8(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidKingsID("K123456");
        assertFalse("The King's ID allows letters then 8 characters", holder);
    }

    @Test
    public void testKingsIDNumberOfCharactersGreaterThan8(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidKingsID("K12345678");
        assertFalse("The King's ID allows more then 8 characters", holder);
    }

    @Test
    public void testPasswordRegexScenario1NumberOfCharacters(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidPassword("test1");
        assertFalse("The password allows less then 6 characters", holder);
    }

    @Test
    public void testPasswordRegexScenario2HavingANumber(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidPassword("testing");
        assertFalse("The password doesn't require a number", holder);
    }

    @Test
    public void testPasswordRegexScenario3VariousLettersAndNumbers(){
        RegisterActivity registerActivity = new RegisterActivity();
        boolean holder = registerActivity.getIsValidPassword("TestinG123");
        assertTrue("The password doesn't allow a assortment of letters and numbers", holder);
    }

    /*---------------------------------RegisterActivity Class Ends---------------------------------*/

    /*--------------------------------RecoveryActivity Class Tests---------------------------------*/
    @Test
    public void testForgetFieldKingsIDScenario1BeginningWithAnyOtherLetterButK(){
        RecoveryActivity recoveryActivity = new RecoveryActivity();
        boolean holder = recoveryActivity.getIsValidForgotString("s1234567");
        assertFalse("The King's ID allows a person to to begin with another letter rather then only k", holder);
    }

    @Test
    public void testForgetFieldKingsIDScenario2BeginningWithSmallK(){
        RecoveryActivity recoveryActivity = new RecoveryActivity();
        boolean holder = recoveryActivity.getIsValidForgotString("k1234567");
        assertTrue("The King's ID does not allow small case k's", holder);
    }

    @Test
    public void testForgetFieldKingsIDScenario3BeginningWithBigK(){
        RecoveryActivity recoveryActivity = new RecoveryActivity();
        boolean holder = recoveryActivity.getIsValidForgotString("K1234567");
        assertTrue("The King's ID does not allow capital K's", holder);
    }

    @Test
    public void testForgetFieldKingsIDScenario4LettersAfterK(){
        RecoveryActivity recoveryActivity = new RecoveryActivity();
        boolean holder = recoveryActivity.getIsValidForgotString("K1234j67");
        assertFalse("The King's ID allows letters after the K", holder);
    }

    @Test
    public void testForgetFieldKingsIDNumberOfCharactersLessThan8(){
        RecoveryActivity recoveryActivity = new RecoveryActivity();
        boolean holder = recoveryActivity.getIsValidForgotString("K123456");
        assertFalse("The King's ID allows letters then 8 characters", holder);
    }

    @Test
    public void testForgetFieldKingsIDNumberOfCharactersGreaterThan8(){
        RecoveryActivity recoveryActivity = new RecoveryActivity();
        boolean holder = recoveryActivity.getIsValidForgotString("K12345678");
        assertFalse("The King's ID allows more then 8 characters", holder);
    }
    /*-------------------------------RecoveryActivity Class Ends-----------------------------------*/

}