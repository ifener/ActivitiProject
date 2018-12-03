package com.wey.framework.controller.refund;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wey.framework.activiti.bo.WorkflowBO;
import com.wey.framework.activiti.constants.WorkflowStatus;
import com.wey.framework.bo.refund.RefundBillBO;
import com.wey.framework.controller.BaseController;
import com.wey.framework.exception.ServiceException;
import com.wey.framework.model.refund.RefundBill;
import com.wey.framework.service.refund.RefundBillManager;
import com.wey.framework.util.ContextUtil;
import com.wey.framework.util.Pagination;

@Controller
@RequestMapping("/refundBill")
public class RefundBillController extends BaseController {

	@Autowired
	private RefundBillManager refundBillManager;

	@RequestMapping("/list")
	public String list(Pagination page, RefundBillBO refundBillBO, Model model) {
		try {
			page.setSearch(refundBillBO);
			refundBillManager.findByPage(page);
			model.addAttribute("page", page);
			model.addAttribute("refundBillBO", refundBillBO);
			return custom();
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	@RequestMapping("/add")
	public String add(Model model) {
		RefundBill refundBill = new RefundBill();
		refundBill.setRefundTime(new Timestamp(System.currentTimeMillis()));
		model.addAttribute("refundBill", refundBill);
		return custom();
	}

	@RequestMapping("/load/{id}")
	public String load(@PathVariable("id") Long id, Model model) {
		RefundBill refundBill = refundBillManager.get(id);
		model.addAttribute("refundBill", refundBill);
		return custom();
	}

	@RequestMapping("/view/{id}")
	public String view(@PathVariable("id") Long id, Model model) {
		RefundBill refundBill = refundBillManager.get(id);
		model.addAttribute("refundBill", refundBill);
		return custom();
	}

	@RequestMapping("/save")
	public String save(RefundBill refundBill) {
		try {
			refundBill.setUser(ContextUtil.getContext().getUser());
			refundBill.setAuditStatus(WorkflowStatus.SUBMIT.getCode());

			if (StringUtils.hasText(refundBill.getContent())) {
				String content = refundBill.getContent();
				if (java.nio.charset.Charset.forName("ISO-8859-1").newEncoder().canEncode(content)) {
					content = new String(content.getBytes("ISO-8859-1"), "UTF-8");
					refundBill.setContent(content);
				}
			}
			refundBillManager.saveAndStart(refundBill);
			return "redirect:list";
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	@PostMapping("/saveAudit")
	public String saveAudit(RefundBill refundBill, WorkflowBO workflowBO) {
		try {
			refundBillManager.saveAuditing(refundBill, workflowBO);
			return "redirect:findTaskList";
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	@RequestMapping("/findTaskList")
	public String findTaskList(Pagination page, RefundBillBO refundBillBO, Model model) {
		if (page == null) {
			page = new Pagination();
		}

		if (refundBillBO == null) {
			refundBillBO = new RefundBillBO();
		}

		refundBillManager.findTaskList(page, refundBillBO);

		model.addAttribute("page", page);
		model.addAttribute("refundBillBO", refundBillBO);
		return custom();
	}

	@RequestMapping("/audit/{id}")
	public String audit(@PathVariable("id") Long id, Model model) {
		RefundBill refundBill = refundBillManager.get(id);
		model.addAttribute("refundBill", refundBill);
		return custom();
	}

}
