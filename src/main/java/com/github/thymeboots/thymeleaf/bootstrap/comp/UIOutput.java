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

package com.github.thymeboots.thymeleaf.bootstrap.comp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.ICloseElementTag;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IOpenElementTag;
//import org.thymeleaf.model.IStandaloneElementTag;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.ITemplateEvent;
import org.thymeleaf.processor.element.AbstractElementModelProcessor;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * @author Anka
 *
 */
public abstract class UIOutput extends AbstractElementModelProcessor {	
	
	private static long        elementId  = 1;
		

    public UIOutput(
    		String             tagVersion,
            final TemplateMode templateMode, final String dialectPrefix,
            final String elementName, final boolean prefixElementName,
            final String attributeName, final boolean prefixAttributeName,
            final int precedence) {
        super(templateMode, dialectPrefix, elementName, prefixElementName,attributeName, prefixAttributeName, precedence);        
        this.tagVersion= tagVersion;   
        this.tagName   = elementName;
    }
	
	protected enum PropertyKeys {
		id
		,rendered
	    ,style
	    ,styleClass
	    ,tooltip
	    ,tooltipPosition("tooltip-position")
	    ;
	 
        private String toString;
        PropertyKeys(String toString) { this.toString = toString; }
        PropertyKeys() { }
        public String toString() {
            return ((toString != null) ? toString : super.toString());
        }	
	}

	private String             tagVersion=null;
	private String             tagName=null;
	IProcessableElementTag     tag=null;
	private IModel             baseModel = null;
	private Map<String,String> properties= null;
	private IElementModelStructureHandler structureHandler=null;    
	private IModelFactory      modelFactory=null;
	
		    
	@Override
	protected void doProcess(ITemplateContext context, IModel model, IElementModelStructureHandler structureHandler) {
		preProcess(context, model, structureHandler);
		
		this.baseModel       = model.cloneModel(); // clone base model		
		this.tag             = findBaseTag(model);
		this.structureHandler= structureHandler;
		this.modelFactory    = context.getModelFactory();
		this.properties      = properties(model);				
		
		if (isResetModel()) {
			//Reset orignal model befoe begin encode
			model.reset();
		}			
		if ("false".equals(this.getRendered())) {
			model.reset();
		}
		else {
			//Render islemine gecilir
			encodeBegin(context, model,  properties);
			encodeChildren(context, model,  properties);
			encodeEnd(context, model,  properties);			
		}
				        						
	}

	/**
	 * Use this method to add additonal attributes in runtime
	 * @return
	 */
	protected Map<String,String> addattributes() {	
		return new HashMap<String,String>();
	}	
	/**
	 * Use this method to reset or not the model before start the encode model
	 * @return
	 */
	protected boolean isResetModel() {	
		return true;
	}	

	/**
	 * true means is StandAloneTag
	 * @return
	 */
	protected boolean isStandAloneTag() {	
		boolean ret= false;
		if ("area".equals(this.getHtmlTag())  || 			
			"base".equals(this.getHtmlTag())  ||
			"basefont".equals(this.getHtmlTag())  ||
			"br".equals(this.getHtmlTag())  ||
			"col".equals(this.getHtmlTag())  ||
			"frame".equals(this.getHtmlTag())  ||
			"hr".equals(this.getHtmlTag())  ||
			"img".equals(this.getHtmlTag())  ||
			"input".equals(this.getHtmlTag()) ||
			"isindex".equals(this.getHtmlTag()) ||
			"link".equals(this.getHtmlTag()) ||
			"meta".equals(this.getHtmlTag()) ||
			"param".equals(this.getHtmlTag()) 
		) {
			ret=true;
		}		
		return ret;
	}	
	
	protected void preProcess(ITemplateContext context, IModel model, IElementModelStructureHandler structureHandler) {		
	}	

