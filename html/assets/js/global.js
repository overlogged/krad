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
var star = false;
var tooltip1;
var tooltip2;
var tooltip3;
var tooltip4;
var tooltip5;
var tooltip6;
var tooltip7;
var tooltip8;
var signUp;
var logIn;
var forgetPW;
var backBtn;
var changepw;
var changeprofile;
var userhelp;
var startgame; 

