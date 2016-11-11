/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 *
 * Copyright (C) OpenHMIS.  All Rights Reserved.
 */
package org.openmrs.module.openhmis.inventory.api.model;

import org.openmrs.Location;
import org.openmrs.module.openhmis.commons.api.entity.model.BaseSerializableOpenmrsMetadata;

/**
 * Model class that represents an institutional department.
 */
public class Department extends BaseSerializableOpenmrsMetadata {
	public static final long serialVersionUID = 0L;

	private Integer departmentId;
	private Location location;

	@Override
	public Integer getId() {
		return departmentId;
	}

	@Override
	public void setId(Integer id) {
		departmentId = id;
	}

	/**
	 * Gets the optional {@link org.openmrs.Location} where this stockroom is located.
	 * @return The stockroom location.
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Sets the optional {@link org.openmrs.Location} where this stockroom is located.
	 * @param location The stockroom location.
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

}
