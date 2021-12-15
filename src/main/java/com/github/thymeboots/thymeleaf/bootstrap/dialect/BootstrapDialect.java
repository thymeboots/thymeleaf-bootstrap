/*
 * =============================================================================
 *
 *   Copyright (c) 2021, The Rifat Yilmaz  (rifyilmaz.github.com)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */

package com.github.thymeboots.thymeleaf.bootstrap.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;


/**
 * <p>
 *   Bootstrap Dialect. This is the class containing the implementation of Thymeleaf Bootstrap Dialect, including all
 *   {@code tb:*} processors, expression objects, etc.
 * </p>
 * <p>
 *   Note this dialect uses <strong>Thymeleaf</strong> dialects and <strong>OGNL</strong> as an expression language
 * </p>
 * <p>
 *   Note this class compatible Thymeleaf 3.0, Bootstrap 4.2.1, Jquery 3.0.0, Font Awesome Free 5.11.2
 * </p> 
 * <p>
 *   Note a class with this name existed since 3.4.0
 * </p>
 *
 * @author Rifat Yilmaz
 *
 * @since 3.4.0
 *
 */
public class BootstrapDialect extends AbstractProcessorDialect {
	private static final int    PRECEDENCE = StandardDialect.PROCESSOR_PRECEDENCE; //1000;
	private static final String BOOTSTRAP_VERSION    = "4.2.1";
    public BootstrapDialect() {
        super(
        	"Bootstrap",  // Dialect name
        	"tb",         // Dialect prefix (tb:*)
        	PRECEDENCE);
    }

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {		
        Set<IProcessor> processors = new HashSet<IProcessor>();    
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsA(              dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsAccordion(      dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsAlert(          dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsBadge(          dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsButton(         dialectPrefix, BOOTSTRAP_VERSION)); 
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsButtonGroup(    dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsButtonDrop(     dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsButtonModal(    dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsCard(           dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsCarousel(       dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsCarouselCaption(dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsCarouselItem(   dialectPrefix, BOOTSTRAP_VERSION));        
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsCollapse(       dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsColumn(         dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsContainer(      dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsFacet(          dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsFormfloat(      dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsFormgroup(      dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsForminline(     dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsFormline(       dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsIcon(           dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsImg(            dialectPrefix, BOOTSTRAP_VERSION));        
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsInputgroup(     dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsLabel(          dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsModal(          dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsNavLink(        dialectPrefix, BOOTSTRAP_VERSION)); 
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsNavList(        dialectPrefix, BOOTSTRAP_VERSION));        
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsPassword(       dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsRow(            dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsSpinner(        dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsTab(            dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsTabView(        dialectPrefix, BOOTSTRAP_VERSION));
        
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsInput(          dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsSelect(        dialectPrefix, BOOTSTRAP_VERSION));
        processors.add(new com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsTextarea(       dialectPrefix, BOOTSTRAP_VERSION));
                        
        return processors;
        
	}




 }