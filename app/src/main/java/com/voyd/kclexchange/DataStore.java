package com.voyd.kclexchange;

/*
/stores languages, levels, campuses, and faculties to avoid redefining on every use
*/

public class DataStore {
    private static String[] arrayListLanguage = {"Arabic", "Bengali", "Dutch", "English", "French",
            "German", "Greek", "Gujarati", "Hebrew",
            "Hindi", "Italian", "Japanese", "Korean",
            "Mandarin", "Punjabi", "Persian (Farsi)", "Polish",
            "Portuguese (Brazilian)", "Portuguese(European)", "Portuguese via Spanish", "Russian",
            "Spanish", "Swedish", "Turkish", "Urdu"};
    private static String[] arrayLanguageLevel = {"A1", "A2", "B1", "B2", "C1", "C2"};
    private String[] arrayFaculty = {"Arts & Humanities", "Dental", "Life Sciences & Medicine ",
            "Psychiatry, Psychology, & Neuroscience", "Law", "Natural & Mathematical Sciences",
            "Nursing & Midwifery", "Social Science & Public Policy"};

    private static String[] arrayCampus = {"Denmark Hill", "Guy's", "Strand", "St Thomas'", "Waterloo"};

    private static UserInformation currentUser;

    //----------------------------------------------------------------

    public static void setCurrentUser(UserInformation u){currentUser = u; }

    public static UserInformation getCurrentUser(){return currentUser; }

    public static String[] getLanguages(){
        return arrayListLanguage;
    }

    public String[] getFaculties(){
        return arrayFaculty;
    }

    public static String[] getLanguageLevels(){
        return arrayLanguageLevel;
    }

    public static String[] getCampuses(){
        return arrayCampus;
    }

    public String getCampus(int i){
        return arrayCampus[i];
    }

    public String getLanguage(int i){
        return arrayLanguageLevel[i];
    }
}


