var AutoTOC = (function () {

    var TAGS = [
        'h1',
        'h2',
        'h3',
        'h4',
        'h5',
        'h6'
    ];

    var _renderSubLevel = function (elements, offset) {

        var content = '<ul>';
        var curLevel = TAGS.indexOf(elements[offset].tagName.toLowerCase());
        var subLevelStarted = false;

        for (var i = offset; i < elements.length; i++) {

            var level = TAGS.indexOf(elements[i].tagName.toLowerCase());

            if (level > curLevel) {
                if (!subLevelStarted) {
                    content += '<li>' + _renderSubLevel(elements, i) + '</li>';
                    subLevelStarted = true;
                }
            } else if (level == curLevel) {
                subLevelStarted = false;
                elements[i].id = 'toc-' + i;
                content += '<a href="#toc-' + i + '"><li>' + elements[i].textContent + '</li></a>';
            } else {
                content += '</ul>';
                return content;
            }
        }

        content += '</ul>';
        return content;
    };

    return {
        init: function (args) {

            var titleContainer = document;
            var tocContainer = document.getElementById(args.tocContainerId);
            var minLevel = Math.min(args.minLevel || 0, 5);

            if (typeof args.titleContainerId != 'undefined') {
                titleContainer = document.getElementById(args.titleContainerId);
            }

            var titles = titleContainer.querySelectorAll(TAGS.slice(minLevel).join(','));

            if (tocContainer && titles.length > 0) {
                tocContainer.innerHTML = _renderSubLevel(titles, 0);

            }
        }
    }
})();