package org.mmsy.product.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.mmsy.product.domain.bo.ProAiAgentBo;
import org.mmsy.product.domain.vo.ProAiAgentVo;
import org.mmsy.product.service.IProAiAgentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * AI智能体
 * 前端访问路由地址为: /product/aiAgent
 *
 * @author luohan
 * @date 2025-11-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/aiAgent")
public class ProAiAgentController extends BaseController {

    private final IProAiAgentService proAiAgentService;

    /**
     * 查询AI智能体列表（管理后台使用）
     */
    @SaCheckPermission("product:aiAgent:list")
    @GetMapping("/list")
    public TableDataInfo<ProAiAgentVo> list(ProAiAgentBo bo, PageQuery pageQuery) {
        return proAiAgentService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询当前租户有权限的AI智能体列表（前端AI智能体广场使用）
     * <p>
     * 数据权限过滤逻辑:
     * 1. 根据当前租户ID查询对应的商户信息
     * 2. 根据商户ID查询 mer_merchant_app_permission 表中 item_type='ai_agent' 的记录
     * 4. 返回这些AI智能体的详细信息
     *
     * @param bo        查询条件（支持按AI智能体名称、类型、分类等筛选）
     * @param pageQuery 分页参数
     * @return 当前租户有权限的AI智能体分页列表
     */
    @SaCheckPermission("product:aiAgent:list")
    @GetMapping("/authorizedList")
    public TableDataInfo<ProAiAgentVo> authorizedList(ProAiAgentBo bo, PageQuery pageQuery) {
        return proAiAgentService.queryAuthorizedAiAgentList(bo, pageQuery);
    }

    /**
     * 导出AI智能体列表
     */
    @SaCheckPermission("product:aiAgent:export")
    @Log(title = "AI智能体", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ProAiAgentBo bo, HttpServletResponse response) {
        List<ProAiAgentVo> list = proAiAgentService.queryList(bo);
        ExcelUtil.exportExcel(list, "AI智能体", ProAiAgentVo.class, response);
    }

    /**
     * 获取AI智能体详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("product:aiAgent:query")
    @GetMapping("/{id}")
    public R<ProAiAgentVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable("id") Long id) {
        return R.ok(proAiAgentService.queryById(id));
    }

    /**
     * 新增AI智能体
     */
    @SaCheckPermission("product:aiAgent:add")
    @Log(title = "AI智能体", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ProAiAgentBo bo) {
        return toAjax(proAiAgentService.insertByBo(bo));
    }

    /**
     * 修改AI智能体
     */
    @SaCheckPermission("product:aiAgent:edit")
    @Log(title = "AI智能体", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ProAiAgentBo bo) {
        return toAjax(proAiAgentService.updateByBo(bo));
    }

    /**
     * 删除AI智能体
     *
     * @param ids 主键串
     */
    @SaCheckPermission("product:aiAgent:remove")
    @Log(title = "AI智能体", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable("ids") Long[] ids) {
        return toAjax(proAiAgentService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("product:aiAgent:edit")
    @Log(title = "AI智能体", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/changeStatus")
    public R<Void> changeStatus(@RequestBody ProAiAgentBo bo) {
        return toAjax(proAiAgentService.updateStatus(bo.getId(), bo.getStatus()));
    }

}
