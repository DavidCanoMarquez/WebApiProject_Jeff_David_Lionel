/*Author: Jeff Zufferey;
*
*
*Project Name: demo2
*Package: ch.hevs.dsl.webservice.Food
*File name: Restclient.java
*
*
*Date of creation: 6 juin 2017
*
*/



package ch.hevs.dsl.webservice.Food;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.hevs.dsl.webservices.database.*;

@RestController
@RequestMapping("/food")
public class RestclientController {
	@Autowired
	ClientRepository folder;
		
	@RequestMapping(method=RequestMethod.POST)
	public Product create(@RequestBody Product product){
		return folder.save(product);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="{id}")
	public void delete(@PathVariable String id){
		folder.delete(id);
	}
	@RequestMapping(method=RequestMethod.PUT, value="{id}")
	public Product update(@RequestBody Product product, @PathVariable String id){
		System.out.println(product.getName()+id);
		Product update = folder.findOne(id);
		update.setName(product.getName());
		update.setIngredients(product.getIngredients());
		update.setQuantity(product.getQuantity());
		update.setUnit(product.getUnit());
		update.setPortion_quantity(product.getPortion_quantity());
		update.setPortionUnit(product.getPortionUnit());
		update.setNutrients(product.getNutrients());
		return folder.save(update);
	}
	@RequestMapping(method=RequestMethod.GET)
	public List<Product> getAll(){
		return folder.findAll();
	}
	@RequestMapping(method=RequestMethod.GET, params={"name"})
	public List<Product> getByNames(@RequestParam(value = "name") String name){
		return folder.findByNameIgnoreCase(name);	
	}
	@RequestMapping(method=RequestMethod.GET, value="quantity")
	public List<Product> getAllByQuantites(){
		return folder.findAll(new Sort(Sort.Direction.ASC, "quantity"));
	}
	@RequestMapping(method=RequestMethod.GET, params={"name", "quantity"})
	public List<Product> getByNamesAndQuantity(@RequestParam(value = "name") String name, 
			@RequestParam(value = "quantity") double quantity){
		return folder.findByQuantityAndNameIgnoreCase(quantity, name);		
	}

}
