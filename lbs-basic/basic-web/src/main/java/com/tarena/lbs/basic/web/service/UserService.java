package com.tarena.lbs.basic.web.service;

import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.basic.web.repository.UserRepository;
import com.tarena.lbs.common.basic.util.RandomUserName;
import com.tarena.lbs.pojo.basic.param.UserParam;
import com.tarena.lbs.pojo.basic.po.UserPO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void register(UserParam param) throws BusinessException {
        //1.先查询 手机号 判断之后再新增 开启redis分布式锁 锁住该电话号码 让多个客户端同时注册一个电话号码的时候
        //只有一个线程可以进入业务流程其他都屏蔽 或者等待
        String phone = param.getPhone();
        //查询是否存在
        Long count=userRepository.countUserByPhone(phone);
        Asserts.isTrue(count>0,new BusinessException("-2","手机号已经存在"));
        //2.封装数据
        UserPO poParam=assembleUserPO(param);
        //3.直接写入数据库
        userRepository.save(poParam);
    }

    private UserPO assembleUserPO(UserParam param) {
        UserPO po=new UserPO();
        BeanUtils.copyProperties(param,po);
        //补充必要数据 用户状态 0表示没有绑定标签 1 表示绑定了标签
        po.setStatus(0);
        //用户昵称 注册时候 不是用户填写的 为了快速注册 我们随机生成一个
        String randomName = RandomUserName.createRandomName();//老 大 小  张王李赵
        po.setNickName(randomName);
        po.setUserName(randomName);
        //注册时间
        po.setRegTime(new Date());
        return po;
    }
}
