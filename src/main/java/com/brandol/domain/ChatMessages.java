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
    @Column(name="chatmessages_id")
    private Long id;

    //보낸 사람Id
    @Column(nullable = false)
    private Long sender;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chatrooms_id", nullable = false)
    private ChatRooms chatRooms;

    @Column(nullable = false)//읽음표시추가
    @ColumnDefault("0")
    private boolean isRead;
}
