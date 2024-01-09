package com.myfitbody.services.contracts;

import com.myfitbody.domain.user.User;

public interface TokenService {

    String generateToken(User user);
    String validateToken(String token);
}
