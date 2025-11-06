package org.mmsy.merchant.controller;

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
import org.mmsy.merchant.domain.vo.MerMerchantAppPermissionVo;
import org.mmsy.merchant.domain.bo.MerMerchantAppPermissionBo;
import org.mmsy.merchant.service.IMerMerchantAppPermissionService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 会员应用权限（买了哪个套餐、到期时间、用量）
 * 前端访问路由地址为: /merchant/merchantAppPermission
 *
 * @author luohan
 * @date 2025-11-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/merchantAppPermission")
public class MerMerchantAppPermissionController extends BaseController {

    private final IMerMerchantAppPermissionService merMerchantAppPermissionService;

    /**
     * 查询会员应用权限（买了哪个套餐、到期时间、用量）列表
     */
    @SaCheckPermission("merchant:merchantAppPermission:list")
    @GetMapping("/list")
    public TableDataInfo<MerMerchantAppPermissionVo> list(MerMerchantAppPermissionBo bo, PageQuery pageQuery) {
        return merMerchantAppPermissionService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出会员应用权限（买了哪个套餐、到期时间、用量）列表
     */
    @SaCheckPermission("merchant:merchantAppPermission:export")
    @Log(title = "会员应用权限（买了哪个套餐、到期时间、用量）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MerMerchantAppPermissionBo bo, HttpServletResponse response) {
        List<MerMerchantAppPermissionVo> list = merMerchantAppPermissionService.queryList(bo);
        ExcelUtil.exportExcel(list, "会员应用权限（买了哪个套餐、到期时间、用量）", MerMerchantAppPermissionVo.class, response);
    }

    /**
     * 获取会员应用权限（买了哪个套餐、到期时间、用量）详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("merchant:merchantAppPermission:query")
    @GetMapping("/{id}")
    public R<MerMerchantAppPermissionVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable("id") Long id) {
        return R.ok(merMerchantAppPermissionService.queryById(id));
    }

    /**
     * 新增会员应用权限（买了哪个套餐、到期时间、用量）
     */
    @SaCheckPermission("merchant:merchantAppPermission:add")
    @Log(title = "会员应用权限（买了哪个套餐、到期时间、用量）", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MerMerchantAppPermissionBo bo) {
        return toAjax(merMerchantAppPermissionService.insertByBo(bo));
    }

    /**
     * 修改会员应用权限（买了哪个套餐、到期时间、用量）
     */
    @SaCheckPermission("merchant:merchantAppPermission:edit")
    @Log(title = "会员应用权限（买了哪个套餐、到期时间、用量）", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MerMerchantAppPermissionBo bo) {
        return toAjax(merMerchantAppPermissionService.updateByBo(bo));
    }

    /**
     * 删除会员应用权限（买了哪个套餐、到期时间、用量）
     *
     * @param ids 主键串
     */
    @SaCheckPermission("merchant:merchantAppPermission:remove")
    @Log(title = "会员应用权限（买了哪个套餐、到期时间、用量）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable("ids") Long[] ids) {
        return toAjax(merMerchantAppPermissionService.deleteWithValidByIds(List.of(ids), true));
    }
}
