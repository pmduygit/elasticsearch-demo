package com.springboot.elasticsearchdemo.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.springboot.elasticsearchdemo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ElasticsearchClient client;

    public Product createProduct(Product product) {
        try {
            IndexResponse response = client.index(i -> i
                    .index("products")
                    .document(product));
            product.setId(response.id());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    public List<Product> getAllProducts() throws IOException {
        SearchRequest request = new SearchRequest.Builder().index("products").build();
        SearchResponse<Product> response = client.search(request, Product.class);
        List<Hit<Product>> hits = response.hits().hits();
        List<Product> products = new ArrayList<>();
        hits.forEach(item -> {
            assert item.source() != null;
            Product product = Product.builder()
                    .id(item.id())
                    .name(item.source().getName())
                    .description(item.source().getDescription())
                    .price(item.source().getPrice())
                    .category(item.source().getCategory())
                    .build();
            products.add(product);
        });

        return products;
    }

    public Product getProductById(String id) throws IOException {
        GetRequest request = new GetRequest.Builder()
                .index("products")
                .id(id)
                .build();

        GetResponse<Product> response = client.get(request, Product.class);

        if (response.found()) {
            assert response.source() != null;
            return Product.builder()
                    .id(response.id())
                    .name(response.source().getName())
                    .description(response.source().getDescription())
                    .price(response.source().getPrice())
                    .category(response.source().getCategory())
                    .build();
        }

        return null;
    }
}
