package org.mmsy.merchant.service;

import org.mmsy.merchant.domain.GlobalWalletFlow;
import org.mmsy.merchant.domain.vo.GlobalWalletFlowVo;
import org.mmsy.merchant.domain.bo.GlobalWalletFlowBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 全球钱包流水Service接口
 *
 * @author luohan
 * @date 2025-11-05
 */
public interface IGlobalWalletFlowService {

    /**
     * 查询全球钱包流水
     *
     * @param id 主键
     * @return 全球钱包流水
     */
    GlobalWalletFlowVo queryById(Long id);

    /**
     * 分页查询全球钱包流水列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 全球钱包流水分页列表
     */
    TableDataInfo<GlobalWalletFlowVo> queryPageList(GlobalWalletFlowBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的全球钱包流水列"
     *
     * @param bo 查询条件
     * @return 全球钱包流水列表
     */
    List<GlobalWalletFlowVo> queryList(GlobalWalletFlowBo bo);

    /**
     * 新增全球钱包流水
     *
     * @param bo 全球钱包流水
     * @return 是否新增成功
     */
    Boolean insertByBo(GlobalWalletFlowBo bo);

    /**
     * 修改全球钱包流水
     *
     * @param bo 全球钱包流水
     * @return 是否修改成功
     */
    Boolean updateByBo(GlobalWalletFlowBo bo);

    /**
     * 校验并批量删除全球钱包流水信"
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校"
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
