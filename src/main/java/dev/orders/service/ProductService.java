package dev.orders.service;

import dev.orders.entity.Product;
import dev.orders.exception.EntityExistsException;
import dev.orders.exception.EntityNotFoundException;
import dev.orders.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    /**
     * Поиск списка всех объектов типа Product
     * @return список объектов типа Product
     */
    public List<Product> findAllGoods() {
        return productRepository.findAll();
    }

    /**
     * Поиск объекта типа Product по идентификатору
     * @param id - идентификатор
     * @return объект типа Product
     */
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Товар с идентификатором %d не найден", id)));
    }

    /**
     * Поиск объекта типа Product по содержанию наименования
     * @param name - наименование
     * @return список объектов типа Product
     */
    public List<Product> findGoodsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Поиск объекта типа Product по изображению
     * @param image - наименование изображения
     * @return класс-оболочка Optional типа Product
     */
    public Optional<Product> findProductByImage(String image) {
        return productRepository.findByImage(image);
    }

    /**
     * Сохранение объекта типа Product
     * @param product - объект типа Product
     * @return сохраненный объект Product
     */
    @Transactional
    public Product saveProduct(Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new EntityExistsException(String.format("Товар с наименованием '%s' уже существует", product.getName()));
        }

        return productRepository.save(product);
    }

    /**
     * Обновление объекта типа Product
     * @param product - объект типа Product
     * @return сохраненный объект типа Product
     */
    @Transactional
    public Product updateProduct(Product product) {
        if (!productRepository.existsById(product.getId())) {
            throw new EntityNotFoundException(String.format("Товар с идентификатором %d не найден", product.getId()));
        }

        return productRepository.save(product);
    }

    /**
     * Удаление объекта типа Product
     * @param id - идентификатор
     */
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Товар с идентификатором %d не найден", id));
        }

        productRepository.deleteById(id);
    }
}
