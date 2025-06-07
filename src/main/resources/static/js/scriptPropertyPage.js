document.addEventListener("DOMContentLoaded", () => {

    const input = document.getElementById('file-upload');
    const fileName = document.getElementById('file-name');

    if(input && fileName) {
        input.addEventListener('change', () => {
            if(input.files.length > 0){
                fileName.textContent = input.files[0].name;
            }
        });
    }

    const glideEl = document.querySelector('.glide');
    if (glideEl) {
        const glide = new Glide('.glide');
        const status = document.getElementById('carousel-status');

        glide.on(['mount.after', 'run'], () => {
            let current = glide.index;
            const total = glide._c.Html.slides.length;

            if (total > 0) {
                current++;
            }

            if (status) {
                status.textContent = `${current} / ${total}`;
            }
        });

        glide.mount();
    }

    const deletePictureForm = document.getElementById("delete-picture-form");
    if (deletePictureForm) {
        deletePictureForm.addEventListener('submit', function (event) {
            const message = window.i18nMessages?.deleteConfirmMessage || 'Are you sure you want to delete this image?'
            if (!confirm(message)) {
                event.preventDefault();
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
