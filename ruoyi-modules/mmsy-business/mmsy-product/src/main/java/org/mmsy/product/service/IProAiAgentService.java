package org.mmsy.product.service;

import org.mmsy.product.domain.vo.ProAiAgentVo;
import org.mmsy.product.domain.bo.ProAiAgentBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * AI智能体Service接口
 *
 * @author luohan
 * @date 2025-11-05
 */
public interface IProAiAgentService {

    /**
     * 查询AI智能体
     *
     * @param id 主键
     * @return AI智能体
     */
    ProAiAgentVo queryById(Long id);

    /**
     * 分页查询AI智能体列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return AI智能体分页列表
     */
    TableDataInfo<ProAiAgentVo> queryPageList(ProAiAgentBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的AI智能体列表
     *
     * @param bo 查询条件
     * @return AI智能体列表
     */
    List<ProAiAgentVo> queryList(ProAiAgentBo bo);

    /**
     * 新增AI智能体
     *
     * @param bo AI智能体
     * @return 是否新增成功
     */
    Boolean insertByBo(ProAiAgentBo bo);

    /**
     * 修改AI智能体
     *
     * @param bo AI智能体
     * @return 是否修改成功
     */
    Boolean updateByBo(ProAiAgentBo bo);

    /**
     * 校验并批量删除AI智能体信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 修改AI智能体状态
     *
     * @param id     主键
     * @param status 状态：1 启用，0 禁用
     * @return 是否修改成功
     */
    Boolean updateStatus(Long id, Long status);

    /**
     * 根据当前租户权限查询有权限的AI智能体列表（分页）
     * <p>
     * 查询逻辑：
     * 1. 通过当前租户ID查询对应的会员ID
     * 2. 根据会员ID查询有权限的AI智能体编码列表
     * 3. 根据AI智能体编码列表查询AI智能体详情
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 有权限的AI智能体分页列表
     */
    TableDataInfo<ProAiAgentVo> queryAuthorizedAiAgentList(ProAiAgentBo bo, PageQuery pageQuery);

}
