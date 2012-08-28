# Bamboo Environment Wallboard
====================================

Bamboo plugin for showing details of your environments on a wallboard.

Features:
- green indicators for showing alive environments
- grey showing those environments that are down / unreachable
- multiple environments & wallboards (groups of environments)
- custom priorities and display width - give emphasis on certain environments
- display properties to allow any number of tiles combinations

![Bamboo Environment Wallboard](https://raw.github.com/gavinbunney/bamboo-environment-wallboard/master/src/main/resources/pluginBanner.png)




----

# Development

* `atlas-run`   -- installs this plugin into Bamboo and starts it on http://localhost:6990/bamboo
* `atlas-debug` -- same as atlas-run, but allows a debugger to attach at port 5005
* `atlas-cli`   -- after atlas-run or atlas-debug, opens a Maven command line window:
                 - 'pi' reinstalls the plugin into the running Bamboo instance
* `atlas-help`  -- prints description for all commands in the SDK

Full documentation is always available at: https://developer.atlassian.com/display/DOCS/Developing+with+the+Atlassian+Plugin+SDK
