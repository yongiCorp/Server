package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.domain.enums.MemberListStatus;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@ToString
public class MemberBrandService {

    private final MemberRepository memberRepository;
    private final BrandRepository brandRepository;
    private final ContentsRepository contentsRepository;
    private final MemberBrandRepository memberBrandRepository;

    @Transactional
    public MemberBrandList MemberBrandListStatusToUnsubscribed(Long memberId,Long brandId){

        List<MemberBrandList> searchResult = memberBrandRepository.findOneByMemberIdAndBrandId(memberId,brandId);
        int size = searchResult.size();
        if(size >1 || size ==0 ){
            if(size >1){throw  new ErrorHandler(ErrorStatus._DUPLICATE_DATABASE_ERROR);} //중복 조회 케이스
            throw new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER_BRAND_LIST); //조회 실패
        }
        MemberBrandList target = searchResult.get(0);
        target.changeMemberListStatus(MemberListStatus.UNSUBSCRIBED); //더티 체킹

        return target;

    }
}
