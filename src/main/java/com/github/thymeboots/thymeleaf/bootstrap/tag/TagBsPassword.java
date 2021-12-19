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
 * <p>
 *   Represents an bootstrap <code>password</code> control element.<br>
 *   Extend from tb:input component and default type="password"
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:password&gt;&lt;tb:/password&gt;
 * 
 * @author Rifat Yilmaz
 *
 * @since 3.4.0
 *
 */
public class TagBsPassword extends TagBsInput  {
	private static final String TAG_NAME       = "password";
	private static final String INPUT_TYPE     = "password";
	private static final int    PRECEDENCE = 1000;
		
    public TagBsPassword(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
	
    @Override
	protected Map<String,String> addattributes() {	
    	Map<String,String> ret=super.addattributes();
    	ret.put("type", INPUT_TYPE);    	    	
    	return ret;
	}	
    
    @Override
    public String getType() {
    	return INPUT_TYPE;
    }      
    

}
