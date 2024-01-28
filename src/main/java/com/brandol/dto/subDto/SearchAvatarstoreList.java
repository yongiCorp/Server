package com.brandol.dto.subDto;

import com.brandol.domain.Brand;
import com.brandol.domain.Items;
import com.brandol.domain.mapping.MemberBrandList;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SearchAvatarstoreList {
    private List<Items> searchavatarstorelist = new ArrayList<>();


    public static Map<String,Object> createsearchavatarstorelist(List<Items>searchavatarstorelist){
        Map<String,Object> result = new LinkedHashMap<>();

        int arrayLen = 3;

        for(int i =0 ; i< arrayLen ; i++){
            String key = "searchavatarstore" + i;
            Map<String,Object> searchavatarstoreData = new LinkedHashMap<>();
            Items target = searchavatarstorelist.get(i);
            searchavatarstoreData.put("items-id",target.getId());
            searchavatarstoreData.put("items-name",target.getName());
            searchavatarstoreData.put("items-itempart",target.getItemPart());
            searchavatarstoreData.put("brand-name",target.getBrand().getName());
            searchavatarstoreData.put("items-image",target.getImage());
            searchavatarstoreData.put("items-description",target.getDescription());
            result.put(key,searchavatarstoreData);
        }

        return result;
    }
}
