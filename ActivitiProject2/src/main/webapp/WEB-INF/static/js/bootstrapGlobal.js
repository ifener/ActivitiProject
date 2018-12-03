function bootStrapConfirm(title,message,okText,cancelText,okCallback,cancelCallback) {
	BootstrapDialog.show({
		title: title,
		message: message,
		cssClass: 'login-dialog',
		buttons: [{
    		label: okText,
    		cssClass: 'btn-primary',
    		action: function(dialog){
    			if(okCallback){
    				okCallback(dialog);
    			}else {
    				dialog.close();
    			}
    			
    		}
		},{
    		label: cancelText,
    		cssClass: 'btn-primary',
    		action: function(dialog){
    			if(cancelCallback){
    				cancelCallback(dialog);
    			} else {
    				dialog.close();
    			}
    		}
		}]
	});
}

function bootStrapDefaultConfirm(message,okCallback,cancelCallback){
	bootStrapConfirm("提示",message,"确定","取消",okCallback,cancelCallback);
}

function bootStrapEnterMessage(title,subTitle,okCallback,cancelCallback)
{
	BootstrapDialog.show({
	    title: title,
	    message: $('<div class="row"><div class="col-xs-2" style="text-align:right">'+subTitle+'</div><div class="col-xs-10"><textarea class="form-control" placeholder="请输入内容..." id="bootstrapEnterMessage"></textarea></div></div>'),
	    buttons: [{
	        label: '确认',
	        cssClass: 'btn-primary',
	        action: function(dialog) {
	        	if(okCallback){
	        		var content = $("#bootstrapEnterMessage").val();
    				okCallback(dialog,content);
    			}else {
    				dialog.close();
    			}
	        }
	    },
	    {
	        label: '取消',
	        cssClass: 'btn-primary',
	        action: function(dialog) {
	        	if(cancelCallback){
    				cancelCallback(dialog);
    			} else {
    				dialog.close();
    			}
	        }
	    }]
	});

}

