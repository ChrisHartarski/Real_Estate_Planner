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


    // const deletePictureButton = document.getElementById('delete-picture-button');
    //
    // deletePictureButton.addEventListener("click", function (event) {
    //     const confirmDeletePictureModal = document.getElementById('confirm-delete-picture-modal');
    //     openModal(confirmDeletePictureModal);
    //
    //     const confirmDeletePictureButton = document.getElementById('confirm-delete-picture-button');
    //     confirmDeletePictureButton.addEventListener('click', function () {
    //         const deletePictureForm = document.getElementById('delete-picture-form');
    //         deletePictureForm.submit();
    //     })
    //
    //     const cancelDeletePictureButton = document.getElementById('cancel-delete-picture-button');
    //     cancelDeletePictureButton.addEventListener('click', function () {
    //         const modal = document.closest('.modal');
    //         closeModal(modal);
    //     })
    // });
    //
    // function openModal(modal) {
    //     modal.style.display="block";
    // }
    //
    // function closeModal(modal) {
    //     modal.style.display="none";
    // }

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

    confirmDeleteButton.addEventListener('click', function () {
        if (formToSubmit) {
            formToSubmit.submit();
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
