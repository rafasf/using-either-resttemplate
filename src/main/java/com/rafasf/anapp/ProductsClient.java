package com.rafasf.anapp;

import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;

public class ProductsClient {
  private RestTemplate restTemplate;

  public ProductsClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<Product> allProducts() {
    var response = restTemplate
      .getForEntity("//products", Product[].class);
    return response.getStatusCode().is2xxSuccessful()
      ? asList(response.getBody())
      : List.of();
  }
}
