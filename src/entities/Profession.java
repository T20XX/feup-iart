package entities;
public enum Profession {
	Medico(Area.Saude),
	Farmaceutico(Area.Saude),
	Nutricionista(Area.Saude),
	Psicologo(Area.Saude),
	Fisioterapeuta(Area.Saude),
	Enfermeio(Area.Saude),
	Informitico(Area.Engenharia),
	Quimico(Area.Engenharia),
	Industrial(Area.Engenharia),
	Aeronautica(Area.Engenharia),
	Civil(Area.Engenharia),
	Guia(Area.Turismo),
	Agente(Area.Turismo),
	Bartender(Area.Turismo),
	Recepcionista(Area.Turismo),
	Matematico(Area.Educacao),
	Biologo(Area.Educacao),
	Fisico(Area.Educacao),
	Pedagogico(Area.Educacao),
	Geografo(Area.Educacao),
	RelPublicas(Area.Comunicacao),
	Publicidade(Area.Comunicacao),
	Jornalismo(Area.Comunicacao),
	LocRadio(Area.Comunicacao),
	Cinema(Area.Arte),
	Moda(Area.Arte),
	Danca(Area.Arte),
	Teatro(Area.Arte),
	Musica(Area.Arte),
	Fotografia(Area.Arte);
	
	
	private final Area area;
	
	Profession(Area area){
		this.area=area;
	}

	private Area getArea(){
		return area;
	}
	
}
