package com.app.android.qxd0102.pluscinematv;

public class File {
	private String nome;
	private String notaMedia;
	private String ano;
	private String genero;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNotaMedia() {
		return notaMedia;
	}
	public void setNotaMedia(String notaMedia) {
		this.notaMedia = notaMedia;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String toString(){
		return nome + " - " +notaMedia +" - "+ ano+" - "+genero;
	}
}
