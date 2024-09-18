# Non-functional Requirements 

## Non-functional requirement 1
    The GUI of the application should be easy to use and understand.

## Non-functional requirement 2
    Page loading should not take longer than 3 seconds. 

## Non-functional requirement 3
    High modifiability. Through use of high orthogonality the program will be able to add new categories with ease. 
    So the program can stay up to date with new trends in meals.

# Functional Requirement 1 Create Recipe

## Actors
    User 
    System

## Use case goal
    Allows the user to create their own recipe. 

## Primary Actor
    User

## Preconditions
    User must be on the recipe page.
    
## Basic flow
    1.) User clicks on the create button to create the recipe.
    2.) User inputs the recipe details.
    3.) User submits the recipe.
    4.) System performs input validation.
    5.) Recipe is succesfully created and stored in the system.
    6.) Recipe details are shown to the user.

## Alternative flows

### Alternative flow 1
    1.) System fails input validation for the recipe and provides the user an error message stating that the creation of the recipe failed.
    2.) User is prompted to re-enter the details for the recipe to try again. 
### Alternative flow 2
    User has the ability to cancel instead of submitting the new recipe.
# Functional Requirement 2 Read Recipe

## Actors
    -User
    -System

## Use case goal
    -Allows the user to view the details for a particular recipe

## Primary Actor
    -User

## Preconditions
    -There is at least one recipe stored in the system.
    -User is on the recipe page.
    -Recipe exists within the system.

## Basic flow
    1.) User is on the recipe page.
    2.) User selects a recipe.
    3.) System retrieves the details for the recipe.
    4.) The recipe page shows the details for the recipe to the user.

## Alternative flows

### Alternative flow 1
    1.) System displays error message to user indicating that the recipe does not exist.
    2.) User is sent to previous page.

# Functional Requirement 3 Update Recipe

## Actors
    -User
    -System

## Use case goal
    -Allows to user to update an existing recipe.

## Primary Actor
    -User

## Preconditions
    -There is at least one recipe stored in the system.
    -Recipe exists in the system.
    -User is on the recipe page.

## Basic flow
    1.) User selects the edit button
    2.) User provides modifications to the recipe details
    3.) User submits the changes.
    4.) System performs input validation.
    5.) Recipe is succesfully updated and changes are stored in the system.
    6.) System displays recipe changes on the recipe page.

## Alternative flows

### Alternative flow 1
    1.) System provides error message indicating that user provided invalid input.
    2.) User is prompted to re-enter the details and try again.
### Alternative flow 2
    1.) System provides error message indicating that the recipe is not found.
    2.) User is sent to the previous page.
# Functional Requirement 4 Delete Recipe

## Actors
    -User
    -System 

## Use case goal
    -Allows the user to delete an existing recipe.

## Primary Actor
    -User

## Preconditions
    -Recipe exists within the system
    -There is at least one recipe within the system.
    -User is on the recipe page 

## Basic flow
    1.) User selects the delete button
    2.) User is prompted with a confirmation message.
    3.) User confirms the deletion.
    4.) System deletes the recipe. 
    5.) Recipe is succesfully deleted and changes are stored in the system.
    6.) System displays success message to the user.
    7.) User is sent to the previous page.

## Alternative flows

### Alternative flow 1
    1.) System displays error message indicating that the recipe was not found.
    2.) User is sent to previous page.
    
