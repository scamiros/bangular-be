package it.lucius.customers.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class CustomersController {

	/*@Autowired
	CustomersDao repository;
 
	@GetMapping(value="/customers",  produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Customers> getAll() {
		List<Customers> list = new ArrayList<>();
		Iterable<Customers> customers = repository.findAll();
 
		customers.forEach(list::add);
		return list;
	}
	
	@PostMapping(value="/postcustomer")
	public Customers postCustomer(@RequestBody Customers customer) {
 
		repository.save(new Customers(customer.getFirstName(), customer.getLastName()));
		return customer;
	}
 
	@GetMapping(value="/findbylastname/{lastName}",  produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Customers> findByLastName(@PathVariable String lastName) {
 
		List<Customers> customers = repository.findByLastName(lastName);
		return customers;
	}
	
	@DeleteMapping(value="/customer/{id}")
	public void deleteCustomer(@PathVariable long id){
		
		repository.delete(id);
	}*/
}
