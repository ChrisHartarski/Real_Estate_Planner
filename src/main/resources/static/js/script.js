document.addEventListener("DOMContentLoaded", () => {
    console.log("DOM loaded");
    const propertyTypeSelect = document.getElementById("propertyType");

    if(propertyTypeSelect) {
        toggleFields(propertyTypeSelect.value);
        propertyTypeSelect
            .addEventListener("change", () => toggleFields(propertyTypeSelect.value));
    }

    const input = document.getElementById('file-upload');
    const fileName = document.getElementById('file-name');

    if(input && fileName) {
        input.addEventListener('change', () => {
            if(input.files.length > 0){
                fileName.textContent = input.files[0].name;
            }
        });
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
