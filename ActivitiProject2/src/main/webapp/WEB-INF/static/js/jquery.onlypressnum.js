jQuery.fn.onlypressnum = function (options) {
  //$(this).css({imeMode:"disabled",'-moz-user-select':"none"}); 
	
  var defaults = {onlyInt:false,callback:null,defaultValue:0};

  $.extend(defaults,options);
  $(this).bind("keypress", function (e) {

      if (e.ctrlKey == true || e.shiftKey == true)
        return false;
      if ((e.which >= 48 && e.which <= 57 && e.ctrlKey == false && e.shiftKey == false) || e.which == 0 || e.which == 8 || (defaults.onlyInt==false && e.which == 46))
        return true;
      else if (e.ctrlKey == true && (e.which == 99 || e.which == 118))
        return false;
      else
        return false;
    })
    .bind("contextmenu", function () {
      return false;
    })
    //.bind("selectstart",function(){return false;}) 
    .bind("focus", function () {
      $(this).select()
    })
    //.bind("paste",function(){return false;})
    .bind("blur", function () {
      var val = $.trim($(this).val());
      if (defaults && defaults.defaultValue) {
        if (val== '' || parseFloat(val) == 0 || isNaN(val)) {
          $(this).val(defaults.defaultValue)
        }
      }

      if (defaults.callback) {
    	  defaults.callback($(this));
      }
    });
};