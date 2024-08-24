package com.narsha.moongge.repository.queryDSL;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<String> findBadgeListByUserId(String userId);
}
