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

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * <p>Represents an bootstrap <code>badge</code> element
 * <p><strong>Attributes</strong> <br>
 * <strong>look    </strong> styles look attribute: [primary , secondary, info , success , warning , danger , dark , light] <br>
 * <strong>value   </strong> value attribute<br>
 * 
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:badge value="12" look="primary" &gt;  &lt;/tb:badge&gt; <br>
 * &lt;tb:badge value="12" look="secondary" &gt;  &lt;/tb:badge&gt; <br>
 * 
 */
public class TagBsBadge extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "badge";
	private static final String TAG_HTML       = "span";
	private static final String TAG_BOOTSCLASS = "badge";	
	private static final int    PRECEDENCE = 1000;
	    
    public TagBsBadge(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
	
	protected enum PropertyKeys {	
		/**look or severity-[success,info,warning,danger]*/
		look
		,value; 
			 
       private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	
    
    
    public String getLook() {
    	String ret= this.nvl(this.getAttributeValue(PropertyKeys.look.toString()),"");
    	
    	if ("error".equals(ret)) {ret="danger";}
    	return ret;
    	
    }          
    public String getValue() {
    	return this.getAttributeValue(PropertyKeys.value.toString());
    }          
    
    @Override
    public String getHtmlTagStyleClass() {
    	String severity=this.nvl(this.getLook(), "").trim();
    	if (severity.isBlank()) {severity="secondary";}
    	String ret=TAG_BOOTSCLASS +" "+TAG_BOOTSCLASS+"-"+severity;
    	return ret;
    }
    @Override
    public String getHtmlTag() {
    	return TAG_HTML;
    }     
          
    @Override
    public void encodeChildren(ITemplateContext context, IModel model,  Map<String,String> attributes)  {						
		String value=this.getValue();
		if (value!=null) {
			model.addModel(this.createModelText(context, value));
		}		    	
    	super.encodeChildren(context, model, attributes);
    }
    
}
