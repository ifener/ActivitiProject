<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp" %>
  <div class="item form-group">
  	 <label class="col-md-3 col-sm-3 col-xs-12 control-label" for="textarea">
  	 </label>
  	 <div class="col-md-8 col-sm-8 col-xs-12">
  	 	<c:import url="/workflow/hiscomments">
         	<c:param name="processKey" value="${processKey}"></c:param>
         	<c:param name="bizId" value="${refundBill.id}"></c:param>
         </c:import>
  	 </div>
  </div>
                                    
 <div class="item form-group">
    <label class="col-md-3 col-sm-3 col-xs-12 control-label" for="textarea">
       	 审批结果<span class="required">*</span>
    </label>
    <div class="col-md-6 col-sm-6 col-xs-12">
       <input type="radio" class="flat" value="Y" checked name="transition" id="transition-Y"> 
       <label for="transition-Y">审批通过</label>
       <input type="radio" class="flat" value="N" name="transition" id="transition-N"> 
       <label for="transition-N">审批不通过</label>
    </div>
</div>
<div class="item form-group">
	<label class="col-md-3 col-sm-3 col-xs-12 control-label" for="textarea">
       	 审批意见<span class="required">*</span>
    </label>
    <div class="col-md-8 col-sm-8 col-xs-12">
        <textarea name="advice" id="advice" required="required" ></textarea>
    </div>
</div>


<div class="form-group">
    <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
        <button type="button" onclick="window.history.back();" class="btn btn-success">返回</button>
        <button type="button" onclick="submitForm()" class="btn btn-success">提交</button>
    </div>
</div>

<script type="text/javascript">
	function submitForm(){
		bootStrapDefaultConfirm("确定要提交吗？",function(){
			$("form")[0].submit();
		});
	}
</script>