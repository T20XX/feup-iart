package entities;

public class Group {

	private static int nextID = 1;
	private int id;
	private Person[] members;
	
	public Group(Person[] members){
		this.id = nextID;
		this.members = members;
		for(Person person : this.members){
			person.setGroup(this);
		}
		nextID++;
	}
	
	public Person[] getMembers(){
		return this.members;
	}
	
	public int getSize(){
		return this.members.length;
	}
}
