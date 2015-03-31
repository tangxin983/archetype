package com.github.tx.archetype.modules.security;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.github.tx.archetype.modules.sys.entity.User;

/**
 * 密码加密
 * 
 * @author tangx
 * @since 2015年1月27日
 */
public class PasswordHelper {

	private String algorithmName;
	private int hashIterations;

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}

	/**
	 * 1、设置盐值；2、加密密码
	 * @param user
	 */
	public void encryptPassword(User user) {
		RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
		user.setSalt(randomNumberGenerator.nextBytes().toHex());
		String newPassword = new SimpleHash(algorithmName, user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()),
				hashIterations).toHex();
		user.setPassword(newPassword);
	}
}
