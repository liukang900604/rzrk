<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<#if  imgUrl?? && imgUrl != "">
<img src="file_upload!downloadFile.action?downloadFilePath=${downloadFilePath}&downloadFileName=${downloadFileName}" />
<#else>
<#if htmlName?? && htmlName != "">
${(htmlName)!}
<#else>
<span>暂不支持此种格式预览，<a href='file_upload!downloadFile.action?downloadFilePath=${downloadFilePath}&downloadFileName=${downloadFileName}'>请下载查看</a></span>
</#if>

</#if>