document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form.filter-section");
    form.addEventListener("submit", function (e) {
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