package com.tarena.lbs.article.web.repository;

import com.tarena.lbs.pojo.content.entity.ArticleSearchEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleESRepository extends ElasticsearchRepository<ArticleSearchEntity,Integer> {
}
