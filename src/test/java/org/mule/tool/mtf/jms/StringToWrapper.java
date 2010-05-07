package org.mule.tool.mtf.jms;

import org.apache.log4j.Logger;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

public class StringToWrapper extends AbstractTransformer{

	private static Logger log = Logger.getLogger(StringToWrapper.class);

	public StringToWrapper(){
		// TODO: Let Mule enforce only accepting a String for this transformer
		registerSourceType(String.class);
	}
	
	protected Object doTransform(Object src, String encoding)
			throws TransformerException {
		// TODO:  Cast the src to a String, the variable should be named 'message'
		String message = (String) src;
		log.info("Transforming: "+message);
		// TODO: return a new Instance of StringWrapper using message as the constructor argument
		return new StringWrapper(message);
	}
}

