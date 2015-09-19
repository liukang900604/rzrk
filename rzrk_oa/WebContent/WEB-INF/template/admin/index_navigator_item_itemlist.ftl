<#list listResult as item>
	<a class="listItem" href="javascript:" title="${item.name}" data-src="${item.accessUrl}">
	<#if item.name?length gt 60>
		${item.name?substring(0, 60)}
	<#else>
		${item.name}
	</#if>
   &nbsp;<span>[${item.modifyDate}]</span></a>
</#list>
		