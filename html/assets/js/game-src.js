var sid = 0

function request(msg,callback) {
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
// for continuation.js
arguments = {}

// choose hero
request({},cont(data,status));

console.log(data);