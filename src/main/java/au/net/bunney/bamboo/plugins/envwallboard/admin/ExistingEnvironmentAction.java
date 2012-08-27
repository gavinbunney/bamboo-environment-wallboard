/*
 * Copyright (C) 2010 JFrog Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package au.net.bunney.bamboo.plugins.envwallboard.admin;

import com.atlassian.bamboo.security.GlobalApplicationSecureObject;
import com.atlassian.bamboo.ww2.actions.admin.user.AbstractEntityPagerSupport;
import com.atlassian.bamboo.ww2.aware.permissions.GlobalAdminSecurityAware;

import java.util.List;

/**
 * @author Gavin Bunney
 */
public class ExistingEnvironmentAction extends AbstractEntityPagerSupport implements GlobalAdminSecurityAware {

    private EnvironmentConfigManager environmentConfigManager;

    public ExistingEnvironmentAction(EnvironmentConfigManager environmentConfigManager) {
        this.environmentConfigManager = environmentConfigManager;
    }

    public String doBrowse() throws Exception {
        return super.execute();
    }

    @Override
    public Object getSecuredDomainObject() {
        return GlobalApplicationSecureObject.INSTANCE;
    }

    public List<EnvironmentConfig> getEnvironmentConfigs() {
        return environmentConfigManager.getAllEnvironmentConfigs();
    }
}
