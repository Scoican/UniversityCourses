function make_sortable_table(id_table) {
    let table = document.getElementById(id_table).tBodies[0];
    let children = table.children;
    for (let i = 0; i < children.length; i++) {
        let local_th = children[i].children[0];
        local_th.addEventListener('click', function() {
            sort_table(children, i);
        })
    }
}

function sort_table(children, i) {
    let pat = new RegExp("^[0-9]*$");
    let tds = children[i].children;
    let convert;
    if (pat.test(tds[1].textContent)) {
        convert = parseInt
    } else {
        convert = local_string
    }
    for (let k = 1; k < tds.length - 1; k++) {
        for (let j = 2; j < tds.length; j++) {
            if (convert(tds[k].textContent) > convert(tds[j].textContent)) {
                swap(j, k, children);
            }
        }
    }
}

function swap(j, k, children) {
    for (let i = 0; i < children.length; i++) {
        let a = children[i].children;
        a[j - 1].parentNode.insertBefore(a[k], a[j - 1].nextSibling);
        a[k - 1].parentNode.insertBefore(a[j], a[k - 1].nextSibling);
    }
}


make_sortable_table("table");
make_sortable_table("table2");