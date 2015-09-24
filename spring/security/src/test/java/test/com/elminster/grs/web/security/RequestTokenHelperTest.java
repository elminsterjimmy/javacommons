package test.com.elminster.grs.web.security;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.elminster.spring.security.helper.RequestTokenHelper;
import com.elminster.spring.security.model.TokenUserDetails;

public class RequestTokenHelperTest {

  @Test
  public void TestTokenHelper() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("User"));
    authorities.add(new SimpleGrantedAuthority("Admin"));
    TokenUserDetails user = new TokenUserDetails("user", "password", authorities);
    RequestTokenHelper helper = new RequestTokenHelper();
    String token = helper.generateToken4User(user);
    System.out.println(token);
    Assert.assertEquals(user, helper.getUserFromToken(token));
  }
}
