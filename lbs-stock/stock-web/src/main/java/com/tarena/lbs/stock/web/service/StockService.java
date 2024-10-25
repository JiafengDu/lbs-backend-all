package com.tarena.lbs.stock.web.service;

import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.pojo.stock.param.CouponStockParam;
import com.tarena.lbs.pojo.stock.po.CouponStockPO;
import com.tarena.lbs.stock.web.repository.StockRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public Boolean initStock(CouponStockParam param) throws BusinessException {
        //封装一下po 写入数据库 如果考虑幂等 一个couponId只能存在一个 stock库存数据
        //先查 在增 外部包裹一个锁
        Long count=stockRepository.countStockByCouponId(param.getCouponId());
        Asserts.isTrue(count>0,new BusinessException("-2","库存已经初始化过了"));
        //没有库存 组织一个poparam数据 写入数据库
        CouponStockPO poParam=new CouponStockPO();
        BeanUtils.copyProperties(param,poParam);
        //状态 时间 createAt updateAt
        poParam.setCreateAt(new Date());
        poParam.setUpdateAt(new Date());//不太严谨
        int result=stockRepository.save(poParam);
        //mysql每次写操作 都会反馈写操作影响的行数 如果是新增 增几条就返回几 如果增一条成了就返回1 败了没异常 返回0
        //mybatis框架是可以在增删改的方法上 接收返回值
        return result>0;
    }

    public Integer getCouponStock(Integer couponId) {
        return stockRepository.getCouponNum(couponId);
    }

    public Boolean reduceStock(Integer couponId, Integer num) {
        int row=stockRepository.updateNumByCouponId(couponId,num);
        return row>0;//大于0 数据库执行更新成功 等于0 数据库执行更新失败
    }
}
