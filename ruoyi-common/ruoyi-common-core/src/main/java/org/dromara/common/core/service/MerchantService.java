package org.dromara.common.core.service;

import java.util.List;

/**
 * 通用 商户服务
 *
 * @author luohan
 */
public interface MerchantService {

    /**
     * 根据租户ID查询会员信息
     *
     * @param tenantId 租户编号
     * @return 会员/租户
     */
    Object queryByTenantId(String tenantId);

    /**
     * 查询会员授权的应用编码列表
     *
     * @param merchantId 会员ID
     * @return 应用编码列表
     */
    List<String> queryAuthorizedAppCodes(Long merchantId);

    /**
     * 查询会员授权的AI智能体编码列表
     *
     * @param merchantId 会员ID
     * @return AI智能体编码列表
     */
    List<String> queryAuthorizedAiAgentCodes(Long merchantId);

}

