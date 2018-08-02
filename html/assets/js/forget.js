var state_forget = {
  preload: function () {
    game.add.image(0, 0, 'background');

    game.add.bitmapText(280, 130, 'chiller', 'Forget Password', 70);
    game.add.bitmapText(290, 240, 'chiller', 'Email', 54);

    forget = game.add.button(285, 320, 'button', do_forget, this, 1, 0, 2, 0);
    game.add.bitmapText(300, 325, 'chiller', 'Confirm', 28);
   
    backloginfp = game.add.button(465, 320, 'button', do_backloginfp, this, 1, 0, 2, 0);
    game.add.bitmapText(490, 325, 'chiller', 'Back', 28);

    tooltip8 = game.add.sprite(515, 355, 'tooltip8');
    tooltip9 = game.add.sprite(335, 355, 'tooltip9');

    tooltip8.inputEnabled = true;
    tooltip9.inputEnabled = true;

    show("forget");
    tooltip_forget()
  },
  update: function(){
    if(star){
      if(forget.input.pointerOver()){
        tooltip9.alpha=0.7;
      }
      else{
        tooltip9.alpha=0;
      }
      if(backloginfp.input.pointerOver()){
        tooltip8.alpha=0.7;
      }
      else{
        tooltip8.alpha=0;
      }
   }
 }
}
function tooltip_forget(){
  star = true;
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