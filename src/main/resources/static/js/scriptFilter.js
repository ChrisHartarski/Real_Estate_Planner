document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form.filter-section");

    const citySelect = document.getElementById('filter-city');
    const availableNeighbourhoodsSelect = document.getElementById('available-neighbourhoods');
    const selectedNeighbourhoodsSelect = document.getElementById('selected-neighbourhoods')

    const addBtn = document.getElementById("add-neighbourhood-btn");
    const removeBtn = document.getElementById("remove-neighbourhood-btn");

    const filterStateEl = document.getElementById("filter-state");

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
                if(!opt.value || opt.value === "null") {
                    opt.selected = false;
                } else {
                    opt.selected = true;
                }
            });
    });
});