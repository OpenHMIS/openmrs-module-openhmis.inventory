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
define(
    [
        openhmis.url.backboneBase + 'js/openhmis',
        openhmis.url.backboneBase + 'js/lib/i18n',
        openhmis.url.backboneBase + 'js/model/generic',
        openhmis.url.backboneBase + 'js/model/concept',
        openhmis.url.inventoryBase + 'js/model/stockroom',
    ],
    function(openhmis, __) {
        openhmis.InventoryStockTake = openhmis.GenericModel.extend({
            meta: {
                name: __("Inventory Stock Take"),
                namePlural: __("Inventory Stock Take"),
                openmrsType: 'metadata',
                restUrl: openhmis.url.inventoryModelBase + 'inventoryStockTake'
            },

            schema: {
                operationNumber: {type: "text"},
                inventoryStockTakeList: { type: "List", itemType: "NestedModel", model: openhmis.InventoryStockTakeEntity },
            },

        });

        openhmis.InventoryStockTakeEntity = openhmis.GenericModel.extend({
        	meta: {},
            schema: {
                actualQuantity: { type: "BasicNumber" },
                quantity: { type: "BasicNumber" },
                expiration: { type: "Date", format: openhmis.dateFormatLocale },
                batchOperation: { type: "Object", objRef: true },
                item: { type: "Object", objRef: true },
                calculatedExpiration: { type: "Text" },
                calculatedBatch: { type: "Text" },
                stockroom: {type: "Object", objRef: true}
            }
        });

        return openhmis;
    }
);