function request(req,callback) {
  $.ajax({
    url: "/api/game",
    type: "POST",
    contentType: 'application/json',
    data: JSON.stringify(req),
    success: callback,
    error: function (data, status) {
      console.log(data);
    }
  });
}

request({},cont(data,status));
console.log(data);