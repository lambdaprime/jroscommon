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
package id.jroscommon.tests;

import id.jroscommon.RosName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author lambdaprime intid@protonmail.com
 */
public class RosNameTest {
    @Test
    public void test() {
        Assertions.assertEquals("ssss", new RosName("ssss").toString());
        Assertions.assertEquals("/ssss", new RosName("ssss").toGlobalName());
        Assertions.assertEquals("/ssss", new RosName("/ssss").toGlobalName());
        Assertions.assertEquals("[ssss]", new RosName("ssss").path().toString());
        Assertions.assertEquals("[ssss]", new RosName("///ssss").path().toString());
        Assertions.assertThrows(
                IllegalArgumentException.class, () -> RosName.ofPackageResourceName("/ssss"));
        Assertions.assertThrows(
                IllegalArgumentException.class, () -> RosName.ofPackageResourceName("ssss"));
        Assertions.assertEquals("[ssss, aa]", new RosName("ssss/aa").path().toString());
        Assertions.assertEquals("ssss/aa/ggg", new RosName("ssss/aa").add("//ggg").toString());
    }
}
