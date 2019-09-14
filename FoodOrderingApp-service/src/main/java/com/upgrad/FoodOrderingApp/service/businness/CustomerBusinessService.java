package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.Customer;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CustomerBusinessService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

  public Customer signUp(Customer customer)
          throws SignUpRestrictedException {

    if (customer.getContactNumber()!= null && customerDao.getContactNumber(customer.getContactNumber()) != null) {
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

}
