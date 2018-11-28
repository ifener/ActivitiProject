jQuery.fn.beautyfileupload = function (options) {
	
  var defaults = {
		  allowExtensions:[],
		  allowExtensionsErrorTip:"文件格式不正确",
		  allowExtensionErrorHandler:null,
		  uploadBtnText:"浏览",
		  okHandler:null
  };

  $.extend(defaults,options);
  $(this).each(function(e){
	  //如果没有渲染过
	  if(!$(this).attr("renderFlag")){
			$(this).attr("renderFlag","beauty-upload");
			var htmlfragment = "<div class='beauty-upload'>";
			htmlfragment += "	<div class='input-append'><input class='input-large' type='text' readonly/></div>";
			htmlfragment += "	<div class='btn-container'>";
			htmlfragment += "   	<button type='button' class='btn btn-primary'>"+defaults.uploadBtnText+"</button>";
			htmlfragment += "	</div>";
			htmlfragment += "</div>";
			htmlfragment = $(htmlfragment);
			$(this).parent().append(htmlfragment);
			htmlfragment.find(".btn-container").prepend($(this));
			
			$(this).change(function() {
				var fileName = $(this).val().toLowerCase();
				
				if(defaults.allowExtensions.length>0) {
					for(idx in defaults.allowExtensions) {
						var regexp = new RegExp(defaults.allowExtensions[idx]+"$","ig"); 
						console.log(regexp);
						if(!regexp.test(fileName)) {
							if(typeof defaults.allowExtensionErrorHandler!='undefined' && defaults.allowExtensionErrorHandler!=null) {
								defaults.allowExtensionErrorHandler.call(this);
							} else {
								$(this).val('');
								alert(defaults.allowExtensionsErrorTip);
							}
							
							
							return false;
						}
					}
				}
				$(this).parents(".beauty-upload").find(".input-large").val($(this).val());
				if(typeof defaults.okHandler!='undefined' && defaults.okHandler!=null) {
					defaults.okHandler.call(this);
				}
			});
		}
  });
};