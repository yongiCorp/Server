package com.brandol.service;

import com.brandol.aws.AmazonS3Manager;
import com.brandol.converter.SearchConverter;
import com.brandol.domain.*;
import com.brandol.dto.response.SearchResponseDto;
import com.brandol.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@ToString
public class SearchService {

    private final AmazonS3Manager s3Manager;

    private final BrandRepository brandRepository;
    private final MemberRepository memberRepository;
    private final ContentsRepository contentsRepository;
    private final ItemsRepository itemsRepository;
    private final ContentImageRepository contentImageRepository;
    private final MemberBrandRepository memberBrandRepository;


    //검색 메인 페이지
    public SearchResponseDto.SearchMainAllDto makeSearchMainPage(){



        // 브랜드 리스트
        List<Brand> searchmainbrandList = brandRepository.findByRandom();//0216 수정

        // 멤버 리스트
        List<Member> searchmainuserList = memberRepository.findThreeByRandom();

        // 컨텐츠 리스트
        List<Contents> searchmaincontentList = contentsRepository.findThreeByRandom();


        // 아바타스토어 리스트
        List<Items> searchmainitemList = itemsRepository.findThreeByRandom();





        List<SearchResponseDto.SearchMainBrandDto> searchMainBrandDtoList = new ArrayList<>();
        for(int i=0; i< searchmainbrandList.size();i++){
            SearchResponseDto.SearchMainBrandDto dto = SearchConverter.toSearchMainBrandDto(searchmainbrandList.get(i));
            searchMainBrandDtoList.add(dto);
        }

        List<SearchResponseDto.SearchMainUserDto> searchMainUserDtoList = new ArrayList<>();
        for(int i=0; i<searchmainuserList.size();i++){
            SearchResponseDto.SearchMainUserDto dto = SearchConverter.toSearchMainUserDto(searchmainuserList.get(i));
            searchMainUserDtoList.add(dto);
        }

        List<SearchResponseDto.SearchMainContentsDto> searchMainContentsDtoList = new ArrayList<>();
        for(int i=0; i<searchmaincontentList.size();i++){
            List<ContentsImage> searchcontentsImages = contentImageRepository.findAllByContentsId(searchmaincontentList.get(i).getId());
            List<String> searchcontentsImageUrlList = searchcontentsImages.stream().map(ContentsImage::getImage).collect(Collectors.toList());
            SearchResponseDto.SearchMainContentsDto dto = SearchConverter.toSearchMainContentsDto(searchmaincontentList.get(i),searchcontentsImageUrlList);
            searchMainContentsDtoList.add(dto);
        }

        List<SearchResponseDto.SearchMainAvatarStoreDto> searchMainAvatarStoreDtoList = new ArrayList<>();
        for(int i=0; i<searchmainitemList.size();i++){
            SearchResponseDto.SearchMainAvatarStoreDto dto = SearchConverter.toSearchMainAvatarStoreDto(searchmainitemList.get(i));
            searchMainAvatarStoreDtoList.add(dto);
        }






        return SearchConverter.tosearchMainAllDto(searchMainBrandDtoList,searchMainUserDtoList,searchMainContentsDtoList,searchMainAvatarStoreDtoList);


    }

    //검색 더보기 페이지 - 브랜드
    public SearchResponseDto.SearchDetailBrandAllDto makeSearchDetailBrandPage(){



        // 브랜드 리스트
        List<Brand> searchdetailbrandList = brandRepository.findByRandom();//0216 수정



        int recentSubscriberCount = memberBrandRepository.getRecentSubscriberCount();

        List<SearchResponseDto.SearchDetailBrandDto> search_Detail_Brand_DtoList = new ArrayList<>();
        for(int i=0; i< searchdetailbrandList.size();i++){
            SearchResponseDto.SearchDetailBrandDto dto = SearchConverter.toSearchDetailBrandDto(searchdetailbrandList.get(i),recentSubscriberCount );
            search_Detail_Brand_DtoList.add(dto);
        }




        return SearchConverter.tosearchDetailBrandAllDto(search_Detail_Brand_DtoList);


    }

    //검색 더보기 페이지 - 유저
    public SearchResponseDto.SearchDetailUserAllDto makeSearchDetailUserPage(){



        // 멤버 리스트
        List<Member> searchdetailuserList = memberRepository.findAllByRandom();





        List<SearchResponseDto.SearchDetailUserDto> search_Detail_User_DtoList = new ArrayList<>();
        for(int i=0; i< searchdetailuserList.size();i++){
            SearchResponseDto.SearchDetailUserDto dto = SearchConverter.toSearchDetailUserDto(searchdetailuserList.get(i));
            search_Detail_User_DtoList.add(dto);
        }




        return SearchConverter.tosearchDetailUserAllDto(search_Detail_User_DtoList);


    }

    //검색 더보기 페이지 - 컨텐츠
    public SearchResponseDto.SearchDetailContentsAllDto makeSearchDetailContentsPage(){


        // 컨텐츠 리스트
        List<Contents> searchdetailcontentList = contentsRepository.findAllByRandom();





        List<SearchResponseDto.SearchDetailContentsDto> search_Detail_Contents_DtoList = new ArrayList<>();
        for(int i=0; i< searchdetailcontentList.size();i++){
            List<ContentsImage> searchdetailcontentsImages = contentImageRepository.findAllByContentsId(searchdetailcontentList.get(i).getId());
            List<String> searchdetailcontentsImageUrlList = searchdetailcontentsImages.stream().map(ContentsImage::getImage).collect(Collectors.toList());
            SearchResponseDto.SearchDetailContentsDto dto = SearchConverter.toSearchDetailContentsDto(searchdetailcontentList.get(i),searchdetailcontentsImageUrlList);
            search_Detail_Contents_DtoList.add(dto);
        }




        return SearchConverter.tosearchDetailContentsAllDto(search_Detail_Contents_DtoList);


    }

