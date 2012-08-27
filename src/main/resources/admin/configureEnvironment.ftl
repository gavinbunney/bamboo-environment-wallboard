[#if mode == 'edit' ]
    [#assign targetAction = 'updateEnvironment']
    [#assign titleText = 'Edit Environment Configuration' /]
<html>
<head>
    <title>Update Environments</title>
</head>
<body>
    [#else]
        [#assign targetAction = 'createEnvironment']
        [#assign titleText = 'Add New Environment Configuration' /]
[/#if]
[#assign cancelUri = '/admin/manageEnvironments.action' /]

<h1>[@ww.text name=titleText /]</h1>
<div class="paddedClearer"></div>
[@ww.form action=targetAction submitLabelKey='global.buttons.update'
titleKey='Environment Details'
cancelUri=cancelUri
descriptionKey='Configure an Environment for display on the wallboard.
If anonymous user is enabled in server, you can leave the auth empty.'
showActionErrors='false']

    [@ww.param name='buttons']
    [@ww.submit value="Test" name="testing" theme='simple' /]
    [/@ww.param]
        [#if actionErrors?? && (actionErrors.size()>0)]
        <div class="aui-message error">
            [#foreach error in formattedActionErrors]
                <p>${error}</p>
            [/#foreach]
            <span class="aui-icon icon-error"></span>
        </div>
        [/#if]

    [@ww.textfield labelKey='Name' name="name" required="true"
    descriptionKey="Name of the environment"/]

    [@ww.textfield labelKey='URL' name="url" required="true"
    descriptionKey="Specify the URL to the build.txt file, e.g. http://app.bunney.net.au/build.txt"/]

    [@ww.textfield labelKey='Auth' name="auth" showPassword="true"
    descriptionKey="Basic authentication username/password in format of 'username:password'. Leave empty if anonymous is enabled."/]

[/@ww.form]
[#if mode=='edit']
</body></html>
[/#if]