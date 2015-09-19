<#list inputFieldNameList as inputFieldName>

$(".contractCategory[field=${inputFieldName}").keyup(function(){  
    var argjson = {
        <#list inputFieldNameList as inputFieldNameArg>
            "${inputFieldNameArg}" : $(".contractCategory[field=${inputFieldNameArg}]").val(),
        </#list>
        };
    $.ajax({
          method:"POST",
          url: "/admin/contract_category!expressionCalc.action",
          dataType: "json",
          data:{
              "id":"${contractCategoryId}",
              "fieldName":"${showFieldName}",
              "expressionArgJson":JSON.stringify(argjson)
          },
          success: function(res){
                if(res.status=='success'){
                  $(".contractCategory[field=${showFieldName}]").val(res.message);
                }else{
                  $(".contractCategory[field=${showFieldName}]").val("");
                    msg.alert('保存信息失败', res.message, 'error')                
                }
          },
          error:function(error){
              $(".contractCategory[field=${showFieldName}]").val("");
              msg.alert('保存信息失败', error, 'error')  
          }
    })
});
</#list>