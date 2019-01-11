package com.edu.hartige.controller;

import com.edu.hartige.common.exception.ProductNotFoundException;
import com.edu.hartige.domain.*;
import com.edu.hartige.repository.BaseOrderRepository;
import com.edu.hartige.repository.ProductCatalogRepository;
import com.edu.hartige.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping(value = "/products")
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final String VIEW_CREATE_PRODUCT = "views/product/create";
    private final ProductService productService;
    private ProductCatalogRepository productCatalogRepository;
    private BaseOrderRepository orderRepository;

    @Autowired
    public ProductController(
            ProductService service,
            ProductCatalogRepository productCatalogRepository,
            BaseOrderRepository orderRepository) {
        this.productService = service;
        this.productCatalogRepository = productCatalogRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    @GetMapping(value = "/addsomemore")
    public ModelAndView addSomeMoreProducts(Model model) {

        logger.debug("addSomeMoreProducts called.");

        createOrder();

        decorateOrder();

        Iterable<Product> products = productService.getProducts();

        return new ModelAndView("redirect:/products", "products", products);
    }

    @GetMapping
    public String listProducts(
            @RequestParam(value = "category", required = false, defaultValue = "all") String category,
            @RequestParam(value = "size", required = false, defaultValue = "10") String size,
            Model model) {

        logger.debug("listProducts called.");

        Iterable<Product> products = productService.getProducts();

        model.addAttribute("category", category);
        model.addAttribute("size", size);
        model.addAttribute("products", products);
        return "views/product/list";
    }

    @GetMapping("{id}")
    public ModelAndView viewProduct(@PathVariable("id") Product product)
            throws ProductNotFoundException {
        if (null == product) {
            throw new ProductNotFoundException("Product niet gevonden!");
        }
        String VIEW_READ_PRODUCT = "views/product/read";
        return new ModelAndView(VIEW_READ_PRODUCT, "product", product);
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String showCreateProductForm(final Product product, final ModelMap model) {
        logger.debug("showCreateProductForm");
        return VIEW_CREATE_PRODUCT;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView validateAndSaveProduct(
            @Valid Product product,
            final BindingResult bindingResult,
            RedirectAttributes redirect) {

        logger.debug("validateAndSaveProduct - adding product " + product.getName());
        if (bindingResult.hasErrors()) {
            logger.debug("validateAndSaveProduct - not added, bindingResult.hasErrors");
            return new ModelAndView(VIEW_CREATE_PRODUCT, "formErrors", bindingResult.getAllErrors());
        }

        product = productService.createProduct(product);

        redirect.addFlashAttribute("globalMessage", "Successfully created a new message");
        return new ModelAndView("redirect:/products/{product.id}", "product.id", product.getId());
    }

    @GetMapping(value = "{id}/edit")
    public ModelAndView modifyForm(@PathVariable("id") Product product) {
        return new ModelAndView(VIEW_CREATE_PRODUCT, "product", product);
    }

    public void createProductCatalogAndProducts() {
        ProductCatalog productCatalog = new ProductCatalog();
        productCatalog = productCatalogRepository.save(productCatalog);
        Product prod1 = new Product("Schroefje", "Zakje schroefjes", 2);
        Product prod2 = new Product("Moertje", "Beschrijving van een moertje", 1);
        productCatalog.add(prod1, 1);
        productCatalog.add(prod2, 3);
    }

    private void createOrder() {

        ProductCatalog productCatalog = (ProductCatalog) productCatalogRepository.findAllById(Collections.singleton(1L));

        Product prod = productCatalog.find(2L);

        Product prodCopy = new Product(prod);

        Order order = new Order();
        order = orderRepository.save(order);
        order.add(prodCopy);
    }

    private void decorateOrder() {
        BaseOrder concreteOrder = orderRepository.findById(1L)
                .orElse(null);
        BaseOrder decoratedOrder1 = new OrderOption("wrapping paper", 7, concreteOrder);
        orderRepository.save(decoratedOrder1);
        BaseOrder decoratedOrder2 = new OrderOption("nice box", 5, decoratedOrder1);
        orderRepository.save(decoratedOrder2);
        BaseOrder decoratedOrder3 = new OrderOption("fast delivery", 12, decoratedOrder2);
        orderRepository.save(decoratedOrder3);
        System.out.println("***** content of the order: " + decoratedOrder3);
        System.out.println("***** price of the order: " + decoratedOrder3.price());
    }
}
