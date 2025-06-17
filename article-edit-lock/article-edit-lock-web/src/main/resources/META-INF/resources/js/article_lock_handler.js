(function() {
    AUI().ready(function() {
        console.log('Article Lock Handler initialized');

        var articleId = null;

        function getArticleId() {
            if (articleId) return articleId;

            var urlParams = new URLSearchParams(window.location.search);
            articleId = urlParams.get('articleId');

            if (!articleId) {
                var input = document.querySelector('input[name*="articleId"]');
                if (input) articleId = input.value;
            }

            return articleId;
        }

        function releaseArticleLock() {
            var currentArticleId = getArticleId();
            if (currentArticleId) {
                console.log('Releasing lock for article:', currentArticleId);

                var resourceURL = Liferay.PortletURL.createResourceURL();
                resourceURL.setPortletId(Liferay.ThemeDisplay.getPortletDisplay().getId());
                resourceURL.setResourceId('/article_lock/unlock');

                fetch(resourceURL.toString(), {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: 'articleId=' + currentArticleId
                }).then(function(response) {
                    console.log('Lock release response:', response.status);
                });
            }
        }

        window.addEventListener('beforeunload', function(e) {
            releaseArticleLock();
        });

        Liferay.on('beforeNavigate', function(event) {
            var destination = event.path || '';
            if (destination.indexOf('edit_article') === -1) {
                releaseArticleLock();
            }
        });

        setTimeout(function() {
            var cancelButtons = document.querySelectorAll(
                'a.btn-secondary[href*="redirect"], ' +
                'button.btn-secondary[type="button"], ' +
                '.btn-cancel, ' +
                'a:contains("Cancel"), ' +
                'a:contains("Cancelar")'
            );

            cancelButtons.forEach(function(button) {
                button.addEventListener('click', function(e) {
                    console.log('Cancel button clicked');
                    releaseArticleLock();
                });
            });
        }, 1000);
    });
})();