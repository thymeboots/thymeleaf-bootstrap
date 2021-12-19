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
 * <p>Represents an bootstrap <code>buttonGroup</code> element
 * <p><strong>Attributes</strong> <br>
 * <strong>layout   </strong> orientation type: [inline,vertical] default:inline <br>
 * <strong>size     </strong> item size: [sm,md,lg] <br>
 * 
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:buttonGroup align="center" justify="start"&gt;&lt;/tb:buttonGroup&gt;
 * 
 * @author Rifat Yilmaz
 *
 * @since 3.4.0
 *
 */
public class TagBsButtonGroup extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "buttonGroup";
	private static final String TAG_HTML       = "div";
	private static final String TAG_BOOTSCLASS = "btn-group";
	private static final int    PRECEDENCE = 1000;
	
	protected enum PropertyKeys {
		 layout		
		,size; 		
	 
       private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	
    public TagBsButtonGroup(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
        
    public String getLayout() {
    	String ret= this.nvl(this.getAttributeValue(PropertyKeys.layout.toString()),"").trim();
    	if ("inline".equals(ret)) {ret="";}      	//space means inline
    	return ret;
    }     
    public String getSize() {
    	return this.getAttributeValue(PropertyKeys.size.toString());
    } 
    
    @Override
    public String getHtmlTagStyleClass() {
    	String ret=TAG_BOOTSCLASS;
    	String layout=this.nvl(this.getLayout(),"").trim();
    	if (!layout.isBlank()) {
    		ret=ret+"-"+layout;
    	}
    	
    	String size=this.nvl(this.getSize(),"").trim();
    	if (!size.isBlank()) {
    		ret=ret+" "+TAG_BOOTSCLASS+"-"+size;
    	}
    	return ret;
    }    
    @Override
    public String getHtmlTag() {
    	return TAG_HTML;
    }     
    
}
