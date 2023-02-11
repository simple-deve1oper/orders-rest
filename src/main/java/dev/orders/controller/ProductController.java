package dev.orders.controller;

import dev.orders.dto.ProductDTO;
import dev.orders.entity.Product;
import dev.orders.entity.Unit;
import dev.orders.exception.EntityValidationException;
import dev.orders.exception.FileException;
import dev.orders.service.ProductService;
import dev.orders.service.UnitService;
import dev.orders.util.FileUtils;
import dev.orders.util.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${files.directory}")
    private String directory;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllGoods() {
        List<ProductDTO> productsDTO = productService.findAllGoods().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        return ResponseEntity.ok(productsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);

        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/find")
    public ResponseEntity<List<ProductDTO>> getGoodsByName(@RequestParam String query) {
        List<ProductDTO> productsDTO = productService.findGoodsByName(query).stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        return ResponseEntity.ok(productsDTO);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(
            @RequestBody @Valid ProductDTO productDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String errors = StringUtils.getErrorsFromValidation(bindingResult);

            throw new EntityValidationException(errors);
        }

        productDTO.setImage("");

        Product product = modelMapper.map(productDTO, Product.class);
        String unitShortName = product.getUnit().getShortName();
        Unit unit = unitService.findByShortName(unitShortName);
        unit.getProducts().add(product);
        product.setUnit(unit);
        product = productService.saveProduct(product);
        productDTO = modelMapper.map(product, ProductDTO.class);

        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @PutMapping("/image/{id}")
    public ResponseEntity<ProductDTO> updateImage(@PathVariable Long id, @RequestParam MultipartFile image) throws IOException {
        String filename = "";
        if (!image.getOriginalFilename().isBlank()) {
            filename = image.getOriginalFilename();
        }
        checkFile(filename);
        if (!filename.isBlank()) {
            if (!directory.isBlank() && directory != null) {
                FileUtils.createDirectory(directory);

                while (!productService.findProductByImage(filename).isEmpty()) {
                    filename = FileUtils.makeFreeName(filename);
                }

                String pathToFile = directory + File.separator + filename;
                FileUtils.saveFile(image, pathToFile);
            } else if (directory.isBlank() && directory == null) {
                throw new IOException("Наименование директории для сохранения не существует");
            }
        } else {
            throw new FileException("Переданный файл пуст");
        }

        Product product = productService.findById(id);
        product.setImage(filename);
        product = productService.updateProduct(product);
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);

        return ResponseEntity.ok(productDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product productFromDB = productService.findById(id);
        Product productFromDTO = modelMapper.map(productDTO, Product.class);
        productFromDTO.setId(productFromDB.getId());
        Unit unit = unitService.findByShortName(productFromDTO.getUnit().getShortName());
        productFromDTO.setImage(productFromDB.getImage());
        productFromDTO.setUnit(unit);
        unit.getProducts().add(productFromDTO);
        productFromDB = productService.updateProduct(productFromDTO);

        productDTO = modelMapper.map(productFromDB, ProductDTO.class);

        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        String message = String.format("Товар с идентификатором '%d' удалён", id);

        return ResponseEntity.ok(message);
    }

    private void checkFile(String filename) {
        List<String> fileErrorMessages = new ArrayList<>();
        if (!filename.isBlank()) {
            FileUtils.getErrorsForUploadedFile(filename, fileErrorMessages);
        }

        if (!fileErrorMessages.isEmpty()) {
            StringBuilder fileErrors = new StringBuilder();
            fileErrorMessages.stream().forEach(fileErrorMessage -> {
                fileErrors.append(String.format("%s;", fileErrorMessage));
            });

            String errors = fileErrors.toString();
            throw new EntityValidationException(errors);
        }
    }
}
