<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Test page</title>
</head>
<body>
Test JSP page from within the war file.
<button onclick="start()">Start</button>
<div id="foo"></div>

<script type="text/javascript">
    function start() {

        var source = null;
        if (!!window.EventSource) {
            source = new EventSource('sse');
        } else {
            alert('EventSource is not supported');
            return
        }

        source.addEventListener('message', function (e) {
            document.getElementById('foo').innerHTML += 'receive data.' + '<br>';
            console.log('message', e);
        }, false);

        source.addEventListener('open', function (e) {
            document.getElementById('foo').innerHTML += 'Connection is opened' + '<br>';
            console.log('open', e);
        }, false);

        source.addEventListener('error', function (e) {
            if (e.readyState == EventSource.CLOSED) {
                document.getElementById('foo').innerHTML += 'Connection was closed.' + '<br>';
            } else {
                document.getElementById('foo').innerHTML += 'Connection was closed. [' + e.readyState + ']<br>';
            }
            console.log('error', e);
        }, false);
    }
</script>
</body>
</html>
