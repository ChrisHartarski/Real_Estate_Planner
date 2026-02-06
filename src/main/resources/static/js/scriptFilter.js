document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form.filter-section");

    const citySelect = document.getElementById('filter-city');
    const availableNeighbourhoodsSelect = document.getElementById('available-neighbourhoods');
    const selectedNeighbourhoodsSelect = document.getElementById('selected-neighbourhoods');

    const params = new URLSearchParams(window.location.search);
    const cityFromUrl = params.get("city");
    const selectedNeighbourhoodsFromUrl = params.getAll("neighbourhood");

    const addSelectedBtn = document.getElementById('add-neighbourhood-btn');
    const removeSelectedBtn = document.getElementById('remove-neighbourhood-btn');

    console.log("City value on load:", cityFromUrl);
    console.log("selected neighbourhoods on load:", selectedNeighbourhoodsFromUrl);


    function sortSelectAlphabetically(selectElement) {
        Array.from(selectElement.options)
            .sort((a,b) => a.textContent.localeCompare(b.textContent))
            .forEach(option => selectElement.appendChild(option));
    }

    function populateAvailableNeighbourhoods(cityName) {
        const city = cityName;

        availableNeighbourhoodsSelect.innerHTML = "";
        selectedNeighbourhoodsSelect.innerHTML = "";

        if (!city) return;

        fetch(`/neighbourhoods?cityName=${encodeURIComponent(city)}`)
            .then(response => response.json())
            .then(data => {
                data.forEach(n => {
                    const option = document.createElement('option');
                    option.value = n;
                    option.textContent = n;

                    if (selectedNeighbourhoodsFromUrl.includes(n)) {
                        selectedNeighbourhoodsSelect.appendChild(option);
                    } else {
                        availableNeighbourhoodsSelect.appendChild(option);
                    }
                })
            })
            .catch(err => console.error('Error loading neighbourhoods:', err));
    }

    if (cityFromUrl) {
        populateAvailableNeighbourhoods(cityFromUrl);
        sortSelectAlphabetically(availableNeighbourhoodsSelect);
        sortSelectAlphabetically(selectedNeighbourhoodsSelect);
    }

    citySelect.addEventListener('change', () => {
        populateAvailableNeighbourhoods(citySelect.value);
        selectedNeighbourhoodsSelect.innerHTML = "";
    });

    addSelectedBtn.addEventListener('click', () => {
        Array.from(availableNeighbourhoodsSelect.selectedOptions)
            .forEach(option => {
                availableNeighbourhoodsSelect.removeChild(option);
                selectedNeighbourhoodsSelect.appendChild(option);
            });
        sortSelectAlphabetically(selectedNeighbourhoodsSelect);
    })

    removeSelectedBtn.addEventListener('click', () => {
        Array.from(selectedNeighbourhoodsSelect.selectedOptions)
            .forEach(option => {
                selectedNeighbourhoodsSelect.removeChild(option);
                availableNeighbourhoodsSelect.appendChild(option);
            });
        sortSelectAlphabetically(availableNeighbourhoodsSelect);
    });

    form.addEventListener("submit", function (e) {
        Array.from(selectedNeighbourhoodsSelect.options).forEach(opt => opt.selected = true);

        const elements = form.elements;
        for (let i = 0; i < elements.length; i++) {
            const el = elements[i];
            if (!el.name || el.disabled) continue;
            if (el.type === "submit") continue;
            if (el.value === "") {
                el.name = ""; // Remove name so it won't be submitted
            }
        }
    });
});