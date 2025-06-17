// Apenas captura fechamento de aba/janela
(function() {
    var lockReleased = false;

    // Marca como liberado quando o form é submetido
    document.addEventListener('submit', function() {
        lockReleased = true;
    });

    // Libera lock ao fechar aba/janela
    window.addEventListener('beforeunload', function() {
        if (!lockReleased) {
            var articleIdInput = document.querySelector('input[name*="articleId"]');
            if (articleIdInput && articleIdInput.value) {
                // Requisição síncrona para liberar o lock
                var xhr = new XMLHttpRequest();
                xhr.open('POST', '/o/article-lock/unlock', false);
                xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xhr.send('articleId=' + articleIdInput.value);
            }
        }
    });
})();