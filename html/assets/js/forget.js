var state_forget = {
  preload: function () {
    game.add.image(0, 0, 'background');

    game.add.bitmapText(280, 130, 'chiller', 'Forget Password', 70);
    game.add.bitmapText(290, 240, 'chiller', 'Email', 54);

    game.add.button(285, 320, 'button', do_forget, this, 1, 0, 2, 0);
    game.add.button(465, 320, 'button', do_backloginfp, this, 1, 0, 2, 0);
    game.add.bitmapText(300, 325, 'chiller', 'Confirm', 28);
    game.add.bitmapText(490, 325, 'chiller', 'Back', 28);
    show("forget");
  }
}

function do_backloginfp (){
  game.state.start('login');
}

function do_forget() {
  var email = $("#forget_email").val();
  var req ={
    'email':email
  };
  console.log(req);
  var v_email = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(email);
  if (!v_email) {
    alert("邮箱格式不正确");
    return;
  }

  $.ajax({
    url:"/api/user/forget",
    type:"POST",
    contentType:'application/json',
    data:JSON.stringify(req),
    success: function(data,status){
      alert("信息已发送至邮箱");
      console.log(data);
      console.log(data.sid);
      game.state.start('login');
    },
    error: function(data,status){
      alert("邮箱不存在");
      console.log(data);
    }
  });
}