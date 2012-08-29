![Bamboo Environment Wallboard](https://raw.github.com/gavinbunney/bamboo-environment-wallboard/master/src/main/resources/pluginBanner.png)

# Build Information File

In order for the Environment Wallboard to monitor your environments, a build information file must exist on the target webserver. This is typically done as part of your build process.

This build file contains the build number, revision and timestamp. The three key-value pairs are:

`buildNumber=xxx`
`buildTimeStamp=xxxx`
`buildRevision=xxx`

The easiest way to do this is to have a Script Task during your 'build' job to echo out to the specified file.

Example Shell Script:

```
echo buildNumber = ${bamboo.buildNumber} > my-app/src/main/webapp/build.txt ;
echo buildTimeStamp = ${bamboo.buildTimeStamp} >> my-app/src/main/webapp/build.txt
echo buildRevision = ${bamboo.repository.revision.number} >> my-app/src/main/webapp/build.txt
```

The URL to this deployed build information file is then put into the Environment Wallboard configuration on the Bamboo Admin screens.

----

# Development

* `atlas-run`   -- installs this plugin into Bamboo and starts it on http://localhost:6990/bamboo
* `atlas-debug` -- same as atlas-run, but allows a debugger to attach at port 5005
* `atlas-cli`   -- after atlas-run or atlas-debug, opens a Maven command line window:
                 - 'pi' reinstalls the plugin into the running Bamboo instance
* `atlas-help`  -- prints description for all commands in the SDK

Full documentation is always available at: https://developer.atlassian.com/display/DOCS/Developing+with+the+Atlassian+Plugin+SDK
