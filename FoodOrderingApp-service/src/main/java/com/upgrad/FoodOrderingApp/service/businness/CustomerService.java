package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.Customer;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuth;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CustomerBusinessService {

  @Autowired private CustomerDao customerDao;

  @Autowired private PasswordCryptographyProvider passwordCryptographyProvider;

  public Customer signUp(Customer customer) throws SignUpRestrictedException {

    if (customer.getContactNumber() != null
        && customerDao.getContactNumber(customer.getContactNumber()) != null) {
      throw new SignUpRestrictedException(
          "SGR-001", "This contact number is already registered! Try other contact number.");
    }
    if (customer.getFirstName() == null
        || customer.getEmail() == null
        || customer.getContactNumber() == null
        || customer.getPassword() == null) {
      throw new SignUpRestrictedException(
          "SGR-005", "Except last name all fields should be filled");
    }
    if (customer.getEmail() != null) {
      String email = customer.getEmail();
      String emailRegex =
          "^[a-zA-Z0-9_+&*-]+(?:\\."
              + "[a-zA-Z0-9_+&*-]+)*@"
              + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
              + "A-Z]{2,7}$";
      Pattern pattern = Pattern.compile(emailRegex);
      if (!pattern.matcher(email).matches()) {
        throw new SignUpRestrictedException("SGR-002", "Invalid email-id format!");
      }
    }
    if (customer.getContactNumber() != null) {
      String contactNum = customer.getContactNumber();
      String contactNumberRegex = "^[0-9]{10}$";
      Pattern pattern = Pattern.compile(contactNumberRegex);
      if (!pattern.matcher(contactNum).matches()) {
        throw new SignUpRestrictedException("SGR-003", "Invalid contact number!");
      }
    }

    if (customer.getPassword() != null) {
      String password = customer.getPassword();
      String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[#@$%&*!^])(?=\\S+$).{8,}$";
      Pattern pattern = Pattern.compile(passwordRegex);
      if (!pattern.matcher(password).matches()) {
        throw new SignUpRestrictedException("SGR-004", "Weak password!");
      }
    }
    String[] encryptedText = passwordCryptographyProvider.encrypt(customer.getPassword());
    customer.setSalt(encryptedText[0]);
    customer.setPassword(encryptedText[1]);
    customer.setUuid(UUID.randomUUID().toString());

    return customerDao.createCustomer(customer);
  }

  public CustomerAuth login(String authorizationHeader) throws AuthenticationFailedException {
    byte[] decode = Base64.getDecoder().decode(authorizationHeader.split("Basic ")[1]);
    String decodedText = new String(decode);

    if (!decodedText.contains(":")){
    throw new AuthenticationFailedException("ATH-003", "Incorrect format of decoded customer name and password");
      }
    String[] credentials = decodedText.split(":");
    String userName = credentials[0];
    String password = credentials[1];

    Customer customer = customerDao.getContactNumber(userName);
    if (customer == null) {
      throw new AuthenticationFailedException("ATH-001","This contact number has not been registered!");
    }
    String encryptedPassword = passwordCryptographyProvider.encrypt(password, customer.getSalt());
    if (encryptedPassword.equals(customer.getPassword())) {
      CustomerAuth customerAuth = new CustomerAuth();
      final ZonedDateTime issuedTime = ZonedDateTime.now();
      final ZonedDateTime expiryTime = ZonedDateTime.now().plusHours(5);
      JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(password);
      customerAuth.setAccessToken(jwtTokenProvider.generateToken(customer.getUuid(),issuedTime,expiryTime));
      customerAuth.setCustomerId(customer);
      customerAuth.setLoginTime(issuedTime);
      customerAuth.setExpiryTime(expiryTime);
      customerAuth.setUuid(UUID.randomUUID().toString());
      return customerDao.createCustomerAuth(customerAuth);
    }
    else{
      throw new AuthenticationFailedException("ATH-002","Invalid Credentials");
    }
  }

  public CustomerAuth logout(String accessToken) throws AuthorizationFailedException {
    CustomerAuth customerAuth = customerDao.findByAccessToken(accessToken);
    if (customerAuth == null){
      throw new AuthorizationFailedException("ATHR-001","Customer is not Logged in.");
    }
    else if(hasUserSignedOut(customerAuth.getLogoutTime())){
      throw new AuthorizationFailedException("ATHR-002","Customer is logged out. Log in again to access this endpoint.");
    }
    else if(hasSessionExpired(customerAuth.getExpiryTime())){
      throw new AuthorizationFailedException("ATHR-003","Your session is expired. Log in again to access this endpoint.");
    }
    else {
      customerAuth.setLogoutTime(ZonedDateTime.now());
      return customerAuth;
    }
  }

  public Customer updateCustomer(String authorization, Customer newCustomer)
      throws AuthorizationFailedException, UpdateCustomerException {
    if (newCustomer.getFirstName() == null) {
      throw new UpdateCustomerException("UCR-002", "First name field should not be empty");
    }
    String[] authorizationHeader = authorization.split("Bearer ");
    String accessToken = "";
    for (String i : authorizationHeader) {
      accessToken = i;
    }
    CustomerAuth customerAuth = customerDao.findByAccessToken(accessToken);

    if (customerAuth == null) {
      throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
    } else if (hasUserSignedOut(customerAuth.getLogoutTime())) {
      throw new AuthorizationFailedException(
          "ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
    } else if (hasSessionExpired(customerAuth.getExpiryTime())) {
      throw new AuthorizationFailedException(
          "ATHR-003", "Your session is expired. Log in again to access this endpoint.");
    } else {
      Customer existingCustomer = customerAuth.getCustomerId();
      existingCustomer.setFirstName(newCustomer.getFirstName());
      existingCustomer.setLastName(newCustomer.getLastName());
      return customerDao.update(existingCustomer);
    }
    }



  /**
   * Checks if the customer has signed out by comparing if the current time is after the loggedOutTime
   * received by the method. Returns true if the current-time is after loggedOutTime(Logout has
   * happened), false otherwise
   *
   * @param loggedOutTime
   * @return boolean value true/false
   */
  public boolean hasUserSignedOut(ZonedDateTime loggedOutTime) {
    return (loggedOutTime != null && ZonedDateTime.now().isAfter(loggedOutTime));
  }

  /**
   * Checks if the customer session has expired by comparing if the current time is after the expiry time
   * received by the method. Returns true if the current-time is after expiry time(Session has expired),
   * false otherwise
   * @param expiryTime
   * @return boolean value true/false
   */
  public boolean hasSessionExpired(ZonedDateTime expiryTime) {
    return (expiryTime != null && ZonedDateTime.now().isAfter(expiryTime));
  }


}
