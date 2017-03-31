package com.mileto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.mileto.domain.AdmGrupo;

@FacesConverter(value="grupoConverter", forClass=AdmGrupo.class)
public class GrupoConverter  implements Converter {

    
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
        
    	AdmGrupo grupo = null;
        try {
            grupo = AdmGrupo.getGrupo(Integer.parseInt(newValue));
        }catch(Throwable ex) {
            throw new ConverterException();
        }
        return grupo;
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String val = null;
        try {
            AdmGrupo grupo = (AdmGrupo) value;
            val = Integer.toString(grupo.getId());
        }catch(Throwable ex) {            
            throw new ConverterException();
        }

        return val;
    }
}
