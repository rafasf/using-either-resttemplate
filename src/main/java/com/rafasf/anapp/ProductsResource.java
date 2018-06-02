package com.rafasf.anapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rafasf.anapp.ErrorsRepresentation.errorsWith;

@RestController
@RequestMapping(
  value = "/products",
  produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductsResource {
  private ProductsClient products;

  public ProductsResource(ProductsClient products) {
    this.products = products;
  }

  @GetMapping
  public ResponseEntity<?> allProducts() {
    return products
      .allProducts()
      .fold(this::toProperError, ResponseEntity::ok);
  }

  private ResponseEntity<ErrorsRepresentation> toProperError(AnError anError) {
    return ResponseEntity
      .status(HttpStatus.BAD_GATEWAY)
      .body(errorsWith("Unable to fetch Products from upstream"));
  }
}
