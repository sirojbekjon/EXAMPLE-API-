package com.example.api.controller;

import com.example.api.entity.Customer;
import com.example.api.payload.CustomerDto;
import com.example.api.payload.Result;
import com.example.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("api/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;


    /**
     * Bu yerda customerni POST qilganmiz
     * @param customerDto tipida JsonObject keladi
     * @return
     * validation qoydik
     */
    @PostMapping("/add")
    public Result addCustomer(@Valid@RequestBody CustomerDto customerDto){
        Result result = customerService.addCustomer(customerDto);
        return result;

    }


    /**
     * BU yerda customersni hammasini qayardik
     * @return
     */
    @GetMapping("/")
    public List<Customer> getCustomers(){
        Collection<Customer> customersService = customerService.getCustomersService();
        return (List<Customer>) customersService;
    }

    /**
     * Bir dona customerni id orqali qaytardik
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Optional<Customer> getCustomerById(@PathVariable Integer id){
        Optional<Customer> optionalCustomer = customerService.getCustomerById(id);
        return optionalCustomer;
    }

    /**
     * customerni o'zgartirish
     * @param id
     * @param customerDto
     * @return
     */
    @PutMapping("/{id}")
    public Result editeCustomerById(@PathVariable Integer id,@Valid @RequestBody CustomerDto customerDto){
        Result result = customerService.editCustomerByIdService(id,customerDto);
        return result;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Integer id){
        Result result = customerService.deleteCustomerById(id);
        return ResponseEntity.status(result.isSuccess()?202:409).body(result);
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }



}
