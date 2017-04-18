package entities;

public class Group {
	
	private Person[] members;
	
	public Group(Person[] members){
		this.members = members;
		for(Person person : this.members){
			person.setGroup(this);
		}
	}
	
	public Person[] getMembers(){
		return this.members;
	}
	
	public int getSize(){
		return this.members.length;
	}
}
