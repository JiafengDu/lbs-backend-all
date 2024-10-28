package com.tarena.lbs.article.web.repository;

import com.tarena.lbs.pojo.content.entity.ActionSearchEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ActionESRepository extends ElasticsearchRepository<ActionSearchEntity,String> {
}
