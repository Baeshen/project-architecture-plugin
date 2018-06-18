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
package de.salomscala.projectarchitectureplugin.plugin.dimensionsatisfier;

import org.gradle.api.Project;

import de.salomscala.projectarchitectureplugin.plugin.dependencies.ProjectElement;

public abstract class ProjectDimensionSatisfier extends ProjectDependencyDimensionSatisfier {

    @Override
    public boolean approves(final ProjectElement dependency) {
        return this.approvesProject(dependency.getProject());
    }

    public abstract boolean approvesProject(Project project);

}
