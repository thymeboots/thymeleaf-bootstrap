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
 * <p>Represents an bootstrap <code>container</code> element
 * <p><strong>Attributes</strong> <br>
 * <strong>fluid     </strong> Fluid attribute: [true] <br>
 * <strong>breakpoint</strong> which is width: 100% until the specified breakpoint: [sm , md , lg , xl , xxl] <br>
 * 
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:container fluid="true" breakpoint="md" &gt;  &lt;tb:/container&gt;
 * 
 */

public class TagBsContainer extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "container";
	private static final String TAG_BOOTSCLASS = "container";
	private static final int    PRECEDENCE = 1000;
				
    public TagBsContainer(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
    
	protected enum PropertyKeys {
	    fluid
	   ,breakpoint;
	 
	   private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}
    
    @Override
	public boolean isAutoEncodeChild() {
		return super.isAutoEncodeChild();
	}    
    @Override
    public String getHtmlTag() {
    	return super.getHtmlTag();
    }
    @Override
    public String getHtmlTagStyleClass() {
    	String _bootclass=TAG_BOOTSCLASS;
    	String _fluid =this.getAttributeValue(PropertyKeys.fluid.toString());
    	String _break =this.getAttributeValue(PropertyKeys.breakpoint.toString());
    	if (_break!=null && !_break.isBlank()) {
    		_fluid="";
    		_bootclass=_bootclass+"-"+_break;
    	}
    	//Fluid masks the breakpoint
        if ("true".equals(_fluid)) {
        	_bootclass=TAG_BOOTSCLASS+"-fluid";
        }
    	return _bootclass;
    }
    
    /*
    @Override
	public void encodeBegin(ITemplateContext context, IModel model,  Map<String,String> props)  {
    	super.encodeBegin(context, model, props);		
	}	
    @Override
    public void encodeChildren(ITemplateContext context, IModel model,  Map<String,String> props)  {
    	super.encodeChildren(context, model, props);
    }
    @Override
    public void encodeEnd(ITemplateContext context, IModel model,  Map<String,String> props)  {
    	super.encodeEnd(context, model,  props);
    }    
    */
    /*
    @Override
    protected void doProcess(ITemplateContext context, IModel model,IElementModelStructureHandler structureHandler) {
    	super.doProcess(context, model, structureHandler);
    	
        IModel baseModelClone = model.cloneModel(); // clone base model
        model.reset();//Reset orignal model
        IOpenElementTag tag= _getBaseTag(baseModelClone);
        
        String _fluid =tag.getAttributeValue("fluid") == null ? "false" : tag.getAttributeValue("fluid");
        String _class="container";
        if ("true".equals(_fluid)) {
        	_class="container-fluid";
        }
	    
	    IModelFactory modelFactory = context.getModelFactory();
	    IModel iModel = modelFactory.createModel();

	    String             outerElement ="div";
	    Map<String,String> outerAttr    = new HashMap<String,String>();
	    outerAttr.put("class", _class);
	    IOpenElementTag    opentag=modelFactory.createOpenElementTag(outerElement, outerAttr, null, false);
	    iModel.add(opentag);
	    iModel=_addChilds(iModel, baseModelClone);	    
	    iModel.add(modelFactory.createCloseElementTag(outerElement));	    	    
	    model.addModel(iModel);	                            
    }
    */
    
    /*
    private IOpenElementTag _getBaseTag(IModel model) {
    	IOpenElementTag ret=null;
    	ITemplateEvent event=model.get(0);
    	if (event instanceof IOpenElementTag) {
    		IOpenElementTag tag= (IOpenElementTag) event;
    		ret=tag;
    	}
    	return ret;
    }
    
    private IModel _addChilds(IModel iModel, IModel baseModelClone) {
	    //Child eventler eklenir
	    if (baseModelClone.size()>1) {
		    for (int i = 0; i < baseModelClone.size(); i++) {
		    	ITemplateEvent event=baseModelClone.get(i);
		    	if (i==0 || i==(baseModelClone.size()-1)) { 
		    		//Kendi eventleri olmali
		    	}
		    	else {
		    		iModel.add(event);
		    	}
		    }	    	
	    }
	    return iModel;
    }
    */

}
