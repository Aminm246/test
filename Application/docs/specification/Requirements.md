# Non-functional Requirements 

## Non-functional requirement 1
    The GUI of the application should be easy to use and understand.

## Non-functional requirement 2
    Page loading should not take longer than 3 seconds. 

## Non-functional requirement 3


# Functional Requirement 1 CRUD Operations for Recipes
    User can create, read, update, and delete recipes. 

# Functional Requirement 2 Browsing and Search Functions for Recipe
    The user has the ability to browse recipes through categories such as breakfast, low calorie, favorites, etc. In addition,
    user also has the capacity to search using recipe attributes such as name, calorie amount, ingredients, etc.

# Functional Requirement 3 Ingredient Scalability
    Within  the instructions of the recipe, the prep list, or within the meal planner, the user is able to scale the 
    meal size/quantity.

# Functional Requirement 4 Favorites
    Users can favorite recipes or categories.


# Functional Requirement 5 Recipe Tags
    Recipes will have tags that can be added and created by the user. They are different ways to categorize meals.


# Functional Requirement 6 Grocery List
    When a user selects recipe(s) they can generate a grocery list from them. It will also compare it to what ingredients 
    the user has on hand, if the user uses that function of the program.

# Functional Requirement 7 Ingredient Tracker
    The ingredient tracker allows the user to input what ingredients they currently have to see what they need for certain recipes.
    
## Actors
    The user of the application
    
## Use case goal
    CRUD Operations Use Case
        -User is able to view, manage, and track their recipes through the use of CRUD operations.

    Browsing and Searching Use Case
        - With enhanced browsing and searching functions, the user is able to quickly find a recipe that they are looking for.

## Primary Actor
    The user of the application

## Preconditions
    User must enter valid data that meets requirements for recipe creation:
        1.) Valid ingredients (valid name, quantity, etc.)
        2.) Valid recipe steps (String format, step number included)
        3.) Input data matches expected type (User enters string for ingredient name or int for number of servings)
        4.) Input data matches expected format (requirements such as non-empty data, no-spacing, etc.) 
    
    User must follow the correct procedure for browsing by category:
        1.) User must select the category first.
        2.) User must enter valid data (similar to the above req.) when using the search function. 

## Basic flow
    Flow for creating recipe
    1.) User clicks button to create recipe
    2.) User is prompted to enter recipe data such as ingredients, steps, and serving sizes.
    3.) If input is valid, recipe is succesfully created and stored. 
    4.) User proceeds to other parts of the program or performs additional CRUD operations.

    Flow for browsing/search 
    1.) User selects category for recipe.
    2.) User is presented with a list of recipes that meet the criteria.
    3.) User uses the search bar to find the recipe by name.
    4.) User views the recipe page. 


## Alternative flows
    - Instead of submitting, user can cancel CRUD operations.
    - While user input is invalid, user is presented with an informative message that explains how to correct error.
    - User can exit and switch between pages/features.

### Alternative flow 1

### Alternative flow 2
