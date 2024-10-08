package com.tarena.lbs.pojo.basic.query;

import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class AreaStoreQuery extends BasePageQuery implements Serializable {

    private List<Long> cityIdList;
}