	protected void encodeBegin(ITemplateContext context, IModel model, Map<String,String> attributes )  {		
	    IModelFactory modelFactory = context.getModelFactory();		
	    String             htmlTag = getHtmlTag();	    
	    
	    
	    //create tooltip for the item
	    String tooltip=this.nvl(attributes.get(PropertyKeys.tooltip.toString() ),"").trim();
	    if (!tooltip.isBlank()) {
	    	attributes.put("title", tooltip);
	    	attributes.put("data-toggle", PropertyKeys.tooltip.toString());	 
	    	attributes.remove(PropertyKeys.tooltip.toString());
	    }
	    String tooltipPosition=this.nvl(attributes.get(PropertyKeys.tooltipPosition.toString() ),"").trim();
	    if (!tooltipPosition.isBlank()) {
	    	attributes.put("data-placement", tooltipPosition);
	    }
	    
		if (isStandAloneTag()) {
			boolean synthetic=false;
			boolean minimized=true;
			model.add(modelFactory.createStandaloneElementTag(htmlTag, attributes, null, synthetic, minimized));	    	        							
		}	
		else {
			model.add(modelFactory.createOpenElementTag(htmlTag, attributes, null, false)  );
		}	    
	    		
	}
	
	
    protected void encodeChildren(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
    	_addChilds(model);    	
    }
    
    protected void encodeEnd(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
    	IModelFactory modelFactory = context.getModelFactory();
		if (!isStandAloneTag()) {
			model.add(modelFactory.createCloseElementTag(getHtmlTag()));
		}	    
    		    	        	
    }

    

    /**
     * <p>Return the tag name (element name)</p> 
     * @return
     */
    public String getTagName() {
    	return this.tagName;
    }
    
    /**
     * <p>Return the html tag</p>
     * <p>Contents: The tag will be replaced with this html tag. The default tag is "div" </p> 
     * @return
     */
    public String getHtmlTag() {
    	return "div";
    }
    
    /**
     * <p>Return the html tag style class </p>
     * <p>Contents: CSS style(s) to be applied when this component is rendered. 
     * @return
     */
    public String getHtmlTagStyleClass() {
    	return "";
    }
    
    /**
     * Add childs event to model
     * @param iModel
     * @return
     */
    protected IModel _addChilds(IModel iModel) {
    	if (this.isAutoEncodeChild()) {
        	List<ITemplateEvent> childs=getChilds();
    	    //Child eventler eklenir
    	    if (childs!=null && childs.size()>0) {
    		    for (int i = 0; i < childs.size(); i++) {
    		    	ITemplateEvent event=childs.get(i);
    		    	iModel.add(event);
    		    }	    	
    	    }    		
    	}
    	
	    return iModel;
    }
    
	


	/**
	 * Return ProcessableElementTag from Model
	 * @param model Model is sequences of events
	 * @return ProcessableElementTag
	 */
	protected IProcessableElementTag findBaseTag(IModel model) {
		IProcessableElementTag ret=null;
    	ITemplateEvent event=model.get(0);
    	if (event instanceof IProcessableElementTag) {
    		IProcessableElementTag tag= (IProcessableElementTag) event;
    		ret=tag;
    	}
    	return ret;
    }	
    
    /**
     * <p>Return the childs events (Tags)</p>
     * <p>Contents: Return the child events (tags) on body of the tag </p>
     * @return
     */
    protected List<ITemplateEvent> getChilds() {
    	List<ITemplateEvent> childs= new ArrayList<ITemplateEvent>();
    	
	    if (baseModel.size()>1) {
		    for (int i = 0; i < baseModel.size(); i++) {
		    	ITemplateEvent event=baseModel.get(i);
		    	if (i==0 || i==(baseModel.size()-1)) { 
		    		//Kendi eventleri olmali
		    	}
		    	else {
		    		childs.add(event);
		    	}
		    }	    	
	    }
	    return childs;
    }
	
    
    /**
     * <p>Return the model include facets childs events (Tags)</p>
     * @return
     */
    public Map<String, IModel> createFacetsModel(ITemplateContext context, String tagNameParent) {
    	Map<String, IModel> ret= new HashMap<String, IModel>();
    	IModelFactory modelFactory = context.getModelFactory();
    	IModel model=null;
   
    	int level=0;
	    if (baseModel.size()>1) {
		    for (int i = 0; i < baseModel.size(); i++) {
		    	ITemplateEvent event=baseModel.get(i);
		    	if (!(i==0 || i==(baseModel.size()-1))) {
		    				    		
		    		if (event instanceof IOpenElementTag) {
		        		IOpenElementTag tag= (IOpenElementTag) event;
		        		String elementName=tag.getElementDefinition().getElementName().getElementName();
		        		if ( elementName.equals(tagNameParent) ) {
		        			level++;
		        		}
		        		if (level==0 && elementName.equals("facet")) {
		        			//modeli olustur ve hashmape akta.
		        			String name=tag.getAttributeValue("name");
		        			model  = modelFactory.createModel();   
		        			ret.put(name, model);
		        			
		        		}
		        		else {
			        		if (model!=null) {
			        			model.add(event);
			        		}		        			
		        		}
		        	}
		    		else if (event instanceof ICloseElementTag) {		    			
		    			ICloseElementTag tag= (ICloseElementTag) event;
		        		String elementName=tag.getElementDefinition().getElementName().getElementName();
		        		if ( elementName.equals(tagNameParent) ) {
		        			level--;
		        		}
		        		
		        		if (level==0 && elementName.equals("facet")) {
		        			model  = null;  		        			
		        		}
		        		else {
			        		if (model!=null) {
			        			model.add(event);
			        		}		        			
		        		}		        		
		    		}
		    		else {
		        		if (model!=null) {
		        			model.add(event);
		        		}		        					    			
		    		}
		    	}
		    }	    	
	    }
	    return ret;
    }
	
