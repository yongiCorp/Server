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
    @Transactional(readOnly = true)
    public List<Contents> findRecentEvents(int cnt){//최근 등록 콘텐츠 cnt개를 가져오는 함수
        List<Contents> contents = contentsRepository.findRecentBrands(cnt);
        if(contents.isEmpty()){throw  new RuntimeException("콘텐츠 조회에 실패하였습니다.");}
        return contents;
    }
}
