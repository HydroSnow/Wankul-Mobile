package dev.hydrosnow.wankul.viewmodel;

import org.json.JSONException;
import org.json.JSONObject;

public class Fromage {
	private final int id;
	private String nom;
	private String origine;
	private int lait;
	private int type;
	private String img;
	private double prix;
	
	public Fromage(final int id) {
		this.id = id;
	}
	
	Fromage(final JSONObject obj) throws JSONException {
		id = obj.getInt("id");
		nom = obj.getString("nom");
		origine = obj.getString("origine");
		lait = obj.getInt("lait");
		type = obj.getInt("type");
		img = obj.getString("img");
		prix = obj.getDouble("prix");
	}
	
	@Override
	public String toString() {
		return "#" + id + "(" + nom + ")";
	}
	
	public int getId() {
		return id;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(final String nom) {
		this.nom = nom;
	}
	
	public String getOrigine() {
		return origine;
	}
	
	public void setOrigine(final String origine) {
		this.origine = origine;
	}
	
	public int getLait() {
		return lait;
	}
	
	public void setLait(final int lait) {
		this.lait = lait;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(final int type) {
		this.type = type;
	}
	
	public String getImg() {
		return img;
	}
	
	public void setImg(final String img) {
		this.img = img;
	}
	
	public double getPrix() {
		return prix;
	}
	
	public void setPrix(final double prix) {
		this.prix = prix;
	}
}
