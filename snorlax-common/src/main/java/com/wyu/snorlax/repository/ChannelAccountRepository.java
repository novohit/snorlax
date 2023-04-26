package com.wyu.snorlax.repository;

import com.wyu.snorlax.model.ChannelAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author novo
 * @since 2023-04-26
 */
public interface ChannelAccountRepository extends JpaRepository<ChannelAccount, Long> {
}
