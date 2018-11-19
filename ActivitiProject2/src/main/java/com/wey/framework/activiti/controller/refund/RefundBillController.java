package com.wey.framework.activiti.controller.refund;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wey.framework.activiti.constants.WorkflowStatus;
import com.wey.framework.activiti.model.RefundBill;
import com.wey.framework.activiti.service.RefundBillManager;
import com.wey.framework.util.ContextUtil;
import com.wey.framework.util.Pagination;

@Controller
@RequestMapping("/refundBill")
public class RefundBillController {

	@Autowired
	private RefundBillManager refundBillManager;
	
	@RequestMapping("/list")
	public String list(Pagination page,Model model) {
		refundBillManager.findByPage(page);
		model.addAttribute("page", page);
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
		refundBillManager.save(refundBill);
		return "redirect:list";
	}
}
