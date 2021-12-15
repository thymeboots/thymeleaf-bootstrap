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
 * <p>Represents an bootstrap <code>column</code> element
 * <p><strong>Attributes</strong> <br>
 * <strong>align  </strong> align-self: [start,center,end] <br>
 * <strong>span   </strong> span or col-md: [1,2,...,12] <br>
 * <strong>xs     </strong> col-xs: [1,2,...,12] <br>
 * <strong>sm     </strong> col-sm: [1,2,...,12] <br>
 * <strong>md     </strong> col-md: [1,2,...,12] <br>
 * <strong>lg     </strong> col-lg: [1,2,...,12] <br>
 * <strong>xl     </strong> col-xl: [1,2,...,12] <br> 
 * <strong>xxl    </strong> col-xxl: [1,2,...,12]<br> 
 * <strong>wd     </strong> w_(%): [1..100]<br>
 * 
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:column align="center" xs="12" md="6" xxl="4" &gt;  &lt;tb:/column&gt;<br>
 * &lt;tb:column span="6"  &gt;  &lt;tb:/column&gt;<br>
 * &lt;tb:column col=""  &gt;  &lt;tb:/column&gt;<br>
 * &lt;tb:column col="6"  &gt;  &lt;tb:/column&gt;<br>
 * 
 */
public class TagBsColumn extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final int    PRECEDENCE     = 1000;
	private static final String TAG_NAME       = "column";
	private static final String TAG_BOOTSCLASS = "col";	

    public TagBsColumn(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
	
	protected enum PropertyKeys {
		/**align-self-[start,center,end]*/
		align		
		/**col-xs or tiny-screen*/
		,xs 
		 /**col-sm or small-screen*/
		,sm
		/**col-md or medium-screen*/
		,md
		/**col-lg or large-screen*/
		,lg
		/**col-xl or x-large-screen*/
		,xl
		/**col-xxl or xx-large-screen*/
		,xxl
		/**span, col-md or medium-screen*/
		,span,
		/**w_value(%)*/
		wd;
	 
	   private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}
	
			    
    private String formatColumnClass(String key) {
    	String ret="";
    	String val =this.getAttributeValue(key);
    	if (val!=null) {
    		if (key.equals("xs")) {
    			//for xs class like that" col or  col-size" 
    			if (val.isBlank()) {
    				ret=TAG_BOOTSCLASS;
    			}
    			else {
    				ret=TAG_BOOTSCLASS+"-"+val.trim();
    			}    			
    		}
    		else {
        		if (key.equals("span")) {
        			key="md";
        		}    			    			
        		if (val.isBlank()) {
        			ret=TAG_BOOTSCLASS+"-"+key;
        		}
        		else {
            		val=val.trim();
            		ret=TAG_BOOTSCLASS+"-"+key+"-"+val;        			
        		}
    		}
    		
    	}    	
    	return ret;
    }
    private String alignClass() {
    	String ret="";
    	String key=PropertyKeys.align.toString();
    	String val =this.getAttributeValue(key);
    	if (val!=null) {
			if (!val.isBlank()) {
				val=val.trim();
				ret="align-self-"+val;
			}    		
    	}    	
    	return ret;
    }

    private String wdClass() {
    	String ret="";
    	String key=PropertyKeys.wd.toString();
    	String val =this.getAttributeValue(key);
    	if (val!=null) {
			if (!val.isBlank()) {
				val=val.trim();
				ret="w-"+val;
			}    		
    	}    	
    	return ret;
    }
    
    @Override
    public String getHtmlTagStyleClass() {
    	String ret="";
    	
    	String xs  = formatColumnClass(PropertyKeys.xs.toString());    	
    	String sm  = formatColumnClass(PropertyKeys.sm.toString());
    	String md  = formatColumnClass(PropertyKeys.md.toString());
    	String lg  = formatColumnClass(PropertyKeys.lg.toString());
    	String xl  = formatColumnClass(PropertyKeys.xl.toString());
    	String xxl = formatColumnClass(PropertyKeys.xxl.toString());

    	//span=medium
    	String span= formatColumnClass(PropertyKeys.span.toString());
    	if (!span.isBlank()) {md=span;}
    	    	
    	
    	ret=(xs +" "+sm).trim();
    	ret=(ret+" "+md).trim();
    	ret=(ret+" "+lg).trim();
    	ret=(ret+" "+xl).trim();
    	ret=(ret+" "+xxl).trim();
    	
    	ret=ret.trim();
    	if (ret.isBlank()) {
    		ret=TAG_BOOTSCLASS;
    	}
    	
    	String align=alignClass() ;
    	ret=(ret+" "+align).trim();

    	String wdClass=wdClass() ;
    	if (!wdClass.isBlank()) {
    		ret=wdClass;
    	}
    	
    	
    	
    	return ret;
    }

}
