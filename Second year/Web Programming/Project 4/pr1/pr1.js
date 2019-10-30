function functionLeft() {
    var list1 = document.getElementById("list1");
    var selectedItem = list1.selectedIndex;

    if (selectedItem == -1) {
        window.alert("You must double-clik on an item.");
    } else {
        var list2 = document.getElementById("list2");
        var newItem = list1[selectedItem].cloneNode(true);
        list1.removeChild(list1[selectedItem]);
        list2.appendChild(newItem);
    }
}

function functionRight() {
    var list2 = document.getElementById("list2");
    var selectedItem = list2.selectedIndex;

    if (selectedItem == -1) {
        window.alert("You must double-clik on an item.");
    } else {
        var list1 = document.getElementById("list1");
        var newItem = list2[selectedItem].cloneNode(true);
        list2.removeChild(list2[selectedItem]);
        list1.appendChild(newItem);
    }
}
