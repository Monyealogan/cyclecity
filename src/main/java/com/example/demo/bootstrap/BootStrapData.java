package com.example.demo.bootstrap;

import com.example.demo.domain.*;
import com.example.demo.repositories.OutsourcedPartRepository;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class BootStrapData implements CommandLineRunner {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;
    private final OutsourcedPartRepository outsourcedPartRepository;

    public BootStrapData(PartRepository partRepository, ProductRepository productRepository, OutsourcedPartRepository outsourcedPartRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.outsourcedPartRepository = outsourcedPartRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (partRepository.count() == 0 && productRepository.count() == 0) {

            // Save OutsourcedPart example
            OutsourcedPart o = new OutsourcedPart();
            o.setCompanyName("Western Governors University");
            o.setName("Disc Brake");
            o.setInv(5);
            o.setPrice(20.0);
            o.setMinInv(1);
            o.setMaxInv(10);
            o.setId(100L);
            outsourcedPartRepository.save(o);

            OutsourcedPart m = new OutsourcedPart();
            m.setCompanyName("Western Governors University");
            m.setName("Shock");
            m.setInv(5);
            m.setPrice(30.0);
            m.setMinInv(1);
            m.setMaxInv(10);
            m.setId(101L);
            outsourcedPartRepository.save(m);

            OutsourcedPart l = new OutsourcedPart();
            l.setCompanyName("Western Governors University");
            l.setName("Handlebar");
            l.setInv(5);
            l.setPrice(200.0);
            l.setMinInv(1);
            l.setMaxInv(10);
            l.setId(102L);
            outsourcedPartRepository.save(l);

            OutsourcedPart n = new OutsourcedPart();
            n.setCompanyName("Western Governors University");
            n.setName("Pedals");
            n.setInv(10);
            n.setPrice(20.0);
            n.setMinInv(1);
            n.setMaxInv(10);
            n.setId(103L);
            outsourcedPartRepository.save(n);

            OutsourcedPart f = new OutsourcedPart();
            f.setCompanyName("Western Governors University");
            f.setName("Bolt");
            f.setInv(10);
            f.setMinInv(1);
            f.setMaxInv(10);
            f.setPrice(15.0);
            f.setId(104L);
            outsourcedPartRepository.save(f);

            OutsourcedPart theOutsourcedPart = null;
            List<OutsourcedPart> outsourcedParts = (List<OutsourcedPart>) outsourcedPartRepository.findAll();
            for (OutsourcedPart part : outsourcedParts) {
                if (part.getName().equals("out test")) theOutsourcedPart = part;
            }

            if (theOutsourcedPart != null) {
                System.out.println(theOutsourcedPart.getCompanyName());
            }

            // Add sample products
            Product huffy = new Product("Huffy", 100.0, 10);
            Product avalanche = new Product("Avalanche", 200.0, 5);
            Product beaumont = new Product("Beaumont", 150.0, 20);
            Product aventon = new Product("Aventon", 300.0, 3);
            Product canyon = new Product("Canyon", 400.0, 2);

            productRepository.save(huffy);
            productRepository.save(avalanche);
            productRepository.save(beaumont);
            productRepository.save(aventon);
            productRepository.save(canyon);

            System.out.println("Sample data added.");

        } else {
            Set<OutsourcedPart> partSet = new HashSet<>();
            List<OutsourcedPart> outsourcedParts = (List<OutsourcedPart>) outsourcedPartRepository.findAll();
            for (OutsourcedPart part : outsourcedParts) {
                if (!partSet.add(part)) {
                    part.setName(part.getName() + " Multi-Pack");
                    partSet.add(part);
                }
            }

            partRepository.saveAll(partSet);
        }

        System.out.println("Number of Products: " + productRepository.count());
        System.out.println("Products: " + productRepository.findAll());
        System.out.println("Number of Parts: " + partRepository.count());
        System.out.println("Parts: " + partRepository.findAll());
    }
}
