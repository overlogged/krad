var state_userinterface = {
  preload: function () {
    game.add.sprite(0, 0, 'background');

    game.add.sprite(350, 20, 'figure1');
    game.add.sprite(350, 140, 'figure2');
    game.add.sprite(350, 260, 'figure3');
    game.add.sprite(480, 20, 'figure4');
    game.add.sprite(480, 140, 'figure5');
    game.add.sprite(480, 260, 'figure6');

    game.add.bitmapText(500, 80, 'chiller', 'infected', 54);
    game.add.bitmapText(650, 80, 'chiller', 'tactical', 54);
    game.add.sprite(-150, -100, user.avatar);

    game.add.bitmapText(120, 20, 'chiller', 'nickname:', 54);
    game.add.bitmapText(260, 20, 'chiller', user.nickname, 54);
    game.add.bitmapText(120, 60, 'chiller', 'grade:', 54);
    game.add.bitmapText(215, 60, 'chiller', user.stats.score.toString(), 54);


    game.add.button(360, 70, 'button', go_changepw, this, 1, 0, 2, 0);
    game.add.bitmapText(380, 70, 'chiller', 'password', 28);

    game.add.button(360, 30, 'button', go_changeprofile, this, 1, 0, 2, 0);
    game.add.bitmapText(380, 30, 'chiller', 'profile', 28);

    game.add.button(150, 100, 'start', do_match, this);
    game.add.button(-150, 200, 'help', go_help, this);
    show("userinterface");
  }
}

function go_help() {
  // game.state.start('help');
  alert("暂无帮助");
}

function do_match() {
  var req = {
    'sid': sid
  };
  alert("开始匹配，请耐心等待");
  $.ajax({
    url: "/api/session/match",
    type: "POST",
    contentType: 'application/json',
    data: JSON.stringify(req),
    success: function (data, status) {
      console.log(data);
      window.location.href="gamewindow.html?sid="+sid;  
    },
    error: function (data, status) {
      alert("匹配失败");
      console.log(data);
    }
  });
}


function go_changepw() {
  game.state.start('changepw');
}


function go_changeprofile() {
  game.state.start('changeprofile');
}