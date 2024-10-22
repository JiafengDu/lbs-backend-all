package com.tarena.lbs.article.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.article.web.repository.ArticleRepository;
import com.tarena.lbs.article.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.common.content.utils.SequenceGenerator;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.content.entity.ArticleSearchEntity;
import com.tarena.lbs.pojo.content.param.ArticleContentParam;
import com.tarena.lbs.pojo.content.query.ArticleQuery;
import com.tarena.lbs.pojo.content.vo.ArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
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
            //入参的标签
            repositoryQuery.setArticleLabel(articleQuery.getArticleLabel());
            //地理位置
            repositoryQuery.setLongitude(articleQuery.getLongitude());
            repositoryQuery.setLatitude(articleQuery.getLatitude());
            //前端要查询的是 status=1
            repositoryQuery.setArticleStatus(CollectionUtils.list(1));
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

    public void addArticle(ArticleContentParam param) throws BusinessException {
        //1.根据param 封装 ArticleSearchEntity(对应es的持久化对象)
        ArticleSearchEntity entity=assembleArticleEntity(param);
        //2.调用仓储层 写入数据 写入到ES中
        articleRepository.save(entity);
        //3.使用文章的id 绑定文章上传的封面和内容图片 文章封面是500 内容600
        bindPictures(entity.getId(),param);
    }

    private ArticleSearchEntity assembleArticleEntity(ArticleContentParam param) throws BusinessException {
        //能拷贝的全拷贝 然后在考虑其他没封装的属性
        ArticleSearchEntity entity=new ArticleSearchEntity();
        BeanUtils.copyProperties(param,entity);
        //1.userId 当前文章的创建人 需要记录
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
        entity.setUserId(userPrinciple.getId());
        //2.文章id是Integer(可以String 可以Long 之所以是integer是为了绑定图片使用)
        entity.setId(SequenceGenerator.sequence());
        //3.s时间
        Date now=new Date();
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        //4.location latitude longitude 合并成一个geo_point字符串location
        entity.setLocation(param.getLatitude()+","+param.getLongitude());
        //5.状态 前端提交 status=1上线 保存 status=0 如果status==null 默认1
        if (param.getArticleStatus()==null){
            entity.setArticleStatus(1);
        }
        //6.文章埋点数据 默认都是0 但是后续将这个数据写入到 redis中
        entity.setAccessCount(0);
        entity.setLikeCount(0);
        entity.setFavoriteCount(0);
        log.info("创建文章入参:{},封装持久化对象:{}",param,entity);
        return entity;
    }

    private void bindPictures(Integer id, ArticleContentParam param) {
        //UNDO 图片的常规业务绑定
    }

    public Set<String> articleLabels(ArticleQuery articleQuery) throws BusinessException {
        //没有复杂业务逻辑,直接调用仓储层
        return articleRepository.getArticleLabels(articleQuery);
    }
}
