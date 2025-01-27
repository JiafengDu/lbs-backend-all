package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.attach.api.AttachApi;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.repository.AdminRepository;
import com.tarena.lbs.basic.web.repository.BusinessRepository;
import com.tarena.lbs.basic.web.repository.StoreRepository;
import com.tarena.lbs.basic.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.common.passport.enums.Roles;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.attach.param.PicUpdateParam;
import com.tarena.lbs.pojo.basic.param.StoreParam;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import com.tarena.lbs.pojo.basic.po.BusinessPO;
import com.tarena.lbs.pojo.basic.po.StorePO;
import com.tarena.lbs.pojo.basic.query.AdminQuery;
import com.tarena.lbs.pojo.basic.query.StoreQuery;
import com.tarena.lbs.pojo.basic.vo.AdminVO;
import com.tarena.lbs.pojo.basic.vo.StoreVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private BusinessRepository businessRepository;
    @DubboReference
    private AttachApi attachApi;

    public PageResult<StoreVO> pageList(StoreQuery query) throws BusinessException {

        UserPrinciple userPrinciple = getUserPrinciple();
        Roles role = userPrinciple.getRole();
        if (role==Roles.SHOP) {
            log.info("current role is shop, need to check businessId");
            AdminPO adminPO = adminRepository.getAdminById(userPrinciple.getId());
            Asserts.isTrue(adminPO==null, new BusinessException("-2","login error, jwt token invalid"));
            query.setBusinessId(adminPO.getBusinessId());
        }
        PageResult<StoreVO> voPage = doPageList(query);
        return voPage;
    }

    private UserPrinciple getUserPrinciple() throws BusinessException {
        UserPrinciple userPrinciple = AuthenticationContextUtils.getPrinciple();
        Asserts.isTrue(userPrinciple==null, new BusinessException("-2","login error, jwt token invalid"));
        return userPrinciple;
    }

    public PageResult<StoreVO> doPageList (StoreQuery query) {
        PageResult<StoreVO> voPages = new PageResult<>();
        PageInfo<StorePO> pageInfo = storeRepository.getPages(query);
        voPages.setPageNo(pageInfo.getPageNum());
        voPages.setPageSize(pageInfo.getPageSize());
        voPages.setTotal(pageInfo.getTotal());
        List<StoreVO> vos = null;
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            vos = pageInfo.getList().stream().map(po -> {
                StoreVO vo = new StoreVO();
                BeanUtils.copyProperties(po, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        voPages.setObjects(vos);
        return voPages;
    }

    public void save(StoreParam param) throws BusinessException {
        checkRole(Roles.SHOP);
        checkBusiness(param.getBusinessId());
        StorePO storePO = storeParam2PO(param);
        storeRepository.save(storePO); // TODO
        bindPicture(param, storePO.getId()); // TODO
    }

    private void bindPicture(StoreParam param, Integer id) {
        List<PicUpdateParam> picParams = new ArrayList<>();
        PicUpdateParam logoParam = new PicUpdateParam();
        logoParam.setBusinessId(id);
        logoParam.setBusinessType(300);
        logoParam.setId(Integer.valueOf(param.getStoreLogo()));
        picParams.add(logoParam);

        List<String> storeImagesIds = param.getStoreImagesIds();
        List<PicUpdateParam> storeImagesParams =
                storeImagesIds.stream().map(storeImageId -> {
                    PicUpdateParam storeImageParam = new PicUpdateParam();
                    storeImageParam.setBusinessType(400);
                    storeImageParam.setBusinessId(id);
                    storeImageParam.setId(Integer.valueOf(storeImageId));
                    return storeImageParam;
                }).collect(Collectors.toList());
        picParams.addAll(storeImagesParams);
        boolean result = attachApi.batchUpdateBusiness(picParams);
    }

    private StorePO storeParam2PO(StoreParam param) {
        StorePO storePO = new StorePO();
        BeanUtils.copyProperties(param, storePO);
        // status 0, createTime update now
        storePO.setStoreStatus(0);
        Date now = new Date();
        storePO.setCreateTime(now);
        storePO.setUpdateTime(now);
        // location
        storePO.setStoreLongitude(param.getLongitude());
        storePO.setStoreLatitude(param.getLatitude());
        // imageId List<String> po String = 1,2,3,4,5
        List<String> storeImagesIds = param.getStoreImagesIds();
        String imageIds = "";
        if (CollectionUtils.isNotEmpty(storeImagesIds)) {
            imageIds = String.join(",", storeImagesIds);
        }
        storePO.setStoreImagesId(imageIds);
        return storePO;
        }

    private void checkBusiness(Integer businessId) throws BusinessException {
        BusinessPO businessPO = businessRepository.getBusinessById(businessId);
        Asserts.isTrue(businessPO==null, new BusinessException("-2", "business not found"));
    }

    private void checkRole(Roles roles) throws BusinessException {
        UserPrinciple userPrinciple = AuthenticationContextUtils.getPrinciple();
        Asserts.isTrue(userPrinciple==null, new BusinessException("-2", "user authentication failed, no user info in request"));
        Roles loginRole = userPrinciple.getRole();
        Asserts.isTrue(loginRole.equals(roles), new BusinessException("-2", "user doesn't have the role to be authorized"));
    }
}
