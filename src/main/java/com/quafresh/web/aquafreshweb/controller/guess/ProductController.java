package com.quafresh.web.aquafreshweb.controller.guess;

import com.quafresh.web.aquafreshweb.entity.Picture;
import com.quafresh.web.aquafreshweb.entity.ProductDetail;
import com.quafresh.web.aquafreshweb.service.Impl.PictureGuessServiceImpl;
import com.quafresh.web.aquafreshweb.service.Impl.ProductDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductDetailImpl productDetail;
    @Autowired
    PictureGuessServiceImpl pictureGuessService;
    @GetMapping
    public ResponseEntity<List<ProductDetail>> getAllProduct(){
        return productDetail.getAll();
    }
    @GetMapping("picture")
    public ResponseEntity<List<Picture>> getAllPicture(){
        return pictureGuessService.getAllPicture();
    }
}
