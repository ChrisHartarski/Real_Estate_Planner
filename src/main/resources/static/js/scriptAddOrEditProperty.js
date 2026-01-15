document.addEventListener("DOMContentLoaded", () => {
    const propertyTypeSelect = document.getElementById("propertyType");
    const citySelect = document.getElementById("city");
    const neighbourhoodSelect = document.getElementById("neighbourhood");

    if(propertyTypeSelect) {
        toggleFields(propertyTypeSelect.value);
        propertyTypeSelect
            .addEventListener("change", () => toggleFields(propertyTypeSelect.value));
    }

    if(citySelect) {
        populateNeighbourhoods(citySelect.value);
        citySelect
            .addEventListener("change", () => populateNeighbourhoods(citySelect.value));
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

function populateNeighbourhoods(cityName) {
    const neighbourhoodSelect = document.getElementById("neighbourhood");

    if (cityName == null) {
        neighbourhoodSelect.innerHTML = '<option value="" th:text="#{property.neighbourhood-placeholder}"></option>';
        return;
    }

    fetch(`/neighbourhoods?cityName=${cityName}`)
        .then(response => response.json())
        .then(data => {
            neighbourhoodSelect.innerHTML = '<option value="" th:text="#{property.neighbourhood-placeholder}"></option>';

            data.forEach(n => {
                const option = document.createElement('option');
                option.value = n;
                option.textContent = n;
                neighbourhoodSelect.appendChild(option);
            })
        })
        .catch(err => console.error('Error loading neighbourhoods:', err));
}