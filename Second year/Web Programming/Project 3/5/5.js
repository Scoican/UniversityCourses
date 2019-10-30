let list = document.getElementById("photos")
let current = list.children[0]
current.style.display = "block"
let manual = true;
let next_btn = document.getElementById("next")
let prev_btn = document.getElementById("prev")
next_btn.addEventListener('click', function() { next() })
prev_btn.addEventListener('click', function() { prev() })

function next(command = false) {
    manual = command
    current.style.display = "none"
    current = current.nextElementSibling
    if (!current) {
        current = list.children[0]
    }
    current.style.display = "block"
}

function prev(command = false) {
    manual = command
    current.style.display = "none"
    current = current.previousElementSibling
    if (!current) {
        current = list.children[list.children.length - 1]
    }
    current.style.display = "block"
}

function auto_slide() {
    setTimeout(function() {
        if (manual) {
            next(true);
        }
        auto_slide();
        manual = true;
    }, 2000);
}

auto_slide()