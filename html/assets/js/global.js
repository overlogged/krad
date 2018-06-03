var current_page;
var body = (document.compatMode && document.compatMode == 'CSS1Compat') ? document.documentElement : document.body;
$(".container").css("left", ((body.clientWidth - 800) / 16) * 7 + "px");

function show(page) {
  if(current_page) $("#" + current_page).hide();
  current_page = page;
  $("#" + current_page).show();
}

// global data
var sid = 0;
var user = {};
var user_nickname="";
var user_avatar="";