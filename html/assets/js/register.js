var state_register = {
  preload: function () {
    game.add.image(0, 0, 'background');
    game.add.bitmapText(350, 55, 'chiller', 'Register', 70);
    game.add.bitmapText(315, 115, 'chiller', 'Email', 54);
    game.add.bitmapText(260, 170, 'chiller', 'Nickname', 54);
    game.add.bitmapText(280, 225, 'chiller', 'Gender', 54);
    game.add.bitmapText(435, 225, 'chiller', 'Boy', 54);
    game.add.bitmapText(530, 225, 'chiller', 'Girl', 54);
    game.add.bitmapText(265, 275, 'chiller', 'Password', 54);
    game.add.bitmapText(275, 330, 'chiller', 'Confirm', 54);
    game.add.bitmapText(290, 385, 'chiller', 'Avatar', 54);

    game.add.button(400, 440, 'button', do_register, this, 1, 0, 2, 0);
    game.add.bitmapText(420, 445, 'chiller', 'Confirm', 28);

    game.avatar = game.add.sprite(620, 360, 'figure1');
    
    show("register");
  }
}

function update_avatar() {
  game.avatar.kill();
  game.avatar = game.add.sprite(620, 280, $("#register_avatar").val());
}

function do_register() {
  var email = $("#register_email").val();
  var nickname = $("#register_nickname").val();
  var password = $("#register_password").val();
  var confirm = $("#register_confirm").val();
  var avatar = "assets/img/figure/" + $("#register_avatar").val()+".png";
  var gender = $("input[name='gender']:checked").val();
  if (password != confirm) {
    alert("两次密码填写不一样");
    return;
  }
  var req = {
    'email': email,
    'nickname': nickname,
    'password': password,
    'avatar': avatar,
    'gender': gender,
  };

  console.log(req);
  var v_email = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(email);
  var v_password = /[a-zA-Z0-9]{6,18}/.test(password);
  var v_nickname = /{};:'/.test(nickname);

  // todo: 判断图像地址
  if (!v_email || !v_password) {
    alert("邮箱格式或密码格式不正确");
    return;
  }
  if (v_nickname) {
    alert("昵称含有非法字符");
    return;
  }

  $.ajax({
    url: "/api/session/register",
    type: "POST",
    contentType: 'application/json',
    data: JSON.stringify(req),
    success: function (data, status) {
      alert("账号注册成功");
      console.log(data);
      sid = data.sid;
      user = data.user;
      game.state.start('home');
    },
    error: function (data, status) {
      alert("账号注册失败");
      console.log(data);
    }
  });
}