package com.junjun.review.api;

import com.junjun.review.api.request.CreateAndEditRestaurantRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestaurantApi {
    //맛집 리스트 가져오기 API
    @GetMapping("/restaurants")
    public String getRestaurants(){
        return "This is getRestaurants";
    }

    //맛집 정보 가져오기 API
    @GetMapping("/restaurant/{restaurantId}")
    public String getRestaurant(
            @PathVariable Long restaurantId
    ){
        return "This is getRestaurant, " + restaurantId;
    }
    //맛집 생성 API
    @PostMapping("/restaurant")
    public String createRestaurant(
            @RequestBody CreateAndEditRestaurantRequest request
            ){
        return "This is createRestaurant, name =  " + request.getName() +  " , " +request.getAddress()
                + ", menu[0].name = " + request.getMenus().get(0).getName()+ ", menu[0].price = " + request.getMenus().get(0).getPrice();
    }
    //맛집 수정 API
    @PutMapping("/restaurant/{restaurantId}")
    public String editRestaurant(
            @PathVariable Long restaurantId,
            @RequestBody CreateAndEditRestaurantRequest request
    ){
        return "This is editRestaurant " + restaurantId + "name = " + request.getName() + ", address  = "+request.getAddress();
    }
    //맛집 삭제 API
    @DeleteMapping("/restaurant/{restaurantId}")
    public String deleteRestaurant(
            @PathVariable Long restaurantId
    ){
        return "This is deleteRestaurant," + restaurantId;
    }
}
