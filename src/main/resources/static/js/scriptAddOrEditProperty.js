document.addEventListener("DOMContentLoaded", () => {
    const propertyTypeSelect = document.getElementById("propertyType");

    if(propertyTypeSelect) {
        toggleFields(propertyTypeSelect.value);
        propertyTypeSelect
            .addEventListener("change", () => toggleFields(propertyTypeSelect.value));
    }
}