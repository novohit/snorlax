package com.wyu.snorlax.repository;

import com.wyu.snorlax.model.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author novo
 * @since 2023-04-12
 */
public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {
}