    //검색 더보기 페이지 - 아바타 스토어 헤더
    public SearchResponseDto.SearchDetailAvatarStoreHeaderDto makeSearchDetailAvatarStoreHeaderPage(Long memberId){

        Member targetMember = memberRepository.findOneById(memberId);

        return SearchConverter.toSearchDetailAvatarStoreHeaderDto(targetMember);


    }

    //검색 더보기 페이지 - 아바타 스토어 바디
    public SearchResponseDto.Search_Detail_AvatarStore_Body_All_Dto makeSearchDetailAvatarStoreBodyPage(String itemPart){



        if(itemPart.equals("전체")){
            // 전체 아바타 스토어 리스트
            List<Items> search_detail_total_item_List = itemsRepository.findTotalByRandom();

            List<SearchResponseDto.SearchDetailAvatarStoreBodyDto> search_Detail_AvatarStore_Body_Total_DtoList = new ArrayList<>();
            for(int i=0; i< search_detail_total_item_List.size();i++){
                SearchResponseDto.SearchDetailAvatarStoreBodyDto dto = SearchConverter.toSearchDetailAvatarStoreBodyDto(search_detail_total_item_List.get(i));
                search_Detail_AvatarStore_Body_Total_DtoList.add(dto);
            }

            return SearchConverter.tosearchDetailAvatarStoreBodyAllDto(search_Detail_AvatarStore_Body_Total_DtoList);
        }



        // 종류별 아바타 스토어 리스트
        List<Items> search_detail_itempart_item_List = itemsRepository.finditemPartByRandom(itemPart);


        List<SearchResponseDto.SearchDetailAvatarStoreBodyDto> search_Detail_AvatarStore_Body_DtoList = new ArrayList<>();
        for(int i=0; i< search_detail_itempart_item_List.size();i++){
            SearchResponseDto.SearchDetailAvatarStoreBodyDto dto = SearchConverter.toSearchDetailAvatarStoreBodyDto(search_detail_itempart_item_List.get(i));
            search_Detail_AvatarStore_Body_DtoList.add(dto);
        }


        return SearchConverter.tosearchDetailAvatarStoreBodyAllDto(search_Detail_AvatarStore_Body_DtoList);


    }

    //직접 검색
    public SearchResponseDto.SearchMainAllDto searchText(String searchContents){



        // 브랜드 리스트
        List<Brand> searchmainbrandList = brandRepository.findByNameContaining(searchContents);

        // 멤버 리스트
        List<Member> searchmainuserList = memberRepository.findByNameContaining(searchContents);

        // 컨텐츠 리스트
        List<Contents> searchmaincontentList = contentsRepository.findByTitleContaining(searchContents);


        // 아바타스토어 리스트
        List<Items> searchmainitemList = itemsRepository.findByNameContaining(searchContents);





        List<SearchResponseDto.SearchMainBrandDto> searchMainBrandDtoList = new ArrayList<>();
        for(int i=0; i< searchmainbrandList.size();i++){
            SearchResponseDto.SearchMainBrandDto dto = SearchConverter.toSearchMainBrandDto(searchmainbrandList.get(i));
            searchMainBrandDtoList.add(dto);
        }

        List<SearchResponseDto.SearchMainUserDto> searchMainUserDtoList = new ArrayList<>();
        for(int i=0; i<searchmainuserList.size();i++){
            SearchResponseDto.SearchMainUserDto dto = SearchConverter.toSearchMainUserDto(searchmainuserList.get(i));
            searchMainUserDtoList.add(dto);
        }

        List<SearchResponseDto.SearchMainContentsDto> searchMainContentsDtoList = new ArrayList<>();
        for(int i=0; i<searchmaincontentList.size();i++){
            List<ContentsImage> searchcontentsImages = contentImageRepository.findAllByContentsId(searchmaincontentList.get(i).getId());
            List<String> searchcontentsImageUrlList = searchcontentsImages.stream().map(ContentsImage::getImage).collect(Collectors.toList());
            SearchResponseDto.SearchMainContentsDto dto = SearchConverter.toSearchMainContentsDto(searchmaincontentList.get(i),searchcontentsImageUrlList);
            searchMainContentsDtoList.add(dto);
        }

        List<SearchResponseDto.SearchMainAvatarStoreDto> searchMainAvatarStoreDtoList = new ArrayList<>();
        for(int i=0; i<searchmainitemList.size();i++){
            SearchResponseDto.SearchMainAvatarStoreDto dto = SearchConverter.toSearchMainAvatarStoreDto(searchmainitemList.get(i));
            searchMainAvatarStoreDtoList.add(dto);
        }






        return SearchConverter.tosearchMainAllDto(searchMainBrandDtoList,searchMainUserDtoList,searchMainContentsDtoList,searchMainAvatarStoreDtoList);


    }




}
