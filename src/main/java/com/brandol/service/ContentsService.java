package com.brandol.service;

import com.brandol.domain.Contents;
import com.brandol.repository.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentsService {

    private final ContentsRepository contentsRepository;

    public List<Contents> findRecentEvents(int cnt){//최근 등록 콘텐츠 cnt개를 가져오는 함수
        return contentsRepository.findRecentEvents(cnt);
    }
}
