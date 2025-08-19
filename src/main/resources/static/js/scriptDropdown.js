document.addEventListener("DOMContentLoaded", () => {
    const dropdownMenuEl = document.getElementById("dropdownMenu");
    const profileBtnEl = document.getElementById("profileBtn");

    profileBtnEl.addEventListener("click", (e) => {
        dropdownMenuEl.classList.toggle("hidden");
        e.stopPropagation();
    });

    document.addEventListener("click", (e) => {
        if (!dropdownMenuEl.contains(e.target)) {
            dropdownMenuEl.classList.add("hidden");
        }
    });
});