$(document).ready(function(){
	/*
	$(document).ajaxStart(function(){
		$("#loading").show();
	}).ajaxStop(function(){
		$("#loading").hide();
	});
*/
});
function post(url,params,sucessFun,failFun){
	$.post(url,params,function(data){
		if(data && data.code=='403'){
			window.location.href="/auth.htm?n_url="+data.n_url;
		}
		sucessFun(data);
	},"json").fail(function(jqXHR, textStatus, errorThrown){
		if(failFun){
			failFun();
		}
	});
}
function stringFormat(str,p){
	for(i=0;i<p.length;i++){
		var re = new RegExp("\\{"+i+"\\}","gm");
		str = str.replace(re,p[i]);
	}
	return str;
}
	
function checkNum(str){
	var re = /^[0-9]\d*$/;
	if (!re.test(str)){
		return false;
	}
	return true;
}

function isBlank(str){
	if(str==""){
		return true;
	}
	return false;
}
/*英文+数字*/
function isText(str){
	var re = /^[A-Za-z0-9]+$/;
	if (!re.test(str)){
		return false;
	}
	return true;
}

function setCookie(name,value,exp){
	var dt  = new Date();
	if(exp == -1){
		dt.setTime(dt.getTime() + 360*24*60*60*1000);
	}else{
		dt.setTime(dt.getTime() + exp*24*60*60*1000);
	}
	
	document.cookie = name + "="+ escape (value) + ";expires=" + dt.toGMTString();
}
function getCookie(name){
	var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
	if(arr != null) return unescape(arr[2]); 
	return null;
}