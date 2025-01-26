package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.attach.api.AttachApi;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.constant.BusinessTypes;
import com.tarena.lbs.basic.web.repository.AdminRepository;
import com.tarena.lbs.basic.web.repository.BusinessRepository;
import com.tarena.lbs.basic.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.common.passport.enums.Roles;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.attach.param.PicUpdateParam;
import com.tarena.lbs.pojo.basic.param.BusinessParam;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import com.tarena.lbs.pojo.basic.po.BusinessPO;
import com.tarena.lbs.pojo.basic.query.BusinessQuery;
import com.tarena.lbs.pojo.basic.vo.BusinessVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BusinessService {
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private AdminRepository adminRepository;;
    @DubboReference
    private AttachApi attachApi;

    public PageResult<BusinessVO> pageList(BusinessQuery query) throws BusinessException {
        UserPrinciple userPrinciple = getUserPrinciple();
        Roles role = userPrinciple.getRole();
        if (role == Roles.SHOP) {
            Integer adminId = userPrinciple.getId();
            AdminPO adminPO = adminRepository.getAdminById(adminId);
            Asserts.isTrue(adminPO==null, new BusinessException("-2", "business doesn't exist anymore"));
            query.setBusinessId(adminPO.getBusinessId());
        }
        return doPageList(query);
    }

    private UserPrinciple getUserPrinciple() throws BusinessException {
        UserPrinciple userPrinciple = AuthenticationContextUtils.getPrinciple();
        Asserts.isTrue(userPrinciple==null, new BusinessException("-2", "user authentication failed"));
        return userPrinciple;
    }

    public PageResult<BusinessVO> doPageList(BusinessQuery query) {
        // 1. initialize voPages
        PageResult<BusinessVO> voPages = new PageResult<>();
        // 2. use PageHelper
        PageInfo<BusinessPO> pageInfo = businessRepository.getPages(query);
        // 3. set voPages
        voPages.setPageNo(pageInfo.getPageNum());
        voPages.setPageSize(pageInfo.getPageSize());
        voPages.setTotal(pageInfo.getTotal());
        // 4. pos need to nonempty, then convert to vo
        List<BusinessVO> vos = null;
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            vos = pageInfo.getList().stream().map(po -> {
                BusinessVO vo = new BusinessVO();
                BeanUtils.copyProperties(po, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        voPages.setObjects(vos);
        return voPages;
        // Deprecated
//        voPages.setPageNo(query.getPageNo());
//        voPages.setPageSize(query.getPageSize());
//        // 2. voPages need 5 properties: total, objects, pageNo, pageSize, totalPages
//        // totalPage is calculated = total%pageSize==0?total/pageSize:total/pageSize+1
//        // 2.1 get total number, select count(*) from lbs_business
//        Long total = businessRepository.count(query);
//        // 2.2 get page objects, select * from lbs_business limit from, size
//        List<BusinessPO> pos = businessRepository.getBusinessByPage(query);
//        // 3 convert PO to VO
//        List<BusinessVO> vos = null;
//        if (CollectionUtils.isNotEmpty(pos)) {
//            // use stream() api (better than for loop)
//            vos = pos.stream().map(po -> {
//                BusinessVO vo = new BusinessVO();
//                BeanUtils.copyProperties(po, vo);
//                return vo;
//            }).collect(Collectors.toList());
//        }
//        voPages.setTotal(total);
//        voPages.setObjects(vos);
//        return voPages;
    }
    @Transactional(rollbackFor=Exception.class)
    public void save(BusinessParam param) throws BusinessException {
        checkRole(Roles.ADMIN);
        Integer id = saveBusiness(param);
        bindPictures(id, param);
    }

    private void bindPictures(Integer id, BusinessParam param) throws BusinessException {
        List<PicUpdateParam> picParams = new ArrayList<>();

        PicUpdateParam licenseParam = new PicUpdateParam();
        licenseParam.setBusinessType(BusinessTypes.BIZ_LICENSE);
        licenseParam.setBusinessId(id);
        String licenseUrl = param.getBusinessLicense();
        licenseParam.setFileUuid(getFileUuidFromUrl(licenseUrl));

        PicUpdateParam logoParam = new PicUpdateParam();
        logoParam.setBusinessType(BusinessTypes.BIZ_LOGO);
        logoParam.setBusinessId(id);
        String logoUrl = param.getBusinessLogo();
        logoParam.setFileUuid(getFileUuidFromUrl(logoUrl));

        picParams.add(licenseParam);
        picParams.add(logoParam);
        boolean result = attachApi.batchUpdateBusiness(picParams);
        if (!result) {
            throw new BusinessException("-2", "failed to bind pictures");
        }
    }

    private String getFileUuidFromUrl(String url) {
        // http://localhost:9081/static/34j5kw4tjkkewj4.png
        String fileUuid = url.split("/")[4];
        log.info("get current file: {} uuid: {}", url, fileUuid);
        return fileUuid;
    }

    private void checkRole(Roles roles) throws BusinessException {
        UserPrinciple userPrinciple = AuthenticationContextUtils.getPrinciple();
        Asserts.isTrue(userPrinciple==null, new BusinessException("-2", "user authentication failed, no user info in request"));
        Roles loginRole = userPrinciple.getRole();
        Asserts.isTrue(loginRole.equals(roles), new BusinessException("-2", "user doesn't have the role to be authorized"));
    }

    private Integer saveBusiness(BusinessParam param) throws BusinessException {
        // 1. use param to get businessName and get count
        String businessName = param.getBusinessName();
        Long count = businessRepository.countBusinessName(businessName);
        // 2. throw exception if business already exists
        Asserts.isTrue(count>0, new BusinessException("-2", "Business already exists"));
        // 3. else initialize a po object and save
        // 3.1 copy properties first
        BusinessPO po = new BusinessPO();
        BeanUtils.copyProperties(param, po);
        // 3.2 set entryTime, businessStatus, remark
        po.setEntryTime(new Date());
        po.setBusinessStatus(2); // 1 pending, 2 passed, 3 rejected
        po.setAuditRemarks("passed without going through the process manually");
        // the id of po is empty at this time
        businessRepository.save(po);
        return po.getId();
    }
}
