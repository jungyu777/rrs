package com.junjun.review.service;

import com.junjun.review.api.request.CreateAndEditRestaurantRequest;
import com.junjun.review.api.response.RestaurantDetailView;
import com.junjun.review.api.response.RestaurantView;
import com.junjun.review.model.MenuEntity;
import com.junjun.review.model.RestaurantEntity;
import com.junjun.review.repository.MenuRepository;
import com.junjun.review.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    /**
     *
     * id값은 넘겨 받을 필요가 없다 왜나하면 id는 데이터베이스에서 자동으로 생성이 되기 떄문이다 이럴떄는
     * RestaurantEntity 에  Builder 에노테이션을 추가해주자
     */
    @Transactional //데이터베이스의 기술 혹은 논리적인 이름
    public RestaurantEntity createRestaurant(
            CreateAndEditRestaurantRequest request
    ){
        RestaurantEntity restaurantEntity = RestaurantEntity.builder()
                .name(request.getName())
                .address(request.getAddress())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();
        restaurantRepository.save(restaurantEntity);

        request.getMenus().forEach((menu)->{
            MenuEntity menuEntity = MenuEntity.builder()
                    .restaurantId(restaurantEntity.getId())
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .createdAt(ZonedDateTime.now())
                    .updateAt(ZonedDateTime.now())
                    .build();
            menuRepository.save(menuEntity);
        });

        return restaurantEntity;
    }
    @Transactional
    public void editRestaurant(
            Long restaurantId,
            CreateAndEditRestaurantRequest request
    ){
       RestaurantEntity restaurant= restaurantRepository.findById(restaurantId).orElseThrow(()->new RuntimeException("없는 레스토랑 입니다"));
       //수정한 레스토랑을 다시 세이브 해준다
        restaurant.changeNameAndAddress(request.getName(),request.getAddress());
        restaurantRepository.save(restaurant);

        //레스토랑 아디디에 연결되어있는 메뉴들을 모두 가져와서 삭제한 이후에 다시 메뉴들을 생성해준다
        List<MenuEntity> menus = menuRepository.findAllByRestaurantId(restaurantId);
        menuRepository.deleteAll(menus);
        //이전에 있던 메뉴들은 모두 삭제가 되고 새로운 메뉴들이 다시 생성이 되는 로직
        request.getMenus().forEach((menu)->{
            MenuEntity menuEntity =MenuEntity.builder()
                    .restaurantId(restaurantId)
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .createdAt(ZonedDateTime.now())
                    .updateAt(ZonedDateTime.now())
                    .build();

            menuRepository.save(menuEntity);
        });

    }
    @Transactional  //레스토랑 아이디만 받아서 구현을 해주어야한다
    public void deleteRestaurant(Long restaurantId){
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        restaurantRepository.delete(restaurant);
        //맛집에 연결된 메뉴둘도 함께 삭제해주어야한다
        List<MenuEntity> menus =menuRepository.findAllByRestaurantId(restaurantId);
        menuRepository.deleteAll(menus);

    }

    /**
     *
     * 생성이나 수정 삭제가 없는 메소드에서는   @Transactional  을 꼭 붙여주지 않아도 된다
     * 만약 붙여주고 싶다면 @Transactional(readOnly = true)
     */
    @Transactional(readOnly = true)
    //맛집정보 리스트 가져오기
    public List<RestaurantView> getAllRestaurants(){
        List<RestaurantEntity> restaurants =restaurantRepository.findAll();

        return restaurants.stream().map((restaurant)-> RestaurantView.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .createdAt(restaurant.getCreatedAt())
                .updatedAt(restaurant.getUpdatedAt())
                .build()
        ).toList();
    }
    //맛집정보
    @Transactional(readOnly = true)
    public RestaurantDetailView getRestaurantDetail(Long restaurantId){
        RestaurantEntity restaurant =restaurantRepository.findById(restaurantId).orElseThrow();
        List<MenuEntity> menus = menuRepository.findAllByRestaurantId(restaurantId);

        return RestaurantDetailView.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .createdAt(restaurant.getCreatedAt())
                .updatedAt(restaurant.getUpdatedAt())
                .menus(
                        menus.stream().map((menu)->RestaurantDetailView.Menu.builder()
                                .id(menu.getId())
                                .name(menu.getName())
                                .price(menu.getPrice())
                                .createdAt(menu.getCreatedAt())
                                .updatedAt(menu.getUpdateAt())
                                .build()
                        ).toList()
                )
                .build();
    }


}
