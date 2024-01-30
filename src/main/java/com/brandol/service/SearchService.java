package com.brandol.service;

import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import com.brandol.domain.Items;
import com.brandol.domain.Member;
import com.brandol.dto.response.SearchMainResponseDto;
import com.brandol.dto.subDto.SearchMainAvatarstoreList;
import com.brandol.dto.subDto.SearchMainBrandList;
import com.brandol.dto.subDto.SearchMainContentsList;
import com.brandol.dto.subDto.SearchMainUserList;
import com.brandol.repository.SearchAvatarstoreRepository;
import com.brandol.repository.SearchBrandRepository;
import com.brandol.repository.SearchContentsRepository;
import com.brandol.repository.SearchUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@ToString
public class SearchService {

    private final SearchBrandRepository sbr;
    private final SearchUserRepository sur;
    private final SearchContentsRepository scr;
    private final SearchAvatarstoreRepository sar;


    @Transactional
    public SearchMainResponseDto makeSearchpage(){



        // 브랜드 리스트
        List<Brand> searchbrandList = sbr.findThreeByRandom();
        Map<String,Object> brandList = SearchMainBrandList.createsearchBrandList(searchbrandList);

        // 멤버 리스트
        List<Member> searchuserList = sur.findThreeByRandom();
        Map<String,Object> memberList = SearchMainUserList.createsearchuserList(searchuserList);

        // 컨텐츠 리스트
        Map<String,Object> contentList = new LinkedHashMap<>();
        List<Contents> targetContentsList = scr.findAll();
        List<Member> targetMemberList = sur.findAll();
        List<Brand> targetBrandList = sbr.findAll();
        for(int i = 0; i < 3; i++) {
            contentList.put("searchcontents" + i
                    , SearchMainContentsList.createsearchcontentsList(targetContentsList.get(i)
                    , scr.countLikesByContents_id(targetContentsList.get(i).getId())
                    , scr.countCommentsByContents_id(targetContentsList.get(i).getId())
                    ,targetBrandList.get(i)
                    ,targetMemberList.get(i)
                    ));



        }



        // 아바타스토어 리스트
        List<Items> searchavatarstoreList = sar.findThreeByRandom();
        Map<String,Object> avatarstoreList = SearchMainAvatarstoreList.createsearchavatarstorelist(searchavatarstoreList);

        return SearchMainResponseDto.mainPage(brandList,memberList,contentList,avatarstoreList);



    }


}
