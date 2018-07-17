package passwordchecker;

/**
 *
 * @author Ryan Nichols
 */
public class PasswordChecker {
    
    /***************************************************************************
    *                          Password Rule Constants                         *
    ***************************************************************************/ 
    final int MIN_LENGTH = 9,
              MAX_LENGTH = 24,
              MIN_LOWER = 2,
              MIN_UPPER = 2,
              MIN_NUMERAL = 2,
              MIN_SPECIAL = 2,
              MIN_SIM_SUBSTRING = 5;  //Minimum length for a similar substring
    //Note: These values must be >= 0
    
    /***************************************************************************
    *                                Attributes                                *
    ***************************************************************************/
    private String newPassword = "",
                   oldPassword1 = "",
                   oldPassword2 = "";
    
    
    /***************************************************************************
     *                              Constructors                               *
     **************************************************************************/
    /**
     * Default Constructor
     */    
    public PasswordChecker () {        
    }    
    /**
     * Complete Constructor
     * @param newPW 
     * @param oldPW1
     * @param oldPW2 
     */
    public PasswordChecker (String newPW, String oldPW1, String oldPW2) {
        newPassword = newPW;
        oldPassword1 = oldPW1;
        oldPassword2 = oldPW2;
    }
    
    
    /***************************************************************************
     *                           Getters and Setters                           * 
     **************************************************************************/
    /**
     * 
     * @param pw 
     */
    public void setNewPassword(String pw) {
        newPassword = pw;
    }
    /**
     * 
     * @param pw
     * @return 
     */
    public String getNewPassword() {
        return newPassword;
    }
    
    /**
     * 
     * @param pw 
     */
    public void setOldPassword1(String pw) {
        oldPassword1 = pw;
    }
    /**
     * 
     * @param pw
     * @return 
     */
    public String getOldPassword1() {
        return oldPassword1;
    }
    
    /**
     * 
     * @param pw 
     */
    public void setOldPassword2(String pw) {
        oldPassword2 = pw;
    }
    /**
     * 
     * @param pw
     * @return 
     */
    public String getOldPassword2() {
        return oldPassword2;
    }
    
    
    /***************************************************************************
     *                            Interface Methods                            *
     **************************************************************************/
    /** This method determines the validity of newPassword as defined by the 
     * password rule constants
     * @return A String indicating if the password has been accepted or rejected
     *         (with all reasons included for the latter)
     */
    public String checkPasswordValdity() {
        String result = "";
        
        /*Check password length*/
        char length = checkLength();
        if (length == 's') 
            result += "\n\tPassword must contain at least 9 characters";        
        else if (length == 'l') 
            result += "\n\tPassword must contain no more than 24 characters";
        //else: valid
        
        /*Check password for invalid characters*/
        if (!checkInvalidChar())  
            result += "\n\tPassword must not contain invalid characters or spaces";
        //else: valid
                
        /*Check password for uppercase requirement*/
        if (!checkUpperCase())    
            result += "\n\tPassword must contain at least " + MIN_UPPER + 
                      " uppercase letters";
        //else: valid
        
        /*Check password for lowercase requirement*/
        if (!checkLowerCase())    
            result += "\n\tPassword must contain at least " + MIN_LOWER + 
                      " lowercase letters";
        //else: valid
        
        /*Check password for numeral requirement*/
        if (!checkNumeral())    
            result += "\n\tPassword must contain at least " + MIN_NUMERAL + 
                      " numerals";
        //else: valid
        
        /*Check password for special character requirement*/
        if (!checkSpecial())     
            result += "\n\tPassword must contain at least " + MIN_SPECIAL + 
                      " special characters";
        //else: valid
        
        /*Check similarity with old passwords*/
        if (!checkSimilar())     
            result += "\n\tPassword must not contain a matching sequence (of "
                    + "length " + MIN_SIM_SUBSTRING + " or more), " 
                    + "backwards or forwards, regardless of letter case, with a"
                    + " previous password";
        
        /*Finalize results*/
        if (result.length() != 0) //If the results string is still empty at this point, no rules were broken
            result = "New Password Rejected:" + result;        
        else 
            result = "New Password Accepted";        
        return result;
    }
    
    /** 
     * @return A char indicating newPassword's length validity
     *         'l' = invalid length, too long
     *         'v' = valid length
     *         "s" = invalid length, too short
     */
    public char checkLength() {
        if (newPassword.length() < MIN_LENGTH) {
            return 's';
        }
        else if (newPassword.length() > MAX_LENGTH)
        {
            return 'l';
        }
        else { //((newPassword.length() => MIN_LENGTH && newPassword.length() <= MAX_LENGTH)
            return 'v';
        }
    }
    
