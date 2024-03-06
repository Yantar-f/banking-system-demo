package com.yantar.bankingsystem.util.impl;

import com.yantar.bankingsystem.config.RefreshTokenConfig;
import com.yantar.bankingsystem.config.AccessTokenConfig;
import com.yantar.bankingsystem.config.TokenConfig;
import com.yantar.bankingsystem.exception.InvalidTokenException;
import com.yantar.bankingsystem.model.TokenClaims;
import com.yantar.bankingsystem.util.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.io.Decoders.BASE64;

@Component
public class TokenUtilImpl implements TokenUtil {
    private final AccessTokenConfig accessTokenConfig;
    private final RefreshTokenConfig refreshTokenConfig;
    private final JwtParser accessTokenParser;
    private final JwtParser refreshTokenParser;
    private final Clock clock;

    public TokenUtilImpl(AccessTokenConfig accessTokenConfig,
                         RefreshTokenConfig refreshTokenConfig,
                         Clock clock) {

        this.accessTokenConfig = accessTokenConfig;
        this.refreshTokenConfig = refreshTokenConfig;
        this.clock = clock;

        accessTokenParser = buildParser(accessTokenConfig);
        refreshTokenParser = buildParser(refreshTokenConfig);
    }

    @Override
    public String generateAccessToken(TokenClaims claims) {
        return generateToken(claims, accessTokenConfig);
    }

    @Override
    public String generateRefreshToken(TokenClaims claims) {
        return generateToken(claims, refreshTokenConfig);
    }

    @Override
    public TokenClaims parseAccessToken(String token) {
        return parseWith(accessTokenParser, token);
    }

    @Override
    public TokenClaims parseRefreshToken(String token) {
        return parseWith(refreshTokenParser, token);
    }

    private TokenClaims parseWith(JwtParser parser, String token) {
        try {
            Claims claimsMap = extractClaimsMapWith(parser, token);
            String sessionID = extractSessionIDFrom(claimsMap);
            String tokenID = extractTokenIDFrom(claimsMap);
            Long  subjectID = extractSubjectIDFrom(claimsMap);

            return new TokenClaims(subjectID, sessionID, tokenID);
        } catch (SignatureException |
                 MalformedJwtException |
                 ExpiredJwtException |
                 UnsupportedJwtException |
                 IllegalArgumentException |
                 ClassCastException exception) {
            throw new InvalidTokenException(token);
        }
    }

    private Claims extractClaimsMapWith(JwtParser parser, String token) {
        return parser.parseClaimsJws(token).getBody();
    }

    private Long extractSubjectIDFrom(Claims claimsMap) {
        return Long.valueOf(claimsMap.getSubject());
    }

    private String extractSessionIDFrom(Claims claimsMap) {
        return claimsMap.get("sid", String.class);
    }

    private String extractTokenIDFrom(Claims claimsMap) {
        return claimsMap.getId();
    }

    private String generateToken(TokenClaims claims, TokenConfig config) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(BASE64.decode(config.getSecretKey())), HS256)
                .setIssuer(config.getIssuer())
                .setExpiration(Date.from(Instant.now(clock).plusMillis(config.getExpirationMs())))
                .setId(claims.getTokenId())
                .setSubject(claims.getSubjectId().toString())
                .claim("sid", claims.getSessionId())
                .compact();
    }

    private JwtParser buildParser(TokenConfig config) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(BASE64.decode(config.getSecretKey())))
                .requireIssuer(config.getIssuer())
                .setAllowedClockSkewSeconds(config.getAllowedClockSkewS())
                .build();
    }
}
