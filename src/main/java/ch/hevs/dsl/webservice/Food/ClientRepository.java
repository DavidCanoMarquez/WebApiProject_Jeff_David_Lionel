/*Author: Jeff Zufferey;
*
*
*Project Name: demo2
*Package: ch.hevs.dsl.webservice.Food
*File name: ClientRepository.java
*
*
*Date of creation: 6 juin 2017
*
*/


//
package ch.hevs.dsl.webservice.Food;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.hevs.dsl.webservices.database.*;

public interface ClientRepository extends MongoRepository<Product, String>{
	//
		List<Product> findByName(String name);
		
		List<Product> findByQuantityAndName(double quantity, String name);
}
