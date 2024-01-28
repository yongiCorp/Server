package com.brandol.dto.subDto;

import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import com.brandol.domain.Member;
import com.brandol.domain.mapping.MemberBrandList;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SearchUserList {
    private List<Contents> searchuserList = new ArrayList<>();


    public static Map<String,Object> createsearchuserList(List<Member>searchuserList){
        Map<String,Object> result = new LinkedHashMap<>();

        int arrayLen =3;

        for(int i =0 ; i< arrayLen ; i++){
            String key = "searchuser" + i;
            Map<String,Object> searchuserData = new LinkedHashMap<>();
            Member target = searchuserList.get(i);
            searchuserData.put("user-id",target.getId());
            searchuserData.put("user-name",target.getName());
            searchuserData.put("user-avatar",target.getAvatar());
            result.put(key,searchuserData);
        }

        return result;
    }
}
