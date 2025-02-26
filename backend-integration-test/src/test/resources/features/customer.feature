Feature: Customer API testing

  # GET
  Scenario: Get all customers
    Given there are 2 customers
    When I request all customers
    Then the response should contain 2 customers
    And return 200 status code

  # GET
  Scenario: Get customer by ID
    Given a customer with ID 1 exists
    When I request customer with ID 1
    Then return 200 status code

  # DELETE
  Scenario: Delete a customer
    Given I have a new customer with ID 0, first name "Naruto", last name "Uzumaki", email "naruto.uzumaki@konoha.jp", phone number "123456789"
    And I send request to create the customer
    When I delete the customer
    Then the customer should be deleted successfully

  # POST
  Scenario: Add a new customer
    Given I have a new customer with ID 0, first name "Naruto", last name "Uzumaki", email "naruto.uzumaki@konoha.jp", phone number "123456789"
    When I send request to create the customer
    Then return 201 status code
    And the response should contain the customer

  # PUT
  Scenario: Update an existing customer (PUT)
    Given a customer with ID 0 exists
    When I update the customer with ID 0 with first name "Michael", last name "Scott", email "michael.scott@scranton.com", phone number "696969"
    Then return 200 status code
    And the response should contain the customer
    
  Scenario: Update an existing customer (PATCH)
    Given a customer with ID 0 exists
    When I update the customer with ID 0 with first name "Krúbi", email "crewbi@gmail.com"
    Then return 200 status code
    And the response should contain the updated customer