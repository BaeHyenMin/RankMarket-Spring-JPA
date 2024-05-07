package com.market.rank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatMessages is a Querydsl query type for ChatMessages
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatMessages extends EntityPathBase<ChatMessages> {

    private static final long serialVersionUID = 293511697L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatMessages chatMessages = new QChatMessages("chatMessages");

    public final QChatMessages_ChatMessageId chatMessageId;

    public final DateTimePath<java.util.Date> creDtm = createDateTime("creDtm", java.util.Date.class);

    public final StringPath msg = createString("msg");

    public QChatMessages(String variable) {
        this(ChatMessages.class, forVariable(variable), INITS);
    }

    public QChatMessages(Path<? extends ChatMessages> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatMessages(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatMessages(PathMetadata metadata, PathInits inits) {
        this(ChatMessages.class, metadata, inits);
    }

    public QChatMessages(Class<? extends ChatMessages> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatMessageId = inits.isInitialized("chatMessageId") ? new QChatMessages_ChatMessageId(forProperty("chatMessageId"), inits.get("chatMessageId")) : null;
    }

}

