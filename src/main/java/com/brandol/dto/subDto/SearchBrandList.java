package com.brandol.dto.subDto;

import com.brandol.domain.Brand;
import com.brandol.domain.mapping.MemberBrandList;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SearchBrandList {

    private List<Brand> searchbrandList = new ArrayList<>();


    public static Map<String,Object> createsearchBrandList(List<Brand>searchbrandList){
        Map<String,Object> result = new LinkedHashMap<>();

        int arrayLen = 3;

        for(int i =0 ; i< arrayLen ; i++){
            String key = "searchbrand" + i;
            Map<String,Object> searchbrandData = new LinkedHashMap<>();
            Brand target = searchbrandList.get(i);
            searchbrandData.put("brand-id",target.getId());
            searchbrandData.put("brand-name",target.getName());
            searchbrandData.put("brand-profileimage",target.getProfileImage());
            searchbrandData.put("brand-description",target.getDescription());
            result.put(key,searchbrandData);
        }

        return result;
    }
}
