package com.market.rank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatMessages_ChatMessageId is a Querydsl query type for ChatMessageId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QChatMessages_ChatMessageId extends BeanPath<ChatMessages.ChatMessageId> {

    private static final long serialVersionUID = -844526131L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatMessages_ChatMessageId chatMessageId = new QChatMessages_ChatMessageId("chatMessageId");

    public final QChatRoom chatRoom;

    public final QUsr usr;

    public QChatMessages_ChatMessageId(String variable) {
        this(ChatMessages.ChatMessageId.class, forVariable(variable), INITS);
    }

    public QChatMessages_ChatMessageId(Path<? extends ChatMessages.ChatMessageId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatMessages_ChatMessageId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatMessages_ChatMessageId(PathMetadata metadata, PathInits inits) {
        this(ChatMessages.ChatMessageId.class, metadata, inits);
    }

    public QChatMessages_ChatMessageId(Class<? extends ChatMessages.ChatMessageId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new QChatRoom(forProperty("chatRoom"), inits.get("chatRoom")) : null;
        this.usr = inits.isInitialized("usr") ? new QUsr(forProperty("usr")) : null;
    }

}

