$(document).ready(function(){
  $(".hamburger").click(function(){
    $(this).toggleClass("is-active");
  });
});


function openHamburger() {
  var class_by_id = $('#hamburger-button').attr('class');
  if(class_by_id == 'hamburger'){
    document.getElementById("hamburger-container").style.width = "70%";
    document.getElementById("hamburger-button-container").style.width = "75%";
    document.getElementById("beside-hamburger").style.width = "100%";
  }else{
    document.getElementById("hamburger-container").style.width = "0px";
    document.getElementById("hamburger-button-container").style.width = "50px";
    document.getElementById("beside-hamburger").style.width = "0px";
  }
}
