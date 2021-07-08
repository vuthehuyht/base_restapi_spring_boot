package vn.co.vis.restful.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.co.vis.restful.dao.entity.User;
import vn.co.vis.restful.dao.repository.UserRepository;
import vn.co.vis.restful.exception.TokenInvalidException;
import vn.co.vis.restful.service.AbstractService;
import vn.co.vis.restful.service.VerifyService;
import vn.co.vis.restful.utility.DateTimeUtils;
import vn.co.vis.restful.utility.passwd.service.PasswordService;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Optional;

/**
 * @author Giang Thanh Quang
 * @since 10/28/2020
 */
@Service
public class VerifyServiceImpl extends AbstractService implements VerifyService {

    private static final String TOKEN_TYPE_PREFIX = "Bearer";
    private static final String TYPE_HEADER_NAME = "typ";
    private static final String JWT_HEADER_TYPE = "JWT";
    private static final String USER_NAME_PAYLOAD = "userName";

    private String tokenKey;
    @Value("${token.expiration}")
    private Integer tokenExpiredValue;

    @Autowired
    PasswordService passwordService;

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void init() {
        this.tokenKey = passwordService.getPassword("token.key");
    }

    @Override
    public String generateLoginToken(String userName) {
        return Jwts.builder()
                .setHeaderParam(TYPE_HEADER_NAME, JWT_HEADER_TYPE)
                .claim(USER_NAME_PAYLOAD, userName)
                .setIssuedAt(new Date())
                .setExpiration(DateTimeUtils.addDayToDate(new Date(), tokenExpiredValue))
                .signWith(SignatureAlgorithm.HS256, tokenKey.getBytes())
                .compact();
    }

    @Override
    public @NonNull User verifyLoginToken(@NonNull String token) {
        String userName;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(tokenKey.getBytes())
                    .parseClaimsJws(token.replace(TOKEN_TYPE_PREFIX, "")).getBody();
            userName = claims.get(USER_NAME_PAYLOAD, String.class);
        } catch (ExpiredJwtException e) {
            throw new TokenInvalidException("E1", "Token is Expired");
        } catch (Exception e) {
            throw new TokenInvalidException("E2", "Can't verify token: " + token);
        }

        Optional<User> user = userRepository.findById(userName);
        return user.orElseThrow(() -> {
            throw new TokenInvalidException("E3", "This user is invalidate: " + userName);
        });
    }
}
