package com.elminster.spring.security.helper;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.elminster.common.util.EncryptUtil;
import com.elminster.spring.security.model.TokenUserDetails;
import com.elminster.spring.security.util.JSONUtils;

/**
 * The Request Token Helper.
 * 
 * @author jgu
 * @version 1.0
 */
@Component
public class RequestTokenHelper {
  
  /** the security seed for DES encrypt. */
  private static final String SECURITY_SEED = "com.elminster.security.seed";
  
  /**
   * Generate request token for token user.
   * @param user the token user
   * @return the generated request token
   */
  public String generateToken4User(TokenUserDetails user) {
    try {
      String jsonString = JSONUtils.INSTANCE.toJsonString(user);
      byte[] encryptedJson = EncryptUtil.encryptDES(jsonString.getBytes(), SECURITY_SEED);
      return EncryptUtil.encryptBASE64(encryptedJson);
    } catch (Exception e) {
      throw new RuntimeException("Generate request token failed.", e);
    }
  }
  
  /**
   * Get token user from request token.
   * @param token the request token
   * @return the token user
   */
  @SuppressWarnings("unchecked")
  public TokenUserDetails getUserFromToken(String token) {
    try {
      byte[] decryptedBase64 = EncryptUtil.decryptBASE64(token);
      byte[] decryptedDES = EncryptUtil.decryptDES(decryptedBase64, SECURITY_SEED);
      // a little trick about construct the TokenUserDetails.
      Map<String, Object> map = (Map<String, Object>) JSONUtils.INSTANCE.toJaveObject(decryptedDES, Map.class);
      TokenUserDetails userDetails = new TokenUserDetails(map);
      if (null != userDetails) {
        // expired
        if (System.currentTimeMillis() > userDetails.getExpiration()) {
          userDetails = null;
        } else {
          // user hit, update expiration
          userDetails.updateExpiration();
        }
      }
      return userDetails;
    } catch (Exception e) {
      throw new RuntimeException("Get user from request token failed.", e);
    }
  }
}
