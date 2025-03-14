package hu.bhr.backend.customer;

import hu.bhr.backend.HttpRequestFactory;
import hu.bhr.backend.customer.dto.CustomerRequest;
import hu.bhr.backend.customer.dto.CustomerResponse;
import hu.bhr.backend.customer.dto.PlatformResponse;
import io.cucumber.core.internal.com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static hu.bhr.backend.Constants.SERVICE_URL;

@Component
public class CustomerStepDefinition {

    private static final String CUSTOMER_PATH = "/api/v1/customers";
    private static final String CUSTOMER_BY_ID_PATH = CUSTOMER_PATH + "/%s"; // Customer ID endpoint

    private CustomerResponse customerResponse;
    private CustomerRequest customerRequest;
    private HttpResponse<String> response;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String createdCustomerId;

    @Given("I have a new customer with first name {string}, last name {string}, nickname {string}, email {string}, phone number {string}, relationship {string}")
    public void iHaveANewCustomer(String firstName, String lastName, String nickname, String email, String phoneNumber, String relationship) {
        customerRequest = new CustomerRequest(
                firstName,
                lastName,
                nickname,
                email,
                phoneNumber,
                relationship
        );
    }

    @Given("I have a new customer with first name {string}, last name {string}, nickname {string}, email {string}, phone number {string}")
    public void iHaveANewCustomerWithoutRelationship(String firstName, String lastName, String nickname, String email, String phoneNumber) {
        customerRequest = new CustomerRequest(
                firstName,
                lastName,
                nickname,
                email,
                phoneNumber,
                null
        );
    }

    @When("I send request to create the customer")
    public void iSendARequestToCreateTheCustomer() throws Exception {
        String requestBody = objectMapper.writeValueAsString(customerRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new java.net.URI(SERVICE_URL + CUSTOMER_PATH))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        try (var client = HttpClient.newHttpClient()) {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }

        // Deserialize response and store created customer ID
        PlatformResponse<CustomerResponse> platformResponse =
                objectMapper.readValue(response.body(), new TypeReference<PlatformResponse<CustomerResponse>>() {});

        // Check if the response contains an error (validation failure)
        if (response.statusCode() == 500) {
            System.out.println("Validation Error: " + platformResponse.message());
            return; // Do not proceed further if the request was invalid
        }

        customerResponse = platformResponse.data();
        createdCustomerId = platformResponse.data().id();
    }

    @And("I request customer with the created ID")
    public void iRequestCustomerWithId() throws Exception {
        HttpRequest request = HttpRequestFactory.createGet(SERVICE_URL + String.format(CUSTOMER_BY_ID_PATH, createdCustomerId));
        try (var client = HttpClient.newHttpClient()) {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
    }

    @Then("return {int} status code")
    public void returnStatusCode(int httpStatusCode) {
        Assert.assertEquals("The status code must be " + httpStatusCode, httpStatusCode, response.statusCode());
    }

    @Then("the response should contain the customer")
    public void theResponseShouldContainTheNewlyCreatedCustomer() throws Exception {
        PlatformResponse<CustomerResponse> platformResponse =
                objectMapper.readValue(response.body(), new TypeReference<PlatformResponse<CustomerResponse>>() {
                });
        CustomerResponse createdCustomer = platformResponse.data();

        Assert.assertEquals("ID should match", customerResponse.id(), createdCustomer.id());
        Assert.assertEquals("First name should match", customerResponse.firstName(), createdCustomer.firstName());
        Assert.assertEquals("Last name should match", customerResponse.lastName(), createdCustomer.lastName());
        Assert.assertEquals("Nickname should match", customerResponse.nickname(), createdCustomer.nickname());
        Assert.assertEquals("Email should match", customerResponse.email(), createdCustomer.email());
        Assert.assertEquals("Phone number should match", customerResponse.phoneNumber(), createdCustomer.phoneNumber());
        Assert.assertEquals("Relationship should match", customerResponse.relationship(), createdCustomer.relationship());
    }
}
