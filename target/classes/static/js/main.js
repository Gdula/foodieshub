function addItem() {
    var lastIngredientSingleList = document.getElementsByClassName('ingredient_single');
    var lastIngredientSingle = lastIngredientSingleList[lastIngredientSingleList.length-1];

    var clonedIngredientSingle = lastIngredientSingle.cloneNode(true);


    lastIngredientSingle.parentNode.insertBefore(clonedIngredientSingle, lastIngredientSingle.nextSibling);
}

function removeItem() {
    var lastIngredientSingleList = document.getElementsByClassName('ingredient_single');
    var lastIngredientSingle = lastIngredientSingleList[lastIngredientSingleList.length-1];

    lastIngredientSingle.remove();
}

var check = function() {
    if (document.getElementById('password-field').value ==
        document.getElementById('confirm_password').value) {
        document.getElementById('message').style.color = 'green';
        document.getElementById('message').innerHTML = '<br> zgodne';
    } else {
        document.getElementById('message').style.color = 'red';
        document.getElementById('message').innerHTML = '<br> nie zgodne';
    }
}