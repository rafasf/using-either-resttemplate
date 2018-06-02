package com.rafasf.anapp;

import io.vavr.control.Either;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.rafasf.anapp.ClientRequest.clientRequest;

@Component
public class ProductsClient {
  private RestTemplate restTemplate;

  public ProductsClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public Either<AnError, List<Product>> allProducts() {
    return clientRequest(() -> List.of(restTemplate
      .getForEntity("//products", Product[].class)
      .getBody()));
  }
}
