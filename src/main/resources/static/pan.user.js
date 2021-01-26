// ==UserScript==
// @name         网盘助手
// @namespace    http://tampermonkey.net/
// @version      0.2
// @description  支持云盘填充密码，百度云分享自定义提取码
// @author       XanderYe
// @require      https://lib.baomitu.com/jquery/3.5.0/jquery.min.js
// @supportURL   https://www.xanderye.cn/
// @updateURL    https://github.com/XanderYe/pan/raw/master/src/main/resources/static/pan.user.js
// @match        *://pan.baidu.com/*
// @match        *://yun.baidu.com/*
// @match        *://*.lanzous.com/*
// @match        *://cloud.189.cn/t/*
// ==/UserScript==

var jQ = $.noConflict(true);
jQ(function ($) {
  var share = {
    id: null,
    shareId: null,
    sourceType: 0,
    password: null,
    url: location.href
  };
  var inputDom;
  var clickBtnDom;
  var queryPwd = false;
  // 自动点击提取文件
  var autoClick = false;
  // 增强功能 防止冲突默认关闭
  var optimize = false;

  checkSourceType();

  if (queryPwd) {
    console.log("检测到有密码，查询密码");
    getPwd(function () {
      initData();
    });
  }

  function checkSourceType() {
    var href = location.href;
    if (href.indexOf("baidu") > -1) {
      share.sourceType = 0;
      share.shareId = getBaiduShareId();
      inputDom = $("#accessCode");
      clickBtnDom = $("#submitBtn");
      if (optimize) {
        baiduOptimize();
      }
    } else if (href.indexOf("lanzous") > -1) {
      share.sourceType = 1;
      share.shareId = getLanzouShareId();
      inputDom = $("#pwd");
      clickBtnDom = $("#sub");
    } else if (href.indexOf("189.cn") > -1) {
      share.sourceType = 2;
      share.shareId = getTianyiShareId();
      inputDom = $("#code_txt");
      clickBtnDom = $(".visit").eq(0);
    }
    if (inputDom.length == 1) {
      queryPwd = true;
      inputDom.bind("input", function () {
        share.password = inputDom.val();
      })
    }
  }

  function getBaiduShareId() {
    var match;
    match = location.href.match(/share\/init\?surl=([a-z0-9-_]+)/i);
    if (match) {
      return match[1];
    }
    match = location.pathname.match(/\/s\/1([a-z0-9-_]+)/i);
    if (match) {
      return match[1];
    }
    return null;
  }

  function getLanzouShareId() {
    return location.pathname.substring(1);
  }

  function getTianyiShareId() {
    return location.pathname.substring(location.pathname.lastIndexOf("/") + 1);
  }

  function getPwd(callback) {
    var params = {
      shareId: share.shareId,
      sourceType: share.sourceType
    };
    $.ajax({
      url: "https://www.xanderye.cn/pan/share/getPassword",
      type: "GET",
      data: params,
      success: function (data) {
        if (data.code == 0) {
          share.password = data.data.password;
        }
        if (callback) {
          callback();
        }
      },
      error: function () {
        if (callback) {
          callback();
        }
      }
    })
  }

  function savePwd(callback) {
    $.ajax({
      url: "https://www.xanderye.cn/pan/share/savePassword",
      type: "POST",
      data: JSON.stringify(share),
      contentType: "application/json",
      dataType: "json",
      success: function (data) {
        if (data.code == 0) {
          console.log("保存成功");
        } else {
          console.error(data.msg);
        }
        if (callback) {
          callback();
        }
      },
      error: function () {
        if (callback) {
          callback();
        }
      }
    })
  }

  function initData() {
    if (share.password) {
      console.log("获取到分享密码", share.password);
      console.log("填写密码");
      inputDom.val(share.password);
      if (autoClick) {
        setTimeout(function () {
          clickBtnDom.trigger("click");
        })
      }
    } else {
      console.log("未获取到分享密码，绑定分享密码事件");
      clickBtnDom.bind("click", function () {
        if (share.password) {
          console.log("提交分享密码", share);
          savePwd();
        }
      })
    }
  }

  function baiduOptimize() {
    $(document).on("click", "a[title=分享]", function () {
      setTimeout(function () {
        if ($(".input-share-pwd").length == 0) {
          var html = '<tr><td class="first-child"><label>提取码</label></td><td><input type="text" class="input-share-pwd" id="input-share-pwd" value="" placeholder="为空则随机四位" style="padding: 6px; width: 80px;border: 1px solid #e9e9e9;"></td></tr>';
          $("#share .dialog-body table").append(html);
          $(document).on("change", "#input-share-pwd", function () {
            var value = this.value;
            if (value && !value.match(/^[0-9a-z]{4}$/i)) {
              showTip("failure", "提取码只能是四位数字或字母");
            } else {
              require(["function-widget-1:share/util/shareFriend/createLinkShare.js"]).prototype.makePrivatePassword=function(){return value};
            }
          })
        } else {
          $("#input-share-pwd").val("");
        }
      }, 100)
    })
  }

  function showTip(mode, msg, hasClose, autoClose) {
    var option = {
      mode: mode,
      msg: msg
    };
    // 关闭按钮
    if (typeof hasClose != "undefined") {
      option.hasClose = hasClose;
    }
    // 自动关闭
    if (typeof autoClose != "undefined") {
      option.autoClose = autoClose;
    }
    require("system-core:system/uiService/tip/tip.js").show(option);
  }
})
