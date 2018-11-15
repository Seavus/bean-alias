/*
 * Copyright (c) 2018 Seavus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.seavus.beanalias;

import com.seavus.testing.api.BaseIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

public class BeanAliasesIT extends BaseIT {

    @Autowired
    TestBean testBean;
    @Autowired
    @Qualifier("BeanAliasesIT/test-bean-alias")
    TestBean aliasedTestBean;
    @Autowired
    @Qualifier("BeanAliasesIT/test-bean-alias-2")
    TestBean aliasedTestBean2;

    @Test
    public void beanIsAliased() throws Exception {
        assertThat(testBean).isEqualTo(aliasedTestBean);
        assertThat(testBean).isEqualTo(aliasedTestBean2);
    }

    @BeanAliases({
            @BeanAlias(name = "test-bean", alias = "BeanAliasesIT/test-bean-alias"),
            @BeanAlias(name = "test-bean", alias = "BeanAliasesIT/test-bean-alias-2")
    })
    @Configuration
    public static class ITConfigurationWithAliasing {
    }
}
