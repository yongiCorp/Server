package com.brandol.service;

import com.brandol.domain.PointHistory;
import com.brandol.domain.enums.HistoryType;
import com.brandol.domain.mapping.MemberMission;
import com.brandol.domain.mapping.MyItem;
import com.brandol.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointHistoryService {
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public void makePurchaseHistory(MyItem myItem) {
        PointHistory pointHistory = PointHistory.builder()
                .amount(-myItem.getItems().getPrice())
                .history(HistoryType.ITEM)
                .member(myItem.getMember())
                .build();
        pointHistoryRepository.save(pointHistory);
    }

    @Transactional
    public void makeSuccessHistory(MemberMission memberMission) {
        PointHistory pointHistory = PointHistory.builder()
                .member(memberMission.getMember())
                .history(HistoryType.MISSION)
                .amount(memberMission.getMission().getPoints())
                .build();
        pointHistoryRepository.save(pointHistory);
    }

    public List<PointHistory> getPointHistory(Long memberId){
        return pointHistoryRepository.findAllByMemberIdOrderByCreatedAt(memberId);
    }
}
