package org.mileto.util;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;

public class MyBean  {


	//private DynaBean myDynaBean;

	public static DynaBean instantiate ( String className, Object[][] properties) {
		try {

			int element = 0;
			DynaProperty[] arrayProperties = new DynaProperty[properties.length];
			for ( Object[] property: properties) {
				arrayProperties[element] = new DynaProperty( property[0].toString(), property[1].getClass());
				element++;			
			}
			DynaClass dynaClass = new BasicDynaClass(className, null, arrayProperties);
			return dynaClass.newInstance();
		} catch (IllegalAccessException | InstantiationException ex) {
			System.err.println(ex.getMessage());
		}
		return null;
	}

	public static JsonObject makeJson ( BasicDynaBean bean ) {

		JsonObjectBuilder j = Json.createObjectBuilder();
		DynaProperty[] d = bean.getDynaClass().getDynaProperties();
		for ( DynaProperty property: d) {
			if ( bean.get( property.getName() ) instanceof String ) {
				j.add( property.getName(), bean.get( property.getName() ).toString() );
			} else if ( bean.get( property.getName() ) instanceof Double ) {
				j.add( property.getName(), (Double) ( bean.get( property.getName() ) ) );
			}
		}
		return j.build();

	}


}
