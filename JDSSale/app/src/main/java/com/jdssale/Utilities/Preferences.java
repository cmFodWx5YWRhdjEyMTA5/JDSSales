package com.jdssale.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by dikhong on 06-07-2018.
 */

public class Preferences {
    private String appName = "AnyApp";
    private static final String TAG = "Preferences";
    private SharedPreferences userPrefs;
    public static final String USER_DETAILS = "user_details";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "username";
    public static final String FIRST_NAME = "firstname";
    public static final String MIDDLE_NAME = "middlename";
    public static final String LAST_NAME = "lastname";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String MOBILE = "mobile";
    public static final String GENDER = "gender";
    public static final String USER_PHOTO_PATH = "userphoto_path";
    public static final String DOB = "dob";
    public static final String VERIFICATION_CODE = "verificationcode";
    public static final String VERIFY_STATUS = "verifystatus";
    public static final String LAST_LOGIN_DATE = "last_login_date";
    public static final String AUTH_KEY = "auth_key";
    public static final String PASSWORD_RESET_TOKEN = "password_reset_token";
    public static final String IS_ACTIVE = "is_active";
    public static final String CREATED_BY = "createdby";
    public static final String CREATED_DATE = "createddate";
    public static final String UPDATED_BY = "updatedby";
    public static final String UPDATED_DATE = "updateddate";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String SUBS_FITNESS_NEWS_LETTER = "subs_fitness_newsletter";
    public static final String REFERRAL_CODE = "referral_code";
    public static final String CDN_URL = "cdn_url";
    public static final String ACTIVITY_URL = "activity_url";
    public static final String AGREE_THIRD_PARTY = "agree_third_party";
    public static final String IS_LOGGEDIN = "islogged";
    public static final String DEVICE_TOKEN = "device_token";
    public static final String AGE = "age";
    public static final String WEIGHT = "weight";
    public static final String HEIGHT = "height";
    public static final String BODY_FAT = "body_fat";
    public static final String DEVICE_TOKEN_SENT_TO_SERVER = "device_token_sent_to_server";
    public static final String PLACE_HOLDER_IMAGE="place_holder_image";
    public static final String USER_TYPE="user_type";
    public static final String TRAINING_TYPE="training_type";
    public static final String BIO_DATA="bio_data";
    public static final String UNIQUE_ID="unique_id";
    public static final String COMPANY="company";
    public static final String GROUP_ID="group_id";
    public static final String VEHICLE_ID="vehicle_id";

    public String userId;
    public String userName;
    public String firstName;
    public String middleName;
    public String lastName;
    public String email;
    public String Password;
    public String Mobile;
    public String gender;
    public String userPhotoPath;
    public String dob;
    public String verificationCode;
    public String verifyStatus;
    public String lastLogindate;
    public String authKey;
    public String passwordResetToken;
    public String isActive;
    public String createdBy;
    public String createdDate;
    public String latitude;
    public String longitude;
    public String updatedBy;
    public String updatedDate;
    public String subFitnessNewsLetter;
    public String agreeThirdParty;
    public String referralCode;
    public String cdnUrl;
    public String activityUrl;
    public String device_token;
    public String age;
    public String uniqueId;
    public String company;
    public String groupId;
    public String vehicleId;

    public String getVehicleId() {
        return userPrefs.getString(VEHICLE_ID,null);
    }

    public void setVehicleId(String vehicleId) {
        userPrefs.edit().putString(VEHICLE_ID,vehicleId).commit();
        this.vehicleId = vehicleId;
    }

    public String getGroupId() {
        return userPrefs.getString(GROUP_ID,null);
    }

    public void setGroupId(String groupId) {
        userPrefs.edit().putString(GROUP_ID,groupId).commit();
        this.groupId = groupId;
    }



    public String getCompany() {
        return userPrefs.getString(COMPANY,null);
    }

    public void setCompany(String company) {
        userPrefs.edit().putString(COMPANY,company).commit();
        this.company = company;
    }


    public String getUniqueId() {
        return userPrefs.getString(UNIQUE_ID, null);
    }

    public void setUniqueId(String uniqueId) {
        userPrefs.edit().putString(UNIQUE_ID,uniqueId).commit();
        this.uniqueId = uniqueId;
    }


    public String getBioData() {
        return userPrefs.getString(BIO_DATA, null);
    }

    public void setBioData(String bioData) {
        userPrefs.edit().putString(BIO_DATA,bioData).commit();
        this.bioData = bioData;
    }

    public String bioData;

    public String getTrainingType() {
        return userPrefs.getString(TRAINING_TYPE, null);
    }

    public void setTrainingType(String trainingType) {
        userPrefs.edit().putString(TRAINING_TYPE,trainingType).commit();
        this.trainingType = trainingType;
    }

    public String trainingType;


    public String getAge() {
        return userPrefs.getString(AGE, null);
    }

    public void setAge(String age) {
        userPrefs.edit().putString(AGE,age).commit();
        this.age = age;
    }

    public String getWeight() {
        return userPrefs.getString(WEIGHT, null);
    }

