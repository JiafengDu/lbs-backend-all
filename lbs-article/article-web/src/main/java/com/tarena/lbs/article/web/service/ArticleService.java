package com.tarena.lbs.article.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.article.web.repository.ArticleRepository;
import com.tarena.lbs.article.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.content.entity.ArticleSearchEntity;
import com.tarena.lbs.pojo.content.query.ArticleQuery;
import com.tarena.lbs.pojo.content.vo.ArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    public PageResult<ArticleVO> pageList(ArticleQuery articleQuery) throws BusinessException {
        //1.准备返回值
        PageResult<ArticleVO> voPage=new PageResult<>();
        //简化分页处理 避免仓储层查询结果 返回二次封装 pageNo=1 pageSize=10 total=100
        voPage.setPageNo(1);
        voPage.setPageSize(10);
        voPage.setTotal(100L);
        //2.把查询条件 在业务层做一次 拆分 source=1 和source=2 查询条件不同
        //复用同一个仓储层查询
        ArticleQuery repositoryQuery=new ArticleQuery();
        //3.根据source写分支
        Integer source = articleQuery.getSource();
        if (source==1){
            log.info("文章列表查询 source来源是 前台 query:{}",articleQuery);
            //TODO
            //手机端查询 query条件携带的数据 包括pageNo pageSize
            //包括业务条件2种 第一个就是 lat long地理位置 第二文章标签
        }else{
            log.info("文章列表查询 source来源是 后台 query:{}",articleQuery);
            //后台管理1 文章标题 2 文章状态 3 文章分类id 4 文章创建 start和end 5 文章所属的userId;
            //后台数据拷贝
            BeanUtils.copyProperties(articleQuery,repositoryQuery);
            //条件多封装一个 userId 认证对象
            UserPrinciple userPrinciple = AuthenticationContextUtils.get();
            Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
            repositoryQuery.setUserId(userPrinciple.getId());
        }
        //4.调用仓储层 仓储层里面使用RestHighLevelClient 返回满足条件的所有文章
        //没有连接数据库 连接的是 es 返回的结果 定义为entities
        List<ArticleSearchEntity> entities=articleRepository.searchArticles(repositoryQuery);
        //5.使用entities封装vos
        List<ArticleVO> vos=null;
        if (CollectionUtils.isNotEmpty(entities)){
            vos=entities.stream().map(entity->{
                ArticleVO vo=new ArticleVO();
                BeanUtils.copyProperties(entity,vo);
                return vo;
            }).collect(Collectors.toList());
        }
        voPage.setObjects(vos);
        return voPage;
    }
}
