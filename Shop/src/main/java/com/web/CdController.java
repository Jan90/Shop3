package com.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.CD;
import com.model.PageInfo;
import com.model.Store;
import com.service.StoreServiceImpl;

@Controller
public class CdController {
	public static final Integer FIRST_PAGE = 1;
	PageInfo pageInfo = new PageInfo();
	private final StoreServiceImpl storeServiceImpl;

	@Autowired
	public CdController(StoreServiceImpl storeServiceImpl) {
		this.storeServiceImpl = storeServiceImpl;
	}

	@RequestMapping(value = "/getAllProductList", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	String getAllProductList(Model model) {
		if (Store.getProductList() != null) {
			Store.deleteProducts();
		}
		storeServiceImpl.getCDsProductList();
		return showProductList(FIRST_PAGE);
	}

	@RequestMapping(value = "/getProductList/name/{name}", method = RequestMethod.GET)
	public @ResponseBody
	String getProductList(@PathVariable("name") String name) {
		if (Store.getProductList() != null) {
			Store.deleteProducts();
		}
		storeServiceImpl.getCDsProductList(name);
		return showProductList(FIRST_PAGE);
	}

	@RequestMapping(value = "/getProductList/type/{type}/genre/{genre}", method = RequestMethod.GET)
	public @ResponseBody
	String getProductList(@PathVariable("type") String type,
			@PathVariable("genre") String genre) {
		if (Store.getProductList() != null) {
			Store.deleteProducts();
		}
		storeServiceImpl.getCDsProductList(type, genre);
		return showProductList(FIRST_PAGE);
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public @ResponseBody
	String showNewItems() {
		if (Store.getProductList() != null) {
			Store.deleteProducts();
		}
		storeServiceImpl.getCDsNewProducts();
		return showProductList(FIRST_PAGE);
	}

	@SuppressWarnings("null")
	@RequestMapping(value = "/getProductList/page/{page}", method = RequestMethod.GET)
	public @ResponseBody
	String showProductList(@PathVariable("page") Integer page) {
		JSONArray pageInformJSON = new JSONArray();
		JSONObject productListJSON = new JSONObject();
		pageInfo.setPageSize();
		pageInfo.setPage(page - 1);
		List<CD> genres = pageInfo.getPageList();
		JSONArray cdsJSON = new JSONArray();

		for (CD cd : genres) {
			JSONObject cdJSON = new JSONObject();
			try {
				cdJSON.put("type", cd.getType());
				cdJSON.put("genre", cd.getGenre());
				cdJSON.put("name", cd.getName());
				cdsJSON.put(cdJSON);
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}
		try {
			productListJSON.put("productList", cdsJSON);
			pageInformJSON.put(productListJSON);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pageCount", pageInfo.getPageCount());
		model.put("page", page);
		JSONObject json = new JSONObject(model);
		pageInformJSON.put(json);
		return pageInformJSON.toString();
	}
}
