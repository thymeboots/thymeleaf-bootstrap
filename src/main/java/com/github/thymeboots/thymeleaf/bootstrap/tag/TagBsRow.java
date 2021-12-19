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
 * <p>Represents an bootstrap <code>row</code> element
 * <p><strong>Attributes</strong> <br>
 * <strong>align     </strong> align-items: [start,center,end] <br>
 * <strong>justify   </strong> justify-content: [start,center,end,around,between,evenly] <br>
 * 
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:row align="center" justify="start" &gt;  &lt;tb:/row&gt;
 * 
 * @author Rifat Yilmaz
 *
 * @since 3.4.0
 *
 */
public class TagBsRow extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "row";
	private static final String TAG_HTML       = "div";
	private static final String TAG_BOOTSCLASS = "row";
	private static final int    PRECEDENCE = 1000;
	
    public TagBsRow(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
    
	protected enum PropertyKeys {
		 align		
		,justify; 		
	 
	  private String toString;
      PropertyKeys(String toString) { this.toString = toString; }
      PropertyKeys() { }
      public String toString() {
          return ((toString != null) ? toString : super.toString());
      }			
	}	
    
    
    private String alignClass() {
    	String ret="";
    	String key=PropertyKeys.align.toString();
    	String val =this.getAttributeValue(key);
    	if (val!=null) {
			if (!val.isBlank()) {
				val=val.trim();
				ret="align-items-"+val;
			}    		
    	}    	
    	return ret;
    }
    private String justifyClass() {
    	String ret="";
    	String key=PropertyKeys.justify.toString();
    	String val =this.getAttributeValue(key);
    	if (val!=null) {
			if (!val.isBlank()) {
				val=val.trim();
				ret="justify-content-"+val;
			}    		
    	}    	
    	return ret;
    }
    
    
    @Override
    public String getHtmlTagStyleClass() {
    	String ret=TAG_BOOTSCLASS;
    	
    	String align=alignClass() ;
    	ret=(ret+" "+align).trim();
    	
    	String justify=justifyClass() ;
    	ret=(ret+" "+justify).trim();
    	
    	return ret;
    }
    
    @Override
    public String getHtmlTag() {
    	return TAG_HTML;
    }     
    
}
