
//请求根路径
window.webroot = "http://localhost:8088/";

function getById(id) {
    return document.getElementById(id);
}

//是否是string类型
function isString(s) {

    return typeof( s ) == "string";
};

//取url参数
function queryString(name) {
    /// 获取QueryString
    var AllVars = window.location.search.substring(1);
    var Vars = AllVars.split("&");
    for (var i = 0; i < Vars.length; i++) {
        var Var = Vars[i].split("=");
        if (Var[0] == name) return Var[1];
    }
    return "";
};


/**
 公用ajax加载，请求示例
 new AjaxRequest({
        type: "get",
        url: "https://5ae979d7531a580014142797.mockapi.io/api/v1/records",
        param: "",
        isShowLoader: true,
        success : function(res){
            console.log(res);
        }
    });
 */
(function(){
    function AjaxRequest(opts){
        this.type         = opts.type || "get";
        this.url          = opts.url;
        this.param        = opts.param || {};
        this.isShowLoader = opts.isShowLoader || false;
        this.success      = opts.success;
        this.error        = opts.error;
        this.init();
    }
    var layIndex = 0;
    AjaxRequest.prototype = {
        //初始化
        init: function(){
            this.sendRequest();
        },
        //渲染loader
        showLoader: function(){
            if(this.isShowLoader){
                layIndex = layer.load(2);
            }
        },
        //隐藏loader
        hideLoader: function(){
            if(this.isShowLoader){
                layer.close(layIndex);
            }
        },
        //发送请求
        sendRequest: function(){
            var self = this;
            $.ajax({
                type: this.type,
                url: this.url,
                data: this.param,
                contentType: 'application/json',
                dataType: 'json',
                beforeSend: this.showLoader(),
                success: function(res){
                    self.hideLoader();
                    if (res != null && res != "") {
                        if(self.success){
                            //Object.prototype.toString.call方法--精确判断对象的类型
                            if (Object.prototype.toString.call(self.success) === "[object Function]") {
                                self.success(res);
                            }else{
                                console.log("callBack is not a function");
                            }
                        }
                    }
                },
                error: function (xhr, type, errorThrown) {
                    console.log("error:" + JSON.stringify(xhr) + "\n" + type + "\n" + errorThrown);
                    self.hideLoader();
                    if(self.error){
                        if (Object.prototype.toString.call(self.error) === "[object Function]") {
                            self.error(xhr, type, errorThrown);
                        }else{
                            console.log("callBack is not a function");
                        }
                    }
                }
            });
        }
    };
    window.AjaxRequest = AjaxRequest;
})();


//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
//例子：
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}


/** ---- Cookie ---- **/
function setCookie(key, value) {
    var exp = new Date();
    exp.setTime(exp.getTime() + (1000 * 60 * 60 * 12)); // 过期时间12小时
    var cookie = key + "=" + encodeURIComponent(value) + "; expires=" + exp.toUTCString() + "; path=/";
    window.document.cookie = cookie;
}

function getCookie(key) {
    var idx = document.cookie.indexOf(key + "=");
    if (idx === -1) return;

    var start = idx + key.length + 1,
        end = document.cookie.indexOf(";", idx);
    if (end === -1) end = document.cookie.length;

    return decodeURIComponent(document.cookie.substring(start, end));
}

function delCookie(key) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cookie = getCookie(key);
    if (cookie) {
        var delCookie = key + "=" + escape(cookie) + ";expires=" + exp.toUTCString() + "; path=/";
        window.document.cookie = delCookie;
    }
}