    public void setWeight(String weight) {
        userPrefs.edit().putString(WEIGHT,weight).commit();
        this.weight = weight;
    }

    public String getHeight() {
        return userPrefs.getString(HEIGHT, null);
    }

    public void setHeight(String height) {
        userPrefs.edit().putString(HEIGHT,height).commit();
        this.height = height;
    }

    public String getBodyFat() {
        return userPrefs.getString(BODY_FAT, null);
    }

    public void setBodyFat(String bodyFat) {
        userPrefs.edit().putString(BODY_FAT,bodyFat).commit();
        this.bodyFat = bodyFat;
    }

    public String weight;
    public String height;
    public String bodyFat;
    public Boolean device_token_sent_to_server;
    public String placeholderimage;

    public String getUserType() {
        return userPrefs.getString(USER_TYPE, null);
    }

    public void setUserType(String userType) {
        userPrefs.edit().putString(USER_TYPE, userType).commit();
        this.userType = userType;
    }

    public String userType;
    public String getPlaceholderimage() {
        return userPrefs.getString(PLACE_HOLDER_IMAGE, null);
    }

    public void setPlaceholderimage(String placeholderimage) {
        userPrefs.edit().putString(PLACE_HOLDER_IMAGE, placeholderimage).commit();
        this.placeholderimage = placeholderimage;
    }

    public String getLatitude() {
        return userPrefs.getString(LATITUDE,null);
    }

    public void setLatitude(String latitude) {
        userPrefs.edit().putString(LATITUDE,latitude).commit();
        this.latitude = latitude;
    }

    public String getLongitude() {
        return userPrefs.getString(LONGITUDE,null);
    }

    public void setLongitude(String longitude) {
        userPrefs.edit().putString(LONGITUDE,longitude).commit();
        this.longitude = longitude;
    }
    private static Preferences instance;

    public static Preferences getInstance() {
        if (instance == null)
            instance = new Preferences();

        return instance;
    }

    private Preferences() {
        initPrefs();
    }

    private void initPrefs() {
        if (null != userPrefs) return;
        userPrefs = JDSSale.getInstance().getSharedPreferences(USER_DETAILS, Context.MODE_PRIVATE);
    }

    public String getDevice_token() {
        return userPrefs.getString(DEVICE_TOKEN, null);
    }

    public void setDevice_token(String device_token) {
        Log.d(TAG, "setDevice_token: Storing device token"+device_token);
        userPrefs.edit().putString(DEVICE_TOKEN, device_token).commit();
        this.device_token = device_token;
    }

    public Boolean getDevice_token_sent_to_server() {
        return userPrefs.getBoolean(DEVICE_TOKEN_SENT_TO_SERVER, false);
    }

    public void setDevice_token_sent_to_server(Boolean sent_to_server) {
        userPrefs.edit().putBoolean(DEVICE_TOKEN_SENT_TO_SERVER,sent_to_server).commit();
        this.device_token_sent_to_server = sent_to_server;
    }

    public String getUserId() {
        return userPrefs.getString(USER_ID, null);
    }

    public void setUserId(String userId) {
        userPrefs.edit().putString(USER_ID, userId).commit();
        this.userId = userId;
    }

    public String getUserName() {
        return userPrefs.getString(USER_NAME, null);
    }

    public void setUserName(String userName) {
        userPrefs.edit().putString(USER_NAME, userName).commit();
        this.userName = userName;
    }

    public String getFirstName() {
        return userPrefs.getString(FIRST_NAME, null);
    }

    public void setFirstName(String firstName) {
        userPrefs.edit().putString(FIRST_NAME, firstName).commit();
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return userPrefs.getString(MIDDLE_NAME, null);
    }

    public void setMiddleName(String middleName) {
        userPrefs.edit().putString(MIDDLE_NAME, middleName).commit();
        this.middleName = middleName;
    }

    public String getLastName() {
        return userPrefs.getString(LAST_NAME, null);
    }

    public void setLastName(String lastName) {
        userPrefs.edit().putString(LAST_NAME, lastName).commit();
        this.lastName = lastName;
    }

    public String getEmail() {
        return userPrefs.getString(EMAIL, null);
    }

    public void setEmail(String email) {
        userPrefs.edit().putString(EMAIL, email).commit();
        this.email = email;
    }

    public String getPassword() {
        return userPrefs.getString(PASSWORD, null);
    }

    public void setPassword(String password) {
        userPrefs.edit().putString(PASSWORD, password).commit();
        Password = password;
    }

    public String getReferralCode() {
        return userPrefs.getString(REFERRAL_CODE, null);
    }

    public void setReferralCode(String referralCode) {
        userPrefs.edit().putString(REFERRAL_CODE, referralCode).commit();
        this.referralCode = referralCode;
    }

    public String getCdnUrl() {
        return userPrefs.getString(CDN_URL, null);
    }

    public void setCdnUrl(String cdnUrl) {
        userPrefs.edit().putString(CDN_URL, cdnUrl).commit();
        this.cdnUrl = cdnUrl;
    }

    public String getActivityUrl() {
        return userPrefs.getString(ACTIVITY_URL, null);
    }

