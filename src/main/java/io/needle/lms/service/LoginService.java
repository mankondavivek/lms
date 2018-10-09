package io.needle.lms.service;

import io.needle.lms.exception.InvalidUserNamePasswordException;
import io.needle.lms.exception.NotFoundException;

public interface LoginService {

	boolean validateUser(String username, String password) throws NotFoundException, InvalidUserNamePasswordException;

}
