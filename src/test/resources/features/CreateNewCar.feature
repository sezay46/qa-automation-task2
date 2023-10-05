Feature: Create a new car

  Scenario: Create a new car
    When the user sent the post request
    Then he receive the response and verify it

  Scenario: Retrieve information for the newly created car
    When the user sent the get request
    Then he receive the response and verify
