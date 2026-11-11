document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form.filter-section");

    const citySelect = document.getElementById('filter-city');
    const availableNeighbourhoodsSelect = document.getElementById('available-neighbourhoods');
    const selectedNeighbourhoodsSelect = document.getElementById('selected-neighbourhoods');

    const addBtn = document.getElementById("add-neighbourhood-btn");
    const removeBtn = document.getElementById("remove-neighbourhood-btn");

    const filterStateEl = document.getElementById("filter-state");

    // Property type filters
    const propertyTypeSelect = document.getElementById('filter-propertyType');

    const apartmentTypeFilter = document.getElementById('apartment-type-filter');
    const houseTypeFilter = document.getElementById('house-type-filter');
    const constructionTypeFilter = document.getElementById('construction-type-filter');

    const apartmentTypeSelect = document.getElementById('filter-apartmentType');
    const houseTypeSelect = document.getElementById('filter-houseType');
    const constructionTypeSelect = document.getElementById('filter-constructionType');

    // Property type filter visibility
    function updatePropertyFilters() {

        const selectedType = propertyTypeSelect.value;

        const isApartment = selectedType === 'APARTMENT';
        const isHouse = selectedType === 'HOUSE';

        apartmentTypeFilter.style.display = isApartment ? '' : 'none';
        houseTypeFilter.style.display = isHouse ? '' : 'none';

        constructionTypeFilter.style.display =
            (isApartment || isHouse) ? '' : 'none';

        // Clear irrelevant selections
        if (!isApartment) {
            apartmentTypeSelect.value = '';
        }

        if (!isHouse) {
            houseTypeSelect.value = '';
        }

        if (!isApartment && !isHouse) {
            constructionTypeSelect.value = '';
        }
    }

    propertyTypeSelect.addEventListener('change', updatePropertyFilters);

    // Initialize property filters on page load
    updatePropertyFilters();


    // Neighbourhood filtering
    function sortSelect(select) {
        const options = Array.from(select.options);

        options.sort((a, b) =>
            a.textContent.localeCompare(b.textContent));

        select.innerHTML = "";
        options.forEach(o => select.appendChild(o));
    }

    function normalizeId(id) {
        return String(id).trim().toLowerCase();
    }

    function getPreselectedNeighbourhoodIds() {
        const raw = filterStateEl?.dataset.neighbourhoods;

        return raw;
    }

    function loadNeighbourhoods(cityId) {

        const selectedIds = getPreselectedNeighbourhoodIds();
        console.log(selectedIds);

        availableNeighbourhoodsSelect.innerHTML = "";
        selectedNeighbourhoodsSelect.innerHTML = "";

        if (!cityId) return;

        fetch(`/neighbourhoods?cityId=${encodeURIComponent(cityId)}`)
            .then(response => response.json())
            .then(data => {

                data.forEach(n => {
                    const option = document.createElement("option");
                    option.value = n.id;
                    option.textContent = n.name;

                    if (selectedIds.includes(n.id)) {
                        selectedNeighbourhoodsSelect.appendChild(option);
                    } else {
                        availableNeighbourhoodsSelect.appendChild(option);
                    }
                });

                sortSelect(availableNeighbourhoodsSelect);
                sortSelect(selectedNeighbourhoodsSelect);
            });
    }

    if (citySelect.value && citySelect.value !== "null") {
        loadNeighbourhoods(citySelect.value);
    }

    citySelect.addEventListener('change', (e) => {
        loadNeighbourhoods(e.target.value);
    });

    addBtn.addEventListener('click', () => {
        Array.from(availableNeighbourhoodsSelect.selectedOptions)
            .forEach(option => {
                availableNeighbourhoodsSelect.removeChild(option);
                selectedNeighbourhoodsSelect.appendChild(option);
            });
        sortSelect(selectedNeighbourhoodsSelect);
    });

    removeBtn.addEventListener('click', () => {
        Array.from(selectedNeighbourhoodsSelect.selectedOptions)
            .forEach(option => {
                selectedNeighbourhoodsSelect.removeChild(option);
                availableNeighbourhoodsSelect.appendChild(option);
            });
        sortSelect(availableNeighbourhoodsSelect);
    });

    form.addEventListener("submit", () => {
        Array.from(selectedNeighbourhoodsSelect.options)
            .forEach(opt => {
                if (!opt.value || opt.value === "null") {
                    opt.selected = false;
                } else {
                    opt.selected = true;
                }
            });
    });
});
