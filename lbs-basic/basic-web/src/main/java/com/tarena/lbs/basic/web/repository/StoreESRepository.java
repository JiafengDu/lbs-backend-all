package com.tarena.lbs.basic.web.repository;

import com.tarena.lbs.pojo.basic.entity.StoreSearchEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StoreESRepository extends ElasticsearchRepository<StoreSearchEntity,Integer> {
}
