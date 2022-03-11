<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Latest</title>
</head>
<body>
<table>
    <#list latestSnippets as code>
        <tr>
            <td><span>${code.date}</span></td>
            <td><pre>${code.code}</pre></td>
        </tr>
    </#list>

</table>
</body>
</html>