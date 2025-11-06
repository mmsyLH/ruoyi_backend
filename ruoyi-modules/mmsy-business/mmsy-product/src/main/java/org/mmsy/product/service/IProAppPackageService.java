package org.mmsy.product.service;

import org.mmsy.product.domain.ProAppPackage;
import org.mmsy.product.domain.vo.ProAppPackageVo;
import org.mmsy.product.domain.vo.ProAppVo;
import org.mmsy.product.domain.vo.ProAiAgentVo;
import org.mmsy.product.domain.bo.ProAppPackageBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 套餐管理Service接口（包含应用套餐和AI智能体套餐）
 *
 * @author luohan
 * @date 2025-11-05
 */
public interface IProAppPackageService {

    /**
     * 查询套餐
     *
     * @param id 主键
     * @return 套餐信息
     */
    ProAppPackageVo queryById(Long id);

    /**
     * 分页查询套餐列表（支持按类型筛选）
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 套餐分页列表
     */
    TableDataInfo<ProAppPackageVo> queryPageList(ProAppPackageBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的套餐列表
     *
     * @param bo 查询条件
     * @return 套餐列表
     */
    List<ProAppPackageVo> queryList(ProAppPackageBo bo);

    /**
     * 新增套餐
     *
     * @param bo 套餐信息
     * @return 是否新增成功
     */
    Boolean insertByBo(ProAppPackageBo bo);

    /**
     * 修改套餐
     *
     * @param bo 套餐信息
     * @return 是否修改成功
     */
    Boolean updateByBo(ProAppPackageBo bo);

    /**
     * 校验并批量删除套餐信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 根据套餐ID查询关联的应用列表
     *
     * @param packageId 套餐ID
     * @return 应用列表
     */
    List<ProAppVo> queryAppsByPackageId(Long packageId);

    /**
     * 根据套餐编码查询套餐信息
     *
     * @param packageCode 套餐编码
     * @return 套餐信息
     */
    ProAppPackageVo queryByPackageCode(String packageCode);

    /**
     * 根据套餐ID查询关联的AI智能体列表
     *
     * @param packageId 套餐ID
     * @return AI智能体列表
     */
    List<ProAiAgentVo> queryAgentsByPackageId(Long packageId);

    /**
     * 修改套餐状态
     *
     * @param id     套餐ID
     * @param status 状态（1-启用，0-禁用）
     * @return 是否修改成功
     */
    Boolean updateStatus(Long id, Long status);

}
