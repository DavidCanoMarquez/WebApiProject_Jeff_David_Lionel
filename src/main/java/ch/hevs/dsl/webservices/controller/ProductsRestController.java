package ch.hevs.dsl.webservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.hevs.dsl.webservices.database.Product;

@RestController
@RequestMapping("/products")
public class ProductsRestController {  
	
	@Autowired
	private ProductRepository repo;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Product> getAllProducts() {
		return repo.findAll();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="quantity")
	public List<Product> getProductsSortedQty() {
		return repo.findAll(new Sort(Sort.Direction.ASC, "quantity"));
	}
	
	@RequestMapping(method=RequestMethod.GET, value="{name}")
	public List<Product> getProductsByName(@PathVariable String name) {
		return repo.findByName(name);
	}
  
	@RequestMapping(method=RequestMethod.GET, value="{name}/{quantity}")
	public List<Product> getProductsByNameAndQuantity(@PathVariable String name, @PathVariable double quantity) {
		return repo.findByNameAndQuantity(name, quantity);
	}
	
  @RequestMapping(method=RequestMethod.POST)
  public Product createProduct(@RequestBody Product product) {
    return repo.save(product);
  }
  
  @RequestMapping(method=RequestMethod.DELETE, value="{id}")
  public void deleteProduct(@PathVariable String id) {
	  repo.delete(id);
  }
  
  @RequestMapping(method=RequestMethod.PUT, value="{id}")
  public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
	  	Product update = repo.findOne(id);
	    update.setIngredients(product.getIngredients());
	    update.setName(product.getName());
	    update.setNutrients(product.getNutrients());
	    update.setPortion_quantity(product.getPortion_quantity());
	    update.setPortionUnit(product.getPortionUnit());
	    update.setQuantity(product.getQuantity());
	    update.setUnit(product.getUnit());
	    return repo.save(update);
  }
  
  /*@RequestMapping(value = "/deleteAll", method=RequestMethod.GET)
  public String deleteAll() {
	  return "Hello";
  }*/

}
