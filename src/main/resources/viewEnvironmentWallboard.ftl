<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>Environment Wallboard</title>

    <meta http-equiv="X-UA-Compatible" content="IE=EDGE" />

    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="-1"/>
    <meta name="decorator" content="none">

    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent"/>

    ${webResourceManager.requireResource("au.net.bunney.bamboo.plugins.bamboo-environment-wallboard:environment-wallboard-resources")}
    ${webResourceManager.requiredResources}
</head>
<body>

[#if environments.size() == 0]
    <div class="no-builds">
        <h3>No environments are configured for displaying</h3>
        (this could be due to a filter or viewing permissions)
    </div>

[#else]
    [#if environments.size() == 1]
        [#assign boxWidth = "100%"]
    [#elseif environments.size() == 2]
        [#assign boxWidth = "50%"]
    [#elseif environments.size() == 3]
        [#assign boxWidth = "33%"]
    [#else]
        [#assign boxWidth = "25%"]
    [/#if]

    <script>
      window.setTimeout(function(){window.location.href=window.location.href},${secondsBeforeNextRefresh}*1000);
    </script>

    [#list environments as environment]

        [#if environment.status.equals('alive')]
            <div class="environment" style="width: ${boxWidth};">
                <div class="up">
                    <div class="name">${environment.name}</div>
                    <div class="build">${environment.buildNumber}</div>
                    <div class="timeStamp">${environment.buildTimeStamp}</div>
                    <div class="revision">${environment.buildRevision}</div>
                </div>
            </div>
        [#else]
            <div class="environment" style="width: ${boxWidth};">
                <div class="down">
                    <div class="name">${environment.name}</div>
                    <div class="build">down</div>
                    <div class="details">&nbsp;</div>
                </div>
            </div>
        [/#if]

    [/#list]
[/#if]
</body>
</html>