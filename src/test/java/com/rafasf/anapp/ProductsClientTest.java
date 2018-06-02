package com.rafasf.anapp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

@SuppressWarnings("WeakerAccess")
public class ProductsClientTest {
  @Mock RestTemplate restTemplate;

  ProductsClient products;

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    products = new ProductsClient(restTemplate);
  }

  @Test
  public void returnsListOfProductsWhenSuccessful() {
    given(restTemplate.getForEntity("//products", Product[].class))
      .willReturn(new ResponseEntity<>(new Product[]{}, HttpStatus.OK));

    List<Product> allProducts = this.products.allProducts();

    assertThat(allProducts, is(equalTo(List.of())));
  }
}
