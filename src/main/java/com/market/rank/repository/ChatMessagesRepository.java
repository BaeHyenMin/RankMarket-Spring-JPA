package com.market.rank.repository;

import com.market.rank.domain.ChatMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessagesRepository extends JpaRepository<ChatMessages, ChatMessages.ChatMessageId> {
}
