package org.mmsy.product.service;

import cn.hutool.json.JSONObject;
import org.mmsy.product.domain.vo.ProAppVo;
import org.mmsy.product.domain.bo.ProAppBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;

import java.util.Collection;
import java.util.List;

/**
 * 中控平台应用Service接口
 *
 * @author luohan
 * @date 2025-11-05
 */
public interface IProAppService {

    /**
     * 查询中控平台应用
     *
     * @param id 主键
     * @return 中控平台应用
     */
    ProAppVo queryById(Long id);

    /**
     * 分页查询中控平台应用列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 中控平台应用分页列表
     */
    TableDataInfo<ProAppVo> queryPageList(ProAppBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的中控平台应用列表
     *
     * @param bo 查询条件
     * @return 中控平台应用列表
     */
    List<ProAppVo> queryList(ProAppBo bo);

    /**
     * 新增中控平台应用
     *
     * @param bo 中控平台应用
     * @return 是否新增成功
     */
    Boolean insertByBo(ProAppBo bo);

    /**
     * 修改中控平台应用
     *
     * @param bo 中控平台应用
     * @return 是否修改成功
     */
    Boolean updateByBo(ProAppBo bo);

    /**
     * 校验并批量删除中控平台应用信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 修改中控平台应用状态
     *
     * @param id     主键
     * @param status 状态：1 启用，0 禁用
     * @return 是否修改成功
     */
    Boolean updateStatus(Long id, Long status);

    /**
     * 获取应用Token（带缓存）
     * <p>
     * 缓存策略：使用Redis缓存Token，缓存时间为Token过期时间的一半
     *
     * @param appId 应用ID
     * @return Token信息
     */
    R<JSONObject> getAppToken(Long appId);

    /**
     * 根据当前租户权限查询有权限的应用列表（分页）
     * <p>
     * 查询逻辑：
     * 1. 通过当前租户ID查询对应的会员ID
     * 2. 根据会员ID查询有权限的应用编码列表
     * 3. 根据应用编码列表查询应用详情
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 有权限的应用分页列表
     */
    TableDataInfo<ProAppVo> queryAuthorizedAppList(ProAppBo bo, PageQuery pageQuery);

}
