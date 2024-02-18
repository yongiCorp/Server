package com.brandol.repository;

import com.brandol.domain.ChatMessages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessages,Long> {
}
