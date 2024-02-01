package com.brandol.dto.request;

import com.brandol.domain.mapping.MyItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public class MyItemRequestDto {
    @Getter
    public static class wearMyItemDto {
        @Schema(description = "구매한 아이템들 중 착용할 아이템의 id 리스트")
        //@NotNull
        private List<Long> wearingMyItemIdList; // 착용할 아이템 아이디 리스트
    }
}
