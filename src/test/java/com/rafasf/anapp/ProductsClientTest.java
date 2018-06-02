package com.rafasf.anapp;

import io.vavr.control.Either;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
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

    Either<AnError, List<Product>> possiblyAllProducts = this.products.allProducts();

    assertThat(possiblyAllProducts.isRight(), is(true));
    assertThat(possiblyAllProducts.get(), is(equalTo(List.of())));
  }

  @Test
  public void returnsAnErrorWhen4xx() {
    given(restTemplate.getForEntity("//products", Product[].class))
      .willThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "ops"));

    Either<AnError, List<Product>> possiblyAllProducts = this.products.allProducts();

    assertThat(possiblyAllProducts.isLeft(), is(true));
    assertThat(
      possiblyAllProducts.getLeft(),
      is(equalTo(new AnError(HttpStatus.BAD_REQUEST, "400 ops"))));
  }

  @Test
  public void returnsAnErrorWhen5xx() {
    given(restTemplate.getForEntity("//products", Product[].class))
      .willThrow(new HttpClientErrorException(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "ops"));

    Either<AnError, List<Product>> possiblyAllProducts = this.products.allProducts();

    assertThat(possiblyAllProducts.isLeft(), is(true));
    assertThat(
      possiblyAllProducts.getLeft(),
      is(equalTo(new AnError(HttpStatus.INTERNAL_SERVER_ERROR, "500 ops"))));
  }
}
