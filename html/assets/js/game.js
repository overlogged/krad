function request(msg, callback) {
  $.ajax({
    url: '/api/game',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify({
      'sid': sid,
      'msg': JSON.stringify(msg)
    }),
    success: callback,
    error: function (data, status) {
      console.log(data);
    }
  });
}
var sid, data, status;
sid = 0;
arguments = {};
request({}, function (arguments, _$param0, _$param1) {
  data = _$param0;
  status = _$param1;
  console.log(data);
}.bind(this, arguments));
/* Generated by Continuation.js v0.1.7 */
