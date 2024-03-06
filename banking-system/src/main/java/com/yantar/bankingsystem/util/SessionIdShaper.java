package com.yantar.bankingsystem.util;

import com.yantar.bankingsystem.model.SessionId;

public interface SessionIdShaper {
    SessionId   generate    (Long userID);
    SessionId   parse       (String sessionId);
    String      convert     (SessionId sessionId);
}
