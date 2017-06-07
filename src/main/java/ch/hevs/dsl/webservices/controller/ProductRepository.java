package ch.hevs.dsl.webservices.controller;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ch.hevs.dsl.webservices.database.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
	
	ArrayList<Product> findByName(String name);
	
	@Query(value="{'name' : ?0, 'quantity' : ?0}")
	ArrayList<Product> findByNameAndQuantity(String name, double quantity);
}
