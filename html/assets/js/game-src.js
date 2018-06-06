function request(msg,callback) {
  var sid = $("#sid").val();
  console.log(sid);
  $.ajax({
    url: "/api/game",
    type: "POST",
    contentType: 'application/json',
    data: JSON.stringify({'sid':sid,'msg':JSON.stringify(msg)}),
    success: callback,
    error: function (data, status) {
      console.log(data);
    }
  });
}

function log(x){
  console.log(x);
  $("#console").innerHTML = $("#console").innerHTML + "\n" + x;
}

// for continuation.js
arguments = {}

// choose hero
request({},cont(data,status));
log(data);