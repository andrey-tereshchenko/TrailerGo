
$(document).ready(function(){
	// $("#Roll").hcSticky({
	// });
    $(document).scroll(function () {
        if ($(document).width() > 785) {
            if ($(document).scrollTop() > $('#header').height() + 268) {
                $('#Roll').addClass('fixed');
                $('#Roll').css("margin-top","-333px");
            }
            else {
                $('#Roll').removeClass('fixed');
                $('#Roll').css("margin-top","8px");
            }
        }
    })
	$("#image2").hcSticky();
	$(".p02, .Info_content2_ref").on("mouseover", function(){
		$(event.target).css("color", "#FF4C00");		
	});
	$(".p02, .Info_content2_ref").on("mouseout", function(){
		$(event.target).css("color", "#e0dfdc");		
	});
	$(".Video").css('opacity','1');
	
});