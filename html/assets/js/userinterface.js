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

    game.add.bitmapText(120, 20, 'desyrel', 'nickname:', 36);
    game.add.bitmapText(285, 20, 'desyrel', user.nickname, 36);
    game.add.bitmapText(120, 60, 'desyrel', 'grade:', 36);
    game.add.bitmapText(215, 60, 'desyrel', user.stats.score.toString(), 36);


    game.add.button(240, 115, 'button', go_changepw, this, 1, 0, 2, 0);
    game.add.bitmapText(255, 115, 'chiller', 'password', 28);

    game.add.button(120, 115, 'button', go_changeprofile, this, 1, 0, 2, 0);
    game.add.bitmapText(145, 115, 'chiller', 'profile', 28);

    game.add.button(250, 210, 'start', do_match, this);
    game.add.button(50, 400, 'help', go_help, this);
    show("userinterface");
  }
}

function go_help() {
  game.state.start('help');
}

function do_match() {
  var req = {
    'sid': sid
  };
  //alert("开始匹配，请耐心等待");
  $.ajax({
    url: "/api/session/match",
    type: "POST",
    contentType: 'application/json',
    data: JSON.stringify(req),
    beforeSend:function(){ 

      $("#loading").html("<img src='loading'>");

   }, 
    success: function (data, status) {
      $("#loading").empty();
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