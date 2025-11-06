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
import org.mmsy.merchant.domain.vo.GlobalWalletFlowVo;
import org.mmsy.merchant.domain.bo.GlobalWalletFlowBo;
import org.mmsy.merchant.service.IGlobalWalletFlowService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 全球钱包流水
 * 前端访问路由地址为: /merchant/walletFlow
 *
 * @author luohan
 * @date 2025-11-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/walletFlow")
public class GlobalWalletFlowController extends BaseController {

    private final IGlobalWalletFlowService globalWalletFlowService;

    /**
     * 查询全球钱包流水列表
     */
    @SaCheckPermission("merchant:walletFlow:list")
    @GetMapping("/list")
    public TableDataInfo<GlobalWalletFlowVo> list(GlobalWalletFlowBo bo, PageQuery pageQuery) {
        return globalWalletFlowService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出全球钱包流水列表
     */
    @SaCheckPermission("merchant:walletFlow:export")
    @Log(title = "全球钱包流水", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(GlobalWalletFlowBo bo, HttpServletResponse response) {
        List<GlobalWalletFlowVo> list = globalWalletFlowService.queryList(bo);
        ExcelUtil.exportExcel(list, "全球钱包流水", GlobalWalletFlowVo.class, response);
    }

    /**
     * 获取全球钱包流水详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("merchant:walletFlow:query")
    @GetMapping("/{id}")
    public R<GlobalWalletFlowVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable("id") Long id) {
        return R.ok(globalWalletFlowService.queryById(id));
    }

    /**
     * 新增全球钱包流水
     */
    @SaCheckPermission("merchant:walletFlow:add")
    @Log(title = "全球钱包流水", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody GlobalWalletFlowBo bo) {
        return toAjax(globalWalletFlowService.insertByBo(bo));
    }

    /**
     * 修改全球钱包流水
     */
    @SaCheckPermission("merchant:walletFlow:edit")
    @Log(title = "全球钱包流水", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody GlobalWalletFlowBo bo) {
        return toAjax(globalWalletFlowService.updateByBo(bo));
    }

    /**
     * 删除全球钱包流水
     *
     * @param ids 主键串
     */
    @SaCheckPermission("merchant:walletFlow:remove")
    @Log(title = "全球钱包流水", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable("ids") Long[] ids) {
        return toAjax(globalWalletFlowService.deleteWithValidByIds(List.of(ids), true));
    }
}
