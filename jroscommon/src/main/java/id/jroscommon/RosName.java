/*
 * Copyright 2025 jrosclient project
 * 
 * Website: https://github.com/lambdaprime/jroscommon
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package id.jroscommon;

import java.util.Arrays;
import java.util.List;

/**
 * @see <a href="https://wiki.ros.org/Names">Names</a>
 * @author lambdaprime intid@protonmail.com
 */
public record RosName(String name) {
    public RosName {
        if (name.isBlank()) throw new IllegalArgumentException("ROS name cannot be empty or blank");
    }

    /**
     * @return path represents list of ROS namespace parts + ROS name (where ROS name is the last
     *     element of the path)
     */
    public List<String> path() {
        return Arrays.asList(strip(name).split("/"));
    }

    public String lastName() {
        var p = path();
        return p.get(p.size() - 1);
    }

    public String packageName() {
        var path = path();
        if (path.size() != 2) throw new IllegalArgumentException("Not a package resource name");
        return path.get(0);
    }

    @Override
    public final String toString() {
        return name;
    }

    public RosName add(String name) {
        return new RosName(this.name + "/" + strip(name));
    }

    /**
     * Returns Fully Qualified Name
     *
     * @see <a
     *     href="https://design.ros2.org/articles/topic_and_service_names.html#fully-qualified-names">Topic
     *     and Service name mapping to DDS</a>
     */
    public String toGlobalName() {
        if (!name.startsWith("/")) return "/" + name;
        return name;
    }

    /**
     * @see <a href="https://wiki.ros.org/Names#Package_Resource_Names">ROS Package Resource
     *     Names</a>
     */
    public static RosName ofPackageResourceName(String name) {
        var rosName = new RosName(name);
        if (rosName.path().size() != 2)
            throw new IllegalArgumentException(
                    "ROS package resource name '"
                            + name
                            + "' should have form <PACKAGE>/<RESOURCE>");
        return rosName;
    }

    private static String strip(String name) {
        return name.replaceAll("^/+", "");
    }
}
