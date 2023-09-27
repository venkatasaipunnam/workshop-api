package com.clarku.workshop.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.clarku.workshop.exception.GlobalException;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class Secure {

	public String getEncrypted(String password) throws GlobalException {

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA");
			messageDigest.update(password.getBytes());
			byte[] resultedArray = messageDigest.digest();

			StringBuilder hashPass = new StringBuilder();

			for (byte eachByte : resultedArray) {
				hashPass.append(String.format("%02x", eachByte));
			}

			return hashPass.toString();

		} catch (NoSuchAlgorithmException exp) {
			log.error("Secure :: hashing : Failed to fetch the Algorithm");
			throw new GlobalException(Constants.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception exp) {
			log.error("Secure :: hashing : " + exp.getMessage());
			throw new GlobalException(Constants.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
