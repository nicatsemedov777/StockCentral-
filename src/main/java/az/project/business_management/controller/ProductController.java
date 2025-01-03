package az.project.business_management.controller;

import az.project.business_management.model.request.InsertProductRequest;
import az.project.business_management.model.request.SellProductRequest;
import az.project.business_management.model.response.ProductResponse;
import az.project.business_management.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;

    @GetMapping("test123")
    public String test() {
        return "OK!";
    }

    @PostMapping("add-as-single")
    public HttpStatus insertProduct(@RequestBody @Valid InsertProductRequest insertProductRequest) {
        productService.insertProduct(insertProductRequest);
        return HttpStatus.OK;
    }

    @PostMapping("add-as-list")
    public HttpStatus insertProductAsList(@RequestBody @Validated List<InsertProductRequest> insertProductRequest) {
        productService.insertProductAsList(insertProductRequest);
        return HttpStatus.OK;
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        return productService.getAll();
    }

    @PostMapping("/sell-product")
    public HttpStatus sellProduct(@RequestBody @Valid SellProductRequest sellProductRequest) {
        productService.sellProduct(sellProductRequest);
        return HttpStatus.OK;
    }
}
