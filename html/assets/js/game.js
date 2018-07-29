function request(msg,callback) {
  var sid = $("#sid").val();
  var sent = JSON.stringify({'sid':parseInt(sid),'msg':JSON.stringify(msg)});
  $.ajax({
    url: "/api/game",
    type: "POST",
    contentType: 'application/json',
    data: JSON.stringify({'sid':parseInt(sid),'msg':JSON.stringify(msg)}),
    success: function(str,status){
      var data = JSON.parse(str);
      log(str);
      log(state);
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

function do_nothing(){}

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

$("#shutdown").click(function(){
  $("#console").html("console:");
  $.ajax({
    url: "/api/shutdown",
    type: "POST",
    success: function(){
      log("shutdown success");
    }
  });
});

var state="Init";
$("#ok").click(function(){
  var arg0 = $("#arg0").val();  //card ok
  var arg1 = $("#arg1").val();  //
  var arg2 = $("#arg2").val();
  var arg3 = $("#arg3").val();
  var arg4 = $("#arg4").val();
  var arg5 = $("#arg5").val();
  
  var req = {};

  if(state=="Init"){
  }else if(state=="choose hero"){
    req = {
      hero:"hero"
    };
  }else if(state=="start game"){
  }else if(state=="choose strategy decision"){
    req = {
      decision: parseInt(arg0), 
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
  }else if(state=="win judge"){
  }else if(state=="ACTION: deposit account"){
  }else if(state=="skills account"){
  }else if(state=="fire account"){
  }else if(state=="move account"){
  }else if(state=="element account"){
  }else if(state=="if human wins"){
  }else if(state=="Game Over, human wins"){
  }else if(state=="infection account"){
  }else if(state=="Game Over,zombie wins"){
  }else if(state=="desert account"){
    req = {
      desertCardList:[parseIn(arg0)]
    }
  }
  request(req,do_nothing);
});
