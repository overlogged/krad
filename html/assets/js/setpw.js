var state_setpw = {
  preload: function () {
    game.add.image(0, 0, 'background');
    game.add.bitmapText(270, 130, 'chiller', 'Modify Password', 70);
    game.add.bitmapText(275, 195, 'chiller', 'Password', 54);
    game.add.bitmapText(280, 255, 'chiller', 'Confirm', 54);

    setpw = game.add.button(440, 320, 'button', do_confirm, this, 1, 0, 2, 0);
    game.add.bitmapText(460, 325, 'chiller', 'Confirm', 28);

    tooltip9 = game.add.sprite(490, 355, 'tooltip9');

    tooltip9.inputEnabled = true;

    show("setpw");
    tooltip_setpw()
  },
  update: function(){
    if(star){
      if(setpw.input.pointerOver()){
        tooltip9.alpha=0.7;
      }
      else{
        tooltip9.alpha=0;
      }
    }

  }
}
function tooltip_setpw(){
  star = true; 
}

function do_confirm() {
  var password = $("#setpw_password").val();
  var confirm = $("#setpw_confirm").val();
  if(confirm!=password){
      alert("两次密码填写不一样");
      return;
  }
  var req = {
    'sid':sid,
    'new_password':password
  };
  console.log(req);
  var v_password = /[a-zA-Z0-9]{6,18}/.test(password);
  if (!v_password) {
    alert("密码格式不正确");
    return;
  }
  $.ajax({
    url: "/api/user/setpw",
    type: "POST",
    contentType: 'application/json',
    data: JSON.stringify(req),
    success: function (data, status) {
      alert("修改成功");
      console.log(data);
      console.log(sid);
    },
    error: function (data, status) {
      alert("修改失败");
      console.log(data);
      console.log(sid);
    }
  });
}