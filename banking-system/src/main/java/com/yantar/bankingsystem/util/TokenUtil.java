package com.yantar.bankingsystem.util;

import com.yantar.bankingsystem.model.TokenClaims;

public interface TokenUtil {
    String      generateAccessToken     (TokenClaims tokenClaims);
    String      generateRefreshToken    (TokenClaims tokenClaims);

    TokenClaims parseAccessToken    (String token);
    TokenClaims parseRefreshToken   (String token);
}
