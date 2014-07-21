package org.openmrs.module.openhmis.inventory.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.openmrs.Concept;
import org.openmrs.ConceptName;
import org.openmrs.api.APIException;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseMetadataDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;
import org.openmrs.module.openhmis.inventory.api.IItemConceptSuggestionDataService;
import org.openmrs.module.openhmis.inventory.api.IItemDataService;
import org.openmrs.module.openhmis.inventory.api.model.Item;
import org.openmrs.module.openhmis.inventory.api.model.ItemConceptSuggestion;

public class ItemConceptSuggestionDataServiceImpl extends BaseMetadataDataServiceImpl<ItemConceptSuggestion>
    implements IItemConceptSuggestionDataService {

    private IItemDataService itemDataService;

    @Override
    protected IMetadataAuthorizationPrivileges getPrivileges() {
        return null;
    }

    @Override
    protected void validate(ItemConceptSuggestion object) throws APIException {

    }

    @Override
    public List<ItemConceptSuggestion> getItemsWithConceptSuggestions() {
        int defaultResultLimit = 50;
        List<ItemConceptSuggestion> itemConceptSuggestions = getItemToConceptMatches(defaultResultLimit);

        List<Integer> excludedItemsIds = new ArrayList<Integer>();
        for (ItemConceptSuggestion suggestion : itemConceptSuggestions) {
            excludedItemsIds.add(suggestion.getItemId());
        }

        if (itemConceptSuggestions.size() < defaultResultLimit) {
            int reachDefaultReultLimit = defaultResultLimit - itemConceptSuggestions.size();
            List<Item> itemsWithoutConcept = itemDataService.findItemsWithoutConcept(excludedItemsIds, reachDefaultReultLimit);
            for (Item item : itemsWithoutConcept) {
                ItemConceptSuggestion itemConceptSuggestion = new ItemConceptSuggestion(item, null, false);
                itemConceptSuggestions.add(itemConceptSuggestion);
            }
        }
        return itemConceptSuggestions;
    }

    private List<ItemConceptSuggestion> getItemToConceptMatches(int defaultResultLimit) {
        String itemKey = "0";
        String conceptKey = "1";

        List<ItemConceptSuggestion> itemToConceptMatches = new ArrayList<ItemConceptSuggestion>();
        String queryString = "select new map(item.uuid, concept.concept) " +
                "from " +  Item.class.getName() + " as item, " + ConceptName.class.getName() + " as concept " +
                "where item.concept is null " + "and item.retired = false " +
                "and item.name like concept.name " +
                "and item.conceptAccepted = false " +
                "group by item.id ";
        Query query = repository.createQuery(queryString);
        query.setMaxResults(defaultResultLimit);
        List<Map<String, Object>> results = (List<Map<String, Object>>) query.list();
        for (Map<String, Object> result : results) {
            String itemUuid = (String) result.get(itemKey);
            Item item = itemDataService.getByUuid(itemUuid);
            Concept concept = (Concept) result.get(conceptKey);
            ItemConceptSuggestion itemConceptSuggestion = new ItemConceptSuggestion(item, concept, false);
            itemToConceptMatches.add(itemConceptSuggestion);
        }
        return itemToConceptMatches;
    }

    public void setItemDataService(IItemDataService itemDataService) {
        this.itemDataService = itemDataService;
    }
}
