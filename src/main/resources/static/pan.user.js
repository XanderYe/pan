// ==UserScript==
// @name         云盘填充密码
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  云盘填充密码
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
  var autoClick = false;

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
})
