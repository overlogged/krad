var state_login = {
  preload: function() {
      var url = window.location.href;
      console.log(url);
      if(url.indexOf("?")!=-1){
        game.state.start('setpw');
      }
      var background = game.add.image(0, 0, 'background');
      background.smoothed = true;
      background.height = game.height;
      background.width = game.width;
      tooltip1 = game.add.sprite(500, 355, 'tooltip1');
      tooltip2 = game.add.sprite(320, 353, 'tooltip2');
      tooltip4 = game.add.sprite(630, 295, 'tooltip4');
      
  
      game.add.bitmapText(350, 130, 'chiller', 'Log In', 70);
      game.add.bitmapText(305, 195, 'chiller', 'Email', 54);
      game.add.bitmapText(250, 255, 'chiller', 'Password', 54);
  
      signUp = game.add.button(270, 320, 'button', go_signup, this, 1, 0, 2, 0);
      game.add.bitmapText(292, 320, 'chiller', 'Sign Up', 28);
  
      logIn = game.add.button(450, 320, 'button', do_login, this, 1, 0, 2, 0);
      game.add.bitmapText(475, 322, 'chiller', 'Log In', 28);

      forgetPW = game.add.button(580, 260, 'button', go_forget, this, 1, 0, 2, 0);
      game.add.bitmapText(600, 265, 'chiller', 'forget', 28);
 
      tooltip1.inputEnabled = true;
      tooltip2.inputEnabled = true;
      tooltip4.inputEnabled = true;
      
      show("login");
      tooltip_log();
  },
  update: function() {
    if(star){
      if(signUp.input.pointerOver()){
        tooltip2.alpha=0.7;
      }
      else{
        tooltip2.alpha=0;
      }
      if(logIn.input.pointerOver()){
        tooltip1.alpha=0.7;
      }
      else{
        tooltip1.alpha=0;
      }
      if(forgetPW.input.pointerOver()){
        tooltip4.alpha=0.7;
      }
      else{
        tooltip4.alpha=0;
      }
    }

  }
}

function tooltip_log(){
   star=true;   
}

function go_forget(){
  game.state.start('forget');
}

function go_signup() {
  game.state.start('register');
}

function do_login() {
  var email = $("#login_email").val();
  var password = $("#login_password").val();
  var req = {
    'email': email,
    'password': password
  };
  var v_email = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(email);
  var v_password = /[a-zA-Z0-9]{6,18}/.test(password);
  if (!v_email || !v_password) {
    alert("邮箱格式或密码格式不正确");
    return;
  }
  $.ajax({
    url: "/api/session/login",
    type: "POST",
    contentType: 'application/json',
    data: JSON.stringify(req),
    success: function (data, status) {
      // alert("登录成功");
      sid = data.sid;
      user = data.user;
      game.state.start('userinterface');
    },
    error: function (data, status) {
      alert("用户名或密码错误");
    }
  });
}