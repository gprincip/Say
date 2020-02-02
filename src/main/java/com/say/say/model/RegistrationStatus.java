package com.say.say.model;

import java.io.Serializable;

/**
 * Information about user registration and activation
 * @author gavrilo
 *
 */
public enum RegistrationStatus implements Serializable{
	OK, USERNAME_ALREADY_EXISTS, EMAIL_ALREADY_EXISTS, GENERIC_ERROR, WRONG_ACTIVATION_CODE, USER_ALREADY_ACTIVE, USER_DOESNT_EXIST
}
