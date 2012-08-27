[#-- @ftlvariable name="action" type="au.net.bunney.bamboo.plugins.envwallboard.admin.ManageEnvironmentsAction" --]
[#-- @ftlvariable name="" type="au.net.bunney.bamboo.plugins.envwallboard.admin.ManageEnvironmentsAction" --]

<html>
<head>
    <title>Manage Environments</title>
    <meta name="decorator" content="adminpage">
</head>

<body>

<h1>Manage Environments</h1>

<p>You can use this page to add, edit and delete Environment configurations.</p>

[@ww.action name="existingEnvironment" namespace="/admin" executeResult="true" /]

<br/>

[@ww.action name="configureEnvironment!default" executeResult="true" /]

</body>
</html>