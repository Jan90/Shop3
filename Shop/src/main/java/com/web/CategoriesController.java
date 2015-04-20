package com.web;

import com.service.StoreServiceImpl;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CategoriesController {
	private final StoreServiceImpl storeServiceImpl;

	@Autowired
	public CategoriesController(StoreServiceImpl storeServiceImpl) {
		this.storeServiceImpl = storeServiceImpl;
	}

	@RequestMapping(value = "/populateDropDownList/type/{type}", method = RequestMethod.GET)
	public @ResponseBody
	String populateDropDownList(@PathVariable("type") String type)
			throws JSONException {
		List<String> genres = storeServiceImpl.getDropDownListGenre(type);
		JSONArray genresJSON = new JSONArray();
		for (String genre : genres) {
			JSONObject genreJSON = new JSONObject();
			genreJSON.put("genre", genre);
			genresJSON.put(genreJSON);
		}
		return genresJSON.toString();
	}

	@RequestMapping(value = "/populateDropDownList/type", method = RequestMethod.GET)
	public @ResponseBody
	String populateDropDownListType() throws JSONException {
		List<String> types = storeServiceImpl.getDropDownListType();
		JSONArray typesJSON = new JSONArray();
		for (String type : types) {
			JSONObject typeJSON = new JSONObject();
			typeJSON.put("type", type);
			typesJSON.put(typeJSON);
		}
		return typesJSON.toString();
	}
}
