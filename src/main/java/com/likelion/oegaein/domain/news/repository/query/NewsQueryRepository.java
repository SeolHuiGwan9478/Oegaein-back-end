package com.likelion.oegaein.domain.news.repository.query;

import com.likelion.oegaein.domain.news.entity.News;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NewsQueryRepository {
    private final JdbcTemplate jdbcTemplate;
    public void saveBulk(List<News> news){
        jdbcTemplate.batchUpdate("insert into news(title, url, created_at)" +
                        " values(?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, news.get(i).getTitle());
                        ps.setString(2, news.get(i).getUrl());
                        ps.setDate(3, Date.valueOf(news.get(i).getCreatedAt()));
                    }

                    @Override
                    public int getBatchSize() {
                        return news.size();
                    }
                });
    }
}
