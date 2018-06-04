var state_changepw = {
  preload: function () {
    game.add.image(0, 0, 'background');
    game.add.bitmapText(300, 130, 'chiller', 'Change Password', 70);
    game.add.bitmapText(205, 195, 'chiller', 'OldPassword', 54);
    game.add.bitmapText(195, 255, 'chiller', 'NewPassword', 54);
    game.add.bitmapText(260, 315, 'chiller', 'Confirm', 54);
    game.add.button(300, 380, 'button', do_change, this, 1, 0, 2, 0);
    game.add.bitmapText(310, 385, 'chiller', 'Confirm', 28);
    game.add.button(420, 380, 'button', go_backlogincp, this, 1, 0, 2, 0);
    game.add.bitmapText(440, 385, 'chiller', 'Back', 28);
    show("changepw");
  }  
}

function go_backlogincp(){
  game.state.start('login');
}

function do_change() {
  var oldpassword = $("#changepw_oldpassword").val();
  var newpassword = $("#changepw_newpassword").val();
  var confirm = $("#changepw_confirm").val();
  if (newpassword != confirm) {
    alert("两次密码填写不一样");
    return;
  }
  var req = {
    'sid':sid,
    'oldpassword': oldpassword,
    'newpassword': newpassword
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
      game.state.start('userinterface');
    },
    error: function (data, status) {
      alert("修改失败");
      console.log(data);
    }
  });
}