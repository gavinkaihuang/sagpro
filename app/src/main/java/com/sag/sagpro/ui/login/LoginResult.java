package com.sag.sagpro.ui.login;

import androidx.annotation.Nullable;

import com.sag.sagpro.data.model.LoggedInUser;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private LoggedInUser loggedInUser;
    @Nullable
    private Integer error;

    public LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

//    private LoggedInUser loggedInUser;

    public void setError(@Nullable Integer error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private String errorMessage;

    LoginResult(@Nullable Integer error, String messsage) {
        this.error = error;
        this.errorMessage = messsage;
    }

    LoginResult(@Nullable LoggedInUser success) {
        this.loggedInUser = success;
    }

//    @Nullable
//    LoggedInUserView getSuccess() {
//        return loggedInUser;
//    }

    @Nullable
    Integer getError() {
        return error;
    }
}