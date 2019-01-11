package com.edu.hartige.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashMap;
import java.util.Map;

@Entity
public class ProductCatalog {

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany(cascade = javax.persistence.CascadeType.ALL)
	private Map<Long, Product> products = new HashMap<>();
	
	public void add(Product product, int quantity) {
		assert(product.getId() != null);
		products.put(product.getId(), product);
	}

	public Product find(Long id) {
	    assert(id != null);
	    return products.get(id);
    }
	
	public Product decrementStock(Long productId) {
		assert(products.containsKey(productId));
		return null;
	}

	public ProductCatalog() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Map<Long, Product> getProducts() {
		return products;
	}

	public void setProducts(Map<Long, Product> products) {
		this.products = products;
	}
}