    public void setActivityUrl(String activityUrl) {
        userPrefs.edit().putString(ACTIVITY_URL, activityUrl).commit();
        this.activityUrl = activityUrl;
    }

    public String getMobile() {
        return userPrefs.getString(MOBILE, null);
    }

    public void setMobile(String mobile) {
        userPrefs.edit().putString(MOBILE, mobile).commit();
        Mobile = mobile;
    }

    public String getGender() {
        return userPrefs.getString(GENDER, null);
    }

    public void setGender(String gender) {
        userPrefs.edit().putString(GENDER, gender).commit();
        this.gender = gender;
    }

    public String getUserPhotoPath() {
        return userPrefs.getString(USER_PHOTO_PATH, null);
    }

    public void setUserPhotoPath(String userPhotoPath) {
        Log.d(TAG, "setUserPhotoPath: " + userPhotoPath);
        userPrefs.edit().putString(USER_PHOTO_PATH, userPhotoPath).commit();
        this.userPhotoPath = userPhotoPath;
    }

    public String getDob() {
        return userPrefs.getString(DOB, null);
    }

    public void setDob(String dob) {
        userPrefs.edit().putString(DOB, dob).commit();
        this.dob = dob;
    }

    public String getVerificationCode() {
        return userPrefs.getString(VERIFICATION_CODE, null);
    }

    public void setVerificationCode(String verificationCode) {
        userPrefs.edit().putString(VERIFICATION_CODE, verificationCode).commit();
        this.verificationCode = verificationCode;
    }

    public String getVerifyStatus() {
        return userPrefs.getString(VERIFY_STATUS, null);
    }

    public void setVerifyStatus(String verifyStatus) {
        userPrefs.edit().putString(VERIFY_STATUS, verifyStatus).commit();
        this.verifyStatus = verifyStatus;
    }

    public String getLastLogindate() {
        return userPrefs.getString(LAST_LOGIN_DATE, null);
    }

    public void setLastLogindate(String lastLogindate) {
        userPrefs.edit().putString(LAST_LOGIN_DATE, lastLogindate).commit();
        this.lastLogindate = lastLogindate;
    }

    public String getAuthKey() {
        return userPrefs.getString(AUTH_KEY, null);
    }

    public void setAuthKey(String authKey) {
        userPrefs.edit().putString(AUTH_KEY, authKey).commit();
        this.authKey = authKey;
    }

    public String getPasswordResetToken() {
        return userPrefs.getString(PASSWORD_RESET_TOKEN, null);
    }

    public void setPasswordResetToken(String passwordResetToken) {
        userPrefs.edit().putString(PASSWORD_RESET_TOKEN, passwordResetToken).commit();
        this.passwordResetToken = passwordResetToken;
    }

    public String getIsActive() {
        return userPrefs.getString(IS_ACTIVE, null);
    }

    public void setIsActive(String isActive) {
        userPrefs.edit().putString(IS_ACTIVE, isActive).commit();
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return userPrefs.getString(CREATED_BY, null);
    }

    public void setCreatedBy(String createdBy) {
        userPrefs.edit().putString(CREATED_BY, createdBy).commit();
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return userPrefs.getString(CREATED_DATE, null);
    }

    public void setCreatedDate(String createdDate) {
        userPrefs.edit().putString(CREATED_DATE, createdDate).commit();
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return userPrefs.getString(UPDATED_BY, null);
    }

    public void setUpdatedBy(String updatedBy) {
        userPrefs.edit().putString(UPDATED_BY, updatedBy).commit();
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return userPrefs.getString(UPDATED_DATE, null);
    }

    public void setUpdatedDate(String updatedDate) {
        userPrefs.edit().putString(UPDATED_DATE, updatedDate).commit();
        this.updatedDate = updatedDate;
    }

    public String getSubFitnessNewsLetter() {
        return userPrefs.getString(SUBS_FITNESS_NEWS_LETTER, null);
    }

    public void setSubFitnessNewsLetter(String subFitnessNewsLetter) {
        userPrefs.edit().putString(SUBS_FITNESS_NEWS_LETTER, subFitnessNewsLetter).commit();
        this.subFitnessNewsLetter = subFitnessNewsLetter;
    }

    public String getAgreeThirdParty() {
        return userPrefs.getString(AGREE_THIRD_PARTY, null);
    }

    public void setAgreeThirdParty(String agreeThirdParty) {
        userPrefs.edit().putString(AGREE_THIRD_PARTY, agreeThirdParty).commit();
        this.agreeThirdParty = agreeThirdParty;
    }

    public boolean isLogIn() {
        return userPrefs.getBoolean(IS_LOGGEDIN, false);
    }

    public void setLogIn(boolean logIn) {
        userPrefs.edit().putBoolean(IS_LOGGEDIN, logIn).commit();
    }

    public void clearLocation() {
        userPrefs.edit().remove("LATITUDE").commit();
        userPrefs.edit().remove("LONGITUDE").commit();
    }

    public void clearUserDetails() {
        String tmp = getDevice_token();

        userPrefs.edit().clear().commit();
        if(tmp!=null){
            setDevice_token(tmp);
        }
    }
}
