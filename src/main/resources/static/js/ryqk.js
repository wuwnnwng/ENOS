/**
 * 
 */
var url ;
var dlzh = $('.dlzh').html();
$('#ryqkli').on('click',function(){
	/*启用layui 时间控件*/
	layui.use('laydate', function() {
		var laydate = layui.laydate;
		laydate.render({
			elem : '#rqksrq'//指定元素
		});
		var laydate2 = layui.laydate;
		laydate2.render({
			elem : '#rqksrqcx', //指定元素
		});
	});
	var pageNumber=$('#ryqk').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#ryqk').datagrid('getPager').data("pagination").options.pageSize;
	pageActionryqk(pageNumber,pageSize );
    var pg = $("#ryqk").datagrid("getPager");
    if(pg)
    {
    	 $(pg).pagination({
 	        onBeforeRefresh:function(){
 	    	},
            onRefresh:function(pageNumber,pageSize ){
            	pageActionryqk(pageNumber,pageSize );
             },
            onChangePageSize:function(){
             },
            onSelectPage:function(pageNumber,pageSize){
            	pageActionryqk(pageNumber,pageSize );
             }
        });
    }
 
});
function pageActionryqk(pageNumber,pageSize ){
	var page = 0;
	if(pageNumber!=0){
		page = pageNumber-1;
	}
    var url="/ENOS/ryqk/find?page="+page+"&size="+pageSize +""
    $('#ryqk').datagrid('options').url = url;
    $("#ryqk").datagrid('reload'); 
}
/*查询工作记录*/
function  searchryqk(){
	var pageNumber=$('#ryqk').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#ryqk').datagrid('getPager').data("pagination").options.pageSize;
	pageActionryqk(pageNumber,pageSize ); 
}
/*新建信息发布*/
function newryqk(){
    $('#ryqkdlg').dialog('open').dialog('center').dialog('setTitle','新建人员情况');
    $('#ryqkfm').form('clear');
    $("#saveryqk"). removeClass("savedisplay");//让保存按钮显示
    /*设置发布人*/
    $('#rqzprxm').combobox('setValue', dlzh);
    url = "/ENOS/ryqk/save";
}
 
/*编辑工作记录*/
function editryqk(){ 
	var rows = $('#ryqk').datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if(rows.length>1){
    	 $.messager.alert('提示框','每次只能修改一条数据','info');
	   	 return;
    }
//    if($('#rqzprxm').combobox('getValue')!=dlzh){
//		 $.messager.alert('提示框','只能对自己发布信息进行修改','info');
//		 return;
//	}
    if (rows){
        $('#ryqkdlg').dialog('open').dialog('center').dialog('setTitle','修改人员情况');
        $('#ryqkfm').form('load',rows[0]);
        $("#saveryqk"). removeClass("savedisplay");//让保存按钮显示
//        $('#ryqkrxm').combobox('setValue', dlzh);
        url = "/ENOS/ryqk/save?rq_id="+rows[0].rq_id;
    }
}
/*保存工作记录*/
function saveryqk(){
	
	if($('input[name="rqksrq"]').val()==""){
		 $.messager.alert('提示框','请填写日期','info');
		 return;
	}
	if($('#rqzprxm').combobox('getValue')!=dlzh){
		 $.messager.alert('提示框','指派人应选择当前登录人','info');
		 return;
	}
	if($('#rqzprxm').combobox('getValue')==$('#rqcxxm').combobox('getValue')){
		 $.messager.alert('提示框','指派人和参项人不能是同一个人','info');
		 return;
	}
	
    $('#ryqkfm').form('submit',{
        url:url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            $.messager.alert('提示框',result.ryqkmsg,'info');
            if (result.errorMsg){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
                });
            } else {
                $('#ryqkdlg').dialog('close');        // close the dialog
                searchryqk();
            }
        }
    });
}
 
/*查询表单重置*/
$('.ryqkcz').on('click',function(){
	 $('#rqcxxmcx').combobox('setValue', ""); 
	 $('#rqxmcx').combobox('setValue', ""); 
	 $('#rqbmmccx').combobox('setValue', ""); 
	 $('#rqjslbcx').val("");
	 searchryqk();
});

/*表单查询*/
$('.cxryqk').on('click',function(){
	
	 var formData  = new FormData();
	 var pageNumber=$('#ryqk').datagrid('getPager').data("pagination").options.pageNumber;
     var pageSize=$('#ryqk').datagrid('getPager').data("pagination").options.pageSize;
     var page = 0;
 	 if(pageNumber!=0){
 		page = pageNumber-1;
 	 }
     
	 var rqcxxmcx =  $('#rqcxxmcx').combobox('getValue' ); 
	 var rqxmcx =  $('#rqxmcx').combobox('getValue' ); 
	 var rqbmmccx =  $('#rqbmmccx').combobox('getValue' ); 
	 var rqjslbcx = $('#rqjslbcx').val();
	 var rqksrqcx = $('#rqksrqcx').val();
	 
	 formData.append("rqcxxm",rqcxxmcx);
  	 formData.append("rqxm",rqxmcx);
  	 formData.append("rqbmmc",rqbmmccx);
	 formData.append("rqjslb",rqjslbcx);
	 formData.append("rqksrq",rqksrqcx);
  	  
   	 
   	 $.ajax({
         type:'post',
         url:"/ENOS/ryqk/find?page="+page+"&size="+pageSize+ "",
         processData: false,
         contentType: false,
         async:false,
         data:formData,
         success: function (data) {
        	   $("#ryqk").datagrid('loadData',data); 
         },
         error: function ( ) {
        		$.messager.alert('提示框',"查询人员情况出错",'info');
          }
      });
  	 
});

 












