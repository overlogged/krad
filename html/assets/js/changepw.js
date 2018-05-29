var state_changepw = {
  preload: function () {
    game.add.image(0, 0, 'background');
    game.add.bitmapText(300, 130, 'chiller', 'Change Password', 70);
    game.add.bitmapText(205, 195, 'chiller', 'OldPassword', 54);
    game.add.bitmapText(195, 255, 'chiller', 'NewPassword', 54);
    game.add.bitmapText(260, 315, 'chiller', 'Confirm', 54);
    game.add.button(360, 380, 'button', do_change, this, 1, 0, 2, 0);
    game.add.bitmapText(380, 380, 'chiller', 'Confirm', 28);
    show("changepw");
  }  
}

function do_change() {
  var oldpassemail = $("#changepw_oldpassword").val();
  var newpassword = $("#changepw_newpassword").val();
  var confirm = $("#changepw_confirm").val();
  if (newpassword != confirm) {
    alert("两次密码填写不一样");
    return;
  }
  var req = {
    'sid':sid,
    'oldpassword': oldpassword,
    'newpassword': newpassword,
  };
  console.log(req);
  var v_password = /[a-zA-Z0-9]{6,18}/.test(newpassword);
  if (!v_password) {
    alert("密码格式不正确");
    return;
  }
  $.ajax({
    url: "/api/user/changepw",
    type: "POST",
    contentType: 'application/json',
    data: JSON.stringify(req),
    success: function (data, status) {
      alert("修改成功");
      console.log(data);
      game.state.start('home');
    },
    error: function (data, status) {
      alert("修改失败");
      console.log(data);
    }
  });
}