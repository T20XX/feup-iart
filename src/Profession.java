public enum Profession {
	M�dico(Area.Sa�de),
	Farmac�utico(Area.Sa�de),
	Nutricionista(Area.Sa�de),
	Psic�logo(Area.Sa�de),
	Fisioterapeuta(Area.Sa�de),
	Enfermeio(Area.Sa�de),
	Inform�tico(Area.Engenharia),
	Qu�mico(Area.Engenharia),
	Industrial(Area.Engenharia),
	Aeron�utica(Area.Engenharia),
	Civil(Area.Engenharia),
	Guia(Area.Turismo),
	Agente(Area.Turismo),
	Bartender(Area.Turismo),
	Recepcionista(Area.Turismo),
	Matem�tico(Area.Educa��o),
	Bi�logo(Area.Educa��o),
	F�sico(Area.Educa��o),
	Pedag�gico(Area.Educa��o),
	Ge�grafo(Area.Educa��o),
	RelP�blicas(Area.Comunica��o),
	Publicidade(Area.Comunica��o),
	Jornalismo(Area.Comunica��o),
	LocR�dio(Area.Comunica��o),
	Cinema(Area.Arte),
	Moda(Area.Arte),
	Dan�a(Area.Arte),
	Teatro(Area.Arte),
	M�sica(Area.Arte),
	Fotografia(Area.Arte);
	
	
	private final Area area;
	
	Profession(Area area){
		this.area=area;
	}

	private Area getArea(){
		return area;
	}
	
}
