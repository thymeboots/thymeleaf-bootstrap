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
 * <p>Represents an bootstrap <code>select</code> element
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:select&gt;&lt;tb:/select&gt;
 */
public class TagBsSelect extends com.github.thymeboots.thymeleaf.bootstrap.tag.TagBsInput {
	private static final String TAG_NAME       = "select";
	private static final String TAG_HTML       = "select";
	private static final String TAG_CUSTOMSELECT = "custom-select";
	private static final String TAG_BOOTSCLASS   = "form-control";
		
	private static final int    PRECEDENCE = 1000;	
	
    public TagBsSelect(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
        
        
    @Override
    public String getHtmlTagStyleClass() {
    	String ret=TAG_BOOTSCLASS;
    	if (this.isCustom()) {
    		ret=TAG_CUSTOMSELECT;
    		ret=(ret+" "+sizeClass()).trim();
    	}
    	return ret;
    }    
    @Override
    public String getHtmlTag() {
    	return TAG_HTML;
    }     
    @Override
    public String getType() {
    	return null;
    }      

    public String sizeClass() {
    	String ret="";
    	String size=this.nvl( this.getSize(),"" );
    	if (!this.isNumber(size) ) {
    		if (this.isCustom()) {
    			ret=TAG_CUSTOMSELECT+"-"+size;
    		}
    	}    	
    	return ret;
    }      
    
    
}