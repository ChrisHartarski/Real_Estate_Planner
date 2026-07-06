package com.butikimoti.real_estate_planner.model.dto.util;

import com.butikimoti.real_estate_planner.model.enums.OfferType;

public class PropertiesPageContext {
    private OfferType offerType;
    private boolean archived;

    public PropertiesPageContext() {
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
