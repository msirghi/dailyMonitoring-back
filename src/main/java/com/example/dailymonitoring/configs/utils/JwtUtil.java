package com.example.dailymonitoring.configs.utils;

import com.example.dailymonitoring.models.entities.UserEntity;
import com.example.dailymonitoring.respositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtUtil {

  private final UserRepository userRepository;

  public JwtUtil(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Value("app.secretKey")
  private String SECRET_KEY;

  @Value("app.secretRefreshKey")
  private String SECRET_REFRESH_KEY;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails.getUsername());
  }

  private String createToken(Map<String, Object> claims, String subject) {
    final UserEntity user = userRepository.findUserByUsername(subject).get();
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setClaims(new HashMap<String, Object>() {{
          put("sub", user.getUsername());
          put("id", user.getId());
          put("fullName", user.getFullName());
        }})
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public String extractUsernameFromRefreshToken(String token, String userPwd) {
    return extractClaimFromRefreshToken(token, userPwd, Claims::getSubject);
  }

  public <T> T extractClaimFromRefreshToken(String token, String userPwd, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaimsFromRefreshToken(token, userPwd);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaimsFromRefreshToken(String token, String userPwd) {
    return Jwts.parser().setSigningKey(SECRET_REFRESH_KEY + userPwd).parseClaimsJws(token).getBody();
  }

  public String createRefreshToken(String username, String userPwd) {
    final UserEntity user = userRepository.findUserByUsername(username).get();
    return Jwts.builder()
        .setClaims(new HashMap<String, Object>() {{
          put("sub", user.getUsername());
          put("id", user.getId());
        }})
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
        .signWith(SignatureAlgorithm.HS256, SECRET_REFRESH_KEY + userPwd).compact();
  }

  public Date extractExpirationForRefreshToken(String token, String userPwd) {
    return extractClaimFromRefreshToken(token, userPwd, Claims::getExpiration);
  }

  public Object getIdFromRefreshToken(String token) {
    int i = token.lastIndexOf('.');
    String withoutSignature = token.substring(0, i + 1);
    Jwt<Header, Claims> untrusted = Jwts.parser().parseClaimsJwt(withoutSignature);
    return untrusted.getBody().get("id");
  }

  public Object getUsernameFromRefreshToken(String token) {
    int i = token.lastIndexOf('.');
    String withoutSignature = token.substring(0, i + 1);
    Jwt<Header, Claims> untrusted = Jwts.parser().parseClaimsJwt(withoutSignature);
    return untrusted.getBody().getSubject();
  }

  public Object getExpFromRefreshToken(String token) {
    int i = token.lastIndexOf('.');
    String withoutSignature = token.substring(0, i + 1);
    Jwt<Header, Claims> untrusted = Jwts.parser().parseClaimsJwt(withoutSignature);
    return untrusted.getBody().getExpiration();
  }
}