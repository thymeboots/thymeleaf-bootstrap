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
 * <p>Represents an bootstrap <code>facet</code> element <br>
 * Facets are not renderable
   <p><strong>Attributes</strong> <br/>
 * <strong>name   </strong> facet name: [header,body,footer,prepend,append] <br>
 * 
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:facet name="header"&gt;<br>
 * &nbsp;&nbsp;&nbsp;&lt;tb:icon value="info-circle" &gt;&lt;tb:/icon&gt;<br>
 * &nbsp;&nbsp;&nbsp;Header Text<br>
 * &lt;tb:/facet&gt;
 */
public class TagBsFacet extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "facet";
	private static final String TAG_HTML       = "";
	private static final String TAG_BOOTSCLASS = "";
	private static final int    PRECEDENCE = 1000;
		
    public TagBsFacet(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }        
    
	protected enum PropertyKeys {
	   name;
		
	   private String toString;
	   PropertyKeys(String toString) { this.toString = toString; }
	   PropertyKeys() { }
	   public String toString() {
	       return ((toString != null) ? toString : super.toString());
	   }			
	}	
	public String getName() {
		return this.getAttributeValue(PropertyKeys.name.toString());
	} 
    
    @Override
    public String getHtmlTagStyleClass() {
    	return TAG_BOOTSCLASS;
    }    
    @Override
    public String getHtmlTag() {
    	return TAG_HTML;
    }     
    @Override
    public java.lang.String getRendered() {
    	//Not renderable
    	return "false";
    }    
   
}

