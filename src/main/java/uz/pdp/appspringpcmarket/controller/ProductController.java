package uz.pdp.appspringpcmarket.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appspringpcmarket.entity.Character;
import uz.pdp.appspringpcmarket.entity.Product;
import uz.pdp.appspringpcmarket.payload.ApiResponse;
import uz.pdp.appspringpcmarket.payload.ProductDto;
import uz.pdp.appspringpcmarket.payload.ProductFilterDto;
import uz.pdp.appspringpcmarket.service.AttachmentService;
import uz.pdp.appspringpcmarket.service.ProductService;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    private final AttachmentService attachmentService;
    public ProductController(ProductService productService, AttachmentService attachmentService) {
        this.productService = productService;
        this.attachmentService = attachmentService;
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @PostMapping
    public HttpEntity<?> add(@RequestBody ProductDto productDto){
        ApiResponse apiResponse = productService.add(productDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping
    public HttpEntity<?> getAll(){
        List<Product> all = productService.getAll();
        return ResponseEntity.ok(all);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        Product byId = productService.getById(id);
        return ResponseEntity.status(byId==null?204:200).body(byId);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody ProductDto productDto){
        ApiResponse edit = productService.edit(id, productDto);
        return ResponseEntity.status(edit.isSuccess()? 202:409).body(edit);
    }

    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse delete = productService.delete(id);
        return ResponseEntity.status(delete.isSuccess()?204:409).body(delete);
    }

    /** Product rasmlarini boshqarish*/

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @PostMapping("/photo/{productId}")
    public HttpEntity<?> addPhoto(@PathVariable Integer productId, @RequestParam MultipartFile photo){
        ApiResponse apiResponse = productService.addPhoto(productId, photo);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping("/photo")
    public  HttpEntity<?> getPhoto(@RequestParam("attachmentId") Integer attachmentId){
        return attachmentService.get(attachmentId);
    }

    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/photo/{productId}")
    public ResponseEntity<?> deletePhoto(@PathVariable Integer productId, @RequestParam Integer attachmentId ){
        ApiResponse apiResponse = productService.deletePhoto(productId, attachmentId);
        return ResponseEntity.status(apiResponse.isSuccess()?204:409).body(apiResponse);
    }

    /** Product characterlarini boshqarish*/

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @PostMapping("character/{productId}")
    public HttpEntity<?> addCharacter(@PathVariable Integer productId, @RequestParam String characterName){
        ApiResponse apiResponse = productService.addCharacter(productId, characterName);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping("/character/{productId}")
    public ResponseEntity<?> getCharacters(@PathVariable Integer productId){
        List<Character> characters = productService.getCharacters(productId);
        return ResponseEntity.status(characters.isEmpty()?204:200).body(characters);
    }

    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/character/{productId}")
    public ResponseEntity<?> deleteCharacter(@PathVariable Integer productId, @RequestParam Integer characterId){
        ApiResponse apiResponse = productService.deleteCharacter(productId, characterId);
        return ResponseEntity.status(apiResponse.isSuccess()?204:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping("/filter")
    public ResponseEntity<?> getList(@RequestBody ProductFilterDto productFilterDto){
        List<Product> filteredProducts=productService.getByFilter(productFilterDto);
        return ResponseEntity.status(filteredProducts.isEmpty()?409:200).body(filteredProducts);
    }
}
