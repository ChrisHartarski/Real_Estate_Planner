package com.butikimoti.real_estate_planner.model.dto.property;

import java.time.LocalDateTime;
import java.util.UUID;

public class ArchivePropertyDTO {
    private UUID id;
    private boolean isArchived;
    private LocalDateTime archivedOn;

    public ArchivePropertyDTO() {
    }

    public ArchivePropertyDTO(UUID id, boolean isArchived, LocalDateTime archivedOn) {
        this.id = id;
        this.isArchived = isArchived;
        this.archivedOn = archivedOn;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public LocalDateTime getArchivedOn() {
        return archivedOn;
    }

    public void setArchivedOn(LocalDateTime archivedOn) {
        this.archivedOn = archivedOn;
    }
}
