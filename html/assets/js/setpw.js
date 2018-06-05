var state_setpw = {
  preload: function () {
    game.add.image(0, 0, 'background');
    game.add.bitmapText(270, 130, 'chiller', 'Modify Password', 70);
    game.add.bitmapText(275, 195, 'chiller', 'Password', 54);
    game.add.bitmapText(280, 255, 'chiller', 'Confirm', 54);

    game.add.button(400, 320, 'button', do_confirm, this, 1, 0, 2, 0);
    game.add.bitmapText(420, 325, 'chiller', 'Confirm', 28);
  }
}

function do_confirm() {
  var password = $("#setpw_password").val();
  var confirm = $("#setpw_confirm").val();
  if(confirm!=password){
      alert("两次密码填写不一样");
      return;
  }
  var url = window.location.href;
  var str = url.split("?")[1];
  var sid = parseInt(str.split("=")[1]); 
  var req = {

    'sid':sid,
    'new_password':password

  };
  console.log(req);
  var v_password = /[a-zA-Z0-9]{6,18}/.test(password);
  if (!v_password) {
    alert("密码格式不正确");
    return;
  }
  $.ajax({
    url: "/api/user/setpw",
    type: "POST",
    contentType: 'application/json',
    data: JSON.stringify(req),
    success: function (data, status) {
      alert("修改成功");
      console.log(data);
      console.log(sid);
      game.state.start('login'); //需要跳转的地址
    },
    error: function (data, status) {
      alert("修改失败");
      console.log(data);
      console.log(sid);
    }
  });
}