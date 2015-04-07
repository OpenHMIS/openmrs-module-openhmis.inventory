package org.openmrs.module.webservices.rest.resource;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.Utility;
import org.openmrs.module.openhmis.inventory.api.IItemStockDetailDataService;
import org.openmrs.module.openhmis.inventory.api.IStockroomDataService;
import org.openmrs.module.openhmis.inventory.api.model.ItemStockSummary;
import org.openmrs.module.openhmis.inventory.api.model.Stockroom;
import org.openmrs.module.openhmis.inventory.web.ModuleRestConstants;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.annotation.PropertySetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

import java.util.List;

@Resource(name = ModuleRestConstants.INVENTORY_STOCK_TAKE_SUMMARY_RESOURCE, supportedClass = ItemStockSummary.class,
        supportedOpenmrsVersions = { "1.9.*", "1.10.*", "1.11.*" })
public class ItemStockSummaryResource extends DelegatingCrudResource<ItemStockSummary> {
	
	private IStockroomDataService stockroomDataService;
	private IItemStockDetailDataService itemStockDetailDataService;
	
	public ItemStockSummaryResource() {
		this.stockroomDataService = Context.getService(IStockroomDataService.class);
		this.itemStockDetailDataService = Context.getService(IItemStockDetailDataService.class);
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("item", Representation.DEFAULT);
		description.addProperty("expiration", Representation.DEFAULT);
		description.addProperty("quantity", Representation.DEFAULT);
		description.addProperty("actualQuantity", Representation.DEFAULT);
		
		return description;
	}
	
	@PropertySetter("expiration")
	public void setExpiration(ItemStockSummary instance, String dateText) {
		instance.setExpiration(Utility.parseOpenhmisDateString(dateText));
	}
	
	@Override
	public ItemStockSummary newDelegate() {
		return new ItemStockSummary();
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		PageableResult result;
		
		String stockroomUuid = context.getParameter("stockroom_uuid");
		if (StringUtils.isNotBlank(stockroomUuid)) {
			PagingInfo pagingInfo = PagingUtil.getPagingInfoFromContext(context);
			Stockroom stockroom = stockroomDataService.getByUuid(stockroomUuid);
			List<ItemStockSummary> itemStockSummaries =
			        itemStockDetailDataService.getItemStockSummaryByStockroom(stockroom, pagingInfo);
			result =
			        new AlreadyPagedWithLength<ItemStockSummary>(context, itemStockSummaries, pagingInfo.hasMoreResults(),
			                pagingInfo.getTotalRecordCount());
		} else {
			result = super.doSearch(context);
		}
		
		return result;
	}
	
	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		return new SimpleObject();
	}
	
	@Override
	public ItemStockSummary save(ItemStockSummary delegate) {
		return null;
	}
	
	@Override
	public ItemStockSummary getByUniqueId(String uniqueId) {
		return null;
	}
	
	@Override
	protected void delete(ItemStockSummary delegate, String reason, RequestContext context) throws ResponseException {
		// Deletes not supported
	}
	
	@Override
	public void purge(ItemStockSummary delegate, RequestContext context) throws ResponseException {
		// Purges not supported
	}
}
