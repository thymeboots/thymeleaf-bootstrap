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

import java.util.Map;

import org.thymeleaf.templatemode.TemplateMode;

/**
 * <p>Represents an HTML <code>buttonModal</code> element
 * which a button for <code>toggle</code> a modal; <br>
 * The label text is specified by the component value.
 * <p><strong>Attributes</strong> <br>
 * <strong>target</strong> modal id <br>
 * 
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:buttonModal look="primary" size="sm" value="open model" target="modalId&gt;  &lt;/tb:buttonModal&gt;<br> 
 * 
 * @author Rifat Yilmaz
 *
 * @since 3.4.0
 *
 */

public class TagBsButtonModal extends TagBsButton{
	private static final String TAG_NAME       = "buttonModal";
	private static final int    PRECEDENCE = 1000;
	
    public TagBsButtonModal(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }

    @Override
	protected Map<String,String> addattributes() {	
    	Map<String,String> ret=super.addattributes();
    	String dataTarget= this.nvl(this.getTarget(),"").trim();
    	if (dataTarget.indexOf("#")<0) {
    		dataTarget="#"+dataTarget;
    	}
    	ret.put("data-target", dataTarget);
    	ret.put("data-toggle", "modal");
    	return ret;
	}	
	
    
}
