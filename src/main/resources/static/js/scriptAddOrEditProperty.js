document.addEventListener("DOMContentLoaded", () => {
    const propertyTypeSelect = document.getElementById("propertyType");

    if(propertyTypeSelect) {
        toggleFields(propertyTypeSelect.value);
        propertyTypeSelect
            .addEventListener("change", () => toggleFields(propertyTypeSelect.value));
    }
});

function toggleFields(selectedTypeValue) {
    const allPropertyTypes = ['apartment', 'house', 'business', 'garage', 'land'];
    const selectedType = selectedTypeValue.toLowerCase();

    // Hide all fields
    document.querySelectorAll('.property-type-main')
        .forEach(el => el.classList.add('hidden'));

    allPropertyTypes.forEach(type => {
        document.querySelectorAll(`.property-type-${type}`)
            .forEach(el => el.classList.add('hidden'))
    });

    // Show main and selected fields
    if (allPropertyTypes.includes(selectedType)) {
        document.querySelectorAll(`.property-type-main, .property-type-${selectedType}`)
            .forEach(el => el.classList.remove('hidden'));
    }
}