    /**
     * <p>Return the value of the <code>id</code> property.</p>
     */    
    public String getId() {
    	return this.getAttributeValue(PropertyKeys.id.toString());
    }      
    
    /**
     * <p>Return the value of the <code>rendered</code> property.</p>
     * <p>false:not render true:render
     */
    public java.lang.String getRendered() {
    	String ret= this.nvl( this.getAttributeValue(PropertyKeys.rendered.toString()) , "true" ).trim()  ;
    	if ("false".equals(ret)) {
    		ret="false";
    	}
    	else {
    		ret="true";
    	}
    	return ret;
    }    
    
    /**
     * <p>Return the value of the <code>style</code> property.</p>
     * <p>Contents: CSS style(s) to be applied when this component is rendered.
     */
    public java.lang.String getStyle() {
    	String style= getAttributeValue(PropertyKeys.style.toString());
    	if (style==null) {style="";}
    	return style;
    }    
    
    /**
     * <p>Return the value of the <code>styleClass</code> and <code>class</code> property.</p>
     * <p>Contents: Space-separated list of CSS style class(es) to be applied when
     * this element is rendered.  This value must be passed through
     * as the "class" attribute on generated markup.
     */
    public java.lang.String getStyleClass() {
    	String _class = this.nvl(getAttributeValue("class"),"");
    	String _style = this.nvl(getAttributeValue("style-class" ),"");
    	String _style2= this.nvl(getAttributeValue(PropertyKeys.styleClass.toString()),"");
    	
    	
    	String ret=_class.trim()+" "+_style.trim()+" "+_style2.trim() ;
    	ret.trim();
    	return ret;

    }    
    
    /**
     * <p>Return the value of the <code>tooltip</code> property.</p>
     */
    public java.lang.String getTooltip() {
    	return getAttributeValue(PropertyKeys.tooltip.toString());
    }    
    
    
    public String getAttributeValue(String key) {
    	return tag.getAttributeValue(key);
    }
    public org.thymeleaf.model.IAttribute  getAttribute(String key) {
    	return tag.getAttribute(key);
    }

	/**
	 * Return Tag Version 
	 * @return
	 */
	public String getTagVersion() {
		return tagVersion;
	}
	
	/**
	 * Return structureHandler
	 * @return
	 */
	protected IElementModelStructureHandler getStructureHandler() {
		return structureHandler;
	}
	
	/**
	 * Return model factory
	 * @return
	 */
	public IModelFactory getModelFactory() {
		return modelFactory;
	}

	public void setModelFactory(IModelFactory modelFactory) {
		this.modelFactory = modelFactory;
	}

	/**
	 * Return Processable Element Tag
	 * @return Cuurent Tag
	 */
	protected IProcessableElementTag getTag() {
		return tag;
	}

	/**
	 * Automatic genarate child at the encodeEnd()
	 * @return true:genarate childs at encodeEnd()
	 */	
	public boolean isAutoEncodeChild() {
		return true;
	}
	
