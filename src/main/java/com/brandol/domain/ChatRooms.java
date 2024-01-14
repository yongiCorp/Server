package com.brandol.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRooms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatrooms_id")
    private Long id;
    //사용자A
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userA")
    private Member userA;
    //사용자B
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userB")
    private Member userB;
}
