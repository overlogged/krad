var state_changepw = {
  preload: function () {
    game.add.image(0, 0, 'background');
    game.add.bitmapText(300, 130, 'chiller', 'Change Password', 70);
    game.add.bitmapText(205, 195, 'chiller', 'OldPassword', 54);
    game.add.bitmapText(195, 255, 'chiller', 'NewPassword', 54);
    game.add.bitmapText(260, 315, 'chiller', 'Confirm', 54);

    change = game.add.button(300, 380, 'button', do_change, this, 1, 0, 2, 0);
    game.add.bitmapText(310, 385, 'chiller', 'Confirm', 28);

    backcp = game.add.button(420, 380, 'button', go_backcp, this, 1, 0, 2, 0);
    game.add.bitmapText(440, 385, 'chiller', 'Back', 28);

    tooltip8 = game.add.sprite(470, 415, 'tooltip8');
    tooltip9 = game.add.sprite(350, 415, 'tooltip9');

    tooltip8.inputEnabled = true;
    tooltip9.inputEnabled = true;
    show("changepw");
    tooltip_changepw();

  },
  update: function(){
    if(star){
      if(change.input.pointerOver()){
        tooltip9.alpha=0.7;
      }
      else{
        tooltip9.alpha=0;
      }
      if(backcp.input.pointerOver()){
        tooltip8.alpha=0.7;
      }
      else{
        tooltip8.alpha=0;
      }
      
    }
  }  
}

function tooltip_changepw(){
  star = true;
}

function go_backcp(){
  game.state.start('userinterface');
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
    'old_password': oldpassword,
    'new_password': newpassword
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