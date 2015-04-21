package org.openmrs.module.openhmis.inventory.web.controller;

import java.io.IOException;

import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.inventory.ModuleSettings;
import org.openmrs.module.openhmis.inventory.api.WellKnownOperationTypes;
import org.openmrs.module.openhmis.inventory.web.ModuleWebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(ModuleWebConstants.INVENTORY_ROOT)
public class InventoryPageController {
	@RequestMapping(method = RequestMethod.GET)
	public void inventory(ModelMap model) throws IOException {
		model.addAttribute("modelBase", "openhmis.inventory.institution");

		model.addAttribute("showStockTakeLink", Context.getAuthenticatedUser() != null &&
				WellKnownOperationTypes.getAdjustment().userCanProcess(Context.getAuthenticatedUser()));
		model.addAttribute("isOperationAutoCompleted", ModuleSettings.isOperationAutoCompleted());
		model.addAttribute("showOperationCancelReasonField",ModuleSettings.showOperationCancelReasonField());
	}

}
