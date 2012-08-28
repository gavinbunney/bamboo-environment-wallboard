[#-- @ftlvariable name="action" type="au.net.bunney.bamboo.plugins.envwallboard.admin.ExistingEnvironmentAction" --]
[#-- @ftlvariable name="" type="au.net.bunney.bamboo.plugins.envwallboard.admin.ExistingEnvironmentAction" --]
<table id="existingEnvironment" class="grid maxWidth">
    <thead>
    <tr>
        <th>Name</th>
        <th>URL</th>
        <th>Auth</th>
        <th>Wallboard</th>
        <th>Display Priority</th>
        <th>Display Width</th>
    </tr>
    </thead>
[#foreach environmentConfig in environmentConfigs]
    <tr>
        <td>
            ${environmentConfig.name}
        </td>
        <td>
            ${environmentConfig.url}
        </td>
        <td>
            ${environmentConfig.auth}
        </td>
        <td>
            ${environmentConfig.wallboardName}
        </td>
        <td>
            ${environmentConfig.displayPriority}
        </td>
        <td>
            ${environmentConfig.displayWidth}
        </td>

        <td class="operations">
            <a id="editEnvironment-${environmentConfig.id}" href="[@ww.url action='editEnvironment' environmentId=environmentConfig.id/]">
                Edit
            </a>
            |
            <a id="deleteEnvironment-${environmentConfig.id}"
               href="[@ww.url action='deleteEnvironment' environmentId=environmentConfig.id/]"
               onclick='return confirm("Are you sure you wish to remove this configuration?")'>
                Delete
            </a>
        </td>
    </tr>
[/#foreach]
</table>