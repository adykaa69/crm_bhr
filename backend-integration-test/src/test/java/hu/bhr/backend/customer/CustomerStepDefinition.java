package hu.bhr.backend.customer;

import hu.bhr.backend.HttpRequestFactory;
import hu.bhr.backend.customer.dto.CustomerDTO;
import hu.bhr.backend.customer.dto.CustomerResponse;
import io.cucumber.core.internal.com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static hu.bhr.backend.Constants.SERVICE_URL;

public class CustomerStepDefinition {

    private static final String CUSTOMER_PATH = "/api/v1/customers";
    private static final String CUSTOMER_BY_ID_PATH = CUSTOMER_PATH + "/%s"; // Customer ID endpoint

    private CustomerDTO customer;
    private HttpResponse<String> response;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private int createdCustomerId;


    @Given("there are {int} customers")
    public void thereAreCustomers(int numCustomers) {
    }

    @When("I request all customers")
    public void iRequestAllCustomers() throws Exception {
        HttpRequest request = HttpRequestFactory.createGet(SERVICE_URL + CUSTOMER_PATH);
        try (var client = HttpClient.newHttpClient()) {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
    }

    @Given("a customer with ID {int} exists")
    public void aCustomerWithIdExists(int customerId) {
    }

    @When("I request customer with ID {int}")
    public void iRequestCustomerWithId(int customerId) throws Exception {
        HttpRequest request = HttpRequestFactory.createGet(SERVICE_URL + String.format(CUSTOMER_BY_ID_PATH, customerId));
        try (var client = HttpClient.newHttpClient()) {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
    }

    @Then("return {int} status code")
    public void returnStatusCode(int httpStatusCode) {
        Assert.assertEquals("The status code must be " + httpStatusCode, httpStatusCode, response.statusCode());
    }

    @Then("the response should contain {int} customers")
    public void theResponseShouldContainCustomers(int expectedCustomerCount) throws Exception {
        // Deserialize JSON response to CustomerResponseDTO
        CustomerResponse<List<CustomerDTO>> customerResponse =
                objectMapper.readValue(response.body(), new TypeReference<CustomerResponse<List<CustomerDTO>>>() {
                });

        // Parse the JSON response to a list of customers
        List<CustomerDTO> customers = customerResponse.data();
        // Verify the size of the list
        Assert.assertEquals("The number of customers returned should be " + expectedCustomerCount, expectedCustomerCount, customers.size());
    }

    @Given("I have a new customer with ID {int}, first name {string}, last name {string}, email {string}, phone number {string}")
    public void iHaveANewCustomer(int id, String firstName, String lastName, String email, String phoneNumber) {
        customer = new CustomerDTO(Integer.toString(id), firstName, lastName, email, phoneNumber);
    }

    @When("I send request to create the customer")
    public void iSendARequestToCreateTheCustomer() throws Exception {
        String requestBody = objectMapper.writeValueAsString(customer);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new java.net.URI(SERVICE_URL + CUSTOMER_PATH))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        try (var client = HttpClient.newHttpClient()) {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }

        // Deserialize response and store created customer ID
        CustomerResponse<CustomerDTO> customerResponse =
                objectMapper.readValue(response.body(), new TypeReference<CustomerResponse<CustomerDTO>>() {});

        createdCustomerId = Integer.parseInt(customerResponse.data().id());
    }

    @Then("the response should contain the customer")
    public void theResponseShouldContainTheNewlyCreatedCustomer() throws Exception {
        CustomerResponse<CustomerDTO> customerResponse =
                objectMapper.readValue(response.body(), new TypeReference<CustomerResponse<CustomerDTO>>() {
                });
        CustomerDTO createdCustomer = customerResponse.data();

        Assert.assertEquals("ID should match", customer.id(), createdCustomer.id());
        Assert.assertEquals("First name should match", customer.firstName(), createdCustomer.firstName());
        Assert.assertEquals("Last name should match", customer.lastName(), createdCustomer.lastName());
        Assert.assertEquals("Email should match", customer.email(), createdCustomer.email());
        Assert.assertEquals("Phone number should match", customer.phoneNumber(), createdCustomer.phoneNumber());
    }

    @When("I delete the customer")
    public void iDeleteTheCustomer() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new java.net.URI(SERVICE_URL + CUSTOMER_PATH + "/" + createdCustomerId))
                .DELETE()
                .build();

        try (var client = HttpClient.newHttpClient()) {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
    }

    @Then("the customer should be deleted successfully")
    public void theCustomerShouldBeDeletedSuccessfully() throws Exception {
        this.returnStatusCode(204);

        // GET request for deleted customer
        this.iRequestCustomerWithId(createdCustomerId);
        this.returnStatusCode(404);
    }

    @When("I update the customer with ID {int} with first name {string}, last name {string}, email {string}, phone number {string}")
    public void iUpdateTheCustomerWithAllInfo(int id, String firstName, String lastName, String email, String phoneNumber) throws Exception {
        customer = new CustomerDTO(Integer.toString(id), firstName, lastName, email, phoneNumber);

        String requestBody = objectMapper.writeValueAsString(customer);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new java.net.URI(SERVICE_URL + CUSTOMER_PATH + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        try (var client = HttpClient.newHttpClient()) {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
    }

    @When("I update the customer with ID {int} with first name {string}, email {string}")
    public void iUpdateTheCustomerWithPartialInfo(int id, String firstName, String email) throws Exception {
        customer = new CustomerDTO(Integer.toString(id), firstName, null, email, null);

        String requestBody = objectMapper.writeValueAsString(customer);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new java.net.URI(SERVICE_URL + CUSTOMER_PATH + "/" + id))
                .header("Content-Type", "application/json")
                .method("PATCH",HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        try (var client = HttpClient.newHttpClient()) {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
    }

    @When("the response should contain the updated customer")
    public void theResponseShouldContainUpdatedCustomer() throws Exception {
        CustomerResponse<CustomerDTO> customerResponse =
                objectMapper.readValue(response.body(), new TypeReference<CustomerResponse<CustomerDTO>>() {});
        CustomerDTO updatedCustomer = customerResponse.data();

        Assert.assertEquals("First name should match",customer.firstName(),updatedCustomer.firstName());
        Assert.assertNull("Last name should not be modified",customer.lastName());
        Assert.assertEquals("Email should match",customer.email(),updatedCustomer.email());
        Assert.assertNull("Phone number should not be modified",customer.phoneNumber());
    }
}