	/**
	 * decide whether print attribute or not 
	 * @param propname
	 * @return
	 */
	protected boolean printProp(String propname) {
		boolean ret=true;
		if (propname!=null) {
			//the attributes whiched processed and then will be deleted
			if (
				propname.equals("header")    || propname.equals("look")          || propname.equals("severity") ||
				propname.equals("footer")    ||
				propname.equals("card-title")|| propname.equals("card-text")     || 
				propname.equals("size")      || propname.equals("active-index")  ||
				propname.equals("layout")    || propname.equals("closable")      ||
				propname.equals("xs")        ||
				propname.equals("sm")        || propname.equals("md")            || propname.equals("lg")       ||
				propname.equals("xl")        || propname.equals("xxl")           || propname.equals("span")     ||
				propname.equals("wd")        ||
				propname.equals("prepend")   || propname.equals("append")        || propname.equals("target")   ||
				propname.equals("styleClass")|| propname.equals("style-class")   || propname.equals("header-class") ||
				propname.equals("backdrop")  || propname.equals("centered")      || propname.equals("content-class") ||
				propname.equals("draggable") || propname.equals("resizable")     || propname.equals("scrollable")  ||
				propname.equals("icon")      || propname.equals("icon-align")    || propname.equals("icon-spin")   ||
				propname.equals("spin")      || propname.equals("fluid")  				

			) {
				ret=false;
			}
			if (propname.equals("value") && isUInputTag()==false) {
				ret=false;
			}
		}
		return ret;
	}
	/**
	 * input control elementi mi?
	 * @return
	 */
	protected boolean isUInputTag() {
		boolean ret=false;
		if (
			"input".equals(this.getHtmlTag()) ||
			"textarea".equals(this.getHtmlTag())
		) {
			ret=true;
		}
		return ret;
	}
	private Map<String,String> properties(IModel model) {
		Map<String,String> properties    = new HashMap<String,String>();
		
		Map<String,String> attr=findBaseTag(model).getAttributeMap();	
		for (Map.Entry<String, String> pair : attr.entrySet()) {
			if (printProp(pair.getKey())) {
				properties.put( pair.getKey(), pair.getValue());
			}		    
		}
		 
		 //Add style to props
		 String style=this.getStyle();
		 if (style!=null && !style.isBlank()) {
			 properties.put(PropertyKeys.style.toString(), style);
		 }
		 //Add styleClass and Html Tag styleClass to props
		 String styleClass= this.getStyleClass();
		 String bootclass = getHtmlTagStyleClass();		 
		 if(styleClass==null) { styleClass=""; }
		 if(bootclass==null ) { bootclass=""; }
		 styleClass=bootclass+" "+styleClass;
		 styleClass=styleClass.trim();
		 if (!styleClass.isBlank()) {
			 properties.put("class", styleClass );
		 }
		 
		 //Add addtional attributes
		 Map<String,String> addattr=addattributes();
		 if (addattr!=null && addattr.size()>0) {
			 for (Map.Entry<String, String> pair : addattr.entrySet()) {
					if (printProp(pair.getKey())) {
						properties.put( pair.getKey(), pair.getValue());
					}		    
				}			 
		 }
		 
		 return properties;
	}

    /**nvl
     * @param p value
     * @param nvl null value
     * @return (p==null)?nvl:p;
     */
    public String nvl(String p, String pnvl) {
    	String ret= p;
    	if (ret==null) {
    		ret=pnvl;
    	}    		
    	return ret;
    }
    
    
	
    public IModel createModelText(ITemplateContext context, String value) {	  
    	IModelFactory modelFactory=context.getModelFactory();
    	IModel model = modelFactory.createModel();
        model.add(modelFactory.createText( value) );
    	return model;    	
    }


    /**
     * Create an html model
     * @param context Template context
     * @param htmlTag Html tag ( div, span stron vs..)
     * @param text Text value (<div>value</div>)
     * @param props Attribute list
     * @param childs Childs models
     * @return Return a model. Example: <div prop1="pval" prop2="pval">value</div>
     */
    public IModel createModel(    		
			ITemplateContext context,
			String htmlTag,
			String text, 
			Map<String,String> props, 
			List<ITemplateEvent> childs
			) {	  
    	IModelFactory modelFactory=context.getModelFactory();
    	IModel model = modelFactory.createModel();
        model.add(modelFactory.createOpenElementTag(htmlTag, props, null, false));
        if (text!=null) {
        	model.add(modelFactory.createText( text) );
        }        
        if (childs!=null && childs.size()>0) {
    	    for (int i = 0; i < childs.size(); i++) {
    	    	ITemplateEvent event=childs.get(i);
    	    	model.add(event);
    	    }	    	
        }
        
        model.add(modelFactory.createCloseElementTag(htmlTag));
        
    	return model;
    	
    }

	
    public  IModel createModel(    		
			ITemplateContext context,
			String htmlTag,
			String text, 
			String clazz, 
			List<ITemplateEvent> childs
			) {	    	
		Map<String,String> props    = new HashMap<String,String>();	
		if (clazz==null) {clazz="";}
		if (!clazz.isBlank()) {
			props.put("class", clazz);
		}
		return createModel(context,htmlTag,text, props, childs);	
	}    
	
