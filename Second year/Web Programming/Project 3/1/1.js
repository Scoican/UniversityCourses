let select1 = document.getElementById("1");
let select2 = document.getElementById("2");

function move(select) {
    let index1;
    let index2;
    if (select === 1) {
        index1 = select1;
        index2 = select2;
    } else {
        index1 = select2;
        index2 = select1;
    }

    let el = index1.options[index1.selectedIndex];
    el.remove();
    index2.append(el);
}