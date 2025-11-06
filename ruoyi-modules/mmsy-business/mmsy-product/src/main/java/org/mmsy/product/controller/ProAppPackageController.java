package org.mmsy.product.controller;

import java.util.List;

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

import org.mmsy.product.domain.vo.ProAppPackageVo;

import org.mmsy.product.domain.vo.ProAppVo;

import org.mmsy.product.domain.vo.ProAiAgentVo;

import org.mmsy.product.domain.bo.ProAppPackageBo;

import org.mmsy.product.service.IProAppPackageService;

import org.dromara.common.mybatis.core.page.TableDataInfo;


/**
 * 套餐管理（包含应用套餐和AI智能体套餐）
 * 前端访问路由地址为: /product/appPackage
 *
 * @author luohan
 * @date 2025-11-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/appPackage")
public class ProAppPackageController extends BaseController {

    private final IProAppPackageService proAppPackageService;

    /**
     * 查询套餐列表（支持按类型筛选）
     */
    @SaCheckPermission("product:appPackage:list")
    @GetMapping("/list")
    public TableDataInfo<ProAppPackageVo> list(ProAppPackageBo bo, PageQuery pageQuery) {
        return proAppPackageService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出套餐列表
     */
    @SaCheckPermission("product:appPackage:export")
    @Log(title = "套餐管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ProAppPackageBo bo, HttpServletResponse response) {
        List<ProAppPackageVo> list = proAppPackageService.queryList(bo);
        ExcelUtil.exportExcel(list, "套餐列表", ProAppPackageVo.class, response);
    }

    /**
     * 获取套餐详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("product:appPackage:query")
    @GetMapping("/{id}")
    public R<ProAppPackageVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable("id") Long id) {
        return R.ok(proAppPackageService.queryById(id));
    }

    /**
     * 新增套餐
     */
    @SaCheckPermission("product:appPackage:add")
    @Log(title = "套餐管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ProAppPackageBo bo) {
        return toAjax(proAppPackageService.insertByBo(bo));
    }

    /**
     * 修改套餐
     */
    @SaCheckPermission("product:appPackage:edit")
    @Log(title = "套餐管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ProAppPackageBo bo) {
        return toAjax(proAppPackageService.updateByBo(bo));
    }

    /**
     * 删除套餐
     *
     * @param ids 主键串
     */
    @SaCheckPermission("product:appPackage:remove")
    @Log(title = "套餐管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable("ids") Long[] ids) {
        return toAjax(proAppPackageService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 根据套餐ID查询关联的应用列表
     *
     * @param packageId 套餐ID
     */
    @SaCheckPermission("product:appPackage:query")
    @GetMapping("/{packageId}/apps")
    public R<List<ProAppVo>> getAppsByPackageId(@NotNull(message = "套餐ID不能为空")
                                                 @PathVariable("packageId") Long packageId) {
        return R.ok(proAppPackageService.queryAppsByPackageId(packageId));
    }

    /**
     * 根据套餐ID查询关联的AI智能体列表
     *
     * @param packageId 套餐ID
     */
    @SaCheckPermission("product:appPackage:query")
    @GetMapping("/{packageId}/agents")
    public R<List<ProAiAgentVo>> getAgentsByPackageId(@NotNull(message = "套餐ID不能为空")
                                                       @PathVariable("packageId") Long packageId) {
        return R.ok(proAppPackageService.queryAgentsByPackageId(packageId));
    }

    /**
     * 修改套餐状态
     *
     * @param id     套餐ID
     * @param status 状态（1-启用，0-禁用）
     */
    @SaCheckPermission("product:appPackage:edit")
    @Log(title = "套餐管理", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/status/{status}")
    public R<Void> updateStatus(@NotNull(message = "套餐ID不能为空")
                                @PathVariable("id") Long id,
                                @NotNull(message = "状态不能为空")
                                @PathVariable("status") Long status) {
        return toAjax(proAppPackageService.updateStatus(id, status));
    }

}
