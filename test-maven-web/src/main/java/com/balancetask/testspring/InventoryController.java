package com.balancetask.testspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InventoryController {

	private ProductManager productManager;

	@RequestMapping(value="/service/hello")
	@ResponseBody 
	public String[] getHello(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		return new String[] { "Hello " + name, "Bye " + name };
	}
	
	@RequestMapping(value="/service/product")
	@ResponseBody 
	public Product getOneProduct() {
		return productManager.getProducts().get(0);
	}
	
	@RequestMapping(value="/products")
	public String getProducts(ModelMap map) {
	
        map.addAttribute("products", this.productManager.getProducts());

        return "products";
	}
	
	@RequestMapping(value="/priceincrease", method = RequestMethod.GET)
	public String getPriceIncrease(ModelMap map) {
		return "priceincrease";
	}

	@RequestMapping(value="/priceincrease", method = RequestMethod.POST)
	public String setPriceIncrease(@RequestParam(value = "percentage", required = true) int increase) {
		if (increase > 0 && increase < 60) {
			productManager.increasePrice(increase);
			return "redirect:/products";
		}
		else {
			return "priceincrease";
		}
	}
	
	@Autowired
    public void setProductManager(ProductManager productManager) {
        this.productManager = productManager;
    }
}
