package com.example.api.service;

import com.example.api.entity.Customer;
import com.example.api.payload.CustomerDto;
import com.example.api.payload.Result;
import com.example.api.repository.CustomerReposetory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerReposetory customerReposetory;

    /**
     * Customer adding method
     * @param customerDto
     * @return
     */
    public Result addCustomer(CustomerDto customerDto){
        boolean exists = customerReposetory.existsByPhoneNumber(customerDto.getPhoneNumber());
        if (exists)
            return new Result("customer already exists",false);
        else {
            Customer customerAdd = new Customer();
            customerAdd.setPhoneNumber(customerDto.getPhoneNumber());
            customerAdd.setAddress(customerDto.getAddress());
            customerAdd.setFullName(customerDto.getFullName());
            customerReposetory.save(customerAdd);
        return new Result("customer added",true);
        }
    }

    /**
     * Get all customers from db
     * @return
     */
    public List<Customer> getCustomersService(){
        Collection<Customer> customers = customerReposetory.findAll();
        return (List<Customer>) customers;
    }

    /**
     * get a customer from db by id
     * @param id
     * @return
     */
    public Optional<Customer> getCustomerById(Integer id){
        Optional<Customer> customer = customerReposetory.findById(id);
        return customer;

    }

    /**
     * edit customer from db by id When after scane by id and phone number
     * @param id
     * @param customerDto
     * @return
     */
    public Result editCustomerByIdService(Integer id , CustomerDto customerDto) {
        boolean exists = customerReposetory.existsByPhoneNumberAndIdNot(customerDto.getPhoneNumber(), id);
        if (exists) {
            return new Result("this phonenumber already exists", false);
        }
        Optional<Customer> optionalCustomer = customerReposetory.findById(id);
        if (!optionalCustomer.isPresent()) {
            return new Result("this customer is not presint", false);
        }
        if (optionalCustomer.isPresent()) {
            Customer customerEdit = optionalCustomer.get();
            customerEdit.setAddress(customerDto.getAddress());
            customerEdit.setFullName(customerDto.getFullName());
            customerEdit.setPhoneNumber(customerDto.getPhoneNumber());
            customerReposetory.save(customerEdit);
            return new Result("customer edited", true);
        }
        return new Result("customer not found",false);
    }
    /**
     * delete customer from db by id
     * @param id
     * @return
     */
    public Result deleteCustomerById(Integer id){
        customerReposetory.deleteById(id);

        Optional<Customer> byId = customerReposetory.findById(id);
        if (byId.isPresent())
            return new Result("customer not deleted",false);
        return new Result("customer deleted",true);

    }
}
