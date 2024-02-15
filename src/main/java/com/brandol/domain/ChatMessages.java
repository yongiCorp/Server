package com.brandol.domain;

import com.brandol.domain.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatMessages extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chat_messages_id")
    private Long id;



    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;





    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_rooms_id",nullable = false)
    private ChatRooms chatRooms;


    @Column(name = "sender_id",nullable = false)
    private Long sender;


    @Column(name = "sended_id",nullable = false)
    private Long sended_id;

    @ColumnDefault("false")
    private boolean isRead; // 읽음표시





    @Column(name="send_time", nullable = false)
    private String sendTime;



}