    public  IModel createModel(    		
			ITemplateContext context,
			String htmlTag,
			String text, 
			String clazz
			) {	    	
		List<ITemplateEvent> childs=null;
    	Map<String,String> props    = new HashMap<String,String>();	
		if (clazz==null) {clazz="";}
		if (!clazz.isBlank()) {
			props.put("class", clazz);
		}		
		return createModel(context,htmlTag,text, props, childs);	
	}    
    
    public  IModel createModel(    		
			ITemplateContext context,
			String htmlTag,
			String value,  
			List<ITemplateEvent> childs
			) {	    	
		Map<String,String> props    = new HashMap<String,String>();	
		return createModel(context,htmlTag,value, props, childs);	
	}    
    
    public  IModel createModel(    		
			ITemplateContext context,
			String htmlTag,
			String value			
			) {	    	
		Map<String,String> props    = new HashMap<String,String>();
		List<ITemplateEvent> childs = null;
		return createModel(context,htmlTag,value, props, childs);	
	}    
    public  IModel createModel(    		
			ITemplateContext context,
			String htmlTag						
			) {	    	
    	String value=null;
		Map<String,String> props    = new HashMap<String,String>();
		List<ITemplateEvent> childs = null;
		return createModel(context,htmlTag,value, props, childs);	
	}    
    
    /**
     * Every Tag has a creator
     * @param context
     * @param attributes
     * @return
     */
    protected  IModel createTag(ITemplateContext context, String tagStyleClass, HashMap<String,String> attributes, List<ITemplateEvent> childs) {
    	String text=null;    	
    	if (attributes==null) { new HashMap<String,String>(); }

    	tagStyleClass=this.nvl(tagStyleClass,"").trim();
    	if (!tagStyleClass.isBlank()) {
    		String _class=this.nvl(attributes.get("class"),"");
    		_class=(tagStyleClass+" "+_class).trim();
    		attributes.put(tagStyleClass, _class);    		
    	}    	    	
    	IModel model=createModel(context,this.getHtmlTag(),text,attributes,childs);
    	return model;
    }
  

	/**
	 * Return all attributes of tag
	 * @param tag Tag
	 * @return
	 */
	protected Map<String,String> getProperties(IProcessableElementTag tag) {
		Map<String,String> properties    = new HashMap<String,String>();				
		Map<String,String> attr=tag.getAttributeMap();	
		for (Map.Entry<String, String> pair : attr.entrySet()) {
			properties.put( pair.getKey(), pair.getValue());
		}
		return properties;
	}
    
    /**
     * Clone the tag and add additonal attributex
     * @param context
     * @param tag Original tag
     * @param addprops Addtional attribute
     * @return Clone tag
     */
    public IProcessableElementTag cloneTag(ITemplateContext context, IProcessableElementTag tag, Map<String,String> addprops) {
    	IModelFactory modelFactory = context.getModelFactory();
    	
    	//Orjinal attribute alinir
    	Map<String,String> attributes=getProperties(tag);
    	//Eklenecek atributeslar dahil edilir.
    	if (addprops!=null) {
    		for (Map.Entry<String, String> pair : addprops.entrySet()) {
    			attributes.put( pair.getKey(), pair.getValue());
    		}    	
    	}    	
    	String elementname=tag.getElementCompleteName();
    	IProcessableElementTag ret= modelFactory.createOpenElementTag(elementname, attributes, null, false);
    	return ret;
    }
	
    /**
     * return the value is numeric or not
     * @param value true:numberic
     * @return
     */
    protected boolean isNumber(String value) {
    	boolean ret=false;
    	value=this.nvl(value, "").trim();
    	if (!value.isBlank()) {
    		Integer intval=null;
    		try {
    			intval=Integer.valueOf(value);
    		}
    		catch (Throwable  e) {    			
    		}
    		finally {  }
    		if (intval!=null) {
    			ret=true;
    		}    		
    	}
    	return ret;
    }
    
	private synchronized static long getElementId() {
		if (elementId==Long.MAX_VALUE ) {
			elementId=0;
		}
		elementId=elementId+1;
		
		return elementId;
	}	
	
    /**
     * Generate random id for html component
     * @return id value
     */
    protected String randomId() {    	
    	long id=getElementId();
    	return "tb_idt"+id;
    }
    
    
}
