public enum Profession {
	Médico(Area.Saúde),
	Farmacêutico(Area.Saúde),
	Nutricionista(Area.Saúde),
	Psicólogo(Area.Saúde),
	Fisioterapeuta(Area.Saúde),
	Enfermeio(Area.Saúde),
	Informático(Area.Engenharia),
	Químico(Area.Engenharia),
	Industrial(Area.Engenharia),
	Aeronáutica(Area.Engenharia),
	Civil(Area.Engenharia),
	Guia(Area.Turismo),
	Agente(Area.Turismo),
	Bartender(Area.Turismo),
	Recepcionista(Area.Turismo),
	Matemático(Area.Educação),
	Biólogo(Area.Educação),
	Físico(Area.Educação),
	Pedagógico(Area.Educação),
	Geógrafo(Area.Educação),
	RelPúblicas(Area.Comunicação),
	Publicidade(Area.Comunicação),
	Jornalismo(Area.Comunicação),
	LocRádio(Area.Comunicação),
	Cinema(Area.Arte),
	Moda(Area.Arte),
	Dança(Area.Arte),
	Teatro(Area.Arte),
	Música(Area.Arte),
	Fotografia(Area.Arte);
	
	
	private final Area area;
	
	Profession(Area area){
		this.area=area;
	}

	private Area getArea(){
		return area;
	}
	
}
