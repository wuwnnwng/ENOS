/**
 * 
 */
var url;
var dlzh = $('.dlzh').html();
$('#sbdkli').on('click',function(){
	seachsbdk();
});
function seachsbdk(){
	 var url="/ENOS/sbdk/find?dlzh="+dlzh+""
     $('#sbdk').datagrid('options').url = url;
	 $("#sbdk").datagrid('reload'); 
} 
/*新建上班打卡*/
function sbdk(){
  	 $.ajax({
        type:'post',
        url:"/ENOS/sbdk/newsbdk",
        processData: false,
        contentType: false,
        async:false,
        data:"",
        success: function (data) {
//       	   $("#sbdk").datagrid('loadData',data); 
        	  $.messager.alert('提示框', data.sbdkmsg,'info');
          	  seachsbdk();
        },
        error: function ( ) {
       		$.messager.alert('提示框',"上班打卡出错",'info');
         }
     });
 	 
}
 

/*下班打卡*/
function xbdk(){ 
	 
	 
 	 $.ajax({
       type:'post',
       url:"/ENOS/sbdk/editsbdk",
       processData: false,
       contentType: false,
       async:false,
       data:"",
       success: function (data) {
//      	   $("#sbdk").datagrid('loadData',data); 
      	   $.messager.alert('提示框', data.sbdkmsg,'info');
      	  seachsbdk();
       },
       error: function ( ) {
      		$.messager.alert('提示框',"下班打卡出错",'info');
        }
    });
	 
}
 












