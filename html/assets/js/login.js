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
  
      game.add.bitmapText(350, 130, 'chiller', 'Log In', 70);
      game.add.bitmapText(305, 195, 'chiller', 'Email', 54);
      game.add.bitmapText(250, 255, 'chiller', 'Password', 54);
  
      game.add.button(270, 320, 'button', go_signup, this, 1, 0, 2, 0);
      game.add.bitmapText(292, 320, 'chiller', 'Sign Up', 28);
  
      game.add.button(450, 320, 'button', do_login, this, 1, 0, 2, 0);
      game.add.bitmapText(475, 322, 'chiller', 'Log In', 28);

      game.add.button(615, 260, 'button', go_forget, this, 1, 0, 2, 0);
      game.add.bitmapText(630, 265, 'chiller', 'forget', 28);

<<<<<<< HEAD
      game.add.button(400,400,'button',hehe,this,1,0,2,0);

=======
  
      
>>>>>>> dev

  
      show("login");

    
    
  }
}

<<<<<<< HEAD
function hehe(){
  game.state.start('setpw');
}
=======
>>>>>>> dev
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
      alert("登录成功");
      sid = data.sid;
      user = data.user;
      game.state.start('userinterface');
    },
    error: function (data, status) {
      alert("用户名或密码错误");
    }
  });
}