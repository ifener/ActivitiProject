package com.wey.framework.activiti.controller.refund;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wey.framework.activiti.constants.WorkflowStatus;
import com.wey.framework.activiti.model.TaskInfo;
import com.wey.framework.bo.refund.RefundBillBO;
import com.wey.framework.model.refund.RefundBill;
import com.wey.framework.service.refund.RefundBillManager;
import com.wey.framework.util.ContextUtil;
import com.wey.framework.util.Pagination;

@Controller
@RequestMapping("/refundBill")
public class RefundBillController {

	@Autowired
	private RefundBillManager refundBillManager;
	
	@RequestMapping("/list")
	public String list(Pagination page,RefundBillBO refundBillBO,Model model) {
		page.setSearch(refundBillBO);
		refundBillManager.findByPage(page);
		model.addAttribute("page", page);
		model.addAttribute("refundBillBO", refundBillBO);
		return "refundBill/list";
	}
	
	@RequestMapping("/add")
	public String add(Model model) {
		RefundBill refundBill = new RefundBill();
		refundBill.setRefundTime(new Timestamp(System.currentTimeMillis()));
		model.addAttribute("refundBill", refundBill);
		return "refundBill/form";
	}
	
	@RequestMapping("/load/{id}")
	public String load(@PathVariable("id") Long id,Model model) {
		RefundBill refundBill = refundBillManager.get(id);
		model.addAttribute("refundBill", refundBill);
		return "refundBill/form";
	}
	
	@RequestMapping("/save")
	public String save(RefundBill refundBill) {
		refundBill.setUser(ContextUtil.getContext().getUser());
		refundBill.setAuditStatus(WorkflowStatus.SUBMIT.getCode());
		refundBillManager.saveAndStar(refundBill);
		return "redirect:list";
	}
	
	@RequestMapping("/taskList")
	public String findTaskList(Pagination page,RefundBillBO refundBillBO,Model model) {
		if(page==null) {
			page = new Pagination();
		}
		
		if(refundBillBO!=null) {
			refundBillBO = new RefundBillBO();
		}
	
		refundBillManager.findTaskList(page, refundBillBO);
		
		model.addAttribute("page", page);
		return "refundBill/findTaskList";
	}
	
	@RequestMapping("/audit/{id}")
	public String audit(@PathVariable("id") Long id,Model model) {
		RefundBill refundBill = refundBillManager.get(id);
		model.addAttribute("refundBill", refundBill);
		return "refundBill/audit";
	}
	
}
