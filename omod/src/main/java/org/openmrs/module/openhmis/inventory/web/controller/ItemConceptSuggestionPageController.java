/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.openhmis.inventory.web.controller;
import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.openmrs.module.openhmis.inventory.api.model.Item;
import org.openmrs.module.openhmis.inventory.web.ModuleWebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value="invItemConceptSuggestionPageController")
public class ItemConceptSuggestionPageController {

    @RequestMapping(value=ModuleWebConstants.ITEM_CONCEPT_SUGGESTION_ROOT, method = RequestMethod.GET)
    public void render(ModelMap model) throws JsonGenerationException, JsonMappingException, IOException {
        model.addAttribute("modelBase", "openhmis.inventory.itemConceptSuggestion");
    }

    @RequestMapping(value=ModuleWebConstants.ITEM_CONCEPT_SUGGESTION_ROOT, method = RequestMethod.POST)
    public void onSubmit(ModelMap model, @ModelAttribute List<Item> itemsWithConcepts) {
        System.out.println("##################### FORM SUBMIT");
        System.out.println("SIZE: " + itemsWithConcepts.size());
    }

}

