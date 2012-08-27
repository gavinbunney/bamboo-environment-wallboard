[#-- @ftlvariable name="action" type="au.net.bunney.bamboo.plugins.envwallboard.admin.ExistingEnvironmentAction" --]
[#-- @ftlvariable name="" type="au.net.bunney.bamboo.plugins.envwallboard.admin.ExistingEnvironmentAction" --]
<table id="existingEnvironment" class="grid maxWidth">
    <thead>
    <tr>
        <th>Name</th>
        <th>URL</th>
        <th>Auth</th>
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

        <td class="operations">
            <a id="editEnvironment-${environmentConfig.id}" href="[@ww.url action='editEnvironment' serverId=environmentConfig.id/]">
                Edit
            </a>
            |
            <a id="deleteEnvironment-${environmentConfig.id}"
               href="[@ww.url action='deleteEnvironment' serverId=environmentConfig.id/]"
               onclick='return confirm("Are you sure you wish to remove this configuration?")'>
                Delete
            </a>
        </td>
    </tr>
[/#foreach]
</table>

[#--[@cp.entityPagination actionUrl='${req.contextPath}/admin/manageEnvironments.action?' paginationSupport=paginationSupport /]--]
