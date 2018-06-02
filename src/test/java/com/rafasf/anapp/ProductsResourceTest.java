package com.rafasf.anapp;

import io.vavr.control.Either;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ProductsResourceTest {
  @Mock
  ProductsClient products;

  MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    mockMvc = standaloneSetup(new ProductsResource(products)).build();
  }

  @Test
  public void returnsBadGatewayWithDescriptionWhenUnsuccessful() throws Exception {
    given(products.allProducts()).willReturn(Either.left(
      new AnError(HttpStatus.INTERNAL_SERVER_ERROR, "I failed")));

    mockMvc.perform(get("/products"))
      .andExpect(status().isBadGateway())
      .andExpect(jsonPath(
        "$.errors[0].description",
        equalTo("Unable to fetch Products from upstream")));
  }
}
