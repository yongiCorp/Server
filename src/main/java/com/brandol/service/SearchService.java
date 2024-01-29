package com.brandol.service;

import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import com.brandol.domain.Items;
import com.brandol.domain.Member;
import com.brandol.dto.response.SearchResponse;
import com.brandol.dto.subDto.SearchAvatarstoreList;
import com.brandol.dto.subDto.SearchBrandList;
import com.brandol.dto.subDto.SearchContentsList;
import com.brandol.dto.subDto.SearchUserList;
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
    public SearchResponse Makesearchpage(){



        // 브랜드 리스트
        List<Brand> searchbrandList = sbr.findThreeByRandom();
        Map<String,Object> brandList = SearchBrandList.createsearchBrandList(searchbrandList);

        // 멤버 리스트
        List<Member> searchuserList = sur.findThreeByRandom();
        Map<String,Object> memberList = SearchUserList.createsearchuserList(searchuserList);

        // 컨텐츠 리스트
        Map<String,Object> contentList = new LinkedHashMap<>();
        List<Contents> targetContentsList = scr.findAll();
        for(int i = 0; i < 3; i++) {
            contentList.put("searchcontents" + i
                    , SearchContentsList.createsearchcontentsList(targetContentsList.get(i)
                    , scr.countLikesByContents_id(targetContentsList.get(i).getId())
                    , scr.countCommentsByContents_id(targetContentsList.get(i).getId())
                    ));

        }



        // 아바타스토어 리스트
        List<Items> searchavatarstoreList = sar.findThreeByRandom();
        Map<String,Object> avatarstoreList = SearchAvatarstoreList.createsearchavatarstorelist(searchavatarstoreList);

        return SearchResponse.MainPage(brandList,memberList,contentList,avatarstoreList);



    }


}
