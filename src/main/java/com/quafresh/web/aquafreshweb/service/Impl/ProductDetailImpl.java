package com.quafresh.web.aquafreshweb.service.Impl;

import com.quafresh.web.aquafreshweb.dto.admin.ProductDetailAdminDTO;
import com.quafresh.web.aquafreshweb.dto.guess.BestSellingProductDTO;
import com.quafresh.web.aquafreshweb.dto.guess.ProductDetailGuessDTO2;
import com.quafresh.web.aquafreshweb.entity.Picture;
import com.quafresh.web.aquafreshweb.entity.ProductDetail;
import com.quafresh.web.aquafreshweb.repositories.PictureRepository;
import com.quafresh.web.aquafreshweb.repositories.ProductDetailRepository;
import com.quafresh.web.aquafreshweb.service.guess.ProductDetailService;
import com.quafresh.web.aquafreshweb.util.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductDetailImpl implements ProductDetailService {
    private final ProductDetailRepository productDetailRepository;
    private  final PictureRepository pictureRepository;
    private final AdminMapper adminMapper;
    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<List<ProductDetail>> getAll() {
        try {
//            List<ProductDetailDTO> dtoList = productDetailRepository.findAll()
//                    .stream()
//                    .map(ProductDetailDTO::new)
//                    .toList();

            return new ResponseEntity<>(productDetailRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ProductDetailGuessDTO2 getById(Integer id) {
        ProductDetail productDetail = productDetailRepository.findById(id).orElseThrow();
        List<Picture> pictureRepository1 = pictureRepository.findByIdProductDetail(productDetail);
        ProductDetailGuessDTO2 productDetailGuessDTO2 = new ProductDetailGuessDTO2();
        productDetailGuessDTO2.setProductDetail(productDetail);
        productDetailGuessDTO2.setPictures(pictureRepository1);
        return productDetailGuessDTO2;
    }

    @Override
    public List<BestSellingProductDTO> getBestSellingProducts() {
        List<BestSellingProductDTO> all= productDetailRepository.findBestSellingProductDetails();
        return all.stream().limit(20).collect(Collectors.toList());

    }

//    @Override
//    public List<ProductDetailAdminDTO> searchByProductName(String keyword) {
//        List<ProductDetail> listSearch = productDetailRepository.findByProductName(keyword);
//        return  listSearch.stream().map(p->adminMapper.toProductDetailAdminDTO(p)).collect(Collectors.toList());
//    }
}
