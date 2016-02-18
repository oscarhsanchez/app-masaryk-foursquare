package com.esocial.foursquare.obj;

import com.esocial.foursquare.util.Utils;

import fi.foyt.foursquare.api.entities.Category;

public class FsCategory {

	public FsCategory(Category cat){
		this.id = cat.getId();
		this.fk_pais = Resources.session.fk_pais;
		this.name = cat.getName();
		this.estado = 1;
		this.token = Utils.getTokenFromString(cat.getId());
	}
	
	public String id;
    public String fk_pais;
    public String name;
    public Integer estado;
    public String created_at;
    public String updated_at;
    public String token;	
}
