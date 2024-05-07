package com.market.rank.repository;

import com.market.rank.domain.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<DeviceToken, DeviceToken.DeviceTokenId> {
}
