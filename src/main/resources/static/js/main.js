function addItem() {
    var lastIngredientSingleList = document.getElementsByClassName('ingredient_single');//pobierasz wszystkie już istniejące elementy do składników
    var lastIngredientSingle = lastIngredientSingleList[lastIngredientSingleList.length-1];//wynajdujesz sobie ostatni

//kopiujesz go
    var clonedIngredientSingle = lastIngredientSingle.cloneNode(true);

//wklejasz za ostatni
    lastIngredientSingle.parentNode.insertBefore(clonedIngredientSingle, lastIngredientSingle.nextSibling);
}

function removeItem() {
    var lastIngredientSingleList = document.getElementsByClassName('ingredient_single');//pobierasz wszystkie już istniejące elementy do składników
    var lastIngredientSingle = lastIngredientSingleList[lastIngredientSingleList.length-1];//wynajdujesz sobie ostatni

    lastIngredientSingle.remove();
}