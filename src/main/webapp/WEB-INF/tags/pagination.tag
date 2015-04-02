<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="page" type="org.springframework.data.domain.Page" required="true"%>
<ul id="pagination"></ul>
<script type='text/javascript'>
	$(document).ready(function(){
		var options = {
		    bootstrapMajorVersion: 3,
		    currentPage: "<%=page.getNumber() + 1%>",
		    totalPages: "<%=page.getTotalPages()%>",
		    tooltipTitles: function (type, page, current) {
                switch (type) {
	                case "first":
	                    return "首页";
	                case "prev":
	                    return "上一页";
	                case "next":
	                    return "下一页";
	                case "last":
	                    return "末页";
	                case "page":
	                    return "";
                }
            },
		     
		    pageUrl: function(type, page, current){
                return "?page=" + page + "&${searchParams}";
            }
		};
		if(<%=page.getTotalPages()%> > 1){
			$('#pagination').bootstrapPaginator(options);
			$('#pagination').append("<li class='disabled'><a>共<%=page.getTotalElements()%>条记录</a></li>");
		} else{
			$('#pagination').addClass('pagination');
			$('#pagination').append("<li class='disabled'><a>共<%=page.getTotalElements()%>条记录</a></li>");
		}
	});
	
</script>