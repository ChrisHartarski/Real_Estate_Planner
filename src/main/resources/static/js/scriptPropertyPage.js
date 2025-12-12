document.addEventListener("DOMContentLoaded", () => {

    const input = document.getElementById('file-upload');
    const fileName = document.getElementById('file-name');

    if(input && fileName) {
        input.addEventListener('change', () => {
            if(input.files.length > 0){
                let names = Array.from(input.files)
                    .map(file => file.name)
                    .join(', ');

                fileName.textContent = names;
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

    const uploadImageForm = document.querySelector('.upload-image-form');
    uploadImageForm.addEventListener('submit', () => {
        const uploadBtn = document.getElementById('upload-image-btn');
        uploadBtn.disabled = true;
        uploadBtn.classList.remove('btn-blue');
        uploadBtn.classList.add('btn-disabled');

        const uploadingMsg = document.getElementById('uploading-text');
        uploadingMsg.style.display = 'block';
    });

    const deleteButtons = document.querySelectorAll('.delete-button');
    const modal = document.querySelector('#modal');
    const confirmDeleteButton = document.querySelector('#confirm-delete-button');
    const cancelDeleteButton = document.querySelector('#cancel-delete-button');

    let formToSubmit = null;

    deleteButtons.forEach(button => {
        button.addEventListener('click', function () {
            formToSubmit = button.closest('form');
            const modalMessage = document.querySelector('#modal-message');
            modalMessage.textContent = formToSubmit.getAttribute("data-message");
            openModal(modal);
        });
    });

    let propertyDeleteMsgEl = document.getElementById('property-delete-in-progress-msg');

    confirmDeleteButton.addEventListener('click', function () {
        if (formToSubmit) {
            formToSubmit.submit();
            confirmDeleteButton.disabled = true;
            confirmDeleteButton.classList.remove('btn-blue');
            confirmDeleteButton.classList.add('btn-disabled');
            propertyDeleteMsgEl.style.display = 'block';
        }
    });

    cancelDeleteButton.addEventListener('click', function () {
        closeModal(modal);
        formToSubmit = null;
    })

    function openModal(modal) {
        modal.style.display="block";
    }

    function closeModal(modal) {
        modal.style.display="none";
    }
});
