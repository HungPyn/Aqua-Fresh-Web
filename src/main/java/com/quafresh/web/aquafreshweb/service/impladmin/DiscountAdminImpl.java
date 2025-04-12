package com.quafresh.web.aquafreshweb.service.impladmin;

import com.quafresh.web.aquafreshweb.dto.admin.DiscountAdminDTO;
import com.quafresh.web.aquafreshweb.dto.admin.ProductDetailAdminDTO;
import com.quafresh.web.aquafreshweb.entity.Discount;
import com.quafresh.web.aquafreshweb.entity.ProductDetail;
import com.quafresh.web.aquafreshweb.repositories.DiscountRepository;
import com.quafresh.web.aquafreshweb.repositories.ProductDetailRepository;
import com.quafresh.web.aquafreshweb.repositories.ProductRepository;
import com.quafresh.web.aquafreshweb.service.admin.DiscountAdminService;
import com.quafresh.web.aquafreshweb.util.AdminMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountAdminImpl implements DiscountAdminService {
    private final DiscountRepository discountRepository;
    private final AdminMapper adminMapper;
    private final ProductDetailRepository productDetailRepository;

    @Override
    public DiscountAdminDTO getByID(Integer id) {
        Discount discount = discountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Discount not found"));
        return adminMapper.toDiscountAdminDTO(discount);
    }

    @Override
    public List<DiscountAdminDTO> getAll() {
        List<Discount> discountList = discountRepository.findAll();
        return discountList.stream().map(adminMapper::toDiscountAdminDTO).collect(Collectors.toList());
    }

    @Override
    public List<DiscountAdminDTO> search(String keyword) {
        List<Discount> discountList = discountRepository.findByName(keyword);
        return discountList.stream().map(adminMapper::toDiscountAdminDTO).collect(Collectors.toList());
    }

    @Override
    public DiscountAdminDTO create(DiscountAdminDTO discountAdminDTO) {
        // Tạo entity Discount từ DTO
        Discount discount = new Discount();
        discount.setDiscountName(discountAdminDTO.getName());
        discount.setDiscountValue(discountAdminDTO.getDiscountValue());
        discount.setDescription(discountAdminDTO.getDescription());
        discount.setStartDate(discountAdminDTO.getStartDate());
        discount.setEndDate(discountAdminDTO.getEndDate());
        discount.setActive(discountAdminDTO.getIsActive());

        // Khởi tạo danh sách rỗng nếu cần
        discount.setProductDetailList(new ArrayList<>());

        // Nếu có ProductDetail được chọn thì thêm vào
        if (discountAdminDTO.getProductDetails() != null && !discountAdminDTO.getProductDetails().isEmpty()) {
            for (ProductDetailAdminDTO dto : discountAdminDTO.getProductDetails()) {
                ProductDetail productDetail = productDetailRepository.findById(dto.getId())
                        .orElseThrow(() -> new EntityNotFoundException("ProductDetail not found with id: " + dto.getId()));
                productDetail.setIdDiscount(discount);
                discount.add(productDetail); // dùng method add() trong entity
            }
        }

        // Lưu
        Discount savedDiscount = discountRepository.save(discount);
        return adminMapper.toDiscountAdminDTO(savedDiscount);
    }



    @Override
    public DiscountAdminDTO update(Integer id, DiscountAdminDTO discountAdminDTO) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discount not found with id: " + id));
        discount.setDiscountName(discountAdminDTO.getName());
        discount.setDiscountValue(discountAdminDTO.getDiscountValue());
        discount.setDescription(discountAdminDTO.getDescription());
        discount.setStartDate(discountAdminDTO.getStartDate());
        discount.setEndDate(discountAdminDTO.getEndDate());
        discount.setActive(discountAdminDTO.getIsActive());

        List<Integer> newIds = discountAdminDTO.getProductDetails() != null
                ? discountAdminDTO.getProductDetails().stream()
                .map(ProductDetailAdminDTO::getId)
                .collect(Collectors.toList())
                : new ArrayList<>();

        List<ProductDetail> currentList = discount.getProductDetailList() != null
                ? discount.getProductDetailList()
                : new ArrayList<>();

        List<ProductDetail> toRemove = new ArrayList<>();
        for (ProductDetail pd : currentList) {
            if (!newIds.contains(pd.getId())) {
                pd.setIdDiscount(null);
                toRemove.add(pd);
            }
        }
        toRemove.forEach(discount::remove);

        for (Integer productId : newIds) {
            boolean alreadyLinked = currentList.stream()
                    .anyMatch(pd -> pd.getId().equals(productId));

            if (!alreadyLinked) {
                ProductDetail newDetail = productDetailRepository.findById(productId)
                        .orElseThrow(() -> new EntityNotFoundException("ProductDetail not found with id: " + productId));
                newDetail.setIdDiscount(discount);
                discount.add(newDetail);
            }
        }

        Discount updated = discountRepository.save(discount);
        return adminMapper.toDiscountAdminDTO(updated);
    }



    @Override
    @Transactional
    public String delete(Integer id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discount not found with id: " + id));

        if (discount.getProductDetailList() != null) {
            List<ProductDetail> copyList = new ArrayList<>(discount.getProductDetailList());

            for (ProductDetail pd : copyList) {
                pd.setIdDiscount(null);
                discount.remove(pd);
            }
        }
        discountRepository.delete(discount);

        return "Deleted discount with id: " + id;
    }

}
