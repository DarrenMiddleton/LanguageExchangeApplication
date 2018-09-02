package com.voyd.kclexchange;

/**
 * Config containing all static strings
 */
public class Config
{
    // Addresses to the scripts of the CRUD.
    public static final String URL_CheckLogin= "http://projectrun.x10host.com/CheckLogin.php";
    public static final String URL_AddUser = "http://projectrun.x10host.com/UserCreation.php";
    public static final String URL_ModifyUser = "http://projectrun.x10host.com/UserModification.php";
    public static final String URL_FindFriends = "http://projectrun.x10host.com/FindFriends.php";
    public static final String URL_RetrieveUser = "http://projectrun.x10host.com/RetrieveUser.php";
    public static final String URL_Search = "http://projectrun.x10host.com/Search.php";
    public static final String URL_AddMeeting = "http://projectrun.x10host.com/newMeeting.php";
    public static final String URL_Msgsearch = "http://projectrun.x10host.com/MessageSpecificSearch.php";
    public static final String URL_Msgsubmit = "http://projectrun.x10host.com/MessageCreate.php";
    public static final String URL_Msgsearch123  = "http://projectrun.x10host.com/test123.php";
    public static final String URL_PasswordRenewal = "http://projectrun.x10host.com/PasswordRenew.php";

    // Keys that will be used to send requests to php scripts.
    public static final String Key_ID = "UniqueCode";
    public static final String Key_Name = "FName";
    public static final String Key_LName = "LName";
    public static final String Key_Email = "Email";
    public static final String Key_Search = "Search";
    public static final String Key_Password = "password";
    public static final String Key_Age = "Age";
    public static final String Key_Gender = "Gender";
    public static final String Key_TeachingLanguage = "TeachingLanguage";
    public static final String Key_PracticeLanguage = "PracticeLanguage";
    public static final String Key_PersonalInterests = "PersonalInterests";
    public static final String Key_Friends = "Friends";
    public static final String Key_IP = "IP";
    public static final String Key_Image = "Image";
    public static final String Key_GPS = "GPS";
    public static final String Key_Stats = "Stats";
    public static final String Key_UniqueA = "UserAUniqueCode";
    public static final String Key_UniqueB = "UserBUniqueCode";
    public static final String Key_StatusOfAccepted = "StatusOfAccepted";
    public static final String Key_DateOFMeet = "DateOFMeet";
    public static final String Key_TimeOfMeet = "TimeOfMeet";
    public static final String Key_StatusBeen = "StatusBeen";
    public static final String Key_Location = "Location";




    public static boolean permissionrequest = false;




}
