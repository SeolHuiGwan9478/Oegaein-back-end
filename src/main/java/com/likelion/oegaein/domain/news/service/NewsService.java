package com.likelion.oegaein.domain.news.service;

import com.likelion.oegaein.domain.news.dto.CreateNewsData;
import com.likelion.oegaein.domain.news.dto.CreateNewsRequest;
import com.likelion.oegaein.domain.news.dto.FindNewsData;
import com.likelion.oegaein.domain.news.dto.FindNewsResponse;
import com.likelion.oegaein.domain.news.entity.News;
import com.likelion.oegaein.domain.news.repository.NewsRepository;
import com.likelion.oegaein.domain.news.repository.query.NewsQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsQueryRepository newsQueryRepository;

    public FindNewsResponse findNews(){
        List<News> news = newsRepository.findAllByOrderByCreatedAtDesc();
        List<FindNewsData> findNewsData = news.stream()
                .map(FindNewsData::toFindNewsData)
                .toList();
        return new FindNewsResponse(findNewsData);
    }

    @Transactional
    public void createNewsAll(CreateNewsRequest dto){
        List<CreateNewsData> newsDatas = dto.getData();
        List<News> news = newsDatas.stream()
                .map((newsData) -> new News(
                        newsData.getTitle(),
                        newsData.getUrl(),
                        newsData.getCreatedAt())
                ).toList();
        newsQueryRepository.saveBulk(news);
    }

    @Transactional
    public void removeNewsAll(){
        newsRepository.deleteAllInBatch();
    }
}