package uz.pdp.appspringpcmarket.service;

import org.springframework.stereotype.Service;
import uz.pdp.appspringpcmarket.entity.Basket;
import uz.pdp.appspringpcmarket.entity.BasketProduct;
import uz.pdp.appspringpcmarket.entity.Product;
import uz.pdp.appspringpcmarket.payload.ApiResponse;
import uz.pdp.appspringpcmarket.payload.BasketDto;
import uz.pdp.appspringpcmarket.payload.BasketResponse;
import uz.pdp.appspringpcmarket.repository.BasketProductRepository;
import uz.pdp.appspringpcmarket.repository.BasketRepository;
import uz.pdp.appspringpcmarket.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService {

    private final BasketRepository basketRepository;

    private final ProductRepository productRepository;

    private final BasketProductRepository basketProductRepository;

    public BasketService(BasketRepository basketRepository,
                         ProductRepository productRepository,
                         BasketProductRepository basketProductRepository) {
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
        this.basketProductRepository = basketProductRepository;
    }

    public BasketResponse addProductToBasket(Integer basketId, Integer productId, Integer amount){
        Basket basket;
        if (basketId==null) {
            basket = new Basket();
            basketRepository.save(basket);
        }else {
            Optional<Basket> optionalBasket = basketRepository.findById(basketId);
            if(!optionalBasket.isPresent())
                return new BasketResponse("savat topilmadi", false, null);
            basket= optionalBasket.get();
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(!optionalProduct.isPresent())
            return new BasketResponse("product topilmadi", false, null);

        Product product = optionalProduct.get();

        List<BasketProduct> basketProducts = basket.getBasketProducts();
        BasketProduct basketProduct=new BasketProduct(basket, product, amount);
        basketProductRepository.save(basketProduct);
        boolean add = basketProducts.add(basketProduct);
        return new BasketResponse("product savatga qo'shildi", true, basket.getId());
    }

    public List<Basket> getAllBaskets(){
        return basketRepository.findAll();
    }

    public Basket getBasketById(Integer id){
        return basketRepository.findById(id).orElse(null);
    }

    public ApiResponse deleteProductFromBasket(Integer basketId, Integer basketProductId){
        Optional<Basket> optionalBasket = basketRepository.findById(basketId);
        if(!optionalBasket.isPresent())
            return new ApiResponse("savat topilmadi", false);

        Optional<BasketProduct> optionalBasketProduct = basketProductRepository.findById(basketProductId);
        if(!optionalBasketProduct.isPresent())
            return new ApiResponse("product topilmadi", false);

        BasketProduct basketProduct=optionalBasketProduct.get();
        Basket basket= optionalBasket.get();
        List<BasketProduct> basketProducts = basket.getBasketProducts();
        basketProductRepository.deleteAll(basketProducts);

        if(!basketProducts.contains(basketProduct))
            return new ApiResponse("savatda bunaqa product yo'q", false);

        boolean remove = basketProducts.remove(basketProduct);

        if(remove)
            return new ApiResponse("product savatdan o'chirildi", true);
        else
            return new ApiResponse("xatolik", false);
    }

    public ApiResponse createOrder(Integer basketId, BasketDto basketDto){
        Optional<Basket> optionalBasket = basketRepository.findById(basketId);
        if(!optionalBasket.isPresent())
            return new ApiResponse("savat topilmadi", false);

        try {
            Basket basket = optionalBasket.get();
            basket.setPhoneNumber(basketDto.getPhoneNumber());
            basket.setEmail(basketDto.getEmail());
            basket.setName(basketDto.getName());
            basket.setAddress(basketDto.getAddress());
            basket.setGeolocation(basketDto.getGeolocation());
            basket.setBasket(basketDto.isBasket());
            basketRepository.save(basket);
            return new ApiResponse("buyurtma saqlandi", true);
        }catch (Exception e) {
            return new ApiResponse(e.getMessage(), false);
        }
    }

    public ApiResponse deleteBasket(Integer basketId){
        Optional<Basket> optionalBasket = basketRepository.findById(basketId);
        if(!optionalBasket.isPresent())
            return new ApiResponse("savat topilmadi", false);

        List<BasketProduct> basketProductList = basketProductRepository.findAllByBasket(optionalBasket.get());
        basketProductRepository.deleteAll(basketProductList);

        try{
            basketRepository.deleteById(basketId);
            return new ApiResponse("savat o'chirildi", true);
        }catch (Exception e){
            return new ApiResponse(e.getMessage(), false);
        }
    }

}
