let table = document.getElementById("table");
let last_item;
let lookin;
let count;
let len;
let pool;
let size;
let number_of_pairs;
var re_size;
let level = 0;

number_of_pairs = 2;
re_size = 4;


init_new_game();

function init_new_game() {
    while (table.rows.length > 0) {
        table.deleteRow(0)
    }
    last_item = [];
    lookin = false;
    len = 0;
    pool = [];
    size = re_size * re_size;
    make_table(table, re_size);

    for (let j = 0; j < number_of_pairs; j++) {
        for (let i = 0; i < size / number_of_pairs; i++) {
            pool.push(i);
        }
    }

    apply(function(child) {
        let poz = Math.floor(Math.random() * size);
        size -= 1;
        child.children[0].innerHTML = pool[poz];
        pool.splice(poz, 1);
    });
}


function make_table(table, size) {
    for (let i = 0; i < size; i++) {
        let local_tr = document.createElement("tr");
        table.appendChild(local_tr);
        for (let j = 0; j < size; j++) {
            let local_p = document.createElement("p");
            let local_td = document.createElement("td");
            local_td.appendChild(local_p);
            local_tr.appendChild(local_td);
        }
    }
}

function apply(lambda) {
    count = 0;
    let tBody = table;
    let children = tBody.children;
    for (let i = 0; i < children.length; i++) {
        let children2 = children[i].children;
        for (let j = 0; j < children2.length; j++) {
            lambda(children2[j]);
            count += 1;
        }
    }
}

function little_copy(arg) {
    let a = document.createElement("p");
    a.appendChild(document.createTextNode(arg.innerText));
    a.style.display = "block";
    let b = document.createElement("td");
    b.appendChild(a);
    return b;
}

function gogo() {
    apply(function(child) {
        child.addEventListener('click', event_handler, true);

        function event_handler() {
            if (last_item[last_item.length - 1] !== child && !lookin) {
                child.children[0].style.display = "block";
                if (last_item.length !== number_of_pairs - 1) {
                    last_item.push(child);
                } else {
                    if (!judge(last_item, child)) {
                        lookin = true;
                        setTimeout(function() {
                            child.children[0].style.display = "none";
                            for (let i = 0; i < last_item.length; i++) {
                                last_item[i].children[0].style.display = "none";
                            }
                            last_item = [];
                            lookin = false;
                        }, 1000);
                    } else {
                        for (let i = 0; i < last_item.length; i++) {
                            let copy1 = little_copy(last_item[i]);
                            last_item[i].parentNode.replaceChild(copy1, last_item[i]);
                        }
                        let copy2 = little_copy(child);
                        child.parentNode.replaceChild(copy2, child);
                        last_item = [];
                        len += number_of_pairs;
                        if (count === len) {
                            table.parentNode.insertBefore(document.createElement("p").appendChild(document.createTextNode("Ai castigat!")), table.nextSibling);
                            return;
                        }
                    }
                }
            }
        }
    });
}

gogo();

function judge(last_item, child) {
    let ok = true;
    for (let i = 0; i < last_item.length - 1; i++) {
        if (last_item[i].children[0].innerHTML.localeCompare(last_item[i + 1].children[0].innerHTML) !== 0) {
            ok = false;
        }
    }
    if (last_item[last_item.length - 1].children[0].innerHTML.localeCompare(child.children[0].innerHTML) !== 0) {
        ok = false;
    }
    return ok;
}