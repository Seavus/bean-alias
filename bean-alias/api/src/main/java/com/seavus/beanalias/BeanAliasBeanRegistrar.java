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

import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.type.AnnotationMetadata;

class BeanAliasBeanRegistrar implements ImportBeanDefinitionRegistrar, PriorityOrdered {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanAliasBeanRegistrar.class);
    private static final boolean LOG_INFO_ENABLED = LOGGER.isInfoEnabled();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        if (metadata.getAnnotationTypes().contains(BeanAliases.class.getName())) {
            metadata.getAnnotationAttributes(BeanAliases.class.getName()).values().stream()
                    .flatMap(beanAliasesValue -> Stream.of((Map<String, Object>[]) beanAliasesValue))
                    .forEach(beanAliasAttributes -> registerAlias(registry, beanAliasAttributes));
        } else {
            registerAlias(registry, metadata.getAnnotationAttributes(BeanAlias.class.getName()));
        }
    }

    private void registerAlias(BeanDefinitionRegistry registry, Map<String, Object> attributes) {
        String name = (String) attributes.get("name");
        String alias = (String) attributes.get("alias");
        if (LOG_INFO_ENABLED) {
            LOGGER.info("Registering alias '{}' for '{}'", alias, name);
        }
        registry.registerAlias(name, alias);
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
