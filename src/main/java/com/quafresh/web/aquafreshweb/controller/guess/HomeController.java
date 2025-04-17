package com.quafresh.web.aquafreshweb.controller.guess;

import com.quafresh.web.aquafreshweb.dto.admin.ProductDetailAdminDTO;
import com.quafresh.web.aquafreshweb.dto.guess.*;
import com.quafresh.web.aquafreshweb.entity.*;
import com.quafresh.web.aquafreshweb.service.Impl.*;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class HomeController {

    private final ProductDetailImpl productDetail;
    private final ColorServiceImpl colorServiceImpl;
    private final DiscountServiceImpl discountServiceImpl;
    private final CategoryServiceImpl categoryServiceImpl;
    private final TechnologyServiceImpl technologyServiceImpl;
    private final CompanyServiceImpl companyServiceImpl;
    private final PictureGuessServiceImpl pictureGuessService;
    private final WardServiceImpl wardService;

    @GetMapping
    public ResponseEntity<List<ProductDetail>> getAllProduct() {
        return productDetail.getAll();
    }

    @GetMapping("/picture")
    public ResponseEntity<List<PictureGuessDTO>> getAllPicture() {
        return pictureGuessService.getAllPicture();
    }

    @GetMapping("/search")
//    public ResponseEntity<List<ProductDetailGuessDTO>> search(
//            @RequestParam(required = false) Integer technologyId,
//            @RequestParam(required = false) String productName,
//            @RequestParam(required = false) BigDecimal priceFrom,
//            @RequestParam(required = false) BigDecimal priceTo
//    ) {
//        return pictureGuessService.getAllPictureProductDetail(technologyId, productName, priceFrom, priceTo);
//    }
    public ResponseEntity<List<ProductDetailAdminDTO>> searchByProductName(@Param("keyword") String keyword) {
        return ResponseEntity.ok(productDetail.searchByProductName(keyword));
    }
    @GetMapping("/best-selling")
    public List<BestSellingProductDTO> getBestSellingProducts() {
        return productDetail.getBestSellingProducts();
    }
    @GetMapping("/{id}")
    public ProductDetailGuessDTO2 getById(@PathVariable Integer id) {
        return productDetail.getById(id);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategory() {
        return categoryServiceImpl.getAllCategory();
    }

    @GetMapping("/company")
    public ResponseEntity<List<Company>> getAllCompany() {
        return companyServiceImpl.getAllCompany();

    }

    @GetMapping("/color")
    public ResponseEntity<List<Color>> getAllColor() {
        return colorServiceImpl.getAllColor();

    }

    @GetMapping("/discount")
    public ResponseEntity<List<Discount>> getAllDiscount() {
        return discountServiceImpl.getAllDiscount();

    }

    @GetMapping("/technology")
    public ResponseEntity<List<Technology>> getAllCTechnology() {
        return technologyServiceImpl.getAllTechnology();
    }

}
