var state_changeprofile = {
  preload: function () {
    game.add.sprite(0, 0, 'background');
    game.add.bitmapText(280, 130, 'chiller', 'Change Profile', 70);
    game.add.bitmapText(260, 230, 'chiller', 'Nickname', 54);
    game.add.bitmapText(280, 290, 'chiller', 'Gender', 54);
    game.add.bitmapText(435, 290, 'chiller', 'Boy', 54);
    game.add.bitmapText(530, 290, 'chiller', 'Girl', 54);
    game.add.bitmapText(290, 345, 'chiller', 'Avatar', 54);

    game.add.button(395, 400, 'button', do_changepf, this, 1, 0, 2);
    game.add.bitmapText(415, 400, 'chiller', 'Confirm', 28);

    game.avatar = game.add.sprite(620, 280, 'figure1');
    show("changeprofile");
  }
}

function update_avatar_changeprofile() {
  game.avatar.kill();
  console.log($("#changeproflie_avatar").val());
  game.avatar = game.add.sprite(620, 280, $("#changeproflie_avatar").val());
}

function do_changepf() {
  var nickname = $("#changeprofile_nickname").val();
  var avatar = "assets/img/figure/" + $("#changeprofile_avatar").val() + ".png";
  var gender = parseInt($("input[name='gender']:checked").val());
  var req = {
    'nickname': nickname,
    'avatar': avatar,
    'gender': gender,
  }

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
      game.state.start('home'); // 跳转用户界面
    },
    error: function (data, status) {
      alert("修改失败");
      console.log(data);
    }
  });
}