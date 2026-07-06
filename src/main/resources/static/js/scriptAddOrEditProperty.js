document.addEventListener("DOMContentLoaded", () => {
    const propertyTypeSelect = document.getElementById("propertyType");
    const citySelect = document.getElementById("city");

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

function sortSelect(select) {
        const options = Array.from(select.options);

        options.sort((a, b) =>
            a.textContent.localeCompare(b.textContent));

        select.innerHTML = "";
        options.forEach(o => select.appendChild(o));
    }

function populateNeighbourhoods(cityId) {
    const neighbourhoodSelect = document.getElementById("neighbourhood");
    const currentNeighbourhoodId = neighbourhoodSelect.dataset.selected;
    console.log(currentNeighbourhoodId);

    if (!cityId) {
        neighbourhoodSelect.innerHTML = '<option value="" th:text="#{property.neighbourhood-placeholder}"></option>';
        return;
    }

    fetch(`/neighbourhoods?cityId=${encodeURIComponent(cityId)}`)
        .then(response => response.json())
        .then(data => {

            neighbourhoodSelect.innerHTML = '<option value="" th:text="#{property.neighbourhood-placeholder}"></option>';

            data.forEach(n => {
                const option = document.createElement('option');
                option.value = n.id;
                option.textContent = n.name;

                if (String(n.id) === String(currentNeighbourhoodId)) {
                    option.selected = true;
                }

                neighbourhoodSelect.appendChild(option);
            })

            sortSelect(neighbourhoodSelect);

        })
        .catch(err => console.error('Error loading neighbourhoods:', err));
}