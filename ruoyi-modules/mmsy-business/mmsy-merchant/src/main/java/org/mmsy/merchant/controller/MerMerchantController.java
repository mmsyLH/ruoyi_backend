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
import org.mmsy.merchant.domain.vo.MerMerchantVo;
import org.mmsy.merchant.domain.bo.MerMerchantBo;
import org.mmsy.merchant.service.IMerMerchantService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 会员
 * 前端访问路由地址为: /merchant/merchant
 *
 * @author luohan
 * @date 2025-11-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/merchant")
public class MerMerchantController extends BaseController {

    private final IMerMerchantService merMerchantService;

    /**
     * 查询会员列表
     */
    @SaCheckPermission("merchant:merchant:list")
    @GetMapping("/list")
    public TableDataInfo<MerMerchantVo> list(MerMerchantBo bo, PageQuery pageQuery) {
        return merMerchantService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出会员列表
     */
    @SaCheckPermission("merchant:merchant:export")
    @Log(title = "会员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MerMerchantBo bo, HttpServletResponse response) {
        List<MerMerchantVo> list = merMerchantService.queryList(bo);
        ExcelUtil.exportExcel(list, "会员", MerMerchantVo.class, response);
    }

    /**
     * 获取会员详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("merchant:merchant:query")
    @GetMapping("/{id}")
    public R<MerMerchantVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable("id") Long id) {
        return R.ok(merMerchantService.queryById(id));
    }

    /**
     * 新增会员
     */
    @SaCheckPermission("merchant:merchant:add")
    @Log(title = "会员", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MerMerchantBo bo) {
        return toAjax(merMerchantService.insertByBo(bo));
    }

    /**
     * 修改会员
     */
    @SaCheckPermission("merchant:merchant:edit")
    @Log(title = "会员", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MerMerchantBo bo) {
        return toAjax(merMerchantService.updateByBo(bo));
    }

    /**
     * 删除会员
     *
     * @param ids 主键串
     */
    @SaCheckPermission("merchant:merchant:remove")
    @Log(title = "会员", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable("ids") Long[] ids) {
        return toAjax(merMerchantService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("merchant:merchant:edit")
    @Log(title = "会员-启用/禁用", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/changeStatus")
    public R<Void> changeStatus(@RequestBody MerMerchantBo bo) {
        return toAjax(merMerchantService.updateStatus(bo.getId(), bo.getStatus()));
    }
}

