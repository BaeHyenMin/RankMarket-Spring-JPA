package com.market.rank.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_messages")
public class ChatMessages implements Persistable<ChatMessages.ChatMessageId> {
    @EmbeddedId
    private ChatMessageId chatMessageId;
    private String msg;

    @UpdateTimestamp
    private Date creDtm;

    @Nullable
    @Override
    public ChatMessageId getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Embeddable
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatMessageId implements Serializable{

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "usr_id")
        private Usr usr;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "chat_id")
        private ChatRoom chatRoom;
    }
}
