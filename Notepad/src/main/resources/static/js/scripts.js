$(document).ready(function () {
    $('#summernote').summernote({
        placeholder: 'Текст заметки',
        lang: 'ru-RU',
        height: 500,
        width: 1100,
        toolbar: [
            ['undo/redo', ['undo', 'redo']],
            ['style', ['style']],
            ['font', ['bold', 'italic', 'underline', 'strikethrough', 'superscript', 'subscript', 'clear']],
            ['font-family', ['fontname', 'fontsize']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['table', ['table']],
            ['insert', ['link', 'picture']],
            ['view', ['fullscreen', 'codeview', 'help']]
        ],
    })
});

function onChangeCheckBox1() {
    let checkbox;
    let button;
    checkbox = document.getElementById("checkbox1");
    button = document.getElementById("button");

    if (checkbox.checked) {
        button.innerHTML = "Сохранить";
    } else {
        button.innerHTML = "Опубликовать";
    }
}