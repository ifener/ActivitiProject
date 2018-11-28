<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="${ctx}/static/js/daterangepicker.js"></script>
<div id="Paginator" style="text-align: center"> <ul id="pageLimit"></ul></div>
<input type="hidden" value="" name="page.pageIndex" />
<input type="hidden" value="${page.pageSize}" name="page.pageSize" />
<script>
    $(function(){
        $('#pageLimit').bootstrapPaginator({    
            currentPage: (${page.pageIndex} || 1),    
            totalPages: (${page.pageSize} || 10),    
            size:"normal",    
            bootstrapMajorVersion: 3,    
            alignment:"right",    
            numberOfPages:(${page.pageTotal}||1),    
            itemTexts: function (type, page, current) {        
                switch (type) {            
                case "first": return "首页";            
                case "prev": return "上一页";            
                case "next": return "下一页";            
                case "last": return "末页";            
                case "page": return page;
                }
            },
            onPageClicked:function(event, originalEvent, type,page){
               $("[name='page.pageIndex']").val(page);
               $("form")[0].submit();
            }
        })
    });
 </script>