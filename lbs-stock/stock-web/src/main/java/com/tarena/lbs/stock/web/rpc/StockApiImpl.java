package com.tarena.lbs.stock.web.rpc;

import com.tarena.lbs.pojo.stock.param.CouponStockParam;
import com.tarena.lbs.stock.api.StockApi;
import com.tarena.lbs.stock.web.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@DubboService
@Slf4j
public class StockApiImpl implements StockApi {
    @Autowired
    private StockService stockService;
    @Override
    public Boolean initCouponStock(CouponStockParam param) {
        //作为服务与服务之间的通信接口方法 方法内执行时出现的异常
        //不应该由调用者处理 应该由执行者处理
        try{
            return stockService.initStock(param);
        }catch(Exception e){
            log.error("当前库存执行初始化失败",e);
        }
        return false;
    }
}
