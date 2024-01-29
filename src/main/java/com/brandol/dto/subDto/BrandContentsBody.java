package com.brandol.dto.subDto;

import com.brandol.domain.Contents;
import com.brandol.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandContentsBody {
    List<Contents> cardNewsList = new ArrayList<>();
    List<Contents> eventsList = new ArrayList<>();
    List<Contents> videoList = new ArrayList<>();

    public static Map<String,Object> makeCotentsBody(
                                                     List<Contents> eventsList,
                                                     List<Contents> cardNewsList,
                                                     List<Contents> videoList,
                                                     Map<Integer,Object> eventImages,
                                                     Map<Integer,Object> cardNewsImages,
                                                     Map<Integer,Object> videoImages,
                                                     Member admin
                                                     ){
        Map<String,Object> result = new LinkedHashMap<>();

        Map<String,Object> cardNews = new LinkedHashMap<>();
        for(int i=0; i< cardNewsList.size();i++){
            Map<String,Object> cardNewsData= new LinkedHashMap<>();
            String key = "card-news" + i;
            cardNewsData.put("writer-id",admin.getId());
            cardNewsData.put("writer-name",admin.getName());
            cardNewsData.put("id",cardNewsList.get(i).getId());
            cardNewsData.put("title",cardNewsList.get(i).getTitle());
            cardNewsData.put("content",cardNewsList.get(i).getContent());
            cardNewsData.put("images",cardNewsImages.get(i));
            cardNewsData.put("likes",cardNewsList.get(i).getLikes());
            cardNewsData.put("comments",cardNewsList.get(i).getComments());
            cardNewsData.put("written-date",cardNewsList.get(i).getCreatedAt());
            cardNews.put(key,cardNewsData);
        }

        Map<String,Object> event = new LinkedHashMap<>();
        for(int i=0; i< eventsList.size();i++){
            Map<String,Object> eventData= new LinkedHashMap<>();
            String key = "card-news" + i;
            eventData.put("writer-id",admin.getId());
            eventData.put("writer-name",admin.getName());
            eventData.put("id",eventsList.get(i).getId());
            eventData.put("title",eventsList.get(i).getTitle());
            eventData.put("content",eventsList.get(i).getContent());
            eventData.put("images",eventImages.get(i));
            eventData.put("likes",eventsList.get(i).getLikes());
            eventData.put("comments",eventsList.get(i).getComments());
            eventData.put("written-date",eventsList.get(i).getCreatedAt());
            event.put(key,eventData);
        }

        Map<String,Object> video = new LinkedHashMap<>();
        for(int i=0; i< eventsList.size();i++){
            Map<String,Object> videoData= new LinkedHashMap<>();
            String key = "card-news" + i;
            videoData.put("writer-id",admin.getId());
            videoData.put("writer-name",admin.getName());
            videoData.put("id",videoList.get(i).getId());
            videoData.put("title",videoList.get(i).getTitle());
            videoData.put("content",videoList.get(i).getContent());
            videoData.put("video-URL",videoList.get(i).getFile());
            videoData.put("images",videoImages.get(i));
            videoData.put("likes",videoList.get(i).getLikes());
            videoData.put("comments",videoList.get(i).getComments());
            videoData.put("written-date",videoList.get(i).getCreatedAt());
            video.put(key,videoData);
        }

        result.put("card-news",cardNews);
        result.put("event",event);
        result.put("video",video);

        return result;
    }
}
