package com.esocial.foursquare.obj;

import com.esocial.foursquare.util.Text;
import com.esocial.foursquare.util.Utils;

import fi.foyt.foursquare.api.entities.Category;
import fi.foyt.foursquare.api.entities.CompactVenue;


public class FsVenue {
	
	public FsVenue(String pk_ubicacion, String fk_pais, CompactVenue venue){
		this.id = venue.getId();
		this.name = venue.getName();
		this.fk_pais = fk_pais;
		this.estado = 1;
		this.token = Utils.getTokenFromString(pk_ubicacion + venue.getId());
		
		if(venue.getCategories()!=null && venue.getCategories().length>0){
			for(Category cat : venue.getCategories()){
				if(cat.getPrimary()){
					String parent = Resources.categoriesHierarchy.get(cat.getId());
					if(Text.isEmpty(parent)){
						this.fk_category = cat.getId();
					}else{
						String aux = "";
						do{
							aux = Resources.categoriesHierarchy.get(parent);
							if(!Text.isEmpty(aux))
								parent = aux;
						}while(!Text.isEmpty(aux));
						this.fk_category = parent;
					}
					
					break;
				}
			}
		}else{
			this.fk_category = null;
		}
		if(venue.getContact()!=null){
			this.phone = venue.getContact().getPhone();
		}
		if(venue.getLocation()!=null){
			this.distance = venue.getLocation().getDistance();
			this.lat = venue.getLocation().getLat();
			this.lon = venue.getLocation().getLng();
		}
		if(venue.getStats()!=null){
			this.checkinscount = venue.getStats().getCheckinsCount();
			this.userscount = venue.getStats().getUsersCount();
			this.tipcount = venue.getStats().getTipCount();
		}
	}
	
	public String id;
    public String fk_pais;
    public String fk_category;
    public String name;
    public String phone;
    public Double lat;
    public Double lon;
    public Double distance;
    public Integer checkinscount;
    public Integer userscount;
    public Integer tipcount;
    public Integer estado;
    public String created_at;
    public String updated_at;
    public String token;
	
}
