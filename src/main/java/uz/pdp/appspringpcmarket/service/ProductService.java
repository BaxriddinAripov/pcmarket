package uz.pdp.appspringpcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appspringpcmarket.entity.Attachment;
import uz.pdp.appspringpcmarket.entity.Category;
import uz.pdp.appspringpcmarket.entity.Character;
import uz.pdp.appspringpcmarket.entity.Product;
import uz.pdp.appspringpcmarket.payload.ApiResponse;
import uz.pdp.appspringpcmarket.payload.ProductDto;
import uz.pdp.appspringpcmarket.payload.ProductFilterDto;
import uz.pdp.appspringpcmarket.repository.AttachmentRepository;
import uz.pdp.appspringpcmarket.repository.CategoryRepository;
import uz.pdp.appspringpcmarket.repository.CharacterRepository;
import uz.pdp.appspringpcmarket.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final AttachmentService attachmentService;

    private final AttachmentRepository attachmentRepository;

    final
    CharacterRepository characterRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          AttachmentService attachmentService,
                          AttachmentRepository attachmentRepository,
                          CharacterRepository characterRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.attachmentService = attachmentService;
        this.attachmentRepository = attachmentRepository;
        this.characterRepository = characterRepository;
    }


    public ApiResponse add(ProductDto productDto){
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent())
            return new ApiResponse("categoriya topilmadi", false);

        try{
            Product product = new Product();
            product.setCategory(optionalCategory.get());
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setWarranty(productDto.getWarranty());
            product.setInStock(productDto.isInStock());
            product.setSaleOnlyInComplete(productDto.isSaleOnlyInComplete());
            productRepository.save(product);
        }catch (Exception e){
            return new ApiResponse("xatolik", false);
        }

        return new ApiResponse("product qo'shildi", true);
    }

    public List<Product> getAll(){
        return productRepository.findAll() ;
    }

    public Product getById(Integer id){
        return productRepository.findById(id).orElse(null) ;
    }

    public ApiResponse edit(Integer id, ProductDto productDto){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(!optionalProduct.isPresent())
            return new ApiResponse("product topilmadi", false);

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent())
            return new ApiResponse("categoriya topilmadi", false);

        try{
            Product product = optionalProduct.get();
            product.setCategory(optionalCategory.get());
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setWarranty(productDto.getWarranty());
            product.setInStock(productDto.isInStock());
            product.setSaleOnlyInComplete(productDto.isSaleOnlyInComplete());
            productRepository.save(product);
        }catch (Exception e){
            return new ApiResponse("xatolik", false);
        }

        return new ApiResponse("product tahrirlandi", true);
    }

    public ApiResponse delete(Integer id){
        try {
            productRepository.deleteById(id);
        }catch (Exception e){
            return new ApiResponse("xatolik", false);
        }
        return new ApiResponse("product o'chirildi", true);
    }
    /** Product rasmlarini boshqarish*/

    public ApiResponse addPhoto(Integer id, MultipartFile photo){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(!optionalProduct.isPresent())
            return new ApiResponse("product topilmadi", false);

        try{
            Attachment attachment = attachmentService.add(photo);
            Product product = optionalProduct.get();
            List<Attachment> photos = product.getPhotos();
            photos.add(attachment);
        }catch (Exception e){
            return new ApiResponse("xatolik", false);
        }

        return new ApiResponse("productga rasm qo'shildi", true);
    }

    public ApiResponse deletePhoto(Integer productId, Integer attachmentId){
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(attachmentId);
        if(!attachmentOptional.isPresent())
            return new ApiResponse("photo topilmadi", false);

        Attachment attachment=attachmentOptional.get();

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(!optionalProduct.isPresent())
            return new ApiResponse("product topilmadi", false);

        Product product = optionalProduct.get();
        List<Attachment> photos = product.getPhotos();
        photos.remove(attachment);

        boolean delete = attachmentService.delete(attachment);

        if(!delete)
            return new ApiResponse("product rasm o'chirilmadi", false);

        return new ApiResponse("product rasm o'chirildi", true);
    }

    /** Product characterlarini boshqarish*/
    public ApiResponse addCharacter(Integer productId, String characterName){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(!optionalProduct.isPresent())
            return new ApiResponse("product topilmadi", false);

        Optional<Character> optionalCharacter = characterRepository.findByName(characterName);
        Character character = new Character();
        if(optionalCharacter.isPresent())
            character=optionalCharacter.get();

        character.setName(characterName);
        // characterRepository.save(character);

        Product product=optionalProduct.get();
        List<Character> characters = product.getCharacters();
        for (Character character1 : characters) {
            if(character1.equals(character))
                return new ApiResponse("productning bunaqa characteri bor", false);
        }

        Character savedCharacter = characterRepository.save(character);
        characters.add(savedCharacter);
        productRepository.save(product);

        return new ApiResponse("productga character qo'shildi", true);
    }

    public List<Character> getCharacters(Integer productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(!optionalProduct.isPresent())
            return null;

        Product product=optionalProduct.get();

        return product.getCharacters();
    }

    public ApiResponse deleteCharacter(Integer productId, Integer characterId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(!optionalProduct.isPresent())
            return new ApiResponse("product topilmadi", false);

        Optional<Character> optionalCharacter = characterRepository.findById(characterId);

        try {
            Character character = optionalCharacter.get();
            Product product = optionalProduct.get();
            product.getCharacters().remove(character);
            characterRepository.delete(character);
        }catch (Exception e){
            return new ApiResponse("xatolik", false);
        }

        return new ApiResponse("product character o'chirildi", true);
    }

    public List<Product> getByFilter(ProductFilterDto productFilterDto) {
        float minPrice=productFilterDto.getMinPrice();
        float maxPrice=productFilterDto.getMaxPrice();
        List<Integer> propertyIds = productFilterDto.getPropertyIds();
        return productRepository.findAllByFilter(propertyIds,minPrice,maxPrice);
    }
}
