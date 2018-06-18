/*******************************************************************************
 * Copyright  2018 Marius Schultchen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package de.salomscala.projectarchitectureplugin.plugin.rules;

import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.Task;

import de.salomscala.projectarchitectureplugin.plugin.ProjectArchitectureDescriptionExtension;

public class ApplyRulesTask extends DefaultTask {

    private final Rules rules;

    @Inject
    public ApplyRulesTask() {
        final ProjectArchitectureDescriptionExtension extension = this.getProject().getExtensions()
                .getByType(ProjectArchitectureDescriptionExtension.class);
        this.rules = extension.getRules();

        this.doLast((final Task t) -> {
            this.rules.apply();
        });
    }
}
