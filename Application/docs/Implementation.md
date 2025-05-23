# Phase 1 [October 23rd]

## Create Recipe
    When the program is ran you are greeted with the create recipe page. This can 
    be filled out and submitted. Which then creates and adds this recipe to the manager.
    After creation the user can choose to view or create another. If the user chooses 
    to view the recipe, they can get back to the create page through the menubar. 
    
    Every section in the create recipe page needs to be submitted to be able to create
    the recipe. The image file path can be left blank when submitted. The tags section
    can also be left blank. 
## Read Recipe 
    After filling out the create recipe page and submitting, the user is given a choice,
    either view the recipe they created or create another one. When they click to view
    the page is populated with the recipe information they entered earlier.

    Submit a filled recipe and click Yes to view the recipe.

# Phase 2 [November 28th]

## Delete Recipe
    This functional requirement allows the user to delete a particular recipe. The user must select the top left menu bar item 
    titled "Recipe", and then select "View Recipes", which will give the user a list of the created recipes. The user can then 
    select a particular recipe and be sent to a page which will show the details of the recipe. To delete the recipe, the user 
    must select delete button on the left side of the page. This will delete the recipe from the database and remove the associated 
    data for the recipe in terms of recipeIngredients, recipeTags, and the instructions for the recipe. This can be tested through viewing 
    the list of recipes to confirm deletion, as well as checking the database to confirm that associated data for the recipe has been
    removed from the database. 
    
## Update Recipe
    The update recipe functional requirement, was one that would allow the user to take an existing recipe
    and make changes or additions to it. In the program you can view a recipe by navigating to the view recipes
    tab in the menubar under recipes. Then after selecting a recipe, the user can select Update Recipe on the
    left hand side. They are then brought to a new page that looks similar to the create recipe page, but it is
    prefilled with the selected recipes information. Then they can edit the overall Name, Description or Image Path.
    Then for the tags, ingredients, and instructions the user can select a specific one and edit or delete it. They
    can also add new ones if necessary.

# Phase 3 [November 30th]

## Search Recipe
    This functional requirement allows a user to search for a particular recipe. The search function can be found
    by selecting the "Recipes" and then "Search Recipes" on menu bar. The user can search for a recipe
    using the criteria of recipe name, ingredient names, and tag names. In order to perform the search at least
    one criteria of the three must be filled. After inputting the search criteria, the user the has the option 
    to either perform the search or clear and start over. If user selects search, the results of search will show 
    on the bottom. If recipes are found from search they will be displayed, otherwise system will indicate no results
    were found.
    
## Grocery List
    When selecting a recipe in recipelist, the user can use CTRL + Click to select multiple at one time.
    This then allows the user the click generate grocery list, which will then display the conbined ingredients in a 
    single list.
