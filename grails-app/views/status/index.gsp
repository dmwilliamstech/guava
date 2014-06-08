<html>
<head>
    <meta name="layout" content="main" />
    <title>What Are You Doing?</title>
    <g:javascript library="jquery" plugin="jquery" />
</head>
<body>
<h1>Search For People To Follow</h1>
<div class="searchForm">
    <g:form controller="searchable">
        <g:textField name="q" value=""/>
    </g:form>
</div>

<h1>What Are You Doing?</h1>
<div class="updateStatusForm">
    <g:formRemote onSuccess="document.getElementById('messageArea').value='';" url="[action: 'updateStatus']" update="messageLists" name="updateStatusForm">
        <g:textArea name="message" value="" id="messageArea" /><br/>
        <g:submitButton name="Update Status" />
    </g:formRemote>
</div>
<div id="messageLists">
    <g:render template="messages" collection="${messages}" var="message"/>
</div>
</body>
</html>