package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.PartService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AddProductController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private PartService partService;

    @Autowired
    private ProductService productService;

    private static Product product1;

    @GetMapping("/showFormAddProduct")
    public String showFormAddPart(Model theModel) {
        theModel.addAttribute("parts", partService.findAll());
        product1 = new Product();
        theModel.addAttribute("product", product1);

        List<Part> availParts = new ArrayList<>();
        for (Part p : partService.findAll()) {
            if (!product1.getParts().contains(p)) availParts.add(p);
        }
        theModel.addAttribute("availparts", availParts);
        theModel.addAttribute("assparts", product1.getParts());
        return "productForm";
    }

    @PostMapping("/showFormAddProduct")
    public String submitForm(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model theModel) {
        theModel.addAttribute("product", product);

        if (bindingResult.hasErrors()) {
            List<Part> availParts = new ArrayList<>();
            for (Part p : partService.findAll()) {
                if (!product.getParts().contains(p)) availParts.add(p);
            }
            theModel.addAttribute("availparts", availParts);
            theModel.addAttribute("assparts", product.getParts());
            return "productForm";
        } else {
            updateProductInventory(product);
            productService.save(product);
            return "confirmationaddproduct";
        }
    }

    @GetMapping("/showProductFormForUpdate")
    public String showProductFormForUpdate(@RequestParam("productID") int theId, Model theModel) {
        Product theProduct = productService.findById(theId);
        product1 = theProduct;
        theModel.addAttribute("product", theProduct);
        theModel.addAttribute("assparts", theProduct.getParts());

        List<Part> availParts = new ArrayList<>();
        for (Part p : partService.findAll()) {
            if (!theProduct.getParts().contains(p)) availParts.add(p);
        }
        theModel.addAttribute("availparts", availParts);
        return "productForm";
    }

    @GetMapping("/deleteproduct")
    public String deleteProduct(@RequestParam("productID") int theId, Model theModel) {
        Product product2 = productService.findById(theId);
        for (Part part : product2.getParts()) {
            part.getProducts().remove(product2);
            partService.save(part);
        }
        product2.getParts().clear();
        productService.save(product2);
        productService.deleteById(theId);

        return "confirmationdeleteproduct";
    }

    @GetMapping("/associatepart")
    public String associatePart(@RequestParam("partID") int theID, Model theModel) {
        if (product1.getName() == null) {
            return "saveproductscreen";
        } else {
            Part part = partService.findById(theID);
            if (part != null && part.getInv() > 0) {
                product1.getParts().add(part);
                part.setInv(part.getInv() - 1);
                product1.setInv(product1.getInv() + 1);
                partService.save(part);
                productService.save(product1);
            }

            theModel.addAttribute("product", product1);
            theModel.addAttribute("assparts", product1.getParts());

            List<Part> availParts = new ArrayList<>();
            for (Part p : partService.findAll()) {
                if (!product1.getParts().contains(p)) availParts.add(p);
            }
            theModel.addAttribute("availparts", availParts);
            return "productForm";
        }
    }

    @GetMapping("/removepart")
    public String removePart(@RequestParam("partID") int theID, Model theModel) {
        Part part = partService.findById(theID);
        if (part != null) {
            product1.getParts().remove(part);
            part.setInv(part.getInv() + 1);
            product1.setInv(product1.getInv() - 1);
            partService.save(part);
            productService.save(product1);
        }

        theModel.addAttribute("product", product1);
        theModel.addAttribute("assparts", product1.getParts());

        List<Part> availParts = new ArrayList<>();
        for (Part p : partService.findAll()) {
            if (!product1.getParts().contains(p)) availParts.add(p);
        }
        theModel.addAttribute("availparts", availParts);
        return "productForm";
    }

    private void updateProductInventory(Product product) {
        if (product.getId() != 0) {
            Product existingProduct = productService.findById((int) product.getId());
            if (existingProduct != null) {
                for (Part part : existingProduct.getParts()) {
                    part.setInv(part.getInv() - (product.getInv() - existingProduct.getInv()));
                    partService.save(part);
                }
            }
        } else {
            product.setInv(0);
        }
    }
}
