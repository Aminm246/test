# Non-functional Requirements 

## Non-functional requirement 1
    The GUI of the application should be easy to use and understand.

## Non-functional requirement 2
    Page loading should not take longer than 3 seconds. 

## Non-functional requirement 3
    Images should be clickable and takes you to page that shows you the details of the recipe.

# Functional Requirement 1 CRUD Operations for Recipes
    User can create, read, update, and delete recipes.

## Actors
    The user of the application
    
## Use case goal
    User is able to view, manage, and track their recipes through the use of CRUD operations.

## Primary Actor
    The user of the application

## Preconditions
    User must enter valid data that meets requirements for recipe creation:
        1.) Valid ingredients (valid name, quantity, etc.)
        2.) Valid recipe steps (String format, step number included)
        3.) Input data matches expected type (User enters string for ingredient name or int for number of servings)
        4.) Input data matches expected format (requirements such as non-empty data, no-spacing, etc.)

## Basic flow
    Flow for creating recipe
    1.) User clicks button to create recipe
    2.) User is prompted to enter recipe data such as ingredients, steps, and serving sizes.
    3.) If input is valid, recipe is succesfully created and stored. 
    4.) User proceeds to other parts of the program or performs additional CRUD operations.

## Alternative flows
    - Instead of submitting, user can cancel CRUD operations.
    - While user input is invalid, user is presented with an informative message that explains how to correct error.
    - User can exit and switch between pages/features.

### Alternative flow 1

### Alternative flow 2


# Functional Requirement 2 Browsing Functions for Recipes
    The user has the ability to browse recipes through categories such as breakfast, low calorie, favorites, etc. 

## Actors
    The user of the application

## Use case goal
    With enhanced browsing and searching functions, the user is able to quickly find a recipe that they are looking for.

## Primary Actor
    The user of the application

## Preconditions
    User must follow the correct procedure for browsing by category:
        1.) User must select the category first.
        2.) User must enter valid data (similar to the above req.) when using the search function. 

## Basic flow
    Flow for browsing/search 
    1.) User selects category for recipe.
    2.) User is presented with a list of recipes that meet the criteria.
    3.) User uses the search bar to find the recipe by name.
    4.) User views the recipe page. 


## Alternative flows

### Alternative flow 1

### Alternative flow 2


# Functional Requirement 3 Search Functions for Recipe
    User has the capacity to search using recipe attributes such as name, calorie amount, ingredients, etc.

## Actors
    The user of the application

## Use case goal
    With enhanced browsing and searching functions, the user is able to quickly find a recipe that they are looking for.

## Primary Actor
    The user of the application

## Preconditions
    User must follow the correct procedure for browsing by category:
        1.) User must select the category first.
        2.) User must enter valid data (similar to the above req.) when using the search function. 

## Basic flow
    Flow for browsing/search 
    1.) User selects category for recipe.
    2.) User is presented with a list of recipes that meet the criteria.
    3.) User uses the search bar to find the recipe by name.
    4.) User views the recipe page. 


## Alternative flows

### Alternative flow 1

### Alternative flow 2


# Functional Requirement 4 View Recipes
    Within  the instructions of the recipe, the prep list, or within the meal planner, the user is able to scale the 
    meal size/quantity.

## Actors
    The user of the application

## Use case goal
    When the user clicks a recipe, either from browsing or searching, 

## Primary Actor

## Preconditions

## Basic flow
    1.) User clicks on a image to take them to the recipe page.
    2.) The system checks to see if the User has a global scale modifier on and adjusts accordingly.
    3.) The system displays the ingredients and recipe, along with the sidebar.

## Alternative flows
    
### Alternative flow 1
    Favorite
### Alternative flow 2
    Scale the meal size/quantity.

# Functional Requirement 5 Ingredient Tracker
    The ingredient tracker allows the user to input what ingredients they currently have to see what they need for certain recipes.
## Actors

## Use case goal

## Primary Actor

## Preconditions

## Basic flow

## Alternative flows

### Alternative flow 1

### Alternative flow 2

# Functional Requirement 6 Review Functionality
    Allow the users to make comments and rate the recipe of a dish.
## Actors

## Use case goal

## Primary Actor

## Preconditions

## Basic flow

## Alternative flows

### Alternative flow 1

### Alternative flow 2