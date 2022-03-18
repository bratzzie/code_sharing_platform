<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Code</title>
    <link rel="stylesheet"
          target="_blank" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<#if code_obj.getTimeRestricted() == true>
    <span id="time_restriction">${code_obj.time}</span>
</#if>

<#if code_obj.getViewsRestricted() == true>
    <span id="views_restriction">${code_obj.views}</span>
</#if>
<h1><span id="load_date">${code_obj.date}</span></h1>

<pre id="code_snippet"><code>${code_obj.code}</code></pre>

</body>
</html>