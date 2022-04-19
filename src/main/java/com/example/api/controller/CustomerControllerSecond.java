package com.example.api.controller;

import com.example.api.entity.Customer;
import com.example.api.payload.CustomerDto;
import com.example.api.payload.Result;
import com.example.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
//////////////////////////////////////////////////////RESTFULL  SERVICE ga misol qilib olingan
@RestController
@RequestMapping("api/customer")
public class CustomerControllerSecond {

    @Autowired
    CustomerService customerService;


    /**
     * Bu yerda customerni POST qilganmiz
     * @param customerDto tipida JsonObject keladi
     * @return
     * validation qoydik
     */
    @PostMapping("/add")
    public HttpEntity<Result> addCustomer(@Valid@RequestBody CustomerDto customerDto){
        Result result = customerService.addCustomer(customerDto);

        //1 - usul
//        if (result.isSuccess()){
//            return ResponseEntity.status(201).body(result);
//        }
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);


        //2 - usul
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(result);



    }


    /**
     * BU yerda customersni hammasini qayardik
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<List<Customer>> getCustomers(){
        List<Customer> customersService = customerService.getCustomersService();
        return ResponseEntity.ok(customersService);
    }

    /**
     * Bir dona customerni id orqali qaytardik
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpEntity<Optional<Customer>> getCustomerById(@PathVariable Integer id){
        Optional<Customer> customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    /**
     * customerni o'zgartirish
     * @param id
     * @param customerDto
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Result> editeCustomerById(@PathVariable Integer id, @Valid @RequestBody CustomerDto customerDto){
        Result result = customerService.editCustomerByIdService(id,customerDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(result);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Integer id){
            Result result = customerService.deleteCustomerById(id);
            return ResponseEntity.status(result.isSuccess() ? 202 : 409).body(result);
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
