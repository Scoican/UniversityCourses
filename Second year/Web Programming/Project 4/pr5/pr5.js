var slideIndex = 1;
var ok = 0;
showSlides(slideIndex);

function plusSlides(n) {
    ok = 1;
    showSlides(slideIndex += n);
}


function showSlides(n) {
    var i;
    var slides = document.getElementsByClassName("mySlides");
    if (n > slides.length) {
        slideIndex = 1
    }
    if (n < 1) {
        slideIndex = slides.length
    }
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }

    slides[slideIndex - 1].style.display = "block";


    if (ok == 0) {
        slideIndex++;
        if (slideIndex > slides.length) {
            slideIndex = 1;
        }
        setTimeout(showSlides, 3000);
    }
    ok = 0;
}
