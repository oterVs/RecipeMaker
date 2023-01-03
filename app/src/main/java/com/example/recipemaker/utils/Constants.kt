package com.example.recipemaker.utils

object Constants {
    const val INFO_NOT_SET = "not logged"
    const val USER_NOT_LOGGED = "user not logged"

    const val USER_COLLECTION = "users"
    const val FOOD_COLLECTION = "recipes"

    var USER_LOGGED_IN_ID = USER_NOT_LOGGED

    const val EMAIL_ALREADY_EXISTS : String = "com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account."
    const val VALUE_REQUIRED = "Campo necesario"
    const val USER_NOT_EXISTS : String = "There is no user record corresponding to this identifier. The user may have been deleted."
    const val WRONG_PASSWORD : String = "The password is invalid or the user does not have a password."

    const val ENCRYPTED_SHARED_PREFERENCES_NAME = "EL_BICHO_SHARED"
    const val USER_PREFERECE_NAME = "user_preferences"

    const val USER_EMAIL = "USER_EMAIL"
    const val IS_SIGN_IN = "IS_SIGN_IN"

    const val SHARED_EMAIL = "email"
    const val SHARED_PASSWORD = "password"
}