package com.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.CD;
import com.model.ShoppingCart;

@Controller
public class ShoppingCartController {
	ShoppingCart shoppingCart;

	@RequestMapping(value = "/addToCart", method = RequestMethod.POST)
	public @ResponseBody
	String addToCart(@RequestBody String[] products,
			HttpServletRequest request, HttpSession session) {
		shoppingCart = (ShoppingCart) request.getSession().getAttribute(
				"ShoppingCart");
		if (shoppingCart == null) {
			shoppingCart = new ShoppingCart();
			session.setAttribute("ShoppingCart", shoppingCart);
		}
		for (int i = 0, j = 1, k = 2; k < products.length; i += 3, j += 3, k += 3) {
			String type = products[i];
			String genre = products[j];
			String name = products[k];
			CD CDProduct = new CD(name, type, genre);
			shoppingCart.addItem(CDProduct);
		}
		return "product(s) was(re) added";
	}

	@RequestMapping(value = "/getShoppingCart", method = RequestMethod.POST)
	public @ResponseBody
	String showShoppingCart(HttpServletRequest request, HttpSession session) {
		shoppingCart = (ShoppingCart) request.getSession().getAttribute(
				"ShoppingCart");
		if (shoppingCart == null) {
			shoppingCart = new ShoppingCart();
		}
		JSONArray pageInformJSON = new JSONArray();
		JSONObject cartProductsJSON = new JSONObject();
		List<CD> cartProducts = shoppingCart.getProducts();
		JSONArray cdsJSON = new JSONArray();
		for (CD cd : cartProducts) {
			JSONObject cdJSON = new JSONObject();
			try {
				cdJSON.put("type", cd.getType());
				cdJSON.put("genre", cd.getGenre());
				cdJSON.put("name", cd.getName());
				cdJSON.put("quantity", cd.getQuantity());
				cdsJSON.put(cdJSON);
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}
		try {
			cartProductsJSON.put("cartProducts", cdsJSON);
			pageInformJSON.put(cartProductsJSON);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return pageInformJSON.toString();
	}

	@RequestMapping(value = "/removeProduct/{index}", method = RequestMethod.POST)
	public @ResponseBody
	String removeProduct(Model model, @PathVariable("index") Integer index,
			HttpServletRequest request, HttpSession session) {
		shoppingCart = (ShoppingCart) request.getSession().getAttribute(
				"ShoppingCart");
		shoppingCart.removeProduct(index);
		return showShoppingCart(request, session);
	}
}
