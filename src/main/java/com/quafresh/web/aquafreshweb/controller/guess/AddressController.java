package com.quafresh.web.aquafreshweb.controller.guess;

import com.quafresh.web.aquafreshweb.dto.guess.IdRequest;
import com.quafresh.web.aquafreshweb.entity.District;
import com.quafresh.web.aquafreshweb.entity.Province;
import com.quafresh.web.aquafreshweb.entity.Ward;
import com.quafresh.web.aquafreshweb.service.Impl.DistrictServiceImpl;
import com.quafresh.web.aquafreshweb.service.Impl.ProvinceServiceImpl;
import com.quafresh.web.aquafreshweb.service.Impl.WardServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/address")
@AllArgsConstructor
public class AddressController {

    private final WardServiceImpl wardService;
    private final ProvinceServiceImpl provinceService;
    private final DistrictServiceImpl districtService;
    @GetMapping("/province")
    public ResponseEntity<List<Province>> getAllProvince(){
        return provinceService.getAllProvince();
    }

//    @PostMapping("/district")
//    public ResponseEntity<List<District>> getDistrictByProvinceId(@RequestBody IdRequest id){
//        return districtService.getDistrictByProvinceId(id);
//    }
    @GetMapping("/district")
    public ResponseEntity<List<District>> getDistrictByProvinceId(@RequestParam Integer id){
        return districtService.getDistrictByProvinceId(id);
    }

//    @PostMapping("/ward")
//    public ResponseEntity<List<Ward>> getWardByDistrictId(@RequestBody IdRequest id){
//        return wardService.getWardByDistrictId(id);
//    }
    @GetMapping("/ward")
    public  ResponseEntity<List<Ward>> getWard(@RequestParam Integer id){
        return wardService.getWardByDistrictId(id);
    }

}
