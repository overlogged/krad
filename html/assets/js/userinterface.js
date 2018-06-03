var state_userinterface = {
    preload: function () {
      var background = game.add.sprite(0, 0, 'background');
      var userfigure = game.add.sprite(-150, -100, 'userfigure');
      var figurepshow1 = game.add.sprite(350, 20, 'figure1');
      var figurepshow2 = game.add.sprite(350, 140, 'figure2');
      var figurepshow3 = game.add.sprite(350, 260, 'figure3');
      var figurepshow4 = game.add.sprite(480, 20, 'figure4');
      var figurepshow5 = game.add.sprite(480, 140, 'figure5');
      var figurepshow6 = game.add.sprite(480, 260, 'figure6');
      background.smoothed = true;
      background.height = game.height;
      background.width = game.width;
      var user_nickname;
      var user_avatar;

      var req = {
        'sid':sid
      };

      $.ajax({
      url: "/api/user?sid＝"+sid,
      type: "GET",
      contentType: 'application/json',
      data: req,
      success: function (data, status) {
        console.log(data);
        console.log(data.nickname);
        console.log(data.avatar);
        user_nickname = data.nickname;
        user_avatar = data.avatar


          // window.location.href = ""; //需要跳转的地址
      },
      error: function (data, status) {
        alert("获取信息错误");
        console.log(data);
      }
    });
    console.log(data.nickname);

      var infectedText = game.add.bitmapText(500, 80, 'chiller', 'infected', 54); 
      var tacticalText = game.add.bitmapText(650, 80, 'chiller', 'tactical', 54);
        

      var nicknameText = game.add.bitmapText(120, 20, 'chiller', 'nickname:', 54);
      var usernickname = game.add.bitmapText(260, 20, 'chiller', user_nickname, 54);
      var gradeText = game.add.bitmapText(120, 60, 'chiller', 'grade:', 54);
      var usergrade = game.add.bitmapText(215, 60, 'chiller', user_avatar, 54);
      usernickname.tacticalText = user_nickname;
        
        
      var changepw = game.add.button(340, 70, 'button', do_changepw, this, 1, 0, 2, 0);
      var changepwText = game.add.bitmapText(360, 70, 'chiller', 'password', 28);

      var changeprofile = game.add.button(340, 30, 'button', do_changeprofile, this, 1, 0, 2, 0);
      var changeprofileText = game.add.bitmapText(360, 30, 'chiller', 'profile', 28);

      var startgame = game.add.button(150, 100, 'start', do_match, this);
      var userhelp = game.add.button(-150, 200, 'help', do_help, this);
      show("userinterface");
    }
  }
  
function do_help() {
    game.state.start('help');

}

function do_match(){
  var req = {
    'sid':sid
  };
  $.ajax({
    url: "/api/session/match",
    type: "POST",
    contentType: 'application/json',
    data: JSON.stringify(req),
    success: function (data, status) {
      alert("正在匹配");
      console.log(data);
      console.log(data.sid);
      console.log(data.user);
      // window.location.href = ""; //需要跳转的地址
    },
    error: function (data, status) {
      alert("匹配失败");
      console.log(data);
      consloe.log(data.nickname);
    }
  });
}


function do_changepw() {
  game.state.start('changepw');
}


function do_changeprofile() {
  game.state.start('changeprofile');
}