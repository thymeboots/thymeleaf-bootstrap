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

package com.github.thymeboots.thymeleaf.bootstrap.tag;


import org.thymeleaf.templatemode.TemplateMode;


/**
 * <p>Represents an bootstrap <code>tab</code> element
 * <p><strong>Attributes</strong> <br>
 *  
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:tab header="tab header" &gt;&lt;tb:/tab&gt;
 *  
 */
public class TagBsTab extends com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsTabCard  {
	private static final String TAG_NAME   = "tab";
	private static final int    PRECEDENCE = 1000;

    public TagBsTab(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
    
	
}
