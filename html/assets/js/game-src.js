function request(msg,callback) {
  var sid = $("#sid").val();
  console.log(sid);
  $.ajax({
    url: "/api/game",
    type: "POST",
    contentType: 'application/json',
    data: JSON.stringify({'sid':parseInt(sid),'msg':JSON.stringify(msg)}),
    success: function(data,status){
      log(data);
      state = data.state;
      callback(data,status);
    },
    error: function (data, status) {
      console.log(data);
    }
  });
}

function log(x){
  console.log(x);
  $("#console").html($("#console").html()+"<br></br>"+x);
}

$("#restart").click(function(){
  $("#console").html("console:");
  $.ajax({
    url: "/api/restart",
    type: "POST",
    success: function(){
      log("restart success");
    }
  });
});

// for continuation.js
arguments = {}

var state="Init";
$("#ok").click(function(){
  var arg0 = $("#arg0").val();
  var arg1 = $("#arg0").val();
  var arg2 = $("#arg0").val();
  var arg3 = $("#arg0").val();
  var arg4 = $("#arg0").val();
  var arg5 = $("#arg0").val();
  
  var req = {};

  if(state=="Init"){
  }else if(state=="Choose hero"){
    req = {
      hero:"hero"
    };
  }else if(state=="Start game"){
  }else if(state=="choose strategy decision"){
    req = {
      decision: parseInt(arg0)
    }
  }else if(state=="choose the feature of the decision"){
    req = {
      moveDirection: parseInt(arg0),
      fireTarget: parseInt(arg1)
    }
  }else if(state=="choose seen card"){
    req = {
      seenCard: parseInt(arg0)
    }
  }else if(state=="GAMBLE:choose gamble"){
    req = {
      gambleCard:[parseInt(arg0)]
    }
  }
  request(req,cont(data,status));
});


