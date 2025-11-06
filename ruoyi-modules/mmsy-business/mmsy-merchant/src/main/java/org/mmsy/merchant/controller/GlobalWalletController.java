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
import org.mmsy.merchant.domain.vo.GlobalWalletVo;
import org.mmsy.merchant.domain.bo.GlobalWalletBo;
import org.mmsy.merchant.service.IGlobalWalletService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 全球钱包
 * 前端访问路由地址为: /merchant/wallet
 *
 * @author luohan
 * @date 2025-11-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wallet")
public class GlobalWalletController extends BaseController {

    private final IGlobalWalletService globalWalletService;

    /**
     * 查询全球钱包列表
     */
    @SaCheckPermission("merchant:wallet:list")
    @GetMapping("/list")
    public TableDataInfo<GlobalWalletVo> list(GlobalWalletBo bo, PageQuery pageQuery) {
        return globalWalletService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出全球钱包列表
     */
    @SaCheckPermission("merchant:wallet:export")
    @Log(title = "全球钱包", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(GlobalWalletBo bo, HttpServletResponse response) {
        List<GlobalWalletVo> list = globalWalletService.queryList(bo);
        ExcelUtil.exportExcel(list, "全球钱包", GlobalWalletVo.class, response);
    }

    /**
     * 获取全球钱包详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("merchant:wallet:query")
    @GetMapping("/{id}")
    public R<GlobalWalletVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable("id") Long id) {
        return R.ok(globalWalletService.queryById(id));
    }

    /**
     * 新增全球钱包
     */
    @SaCheckPermission("merchant:wallet:add")
    @Log(title = "全球钱包", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody GlobalWalletBo bo) {
        return toAjax(globalWalletService.insertByBo(bo));
    }

    /**
     * 修改全球钱包
     */
    @SaCheckPermission("merchant:wallet:edit")
    @Log(title = "全球钱包", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody GlobalWalletBo bo) {
        return toAjax(globalWalletService.updateByBo(bo));
    }

    /**
     * 删除全球钱包
     *
     * @param ids 主键串
     */
    @SaCheckPermission("merchant:wallet:remove")
    @Log(title = "全球钱包", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable("ids") Long[] ids) {
        return toAjax(globalWalletService.deleteWithValidByIds(List.of(ids), true));
    }
}
