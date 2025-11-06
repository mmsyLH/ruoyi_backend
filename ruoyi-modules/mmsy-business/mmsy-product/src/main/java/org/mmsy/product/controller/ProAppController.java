package org.mmsy.product.controller;

import java.util.List;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletResponse;

import jakarta.validation.constraints.*;

import cn.dev33.satoken.annotation.SaCheckPermission;

import org.springframework.web.bind.annotation.*;

import org.springframework.validation.annotation.Validated;

import org.dromara.common.idempotent.annotation.RepeatSubmit;

import org.dromara.common.log.annotation.Log;

import org.dromara.common.web.core.BaseController;

import org.dromara.common.mybatis.core.page.PageQuery;

import org.dromara.common.core.domain.R;

import org.dromara.common.core.validate.AddGroup;

import org.dromara.common.core.validate.EditGroup;

import org.dromara.common.log.enums.BusinessType;

import org.dromara.common.excel.utils.ExcelUtil;

import org.mmsy.product.domain.vo.ProAppVo;

import org.mmsy.product.domain.bo.ProAppBo;

import org.mmsy.product.service.IProAppService;

import org.dromara.common.mybatis.core.page.TableDataInfo;


/**
 * 中控平台应用
 * 前端访问路由地址为: /product/app
 *
 * @author luohan
 * @date 2025-11-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/app")
public class ProAppController extends BaseController {

    private final IProAppService proAppService;

    /**
     * 查询中控平台应用列表（管理后台使用）
     */
    @SaCheckPermission("product:app:list")
    @GetMapping("/list")
    public TableDataInfo<ProAppVo> list(ProAppBo bo, PageQuery pageQuery) {
        return proAppService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询当前租户有权限的应用列表（前端应用广场使用）
     * <p>
     * 数据权限过滤逻辑:
     * 1. 根据当前租户ID查询对应的商户信息
     * 2. 根据商户ID查询 mer_merchant_app_permission 表中 item_type='app' 的记录
     * 4. 返回这些应用的详细信息
     *
     * @param bo        查询条件（支持按应用名称、类型、分类等筛选）
     * @param pageQuery 分页参数
     * @return 当前租户有权限的应用分页列表
     */
    @SaCheckPermission("product:app:list")
    @GetMapping("/authorizedList")
    public TableDataInfo<ProAppVo> authorizedList(ProAppBo bo, PageQuery pageQuery) {
        return proAppService.queryAuthorizedAppList(bo, pageQuery);
    }

    /**
     * 导出中控平台应用列表
     */
    @SaCheckPermission("product:app:export")
    @Log(title = "中控平台应用", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ProAppBo bo, HttpServletResponse response) {
        List<ProAppVo> list = proAppService.queryList(bo);
        ExcelUtil.exportExcel(list, "中控平台应用", ProAppVo.class, response);
    }

    /**
     * 获取中控平台应用详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("product:app:query")
    @GetMapping("/{id}")
    public R<ProAppVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable("id") Long id) {
        return R.ok(proAppService.queryById(id));
    }

    /**
     * 新增中控平台应用
     */
    @SaCheckPermission("product:app:add")
    @Log(title = "中控平台应用", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ProAppBo bo) {
        return toAjax(proAppService.insertByBo(bo));
    }

    /**
     * 修改中控平台应用
     */
    @SaCheckPermission("product:app:edit")
    @Log(title = "中控平台应用", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ProAppBo bo) {
        return toAjax(proAppService.updateByBo(bo));
    }

    /**
     * 删除中控平台应用
     *
     * @param ids 主键串
     */
    @SaCheckPermission("product:app:remove")
    @Log(title = "中控平台应用", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable("ids") Long[] ids) {
        return toAjax(proAppService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("product:app:edit")
    @Log(title = "中控平台应用", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/changeStatus")
    public R<Void> changeStatus(@RequestBody ProAppBo bo) {
        return toAjax(proAppService.updateStatus(bo.getId(), bo.getStatus()));
    }

    // =================== 应用Token获取接口 ===================

    /**
     * 根据应用ID获取应用Token
     *
     * @param appId 应用ID
     * @return Token信息
     */
    @GetMapping("/getAppToken/{appId}")
    @SaCheckPermission("product:app:query")
    @Log(title = "中控平台应用-获取登录TOKEN", businessType = BusinessType.GRANT)
    public R<JSONObject> getAppToken(@PathVariable Long appId) {
        return proAppService.getAppToken(appId);
    }

}
