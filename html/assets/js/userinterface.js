var state_userinterface = {
  preload: function () {
    game.add.sprite(0, 0, 'background');

    game.add.sprite(350, 20, 'figure1');
    game.add.sprite(350, 140, 'figure2');
    game.add.sprite(350, 260, 'figure3');
    game.add.sprite(480, 20, 'figure4');
    game.add.sprite(480, 140, 'figure5');
    game.add.sprite(480, 260, 'figure6');

    tooltip3 = game.add.sprite(140, 355, 'tooltip3');
    tooltip5 = game.add.sprite(430, 170, 'tooltip5');
    tooltip6 = game.add.sprite(160, 150, 'tooltip6');
    tooltip7 = game.add.sprite(290, 150, 'tooltip7');
    tooltip3.inputEnabled = true;
    tooltip5.inputEnabled = true;
    tooltip6.inputEnabled = true;
    tooltip7.inputEnabled = true;

    game.add.bitmapText(500, 80, 'chiller', 'infected', 54);
    game.add.bitmapText(650, 80, 'chiller', 'tactical', 54);
    game.add.sprite(-150, -100, user.avatar);

    game.add.bitmapText(120, 20, 'desyrel', 'nickname:', 36);
    game.add.bitmapText(285, 20, 'desyrel', user.nickname, 36);
    game.add.bitmapText(120, 60, 'desyrel', 'grade:', 36);
    game.add.bitmapText(215, 60, 'desyrel', user.stats.score.toString(), 36);


    changepw = game.add.button(240, 115, 'button', go_changepw, this, 1, 0, 2, 0);
    game.add.bitmapText(255, 115, 'chiller', 'password', 28);

    changeprofile = game.add.button(120, 115, 'button', go_changeprofile, this, 1, 0, 2, 0);
    game.add.bitmapText(145, 115, 'chiller', 'profile', 28);

    startgame = game.add.button(250, 210, 'start', do_match, this);
    userhelp = game.add.button(50, 400, 'help', go_help, this);
    show("userinterface");
    tooltip_user();
  },
  update: function(){
    if(star){
      if(changepw.input.pointerOver()){
        tooltip7.alpha=0.7;
      }
      else{
        tooltip7.alpha=0;
      }
      if(changeprofile.input.pointerOver()){
        tooltip6.alpha=0.7;
      }
      else{
        tooltip6.alpha=0;
      }
      if(startgame.input.pointerOver()){
        tooltip5.alpha=0.7;
      }
      else{
        tooltip5.alpha=0;
      }
      if(userhelp.input.pointerOver()){
        tooltip3.alpha=0.7;
      }
      else{
        tooltip3.alpha=0;
      }
    }
  }
}
function tooltip_user(){
  star = true;   
}

function go_help() {
  game.state.start('help');
}

function do_match() {
  if(canMatch==true){
    canMatch=false;
    var req = {
    'sid': sid,
    'player_count':2
  };
  alert("开始匹配，请耐心等待");
  $.ajax({
    url: "/api/session/match",
    type: "POST",
    contentType: 'application/json',
    data: JSON.stringify(req),
    beforeSend:function(){ 

      $("#loading").html("<img src='./assets/img/loading.gif'>");

   }, 
    success: function (data, status) {
      $("#loading").empty();
      console.log(data);
      window.location.href="gamewindow.html?sid="+sid;  
    },
    error: function (data, status) {
      $("#loading").empty();
      canMatch=true;
      alert("匹配失败");
      console.log(data);
    }
  });
  }
  else{
    alert("正在匹配请稍后！");
  }
}


function go_changepw() {
  game.state.start('changepw');
}


function go_changeprofile() {
  game.state.start('changeprofile');
}