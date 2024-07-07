package com.example.demo.controllers;

import com.example.demo.domain.InhousePart;
import com.example.demo.service.InhousePartService;
import com.example.demo.service.InhousePartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AddInhousePartController {
    @Autowired
    private ApplicationContext context;

    @GetMapping("/showFormAddInPart")
    public String showFormAddInhousePart(Model theModel) {
        InhousePart inhousePart = new InhousePart();
        theModel.addAttribute("inhousepart", inhousePart);
        return "InhousePartForm";
    }

    @PostMapping("/showFormAddInPart")
    public String submitForm(@Valid @ModelAttribute("inhousepart") InhousePart part, BindingResult bindingResult, Model theModel) {
        theModel.addAttribute("inhousepart", part);
        validateInventory(part, bindingResult);

        if (bindingResult.hasErrors()) {
            return "InhousePartForm";
        } else {
            InhousePartService repo = context.getBean(InhousePartServiceImpl.class);
            InhousePart ip = repo.findById((int) part.getId());
            if (ip != null) part.setProducts(ip.getProducts());
            repo.save(part);

            return "confirmationaddpart";
        }
    }

    private void validateInventory(InhousePart part, BindingResult bindingResult) {
        if (part.getInv() < part.getMinInv()) {
            bindingResult.rejectValue("inv", "error.part", "Inventory cannot be less than the minimum value");
        }
        if (part.getInv() > part.getMaxInv()) {
            bindingResult.rejectValue("inv", "error.part", "Inventory cannot be more than the maximum value");
        }
        if (part.getMinInv() >= part.getMaxInv()) {
            bindingResult.rejectValue("minInv", "error.part", "Minimum inventory must be less than maximum inventory");
            bindingResult.rejectValue("maxInv", "error.part", "Maximum inventory must be greater than minimum inventory");
        }
    }
}
