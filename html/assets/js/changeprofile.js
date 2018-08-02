var state_changeprofile = {
  preload: function () {
    game.add.sprite(0, 0, 'background');
    game.add.bitmapText(280, 130, 'chiller', 'Change Profile', 70);
    game.add.bitmapText(260, 230, 'chiller', 'Nickname', 54);
    game.add.bitmapText(280, 290, 'chiller', 'Gender', 54);
    game.add.bitmapText(435, 290, 'chiller', 'Boy', 54);
    game.add.bitmapText(530, 290, 'chiller', 'Girl', 54);
    game.add.bitmapText(290, 345, 'chiller', 'Avatar', 54);

    tooltip8 = game.add.sprite(515, 445, 'tooltip8');
    tooltip9 = game.add.sprite(375, 445, 'tooltip9');

    tooltip8.inputEnabled = true;
    tooltip9.inputEnabled = true;

    changepf = game.add.button(325, 410, 'button', do_changepf, this, 1, 0, 2, 0);
    game.add.bitmapText(340, 415, 'chiller', 'Confirm', 28);



    backpf = game.add.button(465, 410, 'button', do_backpf, this, 1, 0, 2, 0);
    game.add.bitmapText(490, 415, 'chiller', 'Back', 28);

    game.avatar = game.add.sprite(10, 250, 'figure1');
    show("changeprofile");
    tooltip_changeprofile()
  },
  update: function(){
    if(star){
      if(changepf.input.pointerOver()){
        tooltip9.alpha=0.7;
      }
      else{
        tooltip9.alpha=0;
      }
      if(backpf.input.pointerOver()){
        tooltip8.alpha=0.7;
      }
      else{
        tooltip8.alpha=0;
      }
      
    }
  }
}

function tooltip_changeprofile(){
  star = true;
}
function do_backpf(){
  game.state.start('userinterface');
}

function update_avatar_changeprofile() {
  game.avatar.kill();
  game.avatar = game.add.sprite(10, 250, $("#changeprofile_avatar").val());
}

function do_changepf() {
  var nickname = $("#changeprofile_nickname").val();
  var avatar =  $("#changeprofile_avatar").val();
  var gender = parseInt($("input[name='gender']:checked").val());
  var req = {
    'sid':sid,
    'nickname': nickname,
    'avatar': avatar,
    'gender': gender
  };

  console.log(req);
  var v_nickname = /{};:'/.test(nickname);

  // todo: 判断图像地址
  if (v_nickname) {
    alert("昵称含有非法字符");
    return;
  }

  $.ajax({
    url: "/api/user/changeprofile",
    type: "POST",
    contentType: 'application/json',
    data: JSON.stringify(req),
    success: function (data, status) {
      alert("修改成功");
      console.log(data);
      game.state.start('userinterface'); // 跳转用户界面
    },
    error: function (data, status) {
      alert("修改失败");
      console.log(data);
    }
  });
}