    /**
     * 
     * @return true if newPassword contains only valid characters
     *         false if newPassword contains a invalid character or space
     */
    public boolean checkInvalidChar() {                                         //BUG: Cannot handle supplementary characters
        boolean valid = true;       
        for (int index = 0; index < newPassword.length() && valid == true; index++) {
            char curr = newPassword.charAt(index);
            if (curr < 33 || curr > 126 || curr == 34 || curr == 39 || curr == 94 || curr == 96) {                
                valid = false;  //All characters with values between 33 and 126 are valid, exluding 34 ("), 39 ('), 94(^), 96(`)
            }                
        }        
        return valid;
    }
    
    public boolean checkUpperCase() {
        boolean valid = false;
        int upperCount = 0;
        for (int index = 0; index < newPassword.length() && valid == false; index++) {
            char curr = newPassword.charAt(index);
            if (curr >= 65 && curr <= 90) //A-Z
                upperCount++;            
            if (upperCount >= MIN_UPPER) 
                valid = true;     //Break out of loop early if number of uppercase letters reaches MIN_UPPER      
        } 
        return valid;
    }
    
    public boolean checkLowerCase() {
        boolean valid = false;
        int lowerCount = 0;
        for (int index = 0; index < newPassword.length() && valid == false; index++) {
            char curr = newPassword.charAt(index);
            if (curr >= 97 && curr <= 122) //a-z
                lowerCount++;            
            if (lowerCount >= MIN_LOWER) 
                valid = true;     //Break out of loop early if number of lowercase letters reaches MIN_LOWER      
        } 
        return valid;
    }
    
    public boolean checkNumeral() {
        boolean valid = false;
        int numeralCount = 0;
        for (int index = 0; index < newPassword.length() && valid == false; index++) {
            char curr = newPassword.charAt(index);
            if (curr >= 48 && curr <= 57) //0-9
                numeralCount++;            
            if (numeralCount >= MIN_NUMERAL) 
                valid = true;     //Break out of loop early if number of uppercase letters reaches MIN_NUMERAL     
        } 
        return valid;
    }
    
    public boolean checkSpecial() {
        boolean valid = false;
        int specialCount = 0;
        for (int index = 0; index < newPassword.length() && valid == false; index++) {
            char curr = newPassword.charAt(index);
            if (curr == 33 ||                   //!
               (curr >= 35 && curr <= 38) ||    //#$%&
               (curr >= 40 && curr <= 47) ||    //()*+,-./
               (curr >= 58 && curr <= 64) ||    //:;<=>?@
               (curr >= 91 && curr <= 93) ||    //[\]
                curr == 95 ||                   //_
               (curr >= 123 && curr <= 126))    //{|}~
                specialCount++;            
            if (specialCount >= MIN_SPECIAL) 
                valid = true;     //Break out of loop early if number of uppercase letters reaches MIN_SPECIAL     
        } 
        return valid;
    }
    
    /**
     * 
     * @return true if no matches exists
     *         false if matches exists
     */
    public boolean checkSimilar() {
        boolean valid,
                matchOld1,
                matchOld2;
        matchOld1 = checkPairForSimilar (newPassword, oldPassword1);
        matchOld2 = checkPairForSimilar (newPassword, oldPassword2);
        
        if (matchOld1 || matchOld2)
            valid = false;
        else
            valid = true;
        return valid;
    }
    
    /**
     * 
     * @param pw1
     * @param pw2
     * @return true if there is a matching substring of length MIN_SIM_SUBSTRING
     *         false if there is not
     */
    private boolean checkPairForSimilar(String pw1, String pw2) {
        boolean match = false;
        if (pw1.length() < MIN_SIM_SUBSTRING || pw2.length() < MIN_SIM_SUBSTRING)   //Either password is too short to have a matching substring with the other
            return match;
        
        for (int startIndex1 = 0, endIndex1 = (MIN_SIM_SUBSTRING);
        endIndex1 < pw1.length() + 1 && match == false; 
        startIndex1++, endIndex1++) {         
            String pw1Sub = pw1.substring(startIndex1, endIndex1);
            
            for (int startIndex2 = 0, endIndex2 = (MIN_SIM_SUBSTRING);
            endIndex2 < pw2.length() + 1 && match == false; 
            startIndex2++, endIndex2++) {                
                String pw2Sub = pw2.substring(startIndex2, endIndex2);
                if (pw1Sub.equalsIgnoreCase(pw2Sub)) {
                    match = true;
                }                
                pw2Sub = new StringBuilder(pw2Sub).reverse().toString(); //Reverse the substring
                if (pw1Sub.equalsIgnoreCase(pw2Sub)) {
                    match = true;
                }   
            } //End of inner loop
        } //End of out loop
        return match;       
    }
}
