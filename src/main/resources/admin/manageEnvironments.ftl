[#-- @ftlvariable name="action" type="au.net.bunney.bamboo.plugins.envwallboard.admin.ManageEnvironmentsAction" --]
[#-- @ftlvariable name="" type="au.net.bunney.bamboo.plugins.envwallboard.admin.ManageEnvironmentsAction" --]

<html>
<head>
    <title>Environment Wallboard</title>
    <meta name="decorator" content="adminpage">
</head>

<body>

<h1>Manage Environment Wallboard</h1>

<p>You can use this page to add, edit and delete Environment configurations.</p>

<h2>Build Details</h2>

<p>
The environment wallboard plugin will read the specified url for a file which contains the Build Number, Revision and Timestamp.
An example of such a file is:
<br/><br/>
<pre>
[build]
buildNumber = 602
buildTimeStamp = 2012-08-28T09:23:06.099+10:00
buildRevision = af82e93d8c41ab5234b6571ac4af739c24061cd0
</pre>

</p>

<hr/>

<br/>
<h1>Existing Environments</h1>
<br/>

[@ww.action name="existingEnvironment" namespace="/admin" executeResult="true" /]

<br/>

[@ww.action name="configureEnvironment!default" executeResult="true" /]

</body>
</html>